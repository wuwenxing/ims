package com.gwghk.ims.activity.manager.issue.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.activity.dao.entity.ActConditionSettingWrapper;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActRelatedCustomer;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActTradeRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ActTradeRecordDao;
import com.gwghk.ims.activity.manager.ActConditionSettingManager;
import com.gwghk.ims.activity.manager.ActCustomerInfoManager;
import com.gwghk.ims.activity.manager.ActRelatedCustomerManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.issue.AbstractIssueItems;
import com.gwghk.ims.activity.manager.prize.ActBonusManager;
import com.gwghk.ims.common.annotation.IssueItemCategory;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.CurrencyEnum;
import com.gwghk.ims.common.enums.IssueItemCategoryEnum;
import com.gwghk.ims.common.inf.mis.base.MisKeyValDubboService;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.ims.common.vo.base.KeyValVO;

/**
 * 赠金操作接口
 * @author jackson.tang
 *
 */
@Component
@IssueItemCategory({IssueItemCategoryEnum.WITHGOLD})
public class WithGoldApiManager extends AbstractIssueItems{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	@Autowired
	private ActSettingManager actSettingManager;
	@Autowired
	private ActRelatedCustomerManager actRelatedCustomerManager;
	@Autowired
	private ActBonusManager actBonusManager;
	@Autowired
	private MisKeyValDubboService keyValDubboService;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager;
	@Autowired
	private ActConditionSettingManager actConditionManager;
	@Autowired
	private ActTradeRecordDao actTradeRecordDao;
	
	/**
	 * 根据发放记录编号添加赠金 
	 * @param prizeNo
	 * @param actNo
	 * @param env
	 * @return
	 */
	public boolean addBonus(String prizeNo,String actNo) {
		return addOrReleaseBonus(prizeNo, actNo, "add");
	}
	
	/**
	 * 根据发放记录添加赠金 
	 * @param vActPrizeRecord
	 * @param actNo
	 * @param env
	 * @return
	 */
	public boolean addBonus(VActPrizeRecord vActPrizeRecord,String actNo) {
		return _addOrReleaseBonus(vActPrizeRecord, "add");
	}
	
	/**
	 * 释放指定的发放记录金额
	 * @param prizeNo
	 * @param actNo
	 * @param env
	 * @return
	 */
	public boolean releaseBonus(String prizeNo,String actNo) {
		return addOrReleaseBonus(prizeNo, actNo, "release");
	}
	
	/**
	 * 释放指定的发放记录金额
	 * @param vActPrizeRecord
	 * @param actNo
	 * @param env
	 * @return
	 */
	public boolean releaseBonus(VActPrizeRecord vActPrizeRecord,String actNo) {
		return _addOrReleaseBonus(vActPrizeRecord, "release");
	}
	
	/**
	 * 取消指定活动的发放记录,如果存在待释放的交易记录那么其中特定的发放记录将停止执行取消操作
	 * @param account 真实账号
	 * @param actNo
	 * @param reason
	 * @return
	 */
	public int cancelBonus(String account,String actNo,Date settleDate,Integer settleType,String platform,Long companyId) {
		//查找所有的发放记录 
		int noSettleCount=0;//未退回计数
		List<VActPrizeRecord> list=imsPrizeRecordManager.findVByAccount(account, ActItemTypeEnum.WITHGOLD.getValue(),actNo);
		if(list==null || list.size() == 0)
			return noSettleCount;
		
		ActSetting actSetting=actSettingManager.findByactivityPeriods(actNo);
		if(actSetting==null)
			return noSettleCount;
		
		List<VActPrizeRecord> readySettleList=new ArrayList<>();
		for(VActPrizeRecord record:list) {
			//过滤 已经完成的
			if(record.getFinishedTradeLots().compareTo(record.getNeedTradeLots())==0)
				continue;
			
			//如果是禁止释放或者还没有设置统计手数时间则立即清算
			if(record.getReleaseType()==3 || record.getWithdrawalStartTime()==null) {
				readySettleList.add(record);
				continue;
			}
			
			// 判断是否交易数据已经释放完毕
			Map<String, Object> params = new HashMap<String, Object>();
			ActConditionSettingWrapper actConditionCustomInfoWrapper = actConditionManager.findCustCondtionSettingWrap(record.getActNo());
			List<String> realProducLimit = actConditionCustomInfoWrapper.getRealProducts();
			
			Map<String, List<Map<String, Date>>> periodsExcludeDate = actBonusManager.loadActivityMaintainData();
			List<Map<String, Date>> excludeDate = periodsExcludeDate.get(record.getActNo());
			if (realProducLimit != null && !realProducLimit.isEmpty()) {
				params.put("product", realProducLimit);
			}
			if (excludeDate != null && !excludeDate.isEmpty()) {
				params.put("excludeDate", excludeDate);
			}
			params.put("tradeStartTime", record.getWithdrawalStartTime());
			params.put("tradeEndTime", settleDate);
			params.put("inTradeStartTime", record.getWithdrawalStartTime());
			params.put("inTradeEndTime", settleDate);
			if(record.getCumulativeTradeId()!=null){
				params.put("tradeId", record.getCumulativeTradeId());
			}
			if (record.getWithdrawalStartTradeId() != null
					&& !ActEnvEnum.DEMO.getValue().equals(record.getEnv())) {
				params.put("receiveTradeId", record.getWithdrawalStartTradeId());
			}
			String accountNo = null;
			
			accountNo = record.getAccountNo();
			
			params.put("accountNo", accountNo);
			params.put("platform", record.getPlatform());
			params.put("env", ActEnvEnum.REAL.getValue());

			List<ActTradeRecord> tradeList = actTradeRecordDao.getTradeRecordList(params);
			if (tradeList != null && !tradeList.isEmpty()) {
				noSettleCount+=1;
				logger.warn("发放记录{}存在待释放数据，暂时停止该记录结算",record.getRecordNo());
				continue;
			}
			
			readySettleList.add(record);
		}
		
		String cancelReason=ActSettleTypeEnum.findByCode(settleType).getName()+",扣回未完成任务贈金";
		
		//取消赠金
		int cancelFailCount=actBonusManager.cancelBonus(readySettleList, cancelReason,platform, CompanyEnum.getCodeById(companyId));
		if(cancelFailCount>0) {
			logger.info(">>>本次执行赠金释放失败次数{},入参数据:{},进一步查找原因见表act_third_call_record",cancelFailCount,JSON.toJSONString(readySettleList));
			noSettleCount=noSettleCount+cancelFailCount;
		}
		
		return noSettleCount;
	}
	
