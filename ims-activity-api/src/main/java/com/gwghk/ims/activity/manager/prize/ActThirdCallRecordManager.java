package com.gwghk.ims.activity.manager.prize;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.activity.dao.entity.ActPrizeShortData;
import com.gwghk.ims.activity.dao.entity.ActThirdCallRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ActPrizeRecordDao;
import com.gwghk.ims.activity.dao.inf.ActThirdCallRecordDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordExtDao;
import com.gwghk.ims.common.enums.ActThirdCallEnum;
import com.gwghk.ims.common.enums.ActThirdCallRecordSourceEnum;
import com.gwghk.unify.framework.common.util.BeanUtils;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 
 * @ClassName: ActThirdCallManager
 * @Description: 第三方调用
 * @author lawrence
 * @date 2017年11月20日
 *
 */
@Component
@Transactional
public class ActThirdCallRecordManager {

	@Autowired
	private ActThirdCallRecordDao actThirdCallRecordDao;
	private static Logger logger = LoggerFactory.getLogger(ActThirdCallRecordManager.class);
	@Value("${bonus.trycount}")
	private String bonusTrycount;
	@Autowired
	private ApiDistributeManager apiDistributeManager;
	@Autowired
	private ImsPrizeRecordExtDao actPrizeRecordExtMapper;
	@Autowired
	private ActPrizeRecordDao actPrizeRecordMapper;
	/*@Autowired
	private VActDemoPrizeRecordMapper vActDemoPrizeRecordMapper;
	@Autowired
	private ActTempPrizeRecordMapper actTempPrizeRecordMapper;
	@Autowired
	private ActAccountPrizeRedis actAccountPrizeRedis;
	@Autowired
	private ActAccountStatRedis actAccountStatRedis;*/

	public ActThirdCallRecord getActThirdCallRecord(Long id, String companyCode) {
		ActThirdCallRecord params = new ActThirdCallRecord();
		params.setId(id);
		return actThirdCallRecordDao.findObject(id);
	}
	
