package com.gwghk.ims.activity.manager.prize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActStringCode;
import com.gwghk.ims.activity.dao.entity.ImsOrder;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecordWaiting;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ActItemsSettingDao;
import com.gwghk.ims.activity.dao.inf.ActSettingDao;
import com.gwghk.ims.activity.dao.inf.ActStringCodeDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordWaitingDao;
import com.gwghk.ims.activity.dao.inf.MisOrderDao;
import com.gwghk.ims.activity.dao.inf.VActPrizeRecordDao;
import com.gwghk.ims.activity.manager.ActDemoCashAdjustManager;
import com.gwghk.ims.activity.manager.ActItemsSettingManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.redis.ActPrizeRecordRedis;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.DemoCashAdjustDto;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActStringCodeStatusEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动结算相关的业务处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年9月19日
 */
@Component
@Transactional
public class ActSettleManager {
	private static Logger logger = LoggerFactory.getLogger(ActSettleManager.class);

	@Autowired
	private ImsPrizeRecordDao imsPrizeRecordDao;

	@Autowired
	protected ActSettingDao actSettingDao;
	
	@Autowired
	private ActBonusManager actBonusManager;

	@Autowired
	private VActPrizeRecordDao vActPrizeRecordDao;
	
	@Autowired
	private ActPrizeRecordRedis actPrizeRecordRedis;
	
	@Autowired
	private ActSettingManager actInfoManager;
	
	@Autowired
	private ActDemoCashAdjustManager actDemoCashAdjustManager;
	
	@Autowired
	private ActItemsSettingDao actItemsSettingDao;
	
	@Autowired
	private ActStringCodeDao actStringCodeDao;
	
	@Autowired
	private MisOrderDao misOrderDao;
	
	@Autowired
	private ActPrizeRecordRedis actPrizeRecordExtRedis;
	
	@Autowired
	private ActItemsSettingManager actItemsSettingManager;
	
	@Autowired
	private ImsPrizeRecordWaitingDao imsPrizeRecordWaitingDao;
	/*@Autowired
	protected ActConditionSettingMapper actConditionSettingMapper;

	@Autowired
	protected ActJoinQualifyMapper actJoinQualifyMapper;

	@Autowired
	private ApiDistributeManager apiDistributeManager;

	@Autowired
	private ActPrizeRecordExtMapper actPrizeRecordExtMapper;

	@Autowired
	private ActAccountActivityMapper actAccountActivityMapper;

	@Autowired
	private ActSettleWaitPrizeRecordMapper actSettleWaitPrizeRecordMapper;

	@Autowired
	private ActWithdrawAccountRedis actWithdrawAccountRedis;
	
	@Autowired
	private RelatedCustomerMapper relatedCustomerMapper;
	@Autowired
	private SeqManager seqManager;
	@Autowired
	private ActThirdCallRecordManager actThirdCallRecordManager;
	@Autowired
	private ActJoinQualityManager actJoinQualityManager;
	@Autowired
	private ActHandleDubboService actHandleDubboService;
	
	@Autowired
	private ActAccountStatRedis actAccountStatRedis;
	@Autowired
	private ActAccountActStatRedis actAccountActStatRedis;*/
	
	/**
	 * 
	 * @param companyId 
	 * @MethodName: actSettingSettle
	 * @Description: 活动主体清算
	 * @return void
	 */
	public void actSettingSettle(Long companyId) {
		Date settleTime = new Date();
		List<ActSetting> listActSetting = actSettingDao.findReadySettleActSetting();
		logger.info("待清算的活动总数:{}", listActSetting.size());
		for (ActSetting itemActSetting : listActSetting) {
			logger.info("开始处理活动主体清算,编号 {}", itemActSetting.getActivityPeriods());
			// 检查活动是否存在应发物品的数据,存在，即延迟活动主体结算，否，则进行活动主体清算
			ImsPrizeRecord paramsPrizeRecord = new ImsPrizeRecord();
			paramsPrizeRecord.setCompanyId(itemActSetting.getCompanyId());
			paramsPrizeRecord.setActNo(itemActSetting.getActivityPeriods());
			List<ImsPrizeRecord> waitingPrize = imsPrizeRecordDao.findWaitPrizeRecords(paramsPrizeRecord);
			if (waitingPrize.size() > 0) {
				logger.info("活动{}存在待发放的物品记录，暂停活动主体清算", itemActSetting.getActivityPeriods());
			} else {
				itemActSetting.setIsSettlement(true);
				itemActSetting.setSettlementDate(settleTime);
				actSettingDao.update(itemActSetting);
				logger.info("活动{}清算成功", itemActSetting.getActivityPeriods());
			}
		}
	}
	
	/**
	 * 
	 * @MethodName: batchSettlement
	 * @Description: 活动发放记录批量结算
	 * @param companyCode
	 * @return void
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void batchSettlement(Long companyId) {
		// 将已经到结算时间的应发记录，复制内容到另外一张表，由另外一个job负责扫描
		Calendar settlementTime = Calendar.getInstance();
		// // 结算时间推后15分钟
		settlementTime.add(Calendar.MINUTE, -15);
		
		List<ActSetting> actList=actSettingDao.findValidActivity(ActSettingDao.VALID_ACT_ALL);//寻找所有审批通过并且活动开始的活动
		
		//遍历所有的有效活动
		for(ActSetting act:actList) {
			// 释放入金型任务要求手数=完成手数的贈金,并且层级内是已经完成的发放记录
			actBonusManager.releaseDepositBonus(companyId, act.getActivityPeriods(), settlementTime.getTime());
			// 取消到点结算未释放的赠金
			actBonusManager.cancelBonus(companyId, act.getActivityPeriods(), settlementTime.getTime());
			
			VActPrizeRecord actPrizeRecord = new VActPrizeRecord();
			actPrizeRecord.setCompanyId(companyId);
			actPrizeRecord.setSettlementTime(settlementTime.getTime());
		
		
			actPrizeRecord.setActNo(act.getActivityPeriods());
			//更新已经到达结算时间的处于等待中的发放记录(一般是层级任务的发放记录)
			batchUpdateWaitingPrizeRecord(companyId,actPrizeRecord);
			//demo发贈金或者代币，到达结算时间没有建立关联关系，批量取消发放
			batchUpdateUnRelativePrizeRecord(companyId,actPrizeRecord);
		}

		// // 批量更新到达结算时间并且无应发记录的客户活动关系表
		// ActAccountActivity actAccountActivity = new ActAccountActivity();
		// actAccountActivity.setCompanyCode(companyCode);
		// actAccountActivity.setSettlementTime(settlementTime.getTime());
		// actAccountActivityMapper.updateSettle(actAccountActivity);
	}
	
	/**
	 * 
	 * @MethodName: batchUpdateWaitingPrizeRecord
	 * @Description: 更新已经到达结算时间的处于等待中的发放记录(一般是层级任务的发放记录)
	 * @param companyCode
	 * @param params
	 * @return void
	 */
	private void batchUpdateWaitingPrizeRecord(Long companyId,VActPrizeRecord params){
		
		String companyCode=CompanyEnum.getCodeById(companyId);
		
		List<VActPrizeRecord> listWaitingPrize = vActPrizeRecordDao.findSettlementWaitingPrize(params);
		if(listWaitingPrize==null || listWaitingPrize.isEmpty())
			return;
	
		List<String> updateDeal = new ArrayList<String>();
		for(VActPrizeRecord itemPrize:listWaitingPrize){
			updateDeal.add(itemPrize.getRecordNo());
		}
		
		ActPrizeRecordExt updatePrizeExt = new ActPrizeRecordExt();
		updatePrizeExt.setCompanyId(companyId);
		updatePrizeExt.setReleaseFinish(1);
		updatePrizeExt.setRecordNumbers(updateDeal);
		updatePrizeExt.setUpdateDate(new Date());
		
		ImsPrizeRecord updateMainPrize = new ImsPrizeRecord();
		updateMainPrize.setCompanyId(companyId);
		updateMainPrize.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
		updateMainPrize.setRemark("结算，取消发放记录;");
		updateMainPrize.setUpdateDate(new Date());
		updateMainPrize.setRecordNumbers(updateDeal);
	
		actPrizeRecordRedis.rPushRecordExt(updatePrizeExt, companyCode);
		actPrizeRecordRedis.rPushRecordMain(updateMainPrize, companyCode);
	}
	
	/**
	 * 
	 * @MethodName: batchUpdateUnRelativePrizeRecord
	 * @Description: demo发贈金或者代币,模拟币，到达结算时间没有建立关联关系，批量取消发放
	 * @param companyCode
	 * @param params
	 * @return void
	 */
	private void batchUpdateUnRelativePrizeRecord(Long companyId,VActPrizeRecord params){
		List<VActPrizeRecord> waitingCancelPrize =vActPrizeRecordDao.findSettlementGoldCoinUnRelativeRecord(params);
		updateUnRelativePrizeRecord(companyId, waitingCancelPrize);
		waitingCancelPrize =vActPrizeRecordDao.findSettlementAnalogCoinUnRelativeRecord(params);
		updateUnRelativePrizeRecord(companyId, waitingCancelPrize);
	}