	private boolean addOrReleaseBonus(String prizeNo,String actNo,String act) {
		VActPrizeRecord vActPrizeRecord=imsPrizeRecordManager.findVByRecordNo(prizeNo,actNo);
		if(vActPrizeRecord==null) {
			logger.error("发放记录【编号"+prizeNo+"】不存在");
			return false;
		}
		
		return _addOrReleaseBonus(vActPrizeRecord, act);
		
	}
	
	private boolean _addOrReleaseBonus(VActPrizeRecord vActPrizeRecord,String act) {
		ActSetting actSetting=actSettingManager.findByactivityPeriods(vActPrizeRecord.getActNo());
		if(actSetting==null) {
			logger.error("活动【编号:"+vActPrizeRecord.getActNo()+"】不存在");
			return false;
		}
		
		String companyCode=CompanyEnum.getCodeById(vActPrizeRecord.getCompanyId());
		
		//找出用户关联账号
		ActRelatedCustomer relatedCustomerInfo=actRelatedCustomerManager.findByCustomerNumber(vActPrizeRecord.getAccountNo(),vActPrizeRecord.getEnv());
		
		//只有拥有真实账号才可以发送赠金 代币
		String realAccount=null;
		String demoAccount=null;
		if(ActEnvEnum.REAL.getValue().equals(vActPrizeRecord.getEnv()))
			realAccount=vActPrizeRecord.getAccountNo();
		else if(ActEnvEnum.DEMO.getValue().equals(vActPrizeRecord.getEnv()) && relatedCustomerInfo!=null && relatedCustomerInfo.getCustomerNumber()==null) {
			realAccount=relatedCustomerInfo.getCustomerNumber();
			demoAccount=vActPrizeRecord.getAccountNo();
		}
			
		if(realAccount==null) {
			logger.error("活动【编号:"+vActPrizeRecord.getActNo()+"】不存在");
			return false;
		}
		
		vActPrizeRecord.setTransfer(actSetting.getTransfer());//是否允许自动取款
		vActPrizeRecord.setTurnGroup(actSetting.getTurnGroup());

		//是否自动发放
		KeyValVO tmpData=keyValDubboService.findByKey("bonus_flag_" + companyCode,vActPrizeRecord.getCompanyId());
		String bonusFlag = tmpData.getDataVal();
		
		//获取汇率
		tmpData=keyValDubboService.findByKey("exchange_rate_usd_cny_" + companyCode,vActPrizeRecord.getCompanyId());
		String exchangeVal = tmpData.getDataVal();
		
		//设置汇率
		ActCustomerInfo customerInfo=actCustomerInfoManager.findByAccountNo(vActPrizeRecord.getAccountNo(), vActPrizeRecord.getPlatform());
		if(ActEnvEnum.DEMO.getValue().equals(vActPrizeRecord.getEnv()) && CurrencyEnum.CNY.getValue().equals(customerInfo.getAccountCurrency())) {
			BigDecimal exchangeRate = new BigDecimal(exchangeVal);
			vActPrizeRecord.setExchangeRate(exchangeRate);
		}
		
		if("release".equals(act)) {
			Map<String, List<Map<String, Date>>> periodsExcludeDate = actBonusManager.loadActivityMaintainData();
			return actBonusManager.releaseWithGoldBonus(vActPrizeRecord, periodsExcludeDate, demoAccount);
		}
		else if("add".equals(act)) {
			return actBonusManager.addWithGoldBonus(vActPrizeRecord, bonusFlag);
		}
		
		return false;
	}

	@Override
	public Boolean send(String record, String itemType, String actNo, String accountNo, Long companyId) {
		return addBonus(record, actNo);
	}

	@Override
	public Boolean isFinishDeduct(ImsPrizeRecordVO porizeRecord, String accountNo, Long companyId) {
		return false;
	}
}