	/**
	 * 
	 * @MethodName: saveThirdCallRecord
	 * @Description: 保存第三方调用记录
	 * @param itemPrizeRecords
	 * @param callResults
	 * @param actThirdCallEnum
	 * @return void
	 */
	public void saveThirdCallRecord(String companyCode, List<VActPrizeRecord> itemPrizeRecords,
			Map<String, String> callResults, ActThirdCallEnum actThirdCallEnum, String otherRemark) {
		for (VActPrizeRecord itemPrizeRecord : itemPrizeRecords) {
			try {
				ActThirdCallRecord actThirdCallRecord = new ActThirdCallRecord();
				actThirdCallRecord.setType(actThirdCallEnum.getType());
				actThirdCallRecord.setCode(actThirdCallEnum.getCode());
				actThirdCallRecord.setCompanyCode(companyCode);
				actThirdCallRecord.setCompanyId(itemPrizeRecord.getCompanyId());
				actThirdCallRecord.setPlatform(itemPrizeRecord.getPlatform());
				actThirdCallRecord.setAccountNo(itemPrizeRecord.getAccountNo());
				actThirdCallRecord.setParentRecordNo(itemPrizeRecord.getRecordNo());
				actThirdCallRecord.setRecordNo(itemPrizeRecord.getOtherRecordNumber() != null
						? itemPrizeRecord.getOtherRecordNumber() : itemPrizeRecord.getRecordNo());
				actThirdCallRecord.setThirdRecordNo(callResults.get(itemPrizeRecord.getDealNumber()));
				actThirdCallRecord.setThirdDealResult(
						callResults.containsKey(itemPrizeRecord.getDealNumber()) ? "success" : "failure");
				actThirdCallRecord.setCreateDate(new Date());
				actThirdCallRecord.setUpdateDate(new Date());
				actThirdCallRecord.setActivityPeriods(itemPrizeRecord.getActNo());
				actThirdCallRecord.setTryCount(1);
				actThirdCallRecord.setReleaseType(itemPrizeRecord.getReleaseType());
				actThirdCallRecord.setRemark((otherRemark != null ? otherRemark : ""));
				ActPrizeShortData actPrizeShortData = new ActPrizeShortData();
				BeanUtils.copyExceptNull(actPrizeShortData, itemPrizeRecord);
				actThirdCallRecord.setData(JsonUtil.obj2Str(actPrizeShortData));
				actThirdCallRecord.setSource(ActThirdCallRecordSourceEnum.ACT_PRIZE.getCode());
				actThirdCallRecordDao.save(actThirdCallRecord);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * @MethodName: dealWithFailureBonus
	 * @Description: 处理贈金掉单记录
	 * @return void
	 */
	/*public void dealWithFailureBonus() {
		ExecutorService pool = Executors.newFixedThreadPool(32);
		CountDownLatch countDownLatch = new CountDownLatch(CompanyEnum.getList().size());
		try {
			for (CompanyEnum actCompany : CompanyEnum.getList()) {
				pool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							logger.info("处理公司{}贈金掉单数据", actCompany.getCode());
							ActThirdCallRecord actThirdCallRecord = new ActThirdCallRecord();
							actThirdCallRecord.setCompanyCode(actCompany.getCode());
							actThirdCallRecord.setTryCount(Integer.parseInt(bonusTrycount));
							actThirdCallRecord.setCode(ActThirdCallEnum.BONUS_ADD_BONUS.getCode());
							List<ActThirdCallRecord> actThirdCallRecords = actThirdCallRecordDao
									.findFailureBonus(actThirdCallRecord);
							if (actThirdCallRecords != null && !actThirdCallRecords.isEmpty()) {
								logger.info("{}新增贈金掉单记录数{}", actCompany.getCode(), actThirdCallRecords.size());
								for (ActThirdCallRecord itemActThirdCallRecord : actThirdCallRecords) {
									dealWithFailureBonus(itemActThirdCallRecord);
								}
							}
							Thread.sleep(5000);
							actThirdCallRecord.setCode(null);
							actThirdCallRecords = actThirdCallRecordDao
									.findFailureBonus(actThirdCallRecord);
							if (actThirdCallRecords != null && !actThirdCallRecords.isEmpty()) {
								logger.info("{}其余贈金掉单记录数{}", actCompany.getCode(), actThirdCallRecords.size());
								for (ActThirdCallRecord itemActThirdCallRecord : actThirdCallRecords) {
									dealWithFailureBonus(itemActThirdCallRecord);
								}
							}
							logger.info("结束处理公司{}贈金掉单数据", actCompany.getCode());
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						} finally {
							countDownLatch.countDown();
						}
					}
				});
			}
			countDownLatch.await(3, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			pool.shutdown();
		}
	}*/

	/**
	 * 
	 * @MethodName: dealWithFailureBonus
	 * @Description: 处理单笔贈金掉单记录
	 * @param actThirdCallRecord
	 * @return String
	 */
	/*public String dealWithFailureBonus(ActThirdCallRecord actThirdCallRecord) {
		if (!actThirdCallRecord.getThirdDealResult().equals("failure")) {
			logger.info("掉单订单号{},当前状态不正确，不处理该掉单", actThirdCallRecord.getParentRecordNo());
			return null;
		}
		String companyCode = CompanyEnum.getCodeById(actThirdCallRecord.getCompanyId());
		logger.debug("处理掉单数据,订单号{},id:{}", actThirdCallRecord.getParentRecordNo(), actThirdCallRecord.getId());
		String thirdRecordNo = null;
		ActApiManager thirdApi = apiDistributeManager.getApi(CompanyEnum.getCodeById(actThirdCallRecord.getCompanyId()),
				actThirdCallRecord.getPlatform());
		if (ActThirdCallRecordSourceEnum.ACT_PRIZE.getCode().equals(actThirdCallRecord.getSource())) {
			VActPrizeRecord vActPrizeRecord = JsonUtil.json2Obj(actThirdCallRecord.getData(), VActPrizeRecord.class);

			if (vActPrizeRecord != null && thirdApi != null) {

				ActPrizeRecordExt actPrizeRecordExt = null;
				if (ActThirdCallEnum.BONUS_ADD_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
					actPrizeRecordExt = actPrizeRecordExtMapper.getActPrizeRecordExt(companyCode,
							actThirdCallRecord.getParentRecordNo());
					thirdRecordNo = thirdApi.realAddBonus(vActPrizeRecord);
					if (actPrizeRecordExt.getReleaseType()!=null && actPrizeRecordExt.getReleaseType() == 4) {
						 if(ActEnv.DEMO.getValue().equals(vActPrizeRecord.getEnv())){
							actAccountPrizeRedis.rPushDemoAccount(vActPrizeRecord.getAccountNo(), companyCode);
						 }else{
							actAccountPrizeRedis.rPushAccount(vActPrizeRecord.getAccountNo(), companyCode);
						 }
					}
				} else if (ActThirdCallEnum.BONUS_RELEASE_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
					actPrizeRecordExt = actPrizeRecordExtMapper.getActPrizeRecordExt(companyCode,
							actThirdCallRecord.getParentRecordNo());
					vActPrizeRecord.setDealNumber(actPrizeRecordExt.getDealNumber());
					if (actPrizeRecordExt != null) {
						if (actPrizeRecordExt.getFinishedTradeLots()
								.compareTo(vActPrizeRecord.getFinishedTradeLots()) > 0) {
							logger.warn("{}由于当前发放记录的完成手数{}已经比掉单记录的完成手数{}大，因此不会重新重新调用api",
									actThirdCallRecord.getParentRecordNo(), actPrizeRecordExt.getFinishedTradeLots(),
									vActPrizeRecord.getFinishedTradeLots());
							thirdRecordNo = "N/A";
						} else {
							thirdRecordNo = thirdApi.realReleaseBonus(vActPrizeRecord);
						}
					}
				} else if (ActThirdCallEnum.BONUS_CANCEL_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
					ActThirdCallRecord params = new ActThirdCallRecord();
					params.setCompanyCode(companyCode);
					params.setParentRecordNo(actThirdCallRecord.getParentRecordNo());
					params.setCode(ActThirdCallEnum.BONUS_RELEASE_BONUS.getCode());
					//判断是否存在待释放并且释放失败的贈金记录，有则先暂时不做扣除贈金的掉单记录
					List<ActThirdCallRecord> tmpReleaseThirdCallRecord = actThirdCallRecordDao.findFailureBonusRecordNumber(params);
					if(tmpReleaseThirdCallRecord!=null && !tmpReleaseThirdCallRecord.isEmpty()){
						logger.warn("订单号:{}掉单取消的贈金暂时不处理，先处理释放完成后，再继续处理取消贈金的掉单记录",actThirdCallRecord.getParentRecordNo());
						return null;
					}
					
					List<VActPrizeRecord> listActPrizeRecord = new ArrayList<VActPrizeRecord>();
					listActPrizeRecord.add(vActPrizeRecord);
					Map<String, Map<String, String>> resultData = thirdApi.realCancelBonus(listActPrizeRecord, "");

					// 获取释放成功的贈金订单号
					Map<String, String> deals = resultData.get("success");
					if (deals != null) {
						for (Map.Entry<String, String> dealItem : deals.entrySet()) {
							thirdRecordNo = dealItem.getValue();
						}
					}
				}

				if (StringUtil.isNotEmpty(thirdRecordNo)) {
					if (ActThirdCallEnum.BONUS_ADD_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
						ActPrizeRecord dbActPrizeRecord = new ActPrizeRecord();
						dbActPrizeRecord.setRecordNumber(vActPrizeRecord.getRecordNumber());
						dbActPrizeRecord.setIssuingStatus(IssuingStatusEnum.issue_success.getValue());
						dbActPrizeRecord.setCompanyCode(companyCode);
						dbActPrizeRecord.setUpdateDate(new Date());
						actPrizeRecordMapper.updateByRecordNo(dbActPrizeRecord);

						actPrizeRecordExt.setDealNumber(thirdRecordNo);
						actPrizeRecordExt.setFinishTime(new Date());
						actPrizeRecordExt.setDealEndTime(new Date());
						actPrizeRecordExt.setUpdateDate(new Date());
						actPrizeRecordExt.setCompanyCode(companyCode);
						actPrizeRecordExt.setReleasedBonus(vActPrizeRecord.getReleasedBonus());
						actPrizeRecordExtMapper.updateByRecordNumber(actPrizeRecordExt);
					} else if (ActThirdCallEnum.BONUS_RELEASE_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
						// 更新记录只发生在掉单记录的数据的完成手数比原始记录的完成手数相等或者大于时，会重新更新释放贈金栏位
						if (actPrizeRecordExt.getFinishedTradeLots()
								.compareTo(vActPrizeRecord.getFinishedTradeLots()) == 0) {
							actPrizeRecordExt.setCompanyCode(companyCode);
							actPrizeRecordExt.setReleasedBonus(vActPrizeRecord.getReleasedBonus());
							actPrizeRecordExtMapper.updateByRecordNumber(actPrizeRecordExt);
						}
					}
					Map<String,Object> dataParams = new HashMap<String,Object>();
					dataParams.put("accountNo", vActPrizeRecord.getAccountNo());
					dataParams.put("platform", vActPrizeRecord.getGuestPlatForm());
					dataParams.put("activityPeriods", vActPrizeRecord.getActivityPeriods());
					actAccountStatRedis.rPushAccount(dataParams, companyCode);
				}
			}

		} else if (ActThirdCallRecordSourceEnum.ACT_DEMO_PRIZE.getCode().equals(actThirdCallRecord.getSource())) {
			BonusReqDto bonusReqDto = JsonUtil.json2Obj(actThirdCallRecord.getData(), BonusReqDto.class);
			thirdApi = apiDistributeManager.getApi(companyCode, bonusReqDto.getPlatform());
			VActDemoPrizeRecord actDemoPrizeRecord = vActDemoPrizeRecordMapper
					.getByRecordNumber(bonusReqDto.getRecordNumber(), companyCode);
			if (actDemoPrizeRecord != null) {
				if (ActThirdCallEnum.BONUS_ADD_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
					thirdRecordNo = thirdApi.addBonus(bonusReqDto, false);
				}
				if (StringUtil.isNotEmpty(thirdRecordNo)) {
					actDemoPrizeRecord.setDealNumber(thirdRecordNo);
					actDemoPrizeRecord.setIssuingStatus(IssuingStatusEnum.issue_success.getValue());
					actDemoPrizeRecord.setCompanyCode(companyCode);
					actDemoPrizeRecord.setUpdateDate(new Date());
					vActDemoPrizeRecordMapper.update(actDemoPrizeRecord);
				}
			}
		} else if (ActThirdCallRecordSourceEnum.ACT_TEMP_PRIZE_RECORD.getCode()
				.equals(actThirdCallRecord.getSource())) {
			BonusReqDto bonusReqDto = JsonUtil.json2Obj(actThirdCallRecord.getData(), BonusReqDto.class);
			thirdApi = apiDistributeManager.getApi(companyCode, bonusReqDto.getPlatform());
			ActTempPrizeRecord examples = new ActTempPrizeRecord();
			examples.setRecordNumber(bonusReqDto.getRecordNumber());
			List<ActTempPrizeRecord> listPrize = actTempPrizeRecordMapper.listPrize(examples);
			ActTempPrizeRecord actTempPrizeRecord = null;
			if (CollectionUtils.isNotEmpty(listPrize)) {
				actTempPrizeRecord = listPrize.get(0);
			}
			if (actTempPrizeRecord != null) {
				if (ActThirdCallEnum.BONUS_ADD_BONUS.getCode().equals(actThirdCallRecord.getCode())) {
					thirdRecordNo = thirdApi.addBonus(bonusReqDto, false);
				}
				if (StringUtil.isNotEmpty(thirdRecordNo)) {
					actTempPrizeRecord.setDealNumber(thirdRecordNo);
					actTempPrizeRecord.setIsGived(1);
					actTempPrizeRecord.setUpdateDate(new Date());
					actTempPrizeRecordDao.updateByPrimaryKey(actTempPrizeRecord);
				}
			}

		}
		if (StringUtil.isNotEmpty(thirdRecordNo)) {
			actThirdCallRecord.setThirdRecordNo(thirdRecordNo);
			actThirdCallRecord.setThirdDealResult("success");
		} else {
			logger.error("掉单发放记录{},无法获取第三方订单号", actThirdCallRecord.getParentRecordNo());
		}
		actThirdCallRecord.setCompanyCode(companyCode);
		actThirdCallRecord.setUpdateDate(new Date());
		actThirdCallRecord.setTryCount(actThirdCallRecord.getTryCount() + 1);
		actThirdCallRecordDao.update(actThirdCallRecord);
		return thirdRecordNo;
	}*/

	/**
	 * 
	 * @MethodName: saveThirdCallRecord
	 * @Description: 保存第三方调用记录
	 * @param itemPrizeRecord
	 * @param callResult
	 * @param actThirdCallEnum
	 * @return void
	 */
	public void saveThirdCallRecord(String companyCode, VActPrizeRecord itemPrizeRecord, String callResult,
			ActThirdCallEnum actThirdCallEnum) {
		try {
			ActThirdCallRecord actThirdCallRecord = new ActThirdCallRecord();
			actThirdCallRecord.setType(actThirdCallEnum.getType());
			actThirdCallRecord.setCode(actThirdCallEnum.getCode());
			actThirdCallRecord.setActivityPeriods(itemPrizeRecord.getActNo());
			actThirdCallRecord.setCompanyCode(companyCode);
			actThirdCallRecord.setCompanyId(itemPrizeRecord.getCompanyId());
			actThirdCallRecord.setPlatform(itemPrizeRecord.getPlatform());
			actThirdCallRecord.setAccountNo(itemPrizeRecord.getAccountNo());
			actThirdCallRecord.setParentRecordNo(itemPrizeRecord.getRecordNo());
			actThirdCallRecord.setRecordNo(itemPrizeRecord.getOtherRecordNumber());
			actThirdCallRecord.setThirdRecordNo(itemPrizeRecord.getDealNumber());
			actThirdCallRecord.setThirdDealResult(callResult);
			actThirdCallRecord.setCreateDate(new Date());
			actThirdCallRecord.setUpdateDate(new Date());
			actThirdCallRecord.setTryCount(1);
			actThirdCallRecord.setSource(ActThirdCallRecordSourceEnum.ACT_PRIZE.getCode());
			actThirdCallRecord.setReleaseType(itemPrizeRecord.getReleaseType());
			// actThirdCallRecord.setRemark("当前需要完成手数:" +
			// itemPrizeRecord.getNeedTradeLots() + ",已经完成手数:"
			// + itemPrizeRecord.getFinishedTradeLots());
			ActPrizeShortData actPrizeShortData = new ActPrizeShortData();
			BeanUtils.copyExceptNull(actPrizeShortData, itemPrizeRecord);
			actThirdCallRecord.setData(JsonUtil.obj2Str(actPrizeShortData));
			actThirdCallRecordDao.save(actThirdCallRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	

	/**
	 * 
	 * @MethodName: selectCount
	 * @Description: 查询参数匹配的记录数
	 * @param params
	 * @return
	 * @return Integer
	 */
	public Integer selectCount(ActThirdCallRecord params) {
		return actThirdCallRecordDao.selectCount(params);
	}

	/**
	 * 
	 * @MethodName: dealWithFailureBonusRecordNumber
	 * @Description: 指定订单号处理掉单记录
	 * @param companyCode
	 * @param recordNumber
	 * @return void
	 */
	/*public void dealWithFailureBonusRecordNumber(String companyCode, String recordNumber) {
		boolean addBonusExit = false;
		ActThirdCallRecord params = new ActThirdCallRecord();
		params.setCompanyCode(companyCode);
		params.setParentRecordNo(recordNumber);
		params.setCode(ActThirdCallEnum.BONUS_ADD_BONUS.getCode());
		//新增
		List<ActThirdCallRecord> actThirdCallRecords = actThirdCallRecordDao.findFailureBonusRecordNumber(params);
		if(actThirdCallRecords!=null && !actThirdCallRecords.isEmpty()){
			for (ActThirdCallRecord itemActThirdCallRecord : actThirdCallRecords) {
				dealWithFailureBonus(itemActThirdCallRecord);
			}
			addBonusExit = true;
		}		
		//释放或者取消
		params.setCode(null);
		actThirdCallRecords = actThirdCallRecordDao.findFailureBonusRecordNumber(params);
		if(actThirdCallRecords!=null && !actThirdCallRecords.isEmpty()){
			if(addBonusExit){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(),e);
				}
			}
			for (ActThirdCallRecord itemActThirdCallRecord : actThirdCallRecords) {
				dealWithFailureBonus(itemActThirdCallRecord);
			}
		}
		
	}*/
}
