package com.gwghk.ims.activity.manager.prize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ActAccountTradeStat;
import com.gwghk.ims.activity.dao.entity.ActConditionSettingWrapper;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSetting;
import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ActTradeRecord;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ActAccountTradeStatDao;
import com.gwghk.ims.activity.dao.inf.ActMaintenanceSettingDao;
import com.gwghk.ims.activity.dao.inf.ActTradeRecordDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordExtDao;
import com.gwghk.ims.activity.dao.inf.VActPrizeRecordDao;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.activity.manager.ActConditionSettingManager;
import com.gwghk.ims.activity.manager.ImsTaskRecordManager;
import com.gwghk.ims.activity.redis.ActAccountPrizeRedis;
import com.gwghk.ims.activity.redis.ActPrizeRecordRedis;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActThirdCallEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.enums.SeqEnum;
import com.gwghk.ims.common.inf.SeqDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActBonusManager
 * @Description: 贈金完成手数逻辑
 * @author lawrence
 * @date 2017年10月23日
 *
 */
@Component
@Transactional
public class ActBonusManager {

	private static Logger logger = LoggerFactory.getLogger(ActBonusManager.class);

	@Autowired
	private VActPrizeRecordDao vActPrizeRecordDao;

	@Autowired
	private ApiDistributeManager apiDistributeManager;
	
	@Autowired
	private ActThirdCallRecordManager actThirdCallRecordManager;
	
	@Autowired
	private ActPrizeRecordRedis actPrizeRecordExtRedis;
	
	@Autowired
	private ImsPrizeRecordExtDao imsPrizeRecordExtDao;
	
	@Autowired
	private ImsPrizeRecordDao imsPrizeRecordDao;
	
	@Autowired
	private SeqDubboService seqDubboService;
	
	@Autowired
	private ActMaintenanceSettingDao actSysMaintainDao;
	
	@Autowired
	private ActConditionSettingManager actConditionManager;
	
	@Autowired
	private ActTradeRecordDao actTradeRecordDao;
	
	@Autowired
	private ActAccountPrizeRedis actAccountPrizeRedis;
	
	@Autowired
	private ActAccountTradeStatDao actAccountTradeStatDao;

	@Autowired
	private ImsTaskRecordManager imsTaskRecordManager;
	
	@Autowired
	private ActAccountActiviStatManager actAccActStatManager;
	
	
	/**
	 * 取消发放记录
	 * @param actPrizeRecord
	 * @param platform
	 * @param companyCode
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int cancelBonus(List<VActPrizeRecord> actPrizeRecord,String cancelReason,String platform,String companyCode) {
		int cancelCount=0;
		
		// 获取第三方api
		ActApiManager thirdApi = apiDistributeManager.getApi(companyCode, platform);
		
		Map<String, Map<String, String>> resultData = thirdApi.realCancelBonus(actPrizeRecord, cancelReason);
		Map<String, String> callResults = new HashMap<String, String>();
		List<String> updateDeal = new ArrayList<String>();
		BigDecimal discountLot=BigDecimal.ZERO;
		
		
		// 获取取消成功的贈金订单号
		Map<String, String> deals = resultData.get("success");
		if (deals != null) {
			callResults.putAll(deals);
		}
		for (VActPrizeRecord itemPrizeRecord : actPrizeRecord) {
			updateDeal.add(itemPrizeRecord.getRecordNo());
			//更新发放记录已经取消了
			if(deals.containsKey(itemPrizeRecord.getThirdRecordNo())) {
				itemPrizeRecord.setGivedStatus( IssuingStatusEnum.issue_cancel.getValue());
				if(itemPrizeRecord.getFinishedTradeLots()==null)
					itemPrizeRecord.setFinishedTradeLots(BigDecimal.ZERO);
				discountLot=discountLot.add( 
						itemPrizeRecord.getItemAmount().multiply(itemPrizeRecord.getNeedTradeLots().add(itemPrizeRecord.getFinishedTradeLots().negate())
						.divide(itemPrizeRecord.getNeedTradeLots())));
				
			}else {
				cancelCount+=1;
			}
			vActPrizeRecordDao.updateGivedStatus(itemPrizeRecord);
		}
		
		//保存调用记录
		actThirdCallRecordManager.saveThirdCallRecord(companyCode,actPrizeRecord, callResults, ActThirdCallEnum.BONUS_CANCEL_BONUS,null);
		
		//更新用户状态
		updateAccountActData(actPrizeRecord.get(0).getAccountNo(), actPrizeRecord.get(0).getActNo(), actPrizeRecord.get(0).getEnv(), 
				actPrizeRecord.get(0).getPlatform(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, discountLot);
		
		return cancelCount;
	}
	
	/**
	 * 
	 * @param string 
	 * @MethodName: releaseDepositBonus
	 * @Description: 结算，批量释放入金贈金完成手数的贈金
	 * @param companyId
	 * @param actNo
	 * @param settlementTime
	 * @return void
	 */
	public void releaseDepositBonus(Long companyId, String actNo, Date settlementTime) {
		String companyCode=CompanyEnum.getCodeById(companyId);
		VActPrizeRecord vActPrizeRecord = new VActPrizeRecord();
		vActPrizeRecord.setCompanyId(companyId);
		vActPrizeRecord.setSettlementTime(settlementTime);
		List<VActPrizeRecord> limitPrizereRecord = vActPrizeRecordDao.findSettlementWithGoldTradePrizeRecordLimitType3(vActPrizeRecord);
		ActApiManager thirdApi = null;
		String thirdresult = null;
		if (limitPrizereRecord != null && !limitPrizereRecord.isEmpty()) 
			return;
			
		for (VActPrizeRecord itemPrizeRecord : limitPrizereRecord) {
			if (itemPrizeRecord.getFinishedTradeLots().compareTo(itemPrizeRecord.getNeedTradeLots()) != 0) 
				continue;
			
			String pno = seqDubboService.getSeq(SeqEnum.ActivityBonusRecordNumber.getSeqCode(), companyId); 
			itemPrizeRecord.setOtherRecordNumber(pno);
			itemPrizeRecord.setCompanyId(companyId);
			thirdApi = apiDistributeManager.getApi(companyCode, itemPrizeRecord.getPlatform());
			if (thirdApi == null) 
				return;
				
			ActPrizeRecordExt actPrizeRecordExt = new ActPrizeRecordExt();
			thirdresult = thirdApi.realReleaseBonus(itemPrizeRecord);
			itemPrizeRecord.setThirdRecordNo(thirdresult);
			
			actThirdCallRecordManager.saveThirdCallRecord(companyCode,itemPrizeRecord, StringUtil.isNotEmpty(thirdresult) ? "success" : "failure",
					ActThirdCallEnum.BONUS_RELEASE_BONUS);
			
			if (StringUtil.isNotEmpty(thirdresult)) {
				actPrizeRecordExt.setReleasedBonus(itemPrizeRecord.getReleasedBonus());
			}
			actPrizeRecordExt.setRecordNumber(itemPrizeRecord.getRecordNo());
			actPrizeRecordExt.setCompanyId(companyId);
			actPrizeRecordExt.setReleaseFinish(1);
			actPrizeRecordExt.setActNo(itemPrizeRecord.getActNo());
			try {
				actPrizeRecordExtRedis.rPushRecordExt(actPrizeRecordExt, companyCode);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				imsPrizeRecordExtDao.updateByRecordNumber(actPrizeRecordExt);
			}
		}
		
	}
	
