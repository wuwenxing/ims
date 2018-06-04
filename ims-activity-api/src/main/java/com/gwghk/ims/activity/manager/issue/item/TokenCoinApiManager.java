package com.gwghk.ims.activity.manager.issue.item;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActRelatedCustomer;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
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
 * 模拟币接口
 * @author jackson.tang
 *
 */
@Component
@IssueItemCategory({IssueItemCategoryEnum.TOKENCOIN})
public class TokenCoinApiManager extends AbstractIssueItems{
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
	
	/**
	 * 添加代币
	 * @param vActPrizeRecord
	 * @param env
	 * @return
	 */
	public boolean addBonus(VActPrizeRecord vActPrizeRecord) {
		return _addBonus(vActPrizeRecord, vActPrizeRecord.getEnv());
	}
	
	/**
	 * 添加代币
	 * @param prizeNo
	 * @param actNo
	 * @param env
	 * @return
	 */
	public boolean addBonus(String prizeNo,String actNo) {
		VActPrizeRecord vActPrizeRecord=imsPrizeRecordManager.findVByRecordNo(prizeNo,actNo);
		if(vActPrizeRecord==null || !ActItemTypeEnum.TOKENCOIN.getValue().equals(vActPrizeRecord.getItemType())) {
			logger.error("发放记录【编号"+prizeNo+"】不存在 或者 并不是代币类型");
			return false;
		}
		
		return _addBonus(vActPrizeRecord, vActPrizeRecord.getEnv());
	}
	
	/**
	 * 取消指定活动的发放记录
	 * @param account 真实账号
	 * @param actNo
	 * @param reason
	 * @return
	 */
	public boolean cancelBonus(String account,String actNo,Integer settleType,String platform,Long companyId) {
		//查找所有的发放记录
		List<VActPrizeRecord> list=imsPrizeRecordManager.findVByAccount(account,ActItemTypeEnum.TOKENCOIN.getValue(),actNo);
		if(list==null)
			return true;
		
		String cancelReason=ActSettleTypeEnum.findByCode(settleType).getName()+",扣回未完成任务贈金";
		
		//取消赠金
		actBonusManager.cancelBonus(list, cancelReason,platform, CompanyEnum.getCodeById(companyId));
		
		return true;
	}
	
	private boolean _addBonus(VActPrizeRecord vActPrizeRecord,String env) {
		ActSetting actSetting=actSettingManager.findByactivityPeriods(vActPrizeRecord.getActNo());
		if(actSetting==null) {
			logger.error("活动【编号:"+vActPrizeRecord.getActNo()+"】不存在");
			return false;
		}
		
		String companyCode=CompanyEnum.getCodeById(vActPrizeRecord.getCompanyId());
		
		//找出用户关联账号
		ActRelatedCustomer relatedCustomerInfo=actRelatedCustomerManager.findByCustomerNumber(vActPrizeRecord.getAccountNo(),env);
		
		//只有拥有真实账号才可以发送赠金 代币
		String realAccount=null;
		if(ActEnvEnum.REAL.getValue().equals(env))
			realAccount=vActPrizeRecord.getAccountNo();
		else if(ActEnvEnum.DEMO.getValue().equals(env) && relatedCustomerInfo!=null && relatedCustomerInfo.getCustomerNumber()==null) {
			realAccount=relatedCustomerInfo.getCustomerNumber();
		}
			
		if(realAccount!=null) {
			logger.error("活动【编号:"+vActPrizeRecord.getActNo()+"】不存在");
			return false;
		}
		
		vActPrizeRecord.setTransfer(actSetting.getTransfer());//是否允许自动取款
		vActPrizeRecord.setTurnGroup(actSetting.getTurnGroup());
		
		//是否自动发放
		KeyValVO tmpData=keyValDubboService.findByKey("bonus_flag_" + companyCode, vActPrizeRecord.getCompanyId());
		String bonusFlag = tmpData.getDataVal();//
		
		//获取汇率
		tmpData=keyValDubboService.findByKey("exchange_rate_usd_cny_" + companyCode,vActPrizeRecord.getCompanyId());
		String exchangeVal = tmpData.getDataVal();//
		
		//设置汇率
		ActCustomerInfo customerInfo=actCustomerInfoManager.findByAccountNo(vActPrizeRecord.getAccountNo(), ActEnvEnum.REAL.getValue());
		if(ActEnvEnum.DEMO.getValue().equals(vActPrizeRecord.getEnv()) && CurrencyEnum.CNY.getValue().equals(customerInfo.getAccountCurrency())) {
			BigDecimal exchangeRate = new BigDecimal(exchangeVal);
			vActPrizeRecord.setExchangeRate(exchangeRate);
		}
		
		return actBonusManager.addWithGoldBonus(vActPrizeRecord, bonusFlag);
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