	/**
	 * 
	 * @MethodName: updateUnRelativePrizeRecord
	 * @Description: 批量更新到期的未做关联的发放记录
	 * @param companyCode
	 * @param waitingCancelPrize
	 * @return void
	 */
	private void updateUnRelativePrizeRecord(Long companyId, List<VActPrizeRecord> waitingCancelPrize) {
		String companyCode=CompanyEnum.getCodeById(companyId);
		
		if(waitingCancelPrize!=null && !waitingCancelPrize.isEmpty()){
			List<String> updateDealAll = new ArrayList<String>();
			List<String> updateDealNotWpdh = new ArrayList<String>();
			List<String> updateDealWpdhSuccess = new ArrayList<String>();
			List<String> updateDealWpdhFaiure = new ArrayList<String>();

			for(VActPrizeRecord itemPrize:waitingCancelPrize){
				ActSetting actSetting = actInfoManager.findByactivityPeriods(itemPrize.getActNo());
				if(actSetting == null){
					logger.error("活动内容查询失败,活动编号{},公司 {}",itemPrize.getActNo(),itemPrize.getCompanyId());
					continue;
				}
				updateDealAll.add(itemPrize.getRecordNo());
				
				ImsPrizeRecord prizeRecord=new ImsPrizeRecord();
				prizeRecord.setId(itemPrize.getId().intValue());
				prizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
				prizeRecord.setActNo(itemPrize.getActNo());
				
				imsPrizeRecordDao.updateGiveStatus(prizeRecord);
				
				//退还物品
				if(ActTypeEnum.WPDH.getCode().equals(actSetting.getActivityType())){
					logger.info("{}退还模拟币",itemPrize.getRecordNo());
					DemoCashAdjustDto dto = new DemoCashAdjustDto();
					dto.setCompanyId(itemPrize.getCompanyId());
					dto.setEnv(itemPrize.getEnv());
					dto.setAccountNo(itemPrize.getAccountNo());
					dto.setPlatform(itemPrize.getPlatform()); 
					dto.setAmount(new BigDecimal(itemPrize.getExt3()));
					dto.setOperateType("1");
					MisRespResult<Void> result = actDemoCashAdjustManager.demoCashAdjust(dto);
					if (result.isOk() 
							|| ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())) {
						updateDealWpdhSuccess.add(itemPrize.getRecordNo());
					}else{
						updateDealWpdhFaiure.add(itemPrize.getRecordNo());
					}
				}else{
					updateDealNotWpdh.add(itemPrize.getRecordNo());
				}
				logger.info("{}退还物品",itemPrize.getRecordNo());
				cancelPrizeRecordItem(itemPrize);
			}
			
    		ActPrizeRecordExt updatePrizeExt = new ActPrizeRecordExt();
    		updatePrizeExt.setCompanyId(companyId);
    		updatePrizeExt.setUpdateDate(new Date());
    		updatePrizeExt.setReleaseFinish(1);
    		updatePrizeExt.setRecordNumbers(updateDealAll);
			actPrizeRecordExtRedis.rPushRecordExt(updatePrizeExt, companyCode);
    		
			if(!updateDealNotWpdh.isEmpty()){
				ImsPrizeRecord updatePrizeRecord = new ImsPrizeRecord();
	    		updatePrizeRecord.setCompanyId(companyId);
	    		updatePrizeRecord.setUpdateDate(new Date());
	    		updatePrizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
	    		updatePrizeRecord.setRemark("到达结算时间,无建立demo-real关系，取消发放;");
	    		updatePrizeRecord.setRecordNumbers(updateDealNotWpdh);
	    		updatePrizeRecord.setExt1("");
				actPrizeRecordExtRedis.rPushRecordMain(updatePrizeRecord, companyCode);
			}
    		if(!updateDealWpdhSuccess.isEmpty()){
    			ImsPrizeRecord updatePrizeRecord = new ImsPrizeRecord();
	    		updatePrizeRecord.setCompanyId(companyId);
	    		updatePrizeRecord.setUpdateDate(new Date());
	    		updatePrizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
	    		updatePrizeRecord.setRemark("到达结算时间,无建立demo-real关系，取消发放;退还成功;");
	    		updatePrizeRecord.setRecordNumbers(updateDealWpdhSuccess);
	    		updatePrizeRecord.setExt1("");
				actPrizeRecordExtRedis.rPushRecordMain(updatePrizeRecord, companyCode);
    		}
    		if(!updateDealWpdhFaiure.isEmpty()){
    			ImsPrizeRecord updatePrizeRecord = new ImsPrizeRecord();
	    		updatePrizeRecord.setCompanyId(companyId);
	    		updatePrizeRecord.setUpdateDate(new Date());
	    		updatePrizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
	    		updatePrizeRecord.setExt1("");
	    		updatePrizeRecord.setRemark("到达结算时间,无建立demo-real关系，取消发放;退还失败，请手动处理;");
	    		updatePrizeRecord.setRecordNumbers(updateDealWpdhFaiure);
				actPrizeRecordExtRedis.rPushRecordMain(updatePrizeRecord, companyCode);
    		}
		}
	}
	
	/**
	 * 清算时返回对应的物品数量
	 * @param vActPrizeRecord
	 * @return
	 */
	public boolean cancelPrizeRecordItem(VActPrizeRecord prizeRecord){
	    boolean flag = true;
	    if(prizeRecord==null)
	    	return false;
	    
	    	
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("itemNumber", prizeRecord.getItemNo());
        ActItemsSetting actItemsSetting = actItemsSettingDao.findObjectByMap(dataMap);
        if(actItemsSetting==null)
        	return flag;
        	
        //要需处理的记录的发放状态
        Set<String> issuingStatusSet = new HashSet<String>();
        issuingStatusSet.add(IssuingStatusEnum.in_library.getValue());//在库
        issuingStatusSet.add(IssuingStatusEnum.issuing.getValue());//待发放
        issuingStatusSet.add(IssuingStatusEnum.in_distributed.getValue());//发放中
        issuingStatusSet.add(IssuingStatusEnum.waiting.getValue());//等待中
        if(!issuingStatusSet.contains(prizeRecord.getGivedStatus()))
        	return flag;
        	
        int giftAmountStep = 0;//预计剩余数量
        int giftTmpAmountStep = 0;//实际剩余数量
        //实物、接口、虚拟物品需要加数量
        if (ActItemTypeEnum.REAL.getCode().equals(actItemsSetting.getItemType())
            || ActItemTypeEnum.VIRTUAL.getCode().equals(actItemsSetting.getItemType())
            || ActItemTypeEnum.INTERFACE.getCode().equals(actItemsSetting.getItemType())) {
        	
            giftTmpAmountStep = 1;// 预计剩余数量加1
            if (!ActItemTypeEnum.REAL.getCode().equals(actItemsSetting.getItemType())) {// 非实物，实际剩余数量加1
                giftAmountStep = 1;
            }
            
            try {
               actItemsSettingDao.updateActItemsAmount(prizeRecord.getItemNo(), giftTmpAmountStep, giftAmountStep);
            } catch (Exception e) {
                logger.error("--->提前结算，处理返回物品数量出现错误");
                logger.error(e.getMessage(), e);
                flag = false;
            }
            //如果是类型是串码，还要退回串码
            if(ActItemCategoryEnum.STRINGCODE.getValue().equals(actItemsSetting.getItemCategory()) && StringUtil.isNotEmpty(prizeRecord.getSensitiveData())){
              // 修改发放的串码状态及相关属性
                ActStringCode actStringCode = new ActStringCode();
                actStringCode.setStringCode(prizeRecord.getSensitiveData());
                actStringCode.setActivityPeriods(null);
                actStringCode.setAccountNo(null);
                actStringCode.setTaskTitle(null);
                actStringCode.setStatus(ActStringCodeStatusEnum.NOTUSED.getValue());// 未使用
                actStringCode.beforeUpdate();
                try {
                   actStringCodeDao.updateStatusByStringCode(actStringCode);
                } catch (Exception e) {
                    logger.error("--->提前结算，处理返回串码物品出现错误");
                    logger.error(e.getMessage(), e);
                    flag = false;
                }
            } 
            if (ActItemTypeEnum.REAL.getCode().equals(actItemsSetting.getItemType())){
                try{
                    // 实物修改原先订单收货状态
                	Map<String,Object> params=new HashMap<String,Object>();
                	params.put("recordNumber", prizeRecord.getRecordNo());
                    ImsOrder imsOrder = misOrderDao.findObjectByMap(params);
                    if (imsOrder != null && imsOrder.getDeliveryStatus() != 3) {// 为未发货时修改为已取消
                        imsOrder.setDeliveryStatus(2);// 修改已取消
                        imsOrder.setDeliveryDate(new Date());
                        imsOrder.beforeUpdate();
                        misOrderDao.update(imsOrder);
                    }
                } catch (Exception e) {
                    logger.error("--->提前结算，处理订单收货状态出现错误");
                    logger.error(e.getMessage(), e);
                    flag = false;
                }
            }
        } 
	    
	    return flag;
	}
	
	/**
	 * 
	 * @param companyId 
	 * @MethodName: dealWithSettleWaitPrizeRecord
	 * @Description: 处理结算时，由于存在待发列表的发放记录导致结算延误的逻辑
	 * @return void
	 */
	public void dealWithSettleWaitPrizeRecord(Long companyId) {
		String companyCode=CompanyEnum.getCodeById(companyId);
		
		//查找所有待发的发放记录
		List<ImsPrizeRecordWaiting> listWaitPrizeRecord=imsPrizeRecordWaitingDao.findReadySendRecord();
		
		logger.info("{}待处理结算后出现的应发记录数:{}", companyCode, listWaitPrizeRecord.size());
		
		for(ImsPrizeRecordWaiting record:listWaitPrizeRecord) {
			//先保存发放记录
			ImsPrizeRecord prizeRecord=ImsBeanUtil.copyNotNull(new ImsPrizeRecord(), record);
			prizeRecord.setId(null);
			
			// 实物、虚拟物品、接品物品有数量控制和时间限制
			Integer itemUsableAmount = null;
			Integer itemStockAmount = null;
			if (ActItemTypeEnum.REAL.getValue().equals(record.getItemType())
					|| ActItemTypeEnum.VIRTUAL.getCode().equals(record.getItemType())
					|| ActItemTypeEnum.INTERFACE.getCode().equals(record.getItemType())) {
				itemUsableAmount = -1;//  实际可用数量需要减一
				if (!ActItemTypeEnum.REAL.getValue().equals(record.getItemType())) {// 非实物，实物的话当出库时，实际剩余数量再减一
					itemStockAmount = -1;//非实物的话，库存数量也要减一
				}
				
				if(actItemsSettingManager.updateActItemsAmount(record.getItemNo(), itemStockAmount, itemUsableAmount)) {
					imsPrizeRecordDao.save(prizeRecord);
					imsPrizeRecordWaitingDao.delete(prizeRecord.getId());//删除应发记录
					//@todo调用发放接口
				}
				
			}
		}
	}
	
	/**
	 * 
	 * @MethodName: withdrawAdvanceSettlement
	 * @Description: 提前结算(取款)
	 * @param actSettleReqDto
	 * @return
	 * @return ApiRespResult<List<String>>
	 */
	/*public ApiRespResult<List<String>> withdrawAdvanceSettlement(ActSettleReqDTO actSettleReqDto) {
		ApiRespResult<List<String>> result = new ApiRespResult<List<String>>();
		if (actSettleReqDto == null || StringUtil.isEmpty(actSettleReqDto.getAccountNo())
				|| StringUtil.isEmpty(actSettleReqDto.getPlatform())) {
			return result.setRespMsg(ApiResultCode.Err10002);
		}
		if(actSettleReqDto.getApproveDate() == null){
			actSettleReqDto.setApproveDate(new Date());
		}
		actSettleReqDto.setManual(false);
		String companyCode = ActCompany.getCodeByKey(actSettleReqDto.getCompanyId());
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar nowdate = Calendar.getInstance();
		nowdate.add(Calendar.DATE, -1);
		params.put("pointDate", nowdate.getTime());
		params.put("companyId", actSettleReqDto.getCompanyId());
		params.put("excludeType", ActSettingType.QT.getValue());
		if (StringUtil.isNotEmpty(actSettleReqDto.getActivityPeriods())) {
			params.put("activityPeriods", actSettleReqDto.getActivityPeriods());
		}
		logger.info("开始扫描有限制取款的活动，提前结算账号{},平台{}相关数据", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform());
		List<ActSetting> listActSetting = actSettingMapper.getActSettingListByParams(params);
		List<String> activityPeriodsList = new ArrayList<String>();

		RelatedCustomer relatedCustomer = new RelatedCustomer();
		relatedCustomer.setCompanyCode(companyCode);
		relatedCustomer.setCustomerNumber(new Long(actSettleReqDto.getAccountNo()));
		relatedCustomer = relatedCustomerMapper.getByAccountNo(relatedCustomer);
		String demoAccount = null;
		if(relatedCustomer!=null){
			demoAccount = relatedCustomer.getDemoCustomerNumber()!=null?""+relatedCustomer.getDemoCustomerNumber():null;
		}
	
		for (ActSetting itemActSetting : listActSetting) {
			try {
				// 屏蔽hx线上已经进行中的活动
				if ("hx_rw_20171204143024".equals(itemActSetting.getActivityPeriods())) {
					continue;
				}
				params.put("activityPeriods", itemActSetting.getActivityPeriods());
				List<ActConditionSetting> listActConSetting = actConditionSettingMapper.selectByExample(params);
				if (listActConSetting != null && !listActConSetting.isEmpty()) {
					ActCodnitionCustomInfoValJson actConVal = JsonUtil
							.json2Obj(listActConSetting.get(0).getConditionVal(), ActCodnitionCustomInfoValJson.class);
					// HX-MT4默认限制取款，其他公司以及平台判断活动设置是否取款
					if (actConVal.getAllowWithdrawals() != null && !actConVal.getAllowWithdrawals()) {
						ActSettleReqDto realSettleReqDto = new ActSettleReqDto();
						BeanUtils.copyExceptNull(realSettleReqDto, actSettleReqDto);
						//取款需要判断用户是否真正参与活动(有发放记录)
						boolean demoParticipate = false;
						boolean realParticipate = false;
						if(StringUtil.isNotEmpty(demoAccount)){
							demoParticipate = actJoinQualityManager.isParticipateAct(itemActSetting.getActivityPeriods(), demoAccount, actSettleReqDto.getPlatform(), actSettleReqDto.getCompanyId());
						}
						realParticipate = actJoinQualityManager.isParticipateAct(itemActSetting.getActivityPeriods(), actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform(), actSettleReqDto.getCompanyId());
						
						if(demoParticipate || realParticipate){
							String demoActivityPeriods = null;
							//清算demo数据
							if (StringUtil.isNotEmpty(demoAccount)) {
								ActSettleReqDto demoActSettleReqDto = new ActSettleReqDto();
								demoActSettleReqDto.setAccountNo(demoAccount);
								demoActSettleReqDto.setPlatform(actSettleReqDto.getPlatform());
								demoActSettleReqDto.setEnv(ActEnv.DEMO.getValue());
								demoActSettleReqDto.setApproveDate(actSettleReqDto.getApproveDate());
								demoActSettleReqDto.setCompanyId(actSettleReqDto.getCompanyId());
								demoActSettleReqDto.setType(ActSettlementType.WITHDRAW.getCode());
								demoActSettleReqDto.setActivityPeriods(itemActSetting.getActivityPeriods());
								demoActivityPeriods = advanceAccountPeriodsSettlement(demoActSettleReqDto,
										itemActSetting, false);
								if (StringUtil.isNotEmpty(demoActivityPeriods)) {
									realSettleReqDto.setWithDemo(true);
								}
							}

							//清算真实
							realSettleReqDto.setEnv(ActEnv.REAL.getValue());
							realSettleReqDto.setType(ActSettlementType.WITHDRAW.getCode());
							realSettleReqDto.setManual(false);
							String realActivityPeriods = advanceAccountPeriodsSettlement(realSettleReqDto, itemActSetting, false);

							if (StringUtil.isNotEmpty(realActivityPeriods) || StringUtil.isNotEmpty(demoActivityPeriods)) {
								String activityPeriods = StringUtil.isNotEmpty(realActivityPeriods) ?realActivityPeriods:demoActivityPeriods;
								realSettleReqDto.setActivityPeriods(activityPeriods);
								// 取款时先处理释放贈金,由完成手数的job处理扣除
								actWithdrawAccountRedis.rPushAccount(realSettleReqDto, companyCode);
							}
						}else{
							logger.info("账号{},平台{}没有真正参与活动{}",actSettleReqDto.getAccountNo(),actSettleReqDto.getPlatform(),itemActSetting.getActivityPeriods());
						}						
					} else {
						logger.info("活动名称:{},活动编号 {},不限制取款", itemActSetting.getActivityName(),
								itemActSetting.getActivityPeriods());
					}
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		result.setCode(ApiResultCode.OK.getCode());
		result.setData(activityPeriodsList);
		logger.info("结束扫描有限制取款的活动，完成处理提前结算账号{}平台{}相关数据", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform());
		return result.setRespMsg(ApiResultCode.OK);
	}*/

	/**
	 * 
	 * @MethodName: manualAdvanceSettlement
	 * @Description: 后台手动调用清算用户活动
	 * @param actSettleReqDto
	 * @return
	 * @return ApiRespResult<List<String>>
	 */
	/*public ApiRespResult<List<String>> manualAdvanceSettlement(ActSettleReqDTO actSettleReqDto) {
		ApiRespResult<List<String>> result = new ApiRespResult<List<String>>();
		if (actSettleReqDto == null || StringUtil.isEmpty(actSettleReqDto.getAccountNo())
				|| StringUtil.isEmpty(actSettleReqDto.getPlatform())) {
			return result.setRespMsg(ApiResultCode.Err10002);
		}
		if(actSettleReqDto.getApproveDate() == null){
			actSettleReqDto.setApproveDate(new Date());
		}
		actSettleReqDto.setType(ActSettlementType.BO_FORCE.getCode());
		actSettleReqDto.setManual(true);
		String companyCode = ActCompany.getCodeByKey(actSettleReqDto.getCompanyId());
		Map<String, Object> params = new HashMap<String, Object>();
//		Calendar nowdate = Calendar.getInstance();
//		nowdate.add(Calendar.HOUR, -5);
//		params.put("pointDate", nowdate.getTime());
		params.put("companyId", actSettleReqDto.getCompanyId());
		params.put("excludeType", ActSettingType.QT.getValue());
		if (StringUtil.isNotEmpty(actSettleReqDto.getActivityPeriods())) {
			params.put("activityPeriods", actSettleReqDto.getActivityPeriods());
		}
		logger.info("开始处理提前清算的活动，提前处理结算账号{},平台{}相关数据", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform());
		List<ActSetting> listActSetting = actSettingMapper.getActSettingListByParams(params);
		// 结算时间
		List<String> activityPeriodsList = new ArrayList<String>();

		for (ActSetting itemActSetting : listActSetting) {
			try {
				// 屏蔽hx线上已经进行中的活动
				if ("hx_rw_20171204143024".equals(itemActSetting.getActivityPeriods())) {
					continue;
				}
				String rActivityPeriods = advanceAccountPeriodsSettlement(actSettleReqDto, itemActSetting, true);
				if (StringUtil.isNotEmpty(rActivityPeriods)) {
					actSettleReqDto.setActivityPeriods(rActivityPeriods);
					// 取款时先处理释放贈金,由完成手数的job处理扣除
					actWithdrawAccountRedis.rPushAccount(actSettleReqDto, companyCode);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		result.setCode(ApiResultCode.OK.getCode());
		result.setData(activityPeriodsList);
		logger.info("结束处理提前清算的活动，提前结算账号{},平台{}相关数据", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform());
		return result.setRespMsg(ApiResultCode.OK);
	}*/

	/**
	 * 
	 * @MethodName: advanceAccountPeriodsSettlement
	 * @Description: 结算用户活动下的相关数据
	 * @param actSettleReqDto
	 * @param actSettingDto
	 * @param settlementTime
	 * @param manual
	 *            是否手动结算
	 * @return
	 * @return String
	 */
	/*public String advanceAccountPeriodsSettlement(ActSettleReqDTO actSettleReqDto, ActSetting actSettingDto,
			 boolean manual) {
		String execPeriods = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String companyCode = ActCompany.getCodeByKey(actSettleReqDto.getCompanyId());
		logger.info("活动名称:{},活动编号 {},将提前结算该活动下账号{}平台{}相关的数据", actSettingDto.getActivityName(),
				actSettingDto.getActivityPeriods(), actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform());
		if (StringUtil.isNotEmpty(companyCode)) {
			params.put("accountNo", actSettleReqDto.getAccountNo());
			params.put("platform", actSettleReqDto.getPlatform());
			params.put("env", actSettleReqDto.getEnv());
			params.put("activityPeriods", actSettingDto.getActivityPeriods());
			params.put("withdrawSettleTime", actSettleReqDto.getApproveDate());
			ActJoinQualify actJoinQualify = actJoinQualifyMapper.selectSettlementAccount(params);
			if ((actJoinQualify != null && !actJoinQualify.getIsSettlement()) || manual) {
				// 更新join_qualify相关表,将资格匹配相关的数据先做结算标识
				if (actJoinQualify.getSettlementTime().compareTo(actSettleReqDto.getApproveDate()) > 0) {
					Integer updateCount = actJoinQualifyMapper.updateSettlementAccount(params);
					
					if ((updateCount!=null && updateCount > 0) || manual) {
						logger.info("开始结算账号{}平台{}活动{}的结算数据", actSettleReqDto.getAccountNo(),
								actSettleReqDto.getPlatform(), actSettingDto.getActivityPeriods());
						List<ActPrizeRecord> waitingPrize = null;
						if(!ActSettlementType.BLACK.getCode().equals(actSettleReqDto.getType())){
							// 检查应发列表是否有数据,有的话，将这部分数据记录到另外一张表，由另外job扫描应发转可发的数据,再将这些数据进行结算
							ActPrizeRecord paramsPrizeRecord = new ActPrizeRecord();
							paramsPrizeRecord.setCompanyCode(companyCode);
							paramsPrizeRecord.setAccountNo(actSettleReqDto.getAccountNo());
							paramsPrizeRecord.setActivityPeriods(actSettingDto.getActivityPeriods());
							paramsPrizeRecord.setGuestPlatForm(actSettleReqDto.getPlatform());
							waitingPrize = actPrizeRecordMapper
									.findWaitPrizeRecords(paramsPrizeRecord);
							logger.info("账号:{}平台{},活动编号 {},应发列表数据量{}", actSettleReqDto.getAccountNo(),
									actSettleReqDto.getPlatform(), actSettingDto.getActivityPeriods(), waitingPrize.size());
						}
						
						if (waitingPrize != null && !waitingPrize.isEmpty()) {
							logger.info("账号:{}平台{},活动编号 {},只结算部分信息", actSettleReqDto.getAccountNo(),
									actSettleReqDto.getPlatform(), actSettingDto.getActivityName());
							ActSettleWaitPrizeRecord actSettleWaitPrizeRecord = new ActSettleWaitPrizeRecord();
							actSettleWaitPrizeRecord.setActivityPeriods(actSettingDto.getActivityPeriods());
							actSettleWaitPrizeRecord.setAccountNo(actSettleReqDto.getAccountNo());
							actSettleWaitPrizeRecord.setCompanyCode(companyCode);
							actSettleWaitPrizeRecord.setPlatform(actSettleReqDto.getPlatform());
							actSettleWaitPrizeRecordMapper.insert(actSettleWaitPrizeRecord);
							ActAccountActivity actAccountActivity = new ActAccountActivity();
							actAccountActivity.setActivityPeriods(actSettingDto.getActivityPeriods());
							actAccountActivity.setAccountNo(actSettleReqDto.getAccountNo());
							actAccountActivity.setCompanyCode(companyCode);
							actAccountActivity.setPlatform(actSettleReqDto.getPlatform());
							actAccountActivity.setSettleType(actSettleReqDto.getType());
							actAccountActivityMapper.update(actAccountActivity);
						} else {
							// 更新act_account_activity的真实结算标识以及结算时间
							ActAccountActivity actAccountActivity = new ActAccountActivity();
							actAccountActivity.setActivityPeriods(actSettingDto.getActivityPeriods());
							actAccountActivity.setAccountNo(actSettleReqDto.getAccountNo());
							actAccountActivity.setSettlement(true);
							actAccountActivity.setRealSettlement(true);
							actAccountActivity.setSettlementTime(actSettleReqDto.getApproveDate());
							actAccountActivity.setCompanyCode(companyCode);
							actAccountActivity.setPlatform(actSettleReqDto.getPlatform());
							actAccountActivity.setSettleType(actSettleReqDto.getType());
							actAccountActivityMapper.update(actAccountActivity);
							logger.info("账号:{}平台{},活动编号 {},结算成功", actSettleReqDto.getAccountNo(),
									actSettleReqDto.getPlatform(), actSettingDto.getActivityPeriods());
							
						}
						Map<String,Object> dataParams = new HashMap<String,Object>();
						dataParams.put("accountNo", actSettleReqDto.getAccountNo());
						dataParams.put("platform", actSettleReqDto.getPlatform());
						dataParams.put("activityPeriods", actSettleReqDto.getActivityPeriods());
						actAccountActStatRedis.rPushAccountPeriods(dataParams, companyCode);
						execPeriods = actSettingDto.getActivityPeriods();
					}
				} else {
					logger.warn("当前平台{},账号{},活动编号{}结算时间{}小于取款时间{}，将由结算逻辑处理", actSettleReqDto.getPlatform(),
							actSettleReqDto.getAccountNo(), actSettingDto.getActivityPeriods(),
							DateUtil.formatDateToString(actJoinQualify.getSettlementTime(), "yyyy-MM-dd HH:mm:ss"),
							DateUtil.formatDateToString(actSettleReqDto.getApproveDate(), "yyyy-MM-dd HH:mm:ss"));
				}
			}else{
				if(actJoinQualify == null){
					logger.info("当前平台{},账号{},活动编号{},没有参与资格",actSettleReqDto.getPlatform(),actSettleReqDto.getAccountNo(),actSettingDto.getActivityPeriods());
				}else{
					logger.info("当前平台{},账号{},活动编号{},已经结算，不需要重新更新",actSettleReqDto.getPlatform(),actSettleReqDto.getAccountNo(),actSettingDto.getActivityPeriods());
				}
			}
		}
		return execPeriods;
	}*/

	/**
	 * 
	 * @MethodName: delayAdvanceSettlement
	 * @Description: 延迟处理提前清算发放记录贈金
	 * @param actSettleReqDto
	 * @return
	 * @return ApiRespResult<List<String>>
	 */
	/*public ApiRespResult<List<String>> delayAdvanceSettlement(ActSettleReqDTO actSettleReqDto,String withdrawBonusDeduction) {
		ApiRespResult<List<String>> result = new ApiRespResult<List<String>>();
		if(ActSettlementType.BLACK.getCode().equals(actSettleReqDto.getType())||
				ActSettlementType.BO_FORCE.getCode().equals(actSettleReqDto.getType())){
			delayForceAdvanceSettlement(actSettleReqDto, withdrawBonusDeduction);
		}else{
			delayWithdrawAdvanceSettlement(actSettleReqDto, withdrawBonusDeduction);
		}
		return result.setRespMsg(ApiResultCode.OK);
	}*/
	
	/**
	 * 
	 * @MethodName: delayWithdrawAdvanceSettlement
	 * @Description: 延迟处理取款后，进行提前清算
	 * @param actSettleReqDto
	 * @param withdrawBonusDeduction
	 * @return
	 * @return ApiRespResult<List<String>>
	 */
	/*public ApiRespResult<List<String>> delayWithdrawAdvanceSettlement(ActSettleReqDTO actSettleReqDto,String withdrawBonusDeduction) {
		ApiRespResult<List<String>> result = new ApiRespResult<List<String>>();
		if (actSettleReqDto == null || StringUtil.isEmpty(actSettleReqDto.getAccountNo())
				|| StringUtil.isEmpty(actSettleReqDto.getPlatform()) || StringUtil.isEmpty(actSettleReqDto.getActivityPeriods())) {
			return result.setRespMsg(ApiResultCode.Err10002);
		}
		String companyCode = ActCompany.getCodeByKey(actSettleReqDto.getCompanyId());		
		logger.info("开始提前结算账号{},平台{},活动{}相关数据", actSettleReqDto.getAccountNo(),
				actSettleReqDto.getPlatform(), actSettleReqDto.getActivityPeriods());
		RelatedCustomer relatedCustomer = new RelatedCustomer();
		relatedCustomer.setCompanyCode(companyCode);
		relatedCustomer.setCustomerNumber(new Long(actSettleReqDto.getAccountNo()));
		relatedCustomer = relatedCustomerMapper.getByAccountNo(relatedCustomer);
		String demoAccount = null;
		if(relatedCustomer!=null){
			demoAccount = relatedCustomer.getDemoCustomerNumber()!=null?""+relatedCustomer.getDemoCustomerNumber():null;
		}
		// 结算时间
		Date settlementTime = new Date();
		List<String> activityPeriodsList = new ArrayList<String>();
		//清算real
		String rActivityPeriods = advanceCancelAccountUnFinishRecord(actSettleReqDto, settlementTime,
				withdrawBonusDeduction);
		//清算demo
		if (actSettleReqDto.isWithDemo() && StringUtil.isNotEmpty(demoAccount)) {
			ActSettleReqDto demoActSettleReqDto = new ActSettleReqDto();
			demoActSettleReqDto.setActivityPeriods(actSettleReqDto.getActivityPeriods());
			demoActSettleReqDto.setAccountNo(demoAccount);
			demoActSettleReqDto.setPlatform(actSettleReqDto.getPlatform());
			demoActSettleReqDto.setApproveDate(actSettleReqDto.getApproveDate());
			demoActSettleReqDto.setEnv(ActEnv.DEMO.getValue());
			demoActSettleReqDto.setCompanyId(actSettleReqDto.getCompanyId());
			advanceCancelAccountUnFinishRecord(demoActSettleReqDto, settlementTime, withdrawBonusDeduction);
		}
		if(StringUtil.isNotEmpty(rActivityPeriods)){
			activityPeriodsList.add(rActivityPeriods);
		}
		result.setCode(ApiResultCode.OK.getCode());
		result.setData(activityPeriodsList);
		logger.info("结束提前结算账号{}平台{},活动{}相关数据", actSettleReqDto.getAccountNo(),
				actSettleReqDto.getPlatform(), actSettleReqDto.getActivityPeriods());
		return result.setRespMsg(ApiResultCode.OK);
	}*/
	
	/**
	 * 
	 * @MethodName: delayForceAdvanceSettlement
	 * @Description: 延迟处理手动清算后，进行提前清算
	 * @param actSettleReqDto
	 * @param withdrawBonusDeduction
	 * @return
	 * @return ApiRespResult<List<String>>
	 */
	/*public ApiRespResult<List<String>> delayForceAdvanceSettlement(ActSettleReqDTO actSettleReqDto,String withdrawBonusDeduction) {
		ApiRespResult<List<String>> result = new ApiRespResult<List<String>>();
		if (actSettleReqDto == null || StringUtil.isEmpty(actSettleReqDto.getAccountNo())
				|| StringUtil.isEmpty(actSettleReqDto.getPlatform()) || StringUtil.isEmpty(actSettleReqDto.getActivityPeriods())) {
			return result.setRespMsg(ApiResultCode.Err10002);
		}
		logger.info("开始提前结算账号{},平台{},活动{}相关数据", actSettleReqDto.getAccountNo(),
				actSettleReqDto.getPlatform(), actSettleReqDto.getActivityPeriods());
		
		// 结算时间
		Date settlementTime = new Date();
		List<String> activityPeriodsList = new ArrayList<String>();
		String rActivityPeriods = advanceCancelAccountUnFinishRecord(actSettleReqDto, settlementTime,
				withdrawBonusDeduction);
	
		if(StringUtil.isNotEmpty(rActivityPeriods)){
			activityPeriodsList.add(rActivityPeriods);
		}
		result.setCode(ApiResultCode.OK.getCode());
		result.setData(activityPeriodsList);
		logger.info("结束提前结算账号{}平台{},活动{}相关数据", actSettleReqDto.getAccountNo(),
				actSettleReqDto.getPlatform(), actSettleReqDto.getActivityPeriods());
		return result.setRespMsg(ApiResultCode.OK);
	}*/

	/**
	 * 
	 * @MethodName: advanceCancelAccountUnFinishRecord
	 * @Description: 提前清算扣除贈金逻辑
	 * @param actSettleReqDto accountNo,platform,activityPeriods参数必填
	 * @param actSettingDto
	 * @param settlementTime
	 * @return
	 * @return String
	 */
	/*public String advanceCancelAccountUnFinishRecord(ActSettleReqDTO actSettleReqDto,
			Date settlementTime,String withdrawBonusDeduction) {
		try {
			String companyCode = ActCompany.getCodeByKey(actSettleReqDto.getCompanyId());
			ActApiManager thirdApi = apiDistributeManager.getApi(companyCode, actSettleReqDto.getPlatform());
			logger.info("开始结算账号{}平台{}活动{}的结算数据", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform(),
					actSettleReqDto.getActivityPeriods());
			if(thirdApi == null){
				return null;
			}
			VActPrizeRecord actPrizeRecord = new VActPrizeRecord();
			actPrizeRecord.setAccountNo(actSettleReqDto.getAccountNo());
			actPrizeRecord.setGuestPlatForm(actSettleReqDto.getPlatform());
			actPrizeRecord.setCompanyCode(companyCode);
			actPrizeRecord.setActivityPeriods(actSettleReqDto.getActivityPeriods());
			actPrizeRecord.setReleaseFinish(1);
			if (actSettleReqDto.getApproveDate() != null) {
				actPrizeRecord.setSettlementTime(actSettleReqDto.getApproveDate());
			} else {
				actPrizeRecord.setSettlementTime(settlementTime);
			}
			actPrizeRecord.setUpdateDate(new Date());
			
			List<VActPrizeRecord> unfinishedPrizeRecord = null;
			// 获取未完成手数的贈金记录
			List<VActPrizeRecord> oriUnfinishedGoldPrizeRecord = vActPrizeRecordMapper
					.findUnfinishedWithGoldRecordByAccountNo(actPrizeRecord);
			//按层级释放,再进行贈金扣除处理
			if("hierarchy_released".equals(withdrawBonusDeduction)){
				unfinishedPrizeRecord = advanceHierarchyReleasedPrizeRecord(companyCode,thirdApi,oriUnfinishedGoldPrizeRecord);
			}else{
				unfinishedPrizeRecord = oriUnfinishedGoldPrizeRecord;
			}
			//需要扣回贈金的订单列表
			Set<String> unifinishRecordNumber = new HashSet<String>(); 

			// 获取未完成手数的贈金记录
			List<VActPrizeRecord> oriUnfinishedTokenCoinPrizeRecord = vActPrizeRecordMapper
					.findUnfinishedTokenCoinByAccountNo(actPrizeRecord);
			if(oriUnfinishedTokenCoinPrizeRecord!=null && !oriUnfinishedTokenCoinPrizeRecord.isEmpty()){
				unfinishedPrizeRecord.addAll(oriUnfinishedTokenCoinPrizeRecord);
			}
			// 释放未完成任务的贈金奖励
			if (unfinishedPrizeRecord != null && !unfinishedPrizeRecord.isEmpty()) {
				for(VActPrizeRecord itemPrize:unfinishedPrizeRecord){
					unifinishRecordNumber.add(itemPrize.getRecordNumber());
				}
				Map<String, Map<String, String>> resultData = thirdApi.realCancelBonus(unfinishedPrizeRecord,
						"提前扣回贈金");
				Map<String, String> callResults = new HashMap<String, String>();
				if (resultData.get("success") != null) {
					callResults.putAll(resultData.get("success"));
				}
				if (resultData.get("errors") != null) {
					callResults.putAll(resultData.get("errors"));
				}
				actThirdCallRecordManager.saveThirdCallRecord(companyCode, unfinishedPrizeRecord, callResults,
						ActThirdCallEnum.BONUS_CANCEL_BONUS, "提前清算，");
			}
			
			//获取用户活动的发放记录，排除已经完成的发放记录(只查release_finish=0的数据)
			List<VActPrizeRecord> listWaitPrizeRecord = vActPrizeRecordMapper.findByAccountNoAndActPeriods(actPrizeRecord);
			
			if (listWaitPrizeRecord != null && !listWaitPrizeRecord.isEmpty()) {
				for (VActPrizeRecord itemPrizeRecord : listWaitPrizeRecord) {
					String remark = "提前清算，取消发放记录;";
					if(ActSettlementType.BLACK.getCode().equals(actSettleReqDto.getType())){
						remark="黑名单;";
					}
					ActPrizeRecordExt actPrizeRecordExt = new ActPrizeRecordExt();
					itemPrizeRecord.setSettlementTime(actPrizeRecord.getSettlementTime());
					itemPrizeRecord.setUpdateDate(new Date());
					itemPrizeRecord.setCompanyCode(companyCode);
					if(unifinishRecordNumber.contains(itemPrizeRecord.getRecordNumber())){
						itemPrizeRecord.setReleaseFinish(2);
					} else {
						// 黑名单特殊处理，将未完成的发放记录都会取消发放（包括在库、待发放、发放中、等待中这些状态的发放记录以及应发列表的记录）全部状态变成已经取消
						if ((ActSettlementType.BLACK.getCode().equals(actSettleReqDto.getType()) && ((!itemPrizeRecord
								.getIssuingStatus().equals(IssuingStatusEnum.issue_success.getValue())
								&& !itemPrizeRecord.getIssuingStatus().equals(IssuingStatusEnum.issue_cancel.getValue())
								&& !itemPrizeRecord.getIssuingStatus().equals(IssuingStatusEnum.issue_fail.getValue())
								&& !itemPrizeRecord.getIssuingStatus().equals(IssuingStatusEnum.out_library.getValue()))
								|| itemPrizeRecord.getAuditStatus().equals(AuditStatusEnum.waitForApprove.getValue())))
								|| (itemPrizeRecord.getSendStatus().equals(0)
										&& itemPrizeRecord.getAuditStatus().equals(AuditStatusEnum.auditPass.getValue())
										&& itemPrizeRecord.getIssuingStatus()
												.equals(IssuingStatusEnum.waiting.getValue()))) {
							ImsOrder imsOrder = imsOrderMapper.getByRecordNumber(itemPrizeRecord.getRecordNumber());
                            if (imsOrder != null && imsOrder.getDeliveryStatus() ==3) {
                            	logger.warn("订单信息{},已经出库，不允许取消该订单",itemPrizeRecord.getRecordNumber());
                            	continue;
                            }
							
							ActSetting actSetting = actInfoManager.getByActivityPeriods(itemPrizeRecord.getActivityPeriods(), itemPrizeRecord.getCompanyId());
							if(ActSettingType.WPDH.getValue().equals(actSetting.getActivityType())){
								logger.info("{}退还模拟币",itemPrizeRecord.getRecordNumber());
								DemoCashAdjustDto dto = new DemoCashAdjustDto();
								dto.setCompanyId(itemPrizeRecord.getCompanyId());
								dto.setEnv(itemPrizeRecord.getEnv());
								dto.setAccountNo(itemPrizeRecord.getAccountNo());
								dto.setPlatform(itemPrizeRecord.getGuestPlatForm());
								dto.setAmount(new BigDecimal(itemPrizeRecord.getExt3()));
								dto.setOperateType("1");
								ApiRespResult<Void> result = actDemoCashAdjustManager.demoCashAdjust(dto);
								if (result.isOk() 
										|| ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())) {
									remark = "退还成功;"+remark;
								}else{
									remark = "退还失败，请手动处理;"+remark;
								}
							}
							logger.info("{}退还物品",itemPrizeRecord.getRecordNumber());
							cancelPrizeRecordItem(itemPrizeRecord);
							
							ActPrizeRecord paramPrizeRecord = new ActPrizeRecord();
							
							itemPrizeRecord.setReleaseFinish(1);
							
							itemPrizeRecord.setIssuingStatus(IssuingStatusEnum.issue_cancel.getValue());
							if(itemPrizeRecord.getAuditStatus().equals(AuditStatusEnum.waitForApprove.getValue())){
								itemPrizeRecord.setAuditStatus(AuditStatusEnum.auditRefuse.getValue());
							}
							if(!itemPrizeRecord.getSendStatus().equals(0)){
								itemPrizeRecord.setSendStatus(0);
							}
							itemPrizeRecord.setRemark(StringUtil.isEmpty(itemPrizeRecord.getRemark()) ? remark
									: remark + itemPrizeRecord.getRemark());
							BeanUtils.copyExceptNull(paramPrizeRecord, itemPrizeRecord);
							paramPrizeRecord.setExt1("");
							paramPrizeRecord.setUpdateDate(new Date());
							actPrizeRecordMapper.updateByRecordNo(paramPrizeRecord);
						}
					}

					BeanUtils.copyExceptNull(actPrizeRecordExt, itemPrizeRecord);
					actPrizeRecordExtMapper.updateByRecordNumber(actPrizeRecordExt);
				}
				
				Map<String,Object> dataParams = new HashMap<String,Object>();
				dataParams.put("accountNo",	actSettleReqDto.getAccountNo());
				dataParams.put("platform", actSettleReqDto.getPlatform());
				dataParams.put("activityPeriods", actSettleReqDto.getActivityPeriods());
				actAccountStatRedis.rPushAccount(dataParams, companyCode);
			}			
			return actSettleReqDto.getActivityPeriods();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}*/
	
	/**
	 * 
	 * @MethodName: advanceHierarchyReleasedPrizeRecord
	 * @Description: 层级释放所在层完成手数=要求手数的发放记录
	 * @param companyCode
	 * @param thirdApi
	 * @param oriUnfinishedPrizeRecord
	 * @return
	 * @return List<VActPrizeRecord>
	 */
	/*public List<VActPrizeRecord> advanceHierarchyReleasedPrizeRecord(String companyCode,ActApiManager thirdApi, List<VActPrizeRecord> oriUnfinishedPrizeRecord){
		List<VActPrizeRecord> unfinishedPrizeRecord = new ArrayList<VActPrizeRecord>();
		//取款层级释放
		Map<Long, List<VActPrizeRecord>> groupPrize = new HashMap<Long, List<VActPrizeRecord>>();
		for(VActPrizeRecord itemPrizeRecord:oriUnfinishedPrizeRecord){
			List<VActPrizeRecord> tmpList = groupPrize.get(itemPrizeRecord.getTaskId());
			if(tmpList == null){
				tmpList = new ArrayList<VActPrizeRecord>();
				groupPrize.put(itemPrizeRecord.getTaskId(), tmpList);
			}
			tmpList.add(itemPrizeRecord);
		}
		for(Map.Entry<Long, List<VActPrizeRecord>> entryItemGroup:groupPrize.entrySet()){
			boolean release = true;
			for(VActPrizeRecord itemPrizeRecord:entryItemGroup.getValue()){
				if(itemPrizeRecord.getFinishedTradeLots().compareTo(itemPrizeRecord.getNeedTradeLots())!=0){
					release = false;
					break;
				}
			}
			if(release){
				for(VActPrizeRecord itemPrizeRecord:entryItemGroup.getValue()){
					itemPrizeRecord.setCompanyCode(companyCode);
					ActSetting actSetting = actBonusManager.getActSetting(itemPrizeRecord.getActivityPeriods(), itemPrizeRecord.getCompanyId());
					itemPrizeRecord.setActivityName(actSetting.getActivityName());
					String pno = seqManager.updateSeq(
							SequenceEnum.ActivityBonusRecordNumber.getBusinessType(),
							SequenceEnum.ActivityBonusRecordNumber.getSeqCode());
					itemPrizeRecord.setOtherRecordNumber(pno);
					String pThirdresult = thirdApi.realReleaseBonus(itemPrizeRecord);
					itemPrizeRecord.setOtherDealNumber(pThirdresult);
					actThirdCallRecordManager.saveThirdCallRecord(companyCode,itemPrizeRecord,
							StringUtil.isNotEmpty(pThirdresult) ? "success" : "failure",
							ActThirdCallEnum.BONUS_RELEASE_BONUS);
					ActPrizeRecordExt actPrizeRecordExt = new ActPrizeRecordExt();
					BeanUtils.copyExceptNull(actPrizeRecordExt, itemPrizeRecord);
					actPrizeRecordExt.setReleaseFinish(1);
					actPrizeRecordExt.setUpdateDate(new Date());
					if (StringUtil.isNotEmpty(pThirdresult)) {
						actPrizeRecordExt.setReleasedBonus(itemPrizeRecord.getReleasedBonus());
					}
					actPrizeRecordExtMapper.updateByRecordNumber(actPrizeRecordExt);
				}
			}else{
				unfinishedPrizeRecord.addAll(entryItemGroup.getValue());
			}
		}
		return unfinishedPrizeRecord;
	}*/

	
	
	/**
	 * 
	 * @MethodName: batchSaveWaitPrizeRecord
	 * @Description: 将异常订单到达结算时间的数据放置另外一张表
	 * @param companyCode
	 * @return void
	 */
	/*public void batchSaveWaitPrizeRecord(String companyCode){
		Calendar waitSettlementTime = Calendar.getInstance();
		waitSettlementTime.add(Calendar.MINUTE, 5);
		ActSettleWaitPrizeRecord actSettleWaitPrizeRecord = new ActSettleWaitPrizeRecord();
		actSettleWaitPrizeRecord.setCompanyCode(companyCode);
		actSettleWaitPrizeRecord.setSettlementTime(waitSettlementTime.getTime());
		actSettleWaitPrizeRecordMapper.insert(actSettleWaitPrizeRecord);
	}*/
	
	
	
	

	/*@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void batchSettlementAccountActivity(String companyCode) {
		// 将已经到结算时间的应发记录，复制内容到另外一张表，由另外一个job负责扫描
		Calendar settlementTime = Calendar.getInstance();
		// 结算时间推后15分钟
		// settlementTime.add(Calendar.MINUTE, -15);
		// 批量更新到达结算时间并且无应发记录的客户活动关系表
		Calendar nowdate = Calendar.getInstance();
		nowdate.add(Calendar.DATE, -10);
		Map<String, Object> actSettingParams = new HashMap<String, Object>();
		actSettingParams.put("pointDate", nowdate.getTime());
		actSettingParams.put("companyId", ActCompany.getKeyByCode(companyCode));
		List<ActSetting> listActSetting = actSettingMapper.getActiveActSettingListByParams(actSettingParams);
		for (ActSetting actSetting : listActSetting) {
			ActAccountActivity params = new ActAccountActivity();
			params.setCompanyCode(companyCode);
			params.setSettlementTime(settlementTime.getTime());
			params.setUpdateDate(new Date());
			params.setActivityPeriods(actSetting.getActivityPeriods());
			
			List<ActAccountActivity> listData = actAccountActivityMapper.findSettle(params);
			for(ActAccountActivity itemActAccountActivity:listData){
				itemActAccountActivity.setRealSettlement(true);
				itemActAccountActivity.setSettlement(true);
				itemActAccountActivity.setCompanyCode(companyCode);
				actAccountActivityMapper.update(itemActAccountActivity);
				
				Map<String,Object> dataParams = new HashMap<String,Object>();
				dataParams.put("accountNo", itemActAccountActivity.getAccountNo());
				dataParams.put("platform", itemActAccountActivity.getPlatform());
				dataParams.put("activityPeriods", itemActAccountActivity.getActivityPeriods());
				actAccountActStatRedis.rPushAccountPeriods(dataParams, companyCode);
			}
		}

//		ActAccountActivity actAccountActivity = new ActAccountActivity();
//		actAccountActivity.setCompanyCode(companyCode);
//		actAccountActivity.setSettlementTime(settlementTime.getTime());
//		actAccountActivity.setUpdateDate(new Date());
//		actAccountActivityMapper.updateSettle(actAccountActivity);
	}*/

	/**
	 * 
	 * @MethodName: blackSettlement
	 * @Description: 黑名单清算
	 * @param actSettleReqDto
	 * @return
	 * @return String
	 */
	/*@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String blackSettlement(ActSettleReqDTO actSettleReqDto) {
		if (actSettleReqDto == null || StringUtil.isEmpty(actSettleReqDto.getAccountNo())|| 
				StringUtil.isEmpty(actSettleReqDto.getActivityPeriods())) {
			return null;
		}
		String companyCode = ActCompany.getCodeByKey(actSettleReqDto.getCompanyId());
		logger.info("开始处理黑名单提前清算的活动，提前结算账号{},平台{}相关数据，环境{},活动编号:{}", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform(),
				actSettleReqDto.getEnv(),actSettleReqDto.getActivityPeriods());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", actSettleReqDto.getAccountNo());
		params.put("platform", actSettleReqDto.getPlatform());
		params.put("env", actSettleReqDto.getEnv());
		params.put("activityPeriods", actSettleReqDto.getActivityPeriods());
		params.put("companyCode", companyCode);
		List<ActJoinQualify> listJoinQualify = actJoinQualifyMapper.selectByAccountParams(params);
		actSettleReqDto.setType(ActSettlementType.BLACK.getCode());
		actSettleReqDto.setApproveDate(new Date());
		boolean push = false;
		for(ActJoinQualify itemJoinQualify:listJoinQualify){
			String rActivityPeriods = advanceAccountPeriodsSettlement(actSettleReqDto,itemJoinQualify);
			if(StringUtil.isNotEmpty(rActivityPeriods)){
				ActSettleReqDto pushDataSettle = new ActSettleReqDto();
				BeanUtils.copyExceptNull(pushDataSettle, actSettleReqDto);
				pushDataSettle.setType(ActSettlementType.BLACK.getCode());
				pushDataSettle.setAccountNo(itemJoinQualify.getAccountNo());
				pushDataSettle.setPlatform(itemJoinQualify.getPlatform());
				pushDataSettle.setActivityPeriods(rActivityPeriods);
				pushDataSettle.setEnv(itemJoinQualify.getEnv());
				actWithdrawAccountRedis.rPushAccount(pushDataSettle, companyCode);
				push = true;
			}
		}
		logger.info("结束黑名单处理提前清算的活动，提前结算账号{},平台{},环境{}相关数据，活动编号:{}", actSettleReqDto.getAccountNo(), actSettleReqDto.getPlatform(),
				actSettleReqDto.getEnv(),actSettleReqDto.getActivityPeriods());
		if(push){
			return actSettleReqDto.getActivityPeriods();
		} else{
			return null;
		}
		
		return null;
	}*/
	
	/**
	 * 
	 * @MethodName: groupBlackAccount
	 * @Description: 处理集团黑名单清算逻辑
	 * @param accountNo
	 * @return void
	 */
	/*@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void groupBlackAccount(String accountNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar nowdate = Calendar.getInstance();
		nowdate.add(Calendar.DATE, -1);
		params.put("pointDate", nowdate.getTime());
		params.put("excludeType", ActSettingType.QT.getValue());

		List<ActSetting> listActSetting = actSettingMapper.getActSettingListByParams(params);
		for (ActSetting itemActSetting : listActSetting) {
			try {
				ActSettleReqDto actSettleReqDto = new ActSettleReqDto();
				actSettleReqDto.setAccountNo(accountNo);
				actSettleReqDto.setActivityPeriods(itemActSetting.getActivityPeriods());
				actSettleReqDto.setCompanyId(itemActSetting.getCompanyId());
				blackSettlement(actSettleReqDto);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}*/
	
	/*@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String advanceAccountPeriodsSettlement(ActSettleReqDto actSettleReqDto,
			ActJoinQualify actJoinQualify) {
		String execPeriods = null;
		if ((actJoinQualify != null && !actJoinQualify.getIsSettlement())) {
			Map<String, Object> params = new HashMap<String, Object>();
			String companyCode = ActCompany.getCodeByKey(actJoinQualify.getCompanyId());

			// 更新join_qualify相关表,将资格匹配相关的数据先做结算标识
			if (actJoinQualify.getSettlementTime().compareTo(actSettleReqDto.getApproveDate()) > 0) {
				params.put("accountNo", actJoinQualify.getAccountNo());
				params.put("platform", actJoinQualify.getPlatform());
				params.put("env", actJoinQualify.getEnv());
				params.put("activityPeriods", actJoinQualify.getActivityPeriods());
				params.put("withdrawSettleTime", actSettleReqDto.getApproveDate());
				Integer updateCount = actJoinQualifyMapper.updateSettlementAccount(params);
				if ((updateCount != null && updateCount > 0)) {
					logger.info("开始结算账号{}平台{}活动{}的结算数据", actJoinQualify.getAccountNo(), actJoinQualify.getPlatform(),
							actJoinQualify.getActivityPeriods());
					List<ActPrizeRecord> waitingPrize = null;
					if (!ActSettlementType.BLACK.getCode().equals(actSettleReqDto.getType())) {
						// 检查应发列表是否有数据,有的话，将这部分数据记录到另外一张表，由另外job扫描应发转可发的数据,再将这些数据进行结算
						ActPrizeRecord paramsPrizeRecord = new ActPrizeRecord();
						paramsPrizeRecord.setCompanyCode(companyCode);
						paramsPrizeRecord.setAccountNo(actJoinQualify.getAccountNo());
						paramsPrizeRecord.setActivityPeriods(actJoinQualify.getActivityPeriods());
						paramsPrizeRecord.setGuestPlatForm(actJoinQualify.getPlatform());
						waitingPrize = actPrizeRecordMapper.findWaitPrizeRecords(paramsPrizeRecord);
						logger.info("账号:{}平台{},活动编号 {},应发列表数据量{}", actJoinQualify.getAccountNo(),
								actJoinQualify.getPlatform(), actJoinQualify.getActivityPeriods(), waitingPrize.size());
					}

					if (waitingPrize != null && !waitingPrize.isEmpty()) {
						logger.info("账号:{}平台{},活动编号 {},只结算部分信息", actSettleReqDto.getAccountNo(),
								actSettleReqDto.getPlatform(), actJoinQualify.getActivityPeriods());
						ActSettleWaitPrizeRecord actSettleWaitPrizeRecord = new ActSettleWaitPrizeRecord();
						actSettleWaitPrizeRecord.setActivityPeriods(actJoinQualify.getActivityPeriods());
						actSettleWaitPrizeRecord.setAccountNo(actJoinQualify.getAccountNo());
						actSettleWaitPrizeRecord.setCompanyCode(companyCode);
						actSettleWaitPrizeRecord.setPlatform(actJoinQualify.getPlatform());
						actSettleWaitPrizeRecordMapper.insert(actSettleWaitPrizeRecord);
						
						ActAccountActivity actAccountActivity = new ActAccountActivity();
						actAccountActivity.setActivityPeriods(actJoinQualify.getActivityPeriods());
						actAccountActivity.setAccountNo(actSettleReqDto.getAccountNo());
						actAccountActivity.setCompanyCode(companyCode);
						actAccountActivity.setPlatform(actSettleReqDto.getPlatform());
						actAccountActivity.setSettleType(actSettleReqDto.getType());
						actAccountActivityMapper.update(actAccountActivity);
					} else {
						// 更新act_account_activity的真实结算标识以及结算时间
						ActAccountActivity actAccountActivity = new ActAccountActivity();
						actAccountActivity.setActivityPeriods(actJoinQualify.getActivityPeriods());
						actAccountActivity.setAccountNo(actJoinQualify.getAccountNo());
						actAccountActivity.setSettlement(true);
						actAccountActivity.setRealSettlement(true);
						actAccountActivity.setSettlementTime(actSettleReqDto.getApproveDate());
						actAccountActivity.setCompanyCode(companyCode);
						actAccountActivity.setPlatform(actJoinQualify.getPlatform());
						actAccountActivity.setSettleType(actSettleReqDto.getType());
						actAccountActivityMapper.update(actAccountActivity);
						logger.info("账号:{}平台{},活动编号 {},结算成功", actSettleReqDto.getAccountNo(),
								actSettleReqDto.getPlatform(), actJoinQualify.getActivityPeriods());
					}
					execPeriods = actJoinQualify.getActivityPeriods();
				}
			} else {
				logger.warn("当前平台{},账号{},活动编号{}结算时间{}小于取款时间{}，将由结算逻辑处理", actSettleReqDto.getPlatform(),
						actSettleReqDto.getAccountNo(), actJoinQualify.getActivityPeriods(),
						DateUtil.formatDateToString(actJoinQualify.getSettlementTime(), "yyyy-MM-dd HH:mm:ss"),
						DateUtil.formatDateToString(actSettleReqDto.getApproveDate(), "yyyy-MM-dd HH:mm:ss"));
			}
			Map<String,Object> dataParams = new HashMap<String,Object>();
			dataParams.put("accountNo", actSettleReqDto.getAccountNo());
			dataParams.put("platform", actSettleReqDto.getPlatform());
			dataParams.put("activityPeriods", actSettleReqDto.getActivityPeriods());
			actAccountActStatRedis.rPushAccountPeriods(dataParams, companyCode);
		} else {
			if (actJoinQualify == null) {
				logger.info("当前平台{},账号{},活动编号{},没有参与资格", actSettleReqDto.getPlatform(), actSettleReqDto.getAccountNo(),
						actSettleReqDto.getActivityPeriods());
			} else {
				logger.info("当前平台{},账号{},活动编号{},已经结算，不需要重新更新", actSettleReqDto.getPlatform(),
						actSettleReqDto.getAccountNo(), actSettleReqDto.getActivityPeriods());
			}
		}

		return execPeriods;
	}*/
	
	/**
	 * 
	 * @MethodName: accountIsSettlement
	 * @Description: 判断用户是否已经结算
	 * @param actSettleReqDto accountNO:账号, platform:平台,activityPeriods:活动编号,env:环境(real,demo)
	 * @return
	 * @return int -1:用户没有参加活动 0:已经结算 1：未结算
	 */
	/*public int accountIsSettlement(ActSettleReqDto actSettleReqDto){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", actSettleReqDto.getAccountNo());
		params.put("platform", actSettleReqDto.getPlatform());
		params.put("activityPeriods", actSettleReqDto.getActivityPeriods());
		params.put("env", actSettleReqDto.getEnv());
		ActJoinQualify actJoinQualify = actJoinQualifyMapper.selectSettlementAccount(params);
		if(actJoinQualify == null){
			return -1;
		}else if (actJoinQualify.getIsSettlement()){
			return 0;
		}else{
			return 1;
		}
	}*/
	
	/**
	 * 
	 * @MethodName: advanceRegeneratePrizeRecord
	 * @Description: 处理取款时，强制用户重新发放记录
	 * @param companyCode
	 * @return
	 * @return List<ActSettleReqDto>
	 */
	/*public List<ActSettleReqDTO> advanceRegeneratePrizeRecord(String companyCode) {
		// 获取取款的账号列表
		Long companyId = ActCompany.getKeyByCode(companyCode);
		List<ActSettleReqDto> withdrawActSettleReqDto = actWithdrawAccountRedis.popAccount(companyCode);
		if (withdrawActSettleReqDto != null && !withdrawActSettleReqDto.isEmpty()) {
			for (ActSettleReqDto dto : withdrawActSettleReqDto) {
				// 处理取款时，将未发放的记录全部处理完发放
				try {
					logger.info("取款调用重发物品逻辑,参数:{},{},{},{}", companyId, dto.getActivityPeriods(), dto.getAccountNo(),
							dto.getPlatform());
					actHandleDubboService.autoGenerateRecordByAccount(companyId, dto.getActivityPeriods(),
							dto.getAccountNo(), dto.getPlatform(), false);
				} catch (Exception e) {
					logger.error("--->提前结算，处理未发放记录出现错误");
					logger.error(e.getMessage(), e);
				}
			}
		}
		return withdrawActSettleReqDto;
	}*/
	
}