	/**
	 * 
	 * @param string 
	 * @MethodName: cancelBonus
	 * @Description: 结算时间到点释放未完成手数的贈金
	 * @param companyId
	 * @param actNo
	 * @param settleTime
	 * @return void
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void cancelBonus(Long companyId, String actNo, Date settleTime) {
		if (CompanyEnum.hx.getId().equals(companyId)) {
			apiDistributeManager.resetHxSid();
		}

		int tryCount = 0;
		boolean loopContinue = true;
		VActPrizeRecord pActPrizeRecord = new VActPrizeRecord();
		pActPrizeRecord.setSettlementTime(settleTime);
		pActPrizeRecord.setCompanyId(companyId);
		pActPrizeRecord.setActNo(actNo);

		Map<String, List<Map<String, Date>>> periodsExcludeDate = loadActivityMaintainData();
		//处理除入金贈金型的活动发放记录结算
		List<VActPrizeRecord> listActPrizeRecord = vActPrizeRecordDao.findSettlementWithGoldTradePrizeRecord(pActPrizeRecord);
		loopContinue = listActPrizeRecord != null && !listActPrizeRecord.isEmpty();
		long lastId = 0l;
		ExecutorService pool = Executors.newFixedThreadPool(32);
		try {
			while (loopContinue) {
				tryCount++;
				lastId = loopCancelBonus(companyId, listActPrizeRecord, lastId, pool,periodsExcludeDate);
				pActPrizeRecord.setId(lastId);
				listActPrizeRecord = vActPrizeRecordDao.findSettlementWithGoldTradePrizeRecord(pActPrizeRecord);
				if (tryCount >= 5 || listActPrizeRecord == null || listActPrizeRecord.isEmpty()) {
					break;
				}
			}
			pActPrizeRecord.setId(null);
			pActPrizeRecord.setReleaseType(3);
			//处理入金贈金型的活动发放记录结算
			listActPrizeRecord = vActPrizeRecordDao.findSettlementWithGoldTradePrizeRecord(pActPrizeRecord);
			loopCancelBonus(companyId, listActPrizeRecord, lastId, pool,periodsExcludeDate);
//			//处理代币发放记录结算
//			listActPrizeRecord = vActPrizeRecordMapper.findSettlementTokenCoinTradePrizeRecord(pActPrizeRecord);
//			loopCancelBonus(companyCode, listActPrizeRecord, lastId, pool,periodsExcludeDate);
		} finally {
			pool.shutdown();
		}
	}

	/**
	 * 
	 * @MethodName: loopCancelBonus
	 * @Description: 处理循环取消贈金任务
	 * @param companyId
	 * @param listActPrizeRecord
	 * @param lastId
	 * @param pool
	 * @return
	 * @return long
	 */
	private long loopCancelBonus(Long companyId, List<VActPrizeRecord> listActPrizeRecord, long lastId,
			ExecutorService pool,Map<String, List<Map<String, Date>>> periodsExcludeDate) {
		
		if(listActPrizeRecord==null || listActPrizeRecord.size()<=0)
			return lastId;
		
		String companyCode=CompanyEnum.getCodeById(companyId);
		
		ActApiManager thirdApi;
		Map<ActApiManager, List<VActPrizeRecord>> exec = new HashMap<ActApiManager, List<VActPrizeRecord>>();
		//通知释放账号列表
		Set<String> notifyAccountNo = new HashSet<String>();
		for (VActPrizeRecord itemPrizeRecord : listActPrizeRecord) {
			// 获取第三方api
			thirdApi = apiDistributeManager.getApi(companyCode, itemPrizeRecord.getPlatform());
			itemPrizeRecord.setCompanyId(companyId);
			if (thirdApi == null) {
				logger.error(">>>无法获取第三方API,使用参数companyCode:{},platform:{}",companyCode,itemPrizeRecord.getPlatform());
				return lastId;
			}
			
			List<VActPrizeRecord> readyActPrizeRecord = exec.get(thirdApi);
			if (readyActPrizeRecord == null) {
				readyActPrizeRecord = new ArrayList<VActPrizeRecord>();
				exec.put(thirdApi, readyActPrizeRecord);
			}
				
			if(!ActItemTypeEnum.WITHGOLD.getCode().equals(itemPrizeRecord.getItemType()))
				continue;
			
			if(itemPrizeRecord.getNeedTradeLots().compareTo(itemPrizeRecord.getFinishedTradeLots())!=0){
				
				// 判断是否交易数据已经释放完毕
				Map<String, Object> params = new HashMap<String, Object>();
				ActConditionSettingWrapper actConditionCustomInfoWrapper = actConditionManager.findCustCondtionSettingWrap(itemPrizeRecord.getActNo());
				List<String> realProducLimit = actConditionCustomInfoWrapper.getRealProducts();
				List<Map<String, Date>> excludeDate = periodsExcludeDate.get(itemPrizeRecord.getActNo());
				if (realProducLimit != null && !realProducLimit.isEmpty()) {
					params.put("product", realProducLimit);
				}
				if (excludeDate != null && !excludeDate.isEmpty()) {
					params.put("excludeDate", excludeDate);
				}
				params.put("tradeStartTime", itemPrizeRecord.getWithdrawalStartTime());
				params.put("tradeEndTime", itemPrizeRecord.getSettlementTime());
				params.put("inTradeStartTime", itemPrizeRecord.getWithdrawalStartTime());
				params.put("inTradeEndTime", itemPrizeRecord.getSettlementTime());
				if(itemPrizeRecord.getCumulativeTradeId()!=null){
					params.put("tradeId", itemPrizeRecord.getCumulativeTradeId());
				}
				if (itemPrizeRecord.getWithdrawalStartTradeId() != null
						&& !ActEnvEnum.DEMO.getValue().equals(itemPrizeRecord.getEnv())) {
					params.put("receiveTradeId", itemPrizeRecord.getWithdrawalStartTradeId());
				}
				String accountNo = null;
				/*if (StringUtil.isNotEmpty(itemPrizeRecord.getRefAccountNo())
						&& ActEnvEnum.DEMO.getValue().equals(itemPrizeRecord.getEnv())) {
					accountNo = itemPrizeRecord.getRefAccountNo();
				} else {
					*/accountNo = itemPrizeRecord.getAccountNo();
				/*}*/
				params.put("accountNo", accountNo);
				params.put("platform", itemPrizeRecord.getPlatform());
				params.put("env", ActEnvEnum.REAL.getValue());

				List<ActTradeRecord> tradeList = actTradeRecordDao.getTradeRecordList(params);
				if (tradeList != null && !tradeList.isEmpty()) {
					notifyAccountNo.add(accountNo);
					logger.warn("发放记录{}存在待释放数据，暂时停止该记录结算",itemPrizeRecord.getRecordNo());
					continue;
				}
			}
				
			readyActPrizeRecord.add(itemPrizeRecord);
			
			lastId = itemPrizeRecord.getId();
		}

		if(!notifyAccountNo.isEmpty()){
			actAccountPrizeRedis.rPushTradeAccount(notifyAccountNo, companyCode);
		}
		for (Map.Entry<ActApiManager, List<VActPrizeRecord>> apiPrizeRecord : exec.entrySet()) {
			// 取消贈金
			cancelBonus(apiPrizeRecord.getValue(), apiPrizeRecord.getKey(), companyId, pool);
		}
		exec.clear();
		return lastId;
	}
	
	/**
	 * 
	 * @MethodName: cancelBonus
	 * @Description: 取消贈金
	 * @param actPrizeRecord
	 * @param thirdApi
	 * @param companyCode
	 * @return void
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void cancelBonus(List<VActPrizeRecord> actPrizeRecord, ActApiManager thirdApi, Long companyId,
			ExecutorService pool) {
		String companyCode=CompanyEnum.getCodeById(companyId);
		try {
			Map<Integer, List<VActPrizeRecord>> groupMap = new ConcurrentHashMap<>();
			int i = 0;
			for (VActPrizeRecord vr : actPrizeRecord) {
				i++;
				int key = i / 10;
				if (groupMap.get(key) == null) {
					List<VActPrizeRecord> vprList = new ArrayList<VActPrizeRecord>();
					vprList.add(vr);
					groupMap.put(key, vprList);
				} else {
					List<VActPrizeRecord> vprList = groupMap.get(key);
					vprList.add(vr);
				}
			}
			CountDownLatch countDownLatch = new CountDownLatch(groupMap.size());
			// try{
			for (Map.Entry<Integer, List<VActPrizeRecord>> val : groupMap.entrySet()) {

				pool.execute(new Runnable() {
					@Override
					public void run() {
						try{
							Map<String, Map<String, String>> resultData = thirdApi.realCancelBonus(val.getValue(), "客户活动结算时间已经到达，扣回未完成任务贈金");
							Map<String, String> callResults = new HashMap<String, String>();
							List<String> updateDeal = new ArrayList<String>();
							// 获取取消成功的贈金订单号
							Map<String, String> deals = resultData.get("success");
							if (deals != null) {
								callResults.putAll(deals);
							}
							for (VActPrizeRecord itemPrizeRecord : val.getValue()) {
								updateDeal.add(itemPrizeRecord.getRecordNo());
								//更新发放记录已经取消了
								itemPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
								vActPrizeRecordDao.updateGivedStatus(itemPrizeRecord);
								/*saveActPrizeRecordExtCancel(companyId, itemPrizeReocrd.getId(),
										itemPrizeReocrd.getRecordNo(),itemPrizeReocrd.getActNo());*/
							}
	
							ActPrizeRecordExt actPrizeRecordExt = new ActPrizeRecordExt();
							actPrizeRecordExt.setCompanyId(companyId);
							actPrizeRecordExt.setReleaseFinish(2);
							actPrizeRecordExt.setRecordNumbers(updateDeal);
							actPrizeRecordExt.setUpdateDate(new Date());
							// 批量更新释放标识(将由发放记录job进行更新)
							// actPrizeRecordExtMapper.updateBatchByRecordNumbers(actPrizeRecordExt);
							try {
								actPrizeRecordExtRedis.rPushRecordExt(actPrizeRecordExt, companyCode);
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								imsPrizeRecordExtDao.updateBatchByRecordNumbers(actPrizeRecordExt);
							}
							actThirdCallRecordManager.saveThirdCallRecord(companyCode,val.getValue(), callResults, ActThirdCallEnum.BONUS_CANCEL_BONUS,null);
						}catch(Exception ex){
							logger.error(ex.getMessage(),ex);
						}finally{
							countDownLatch.countDown();
						}
					}
				});
			}
			countDownLatch.await(3, TimeUnit.MINUTES); // 等待所有子线程执行完
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
	
	/**
	 * 
	 * @MethodName: loadActivityMaintainData
	 * @Description: 加载活动维护列表
	 * @return
	 * @return Map<String,List<Map<String,Date>>>
	 */
	public Map<String, List<Map<String, Date>>> loadActivityMaintainData(){
		Map<String, List<Map<String, Date>>> periodsExcludeDate = new HashMap<String, List<Map<String, Date>>>();
		List<ActMaintenanceSetting> listSysMaintain = actSysMaintainDao.findAll();
		List<Map<String, Date>> item = null;
		for (ActMaintenanceSetting itemMaintain : listSysMaintain) {
			item = periodsExcludeDate.get(itemMaintain.getActivityPeriods());
			if (item == null) {
				item = new ArrayList<Map<String, Date>>();
				periodsExcludeDate.put(itemMaintain.getActivityPeriods(), item);
			}
			Map<String, Date> betweenDate = new HashMap<String, Date>();
			betweenDate.put("dateBegin", itemMaintain.getStartTime());
			betweenDate.put("dateEnd", itemMaintain.getEndTime());
			item.add(betweenDate);
		}
		return periodsExcludeDate;
	}
	
	/**
	 * 处理代币发放
	 * @param vActPrizeRecord
	 * @param bonusFlag
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void dealTokenCoin(VActPrizeRecord vActPrizeRecord, String bonusFlag) {
		if(!ActItemTypeEnum.TOKENCOIN.getValue().equals(vActPrizeRecord.getItemType()))
			return;
			
		sendTokenCoin(vActPrizeRecord,bonusFlag);
	}
	
	/**
	 * 发送代币
	 * @param vActPrizeRecord
	 * @param bonusFlag
	 */
	private void sendTokenCoin(VActPrizeRecord vActPrizeRecord, String bonusFlag) {
		ActApiManager thirdApi;
		String thirdResult;
		
		String companyCode=CompanyEnum.getCodeById(vActPrizeRecord.getCompanyId());
		
		thirdApi = apiDistributeManager.getApi(companyCode, vActPrizeRecord.getPlatform());

		if (thirdApi == null) {
			return;
		}
		
		ActPrizeRecordExt actPrizeRecordExt = new ActPrizeRecordExt();
		actPrizeRecordExt.setCompanyId(vActPrizeRecord.getCompanyId());
		actPrizeRecordExt.setDealStartTime(new Date());
		actPrizeRecordExt.setRecordNumber(vActPrizeRecord.getRecordNo());
		
		ImsPrizeRecord dbActPrizeRecord = new ImsPrizeRecord();
		dbActPrizeRecord.setCompanyId(vActPrizeRecord.getCompanyId());
		dbActPrizeRecord.setRecordNo(vActPrizeRecord.getRecordNo());
		
		thirdResult = thirdApi.realAddBonus(vActPrizeRecord);
		if (StringUtil.isNotEmpty(thirdResult)) {
			vActPrizeRecord.setDealNumber(thirdResult);
			actPrizeRecordExt.setDealNumber(thirdResult);
			actPrizeRecordExt.setFinishTime(new Date());
			actPrizeRecordExt.setDealEndTime(new Date());
			actPrizeRecordExt.setUpdateDate(new Date());
			actPrizeRecordExt.setReleasedBonus(BigDecimal.ZERO);

			dbActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
		} else {
			dbActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
		}
		imsPrizeRecordExtDao.updateByRecordNumber(actPrizeRecordExt);

		dbActPrizeRecord.setUpdateDate(new Date());
		imsPrizeRecordDao.update(dbActPrizeRecord);
		logger.info("<--第三方到活动中心同步结果！发放记录流水号:{},第三方流水号:{}", vActPrizeRecord.getRecordNo(), thirdResult);

		vActPrizeRecord.setDealNumber(thirdResult);
		vActPrizeRecord.setOtherRecordNumber(vActPrizeRecord.getRecordNo());
		actThirdCallRecordManager.saveThirdCallRecord(companyCode, vActPrizeRecord,
				IssuingStatusEnum.issue_success.getValue().equals(dbActPrizeRecord.getGivedStatus())
						? "success" : "failure",
				ActThirdCallEnum.BONUS_ADD_BONUS);
		
		/*Map<String,Object> dataParams = new HashMap<String,Object>();
		dataParams.put("accountNo", vActPrizeRecord.getAccountNo());
		dataParams.put("platform", vActPrizeRecord.getPlatform());
		dataParams.put("activityPeriods", vActPrizeRecord.getActivityPeriods());
		actAccountStatRedis.rPushAccount(dataParams, companyCode);*/

//		saveSimpleTokenCoinAccountActivityStat(itemPrizeRecord);
		
	}
	
	/**
	 * 
	 * @MethodName: calAccountPeriodsFinishTradeLot
	 * @Description: 活动，平台，用户所对应的发放记录，只处理完成手数为0的未发放记录
	 * @param listActPrizeRecord
	 * @param periodsExcludeDate
	 * @param execDate
	 * @param companyCode
	 * @param bonusFlag
	 * @param actFinishTaskReqDtoMap
	 * @return void
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void calZeroAccountPeriodsFinishTradeLot(VActPrizeRecord vActPrizeRecord) {
		
		String companyCode=CompanyEnum.getCodeById(vActPrizeRecord.getCompanyId());
		
		// 第三方返回结果
		String pThirdresult = null;
		Map<String, Object> data = new HashMap<String, Object>();

		ActPrizeRecordExt actPrizeRecordExt = null;
		ActApiManager thirdApi = null;
		ImsPrizeRecord dbActPrizeRecord = null;

		logger.debug("处理订单号{}贈金", vActPrizeRecord.getRecordNo());
		data.clear();

		actPrizeRecordExt = new ActPrizeRecordExt();
		actPrizeRecordExt.setRecordNumber(vActPrizeRecord.getRecordNo());
		actPrizeRecordExt.setCompanyId(vActPrizeRecord.getCompanyId());

		dbActPrizeRecord = new ImsPrizeRecord();
		dbActPrizeRecord.setRecordNo(vActPrizeRecord.getRecordNo());
		dbActPrizeRecord.setCompanyId(vActPrizeRecord.getCompanyId());

		if (!vActPrizeRecord.getGivedStatus().equals(IssuingStatusEnum.waiting.getValue())) {
			// 获取第三方api
			thirdApi = apiDistributeManager.getApi(companyCode, vActPrizeRecord.getPlatform());
		}

		if (thirdApi == null) 
			return;
		
		if (StringUtil.isEmpty(vActPrizeRecord.getDealNumber())) {
			pThirdresult = thirdApi.realAddBonus(vActPrizeRecord);
			if (StringUtil.isNotEmpty(pThirdresult)) {
				vActPrizeRecord.setDealNumber(pThirdresult);
				actPrizeRecordExt.setDealNumber(pThirdresult);
				actPrizeRecordExt.setFinishTime(new Date());
				actPrizeRecordExt.setDealEndTime(new Date());
				actPrizeRecordExt.setUpdateDate(new Date());
				actPrizeRecordExt.setReleasedBonus(vActPrizeRecord.getReleasedBonus());
				dbActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
			} else {
				dbActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
			}
			if (vActPrizeRecord.getFinishedTradeLots().compareTo(vActPrizeRecord.getNeedTradeLots()) == 0) {
				// releaseType=3由releaseType=4的发放记录做释放(4的完成手数=要求手数)
				if (!vActPrizeRecord.getReleaseType().equals(3)) {
					actPrizeRecordExt.setReleaseFinish(1);
				} else {
					actPrizeRecordExt.setReleaseFinish(0);
				}
			} else {
				actPrizeRecordExt.setReleaseFinish(0);
			}
			
			dbActPrizeRecord.setUpdateDate(new Date());
			imsPrizeRecordDao.update(dbActPrizeRecord);
			
			logger.info("<--第三方到活动中心同步结果！发放记录流水号:{},第三方流水号:{}", vActPrizeRecord.getRecordNo(),pThirdresult);

			vActPrizeRecord.setDealNumber(pThirdresult);
			vActPrizeRecord.setOtherRecordNumber(vActPrizeRecord.getRecordNo());
			actThirdCallRecordManager.saveThirdCallRecord(companyCode,vActPrizeRecord,
					IssuingStatusEnum.issue_success.getValue().equals(dbActPrizeRecord.getGivedStatus())
							? "success" : "failure",
					ActThirdCallEnum.BONUS_ADD_BONUS);
			imsPrizeRecordExtDao.updateByRecordNumber(actPrizeRecordExt);
		}
		
		Map<String,Object> dataParams = new HashMap<String,Object>();
		dataParams.put("accountNo", vActPrizeRecord.getAccountNo());
		dataParams.put("platform", vActPrizeRecord.getPlatform());
		dataParams.put("activityPeriods", vActPrizeRecord.getActNo());
		//actAccountStatRedis.rPushAccount(dataParams, companyCode);

		// 处理用户活动相关信息的统计
//			stat = statSummary(itemPrizeRecord, stat, null, null, null, null, null, true);
//			if(ActEnv.DEMO.getValue().equals(env)){
//				saveSimpleAccountActivityStat(itemPrizeRecord);
//			}
		
	}
	
	/**
	 * 处理已经完成手数为0并且处理待发放的发放记录
	 * @param actPrizeRecord
	 * @param companyId
	 * @param exchangeRate
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void dealZeroPrizeFinishTradeLot(VActPrizeRecord actPrizeRecord) {
		calZeroAccountPeriodsFinishTradeLot(actPrizeRecord);

	}
	
	/**
	 * 释放赠金
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean releaseWithGoldBonus(VActPrizeRecord vActPrizeRecord,Map<String, List<Map<String, Date>>> periodsExcludeDate, String demoAccountNo) {	
		String companyCode=CompanyEnum.getCodeById(vActPrizeRecord.getCompanyId());
		if(BigDecimal.ZERO.compareTo(vActPrizeRecord.getNeedTradeLots())>=0 || !IssuingStatusEnum.issue_success.getValue().equals(vActPrizeRecord.getGivedStatus()) ||
				vActPrizeRecord.getNeedTradeLots().compareTo(vActPrizeRecord.getFinishedTradeLots())==0 || 
				vActPrizeRecord.getReleaseType()==3 || vActPrizeRecord.getWithdrawalStartTime()==null)
			return true;
		
		logger.info(">>>开始对发放记录【{}:{}】做释放处理,统计交易手数的起始ID:{},本次手数统计时间:{},释放类型:{}",
				vActPrizeRecord.getActNo(),vActPrizeRecord.getRecordNo(),vActPrizeRecord.getWithdrawalStartTradeId(),
				vActPrizeRecord.getWithdrawalStartTime(),vActPrizeRecord.getReleaseType());
		
		// 第三方返回结果
		String pThirdresult = null;
		Map<String, Object> data = new HashMap<String, Object>();

		// 用户交易数据完成的手数(这个值已经包含了已经完成的手数) 
		BigDecimal tradeLot = vActPrizeRecord.getFinishedTradeLots();//BigDecimal.ZERO;
		// 交易订单ID
		long tradeId = 0;
		// 剩余完成手数(超前完成手数)
		BigDecimal surplusFinishTradeLot = BigDecimal.ZERO;
		ActPrizeRecordExt actPrizeRecordExt = null;
		ActApiManager thirdApi = null;

		// 判断前一次的发放记录需要完成手数是否不等于已完成手数
		Long pFinishedTradeId = null;
		Date pFinishOutTradeTime = null;
		Date pFinishInTradeTime = null;
		
		ActConditionSettingWrapper  actConditionCustomInfoWrapper = actConditionManager.findCustCondtionSettingWrap(vActPrizeRecord.getActNo());
		List<String> realProducLimit = actConditionCustomInfoWrapper.getRealProducts();
		List<Map<String, Date>> excludeDate = periodsExcludeDate.get(vActPrizeRecord.getActNo());

		//取出真实账号的统计数据
		//ActAccountActivityStat stat = null;
		ActAccountTradeStat actAccountTradeStat=null;
		//兼容旧数据,获取统计数据
		
		if(ActEnvEnum.REAL.getValue().equals(vActPrizeRecord.getEnv())){
			actAccountTradeStat = actAccountTradeStatDao.findActAccountActivityStat(
					vActPrizeRecord.getActNo(), vActPrizeRecord.getAccountNo(),
					vActPrizeRecord.getPlatform());
		}else if(ActEnvEnum.DEMO.getValue().equals(vActPrizeRecord.getEnv())){
			actAccountTradeStat = actAccountTradeStatDao.findActAccountActivityStat(
					vActPrizeRecord.getActNo(), demoAccountNo,vActPrizeRecord.getPlatform());
		}
		Long lastCumulativeTradeId = null;
		
		data.put("excludeDate", excludeDate);
		data.put("accountNo",vActPrizeRecord.getAccountNo());
		data.put("platform", vActPrizeRecord.getPlatform());
		data.put("env", ActEnvEnum.REAL.getValue());
		
		// 限制交易数据是真正发放时间到结算时间断内
		data.put("tradeStartTime", vActPrizeRecord.getWithdrawalStartTime());
		data.put("tradeEndTime", vActPrizeRecord.getSettlementTime());
		data.put("inTradeStartTime", vActPrizeRecord.getWithdrawalStartTime());
		data.put("inTradeEndTime", vActPrizeRecord.getSettlementTime());
		if (realProducLimit != null && !realProducLimit.isEmpty()) {
			data.put("product", realProducLimit);
		}
		
		if (actAccountTradeStat != null) {
			if (actAccountTradeStat.getFinishTradeId() != null) {
				data.put("tradeId", actAccountTradeStat.getFinishTradeId());
			}
			// 判断上一笔完成计算的交易数据是否能在当前发放纪录里使用,如果可以就可以取超前完成的剩余手数
			if (vActPrizeRecord.getWithdrawalStartTime() != null
					&& actAccountTradeStat.getFinishTradeTime() != null
					&& actAccountTradeStat.getFinishInTradeTime() != null) {
				if (vActPrizeRecord.getWithdrawalStartTime() .compareTo(actAccountTradeStat.getFinishTradeTime()) <= 0
						&& vActPrizeRecord.getWithdrawalStartTime().compareTo(actAccountTradeStat.getFinishInTradeTime()) <= 0) {
					surplusFinishTradeLot = actAccountTradeStat.getSurplusFinishTradeLot();
					pFinishOutTradeTime = actAccountTradeStat.getFinishTradeTime();
					pFinishInTradeTime = actAccountTradeStat.getFinishInTradeTime();
				}
			}
		}
		
		//判断入金型任务的释放类型
		/*boolean taskFinishRelease=false;
		
		//入金任务：
		//1.部分释放只需要关心统计手数时间到结算时间是否有未释放的赠金(如果第一个都没有释放完，其它的一定没有开始释放）
		//2.任务完成释放，只需要关心任务的结束时间，假定任务的结束时间即就是统计手数的时间
		ActSettingParamsValJson actParamVal = JsonUtil.json2Obj(actSetting.getParamsVal(), ActSettingParamsValJson.class);
		if(ActTypeEnum.RJZJ.getCode().equals(actSetting.getActivityType()) && ActRJZJReleaseTypeEnum.TASK_RELEASE.getKey().equals(actParamVal.getReleaseType()))
			taskFinishRelease=true;*/
		
		
		//------------------------开始手数统计-----------------------
	
		// 如果前个发放贈金计算手数还有剩余，叠加到下一个发放贈金的手数计算
		tradeLot = tradeLot.add(surplusFinishTradeLot);
		BigDecimal actPrizeNeedTradeLot = vActPrizeRecord.getNeedTradeLots();
		if (actPrizeNeedTradeLot.compareTo(tradeLot) <= 0) {
			surplusFinishTradeLot = tradeLot.subtract(actPrizeNeedTradeLot);
			tradeLot = actPrizeNeedTradeLot;
		} else {
			
			//查找可用的交易手数
			List<ActTradeRecord> actTradeRecords = actTradeRecordDao.getTradeRecordList(data);
			
			// 迭代计算交易数据已经完成手数
			if(actTradeRecords!=null ) {
				for (ActTradeRecord itemTrade : actTradeRecords) {
					tradeLot = tradeLot.add(itemTrade.getTradeLot());
					tradeId = itemTrade.getId();
					lastCumulativeTradeId = itemTrade.getId();
					// 如果要求手数已经完成，终止交易数据统计
					if (actPrizeNeedTradeLot.compareTo(tradeLot) <= 0) {
						surplusFinishTradeLot = tradeLot.subtract(actPrizeNeedTradeLot);
	
						tradeLot = actPrizeNeedTradeLot;
						pFinishedTradeId = tradeId;
						pFinishOutTradeTime = itemTrade.getTradeTime();
						pFinishInTradeTime = itemTrade.getInTradeTime();
	
						if (surplusFinishTradeLot.compareTo(BigDecimal.ZERO) > 0) {
							logger.debug("处理订单{}出现剩余手数{},交易记录id:{}", vActPrizeRecord.getRecordNo(),
									surplusFinishTradeLot, pFinishedTradeId);
						}
						break;
					}
	
				}
			}
			
		}
		
		//tradeLot (这个值已经包含了已经完成的手数) ,说明并没有可用的交易手数
		if(tradeLot.compareTo(vActPrizeRecord.getFinishedTradeLots())<=0)
			return true;
		
		// 发放状态正常并且不处理等待状态，不需要发至第三方平台
		thirdApi = apiDistributeManager.getApi(companyCode, vActPrizeRecord.getPlatform());
		
		BigDecimal releaseLot=tradeLot.subtract(vActPrizeRecord.getFinishedTradeLots());//实际释放手数
		vActPrizeRecord.setFinishedTradeLots(tradeLot);//设置待释放手数
		
		if(vActPrizeRecord.getReleaseType()==1 ||  //立即释放
			( (	vActPrizeRecord.getReleaseType()==2 || vActPrizeRecord.getReleaseType()==4) 
					&& vActPrizeRecord.getNeedTradeLots().compareTo(vActPrizeRecord.getFinishedTradeLots())==0) //到达完成手数  或者 完成任务并且达到完成手数
				) {
			
			thirdApi = apiDistributeManager.getApi(companyCode, vActPrizeRecord.getPlatform());
			//String pno = seqDubboService.getSeq(SeqEnum.ActivityBonusRecordNumber.getSeqCode(), vActPrizeRecord.getCompanyId()); 
			//vActPrizeRecord.setOtherRecordNumber(pno);
			
			//开始执行具体的释放
			pThirdresult = thirdApi.realReleaseBonus(vActPrizeRecord);
			vActPrizeRecord.setDealNumber(pThirdresult);
			
			boolean isSuccess=StringUtil.isNotEmpty(pThirdresult);
			
			actThirdCallRecordManager.saveThirdCallRecord(companyCode, vActPrizeRecord, isSuccess ? "success" : "failure",ActThirdCallEnum.BONUS_RELEASE_BONUS);
			if (isSuccess) {
				//更新扩张表
				actPrizeRecordExt=new ActPrizeRecordExt();
				actPrizeRecordExt.setRecordNumber(vActPrizeRecord.getRecordNo());
				actPrizeRecordExt.setReleasedBonus(vActPrizeRecord.getReleasedBonus());
				actPrizeRecordExt.setFinishedTradeLots(vActPrizeRecord.getFinishedTradeLots());
				actPrizeRecordExt.setFinishedLotsTradeTime(new Date());
				
				if (lastCumulativeTradeId != null && !lastCumulativeTradeId.equals(vActPrizeRecord.getCumulativeTradeId())) {
					actPrizeRecordExt.setCumulativeTradeId(lastCumulativeTradeId);
					vActPrizeRecord.setCumulativeTradeId(lastCumulativeTradeId);
				}
				imsPrizeRecordExtDao.updateByRecordNumber(actPrizeRecordExt);
				
				//更新用户参与活动表
				updateAccountActData(vActPrizeRecord.getAccountNo(), vActPrizeRecord.getActNo(),vActPrizeRecord.getEnv(), vActPrizeRecord.getPlatform(),
						BigDecimal.ZERO, releaseLot, BigDecimal.ZERO, releaseLot, BigDecimal.ZERO, BigDecimal.ZERO);
			}
		}
		
		//保存手数统计数据
		statTradeSummary(vActPrizeRecord, actAccountTradeStat, surplusFinishTradeLot,pFinishedTradeId, pFinishOutTradeTime, pFinishInTradeTime);
		
		return true;
	}
	
	/**
	 * 统计完成手数
	 * @param vActPrizeRecord
	 * @param actAccountTradeStat
	 * @param surplusFinishTradeLot
	 * @param finishTradeId
	 * @param tradeTime
	 * @param inTradeTime
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void statTradeSummary(VActPrizeRecord vActPrizeRecord, ActAccountTradeStat actAccountTradeStat,
			BigDecimal surplusFinishTradeLot, Long finishTradeId, Date tradeTime, Date inTradeTime) {
		
		try {
			// step1:记录ActAccountActivityStat总统计信息
			if (actAccountTradeStat == null) {
				actAccountTradeStat = new ActAccountTradeStat();
				actAccountTradeStat.setCompanyId(vActPrizeRecord.getCompanyId());
				actAccountTradeStat.setPlatform(vActPrizeRecord.getPlatform());
				actAccountTradeStat.setActivityPeriods(vActPrizeRecord.getActNo());
				actAccountTradeStat.setCreateDate(new Date());
			}
			
			actAccountTradeStat.setAccountNo(vActPrizeRecord.getAccountNo());
			
			actAccountTradeStat.setCompanyId(vActPrizeRecord.getCompanyId());
			actAccountTradeStat.setUpdateDate(new Date());
			if (surplusFinishTradeLot != null) {
				actAccountTradeStat.setSurplusFinishTradeLot(surplusFinishTradeLot);
			}
			String statOtherMsg = null;
			if (finishTradeId != null && finishTradeId > 0) {
				if (!finishTradeId.equals(actAccountTradeStat.getFinishTradeId())) {
					statOtherMsg = "FinishTradeId:" + finishTradeId;
				}
				actAccountTradeStat.setFinishTradeId(finishTradeId);
			}
			if (tradeTime != null) {
				if (!tradeTime.equals(actAccountTradeStat.getFinishTradeTime())) {
					statOtherMsg = statOtherMsg + " FinishTradeTime:" + tradeTime;
				}
				actAccountTradeStat.setFinishTradeTime(tradeTime);
			}
			if (inTradeTime != null) {
				if (!inTradeTime.equals(actAccountTradeStat.getFinishInTradeTime())) {
					statOtherMsg = statOtherMsg + " FinishInTradeTime:" + inTradeTime;
				}
				actAccountTradeStat.setFinishInTradeTime(inTradeTime);
			}
			if (statOtherMsg != null) {
				logger.debug("统计表stat:id:{},账号{},活动{}," + statOtherMsg, actAccountTradeStat.getId(), actAccountTradeStat.getAccountNo(),
						actAccountTradeStat.getActivityPeriods());
			}
			// if(saveStat){
//			Map<String, Double> finishTradeLotData = vActPrizeRecordMapper.getFinishTradeLot(vActPrizeRecord);
//			if(finishTradeLotData!=null){
//				Double needTradeLot = finishTradeLotData.get("need_trade_lots");
//				Double finishTradeLot = finishTradeLotData.get("finished_trade_lots");
//				Double giftAmount = finishTradeLotData.get("gift_amount");
//
//				stat.setGoldTradenumSum(ActBigDecimalUtil.convertUpFromDouble2(needTradeLot));
//				stat.setGoldTradenumFinishedSum(ActBigDecimalUtil.convertUpFromDouble2(finishTradeLot));
//				stat.setGoldSum(ActBigDecimalUtil.convertUpFromDouble2(giftAmount));
//			}
//			if(saveStat){
				actAccountTradeStatDao.saveOrUpdate(actAccountTradeStat);
//			}
			// }
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("处理订单{}统计结果时，出现异常 {}", vActPrizeRecord.getRecordNo(), ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @MethodName: addWithGoldBonus
	 * @Description: 加赠金
	 * @param vActPrizeRecord
	 * @param bonusFlag
	 * @return
	 * @return List<VActPrizeRecord>
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addWithGoldBonus(VActPrizeRecord vActPrizeRecord,String bonusFlag) {
		String companyCode=CompanyEnum.getCodeById(vActPrizeRecord.getCompanyId());
		
		ActApiManager thirdApi = apiDistributeManager.getApi(companyCode,vActPrizeRecord.getPlatform());
		if(!IssuingStatusEnum.issuing.getValue().equals(vActPrizeRecord.getGivedStatus()) || thirdApi==null)
			return false;
		
		//是否自动 发放赠金
		if (StringUtil.isEmpty(bonusFlag) || !"true".equals(bonusFlag)) 
			return false;
		
		//执行发放
		String pThirdresult = thirdApi.realAddBonus(vActPrizeRecord);
		
		//保存状态
		ActPrizeRecordExt actPrizeRecordExt = new ActPrizeRecordExt();
		actPrizeRecordExt.setRecordNumber(vActPrizeRecord.getRecordNo());
		actPrizeRecordExt.setDealStartTime(new Date());
		actPrizeRecordExt.setCompanyId(vActPrizeRecord.getCompanyId());
		actPrizeRecordExt.setActNo(vActPrizeRecord.getActNo());
		
		if (StringUtil.isNotEmpty(pThirdresult)) {
			vActPrizeRecord.setDealNumber(pThirdresult);
			vActPrizeRecord.setThirdRecordNo(pThirdresult);
			vActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
			vActPrizeRecord.setGivedTime(new Date());
			vActPrizeRecord.setUpdateDate(new Date());
			actPrizeRecordExt.setDealNumber(pThirdresult);
			actPrizeRecordExt.setFinishTime(new Date());
			actPrizeRecordExt.setDealEndTime(new Date());
			actPrizeRecordExt.setUpdateDate(new Date());

			//更新用户活动参与表
			updateAccountActData(vActPrizeRecord.getAccountNo(), vActPrizeRecord.getActNo(), vActPrizeRecord.getEnv(), vActPrizeRecord.getPlatform(),
					vActPrizeRecord.getItemAmount(), BigDecimal.ZERO, vActPrizeRecord.getNeedTradeLots(), 
					BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		} else {
			vActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
		}
		
		imsPrizeRecordExtDao.updateByRecordNumber(actPrizeRecordExt);
		//更新状态
		ImsPrizeRecord prizeRecord =ImsBeanUtil.copyNotNull(new ImsPrizeRecord(), vActPrizeRecord);
		prizeRecord.setId(Integer.parseInt(vActPrizeRecord.getId().toString()));
		imsPrizeRecordDao.update(prizeRecord);
		logger.info("<--第三方到活动中心同步结果！发放记录流水号:{},第三方流水号:{}", vActPrizeRecord.getRecordNo(),
				pThirdresult);

		//vActPrizeRecord.setDealNumber(pThirdresult);
		
		boolean isSuccess=IssuingStatusEnum.issue_success.getValue().equals(vActPrizeRecord.getGivedStatus());
		//保存第三方调用结果
		actThirdCallRecordManager.saveThirdCallRecord(companyCode,vActPrizeRecord, (isSuccess ? "success" : "failure"), ActThirdCallEnum.BONUS_ADD_BONUS );
		
		return isSuccess;
	}
	
	/**
	 * 更新客户参与活动表
	 * @param account
	 * @param actNo
	 * @param env
	 * @param platform
	 * @param beforeGold 先增金额
	 * @param beforeReleaseGold 先增释放金额
	 * @param beforeRequiredLot 先增要求手数
	 * @param beforeFinishLot 先增完成手数
	 * @param afterGold 后增金额
	 * @param discountGold 扣回金额
	 */
	public void updateAccountActData(String account,String actNo,String env,String platform,
			BigDecimal beforeGold,BigDecimal beforeReleaseGold,BigDecimal beforeRequiredLot,
			BigDecimal beforeFinishLot,BigDecimal afterGold,BigDecimal discountGold) {
		ActAccountActiviStat entity=new ActAccountActiviStat();
		
		entity.setAccountNo(account);
		entity.setEnv(env);
		entity.setActNo(actNo);
		entity.setPlatform(platform);
		
		entity=actAccActStatManager.findByBean(entity);
		if(entity.getBeforeGold()==null)
			entity.setBeforeGold(BigDecimal.ZERO);
		if(entity.getBeforeRequiredLot()==null)
			entity.setBeforeRequiredLot(BigDecimal.ZERO);
		if(entity.getBeforeReleaseGold()==null)
			entity.setBeforeReleaseGold(BigDecimal.ZERO);
		if(entity.getBeforeFinishLot()==null)
			entity.setBeforeFinishLot(BigDecimal.ZERO);
		if(entity.getAfterGold()==null)
			entity.setAfterGold(BigDecimal.ZERO);
		if(entity.getDiscountGold()==null)
			entity.setDiscountGold(BigDecimal.ZERO);
			
		entity.setBeforeGold(entity.getBeforeGold().add(beforeGold));
		entity.setBeforeRequiredLot(entity.getBeforeRequiredLot().add(beforeRequiredLot));
		entity.setBeforeReleaseGold(entity.getBeforeReleaseGold().add(beforeReleaseGold));
		
		entity.setBeforeFinishLot(entity.getBeforeFinishLot().add(beforeFinishLot));
		entity.setAfterGold(entity.getAfterGold().add(afterGold));
		entity.setDiscountGold(entity.getDiscountGold().add(discountGold));
		
		actAccActStatManager.update(entity);
	}
	
}
