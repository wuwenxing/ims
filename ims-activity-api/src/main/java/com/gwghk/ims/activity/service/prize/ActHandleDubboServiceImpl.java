package com.gwghk.ims.activity.service.prize;

import org.springframework.stereotype.Service;

import com.gwghk.ims.common.inf.external.activity.ActHandleDubboService;

/**
 * 摘要：发放处理服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月26日
 */
@Service
public class ActHandleDubboServiceImpl /*implements ActHandleDubboService */{
	/*private static Logger logger = LoggerUtil.getLogger(ActHandleDubboServiceImpl.class);

	@Autowired
	private ActWpdhTaskManager actWpdhTaskManager;

	@Autowired
	private ActTaskManager actTaskManager;

	@Autowired
	private ActPrizeRecordManager actPrizeRecordManager;

	@Autowired
	private ActProgressManager actProgressManager;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private ActTaskPrizeQualifyManager actTaskPrizeQualifyManager;
	
	@Autowired
	private ActPrizeRecordRedis actPrizeRecordRedis;
	
	@Autowired
	private ActPrizeRecordRedoRedis actPrizeRecordRedoRedis;
	
	@Autowired
	private ActJoinQualityManager actJoinQualityManager;
	
	@Autowired
	private PrizeRecordRedoManager prizeRecordRedoManager;*/

	/**
	 * 功能：物品兑换型-兑奖
	 */
	/*public ApiRespResult<Void> doExchange(ActTaskExchangeReqDto reqDto) {
		logger.info("开始本轮兑奖->活动编号:{},账号：{},平台：{},任务-物品ID：{},公司Id：{}", new Object[] { reqDto.getActivityPeriods(),
				reqDto.getAccountNo(), reqDto.getPlatform(), reqDto.getTaskItemId(), reqDto.getCompanyId() });
		ApiRespResult<Void> result = new ApiRespResult<Void>();
		try {
			result = actWpdhTaskManager.saveWpdhTask(reqDto);
			if (result.isOk()) {
				logger.info("本轮兑奖成功！谢谢参与！");
			} else {
				logger.error("本轮兑奖失败！code:{},msg；{}", new Object[] { result.getCode(), result.getMsg() });
			}
			return result;
		} catch (Exception e) {
			logger.error("本轮兑奖失败,系统出现异常！", e);
			return result.setRespMsg(ApiResultCode.EXCEPTION);
		}
	}*/

	/**
     * 功能：自动生成发放纪录
     */
    /*@Override
    public ApiRespResult<Void> doAutoGenerateRecord(Long companyId) {
        String companyCode = ActCompany.getCodeByKey(companyId);
        logger.info("--->{}开始自动发送记录", companyCode);
        ApiRespResult<Void> result = new ApiRespResult<Void>();
        try {
            ApiRespResult<Map<String, Map<Integer, List<Integer>>>> curResult = actTaskManager.autoGenerateRecord(companyId);
            if (result.isOk()) {
                logger.info("<---完成自动发放纪录(companyId:{}),谢谢参与！", companyId);
                Map<String, Map<Integer, List<Integer>>> actQualifyChangeInfoMap = curResult.getData();
                if (actQualifyChangeInfoMap != null && !actQualifyChangeInfoMap.isEmpty()) {
                    CountDownLatch countDownLatch = new CountDownLatch(actQualifyChangeInfoMap.size()); // 加入同步工具类，等待所有活动更新完
                    for (String activityPeriods : actQualifyChangeInfoMap.keySet()) {
                        taskExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Map<Integer, List<Integer>> actQualifyChangeInfos = actQualifyChangeInfoMap
                                            .get(activityPeriods);
                                    if (actQualifyChangeInfos != null && !actQualifyChangeInfos.isEmpty()) {
                                        for (Integer changeInfo : actQualifyChangeInfos.keySet()) {
                                            List<Integer> updateKeys = actQualifyChangeInfos.get(changeInfo);
                                            List<Integer> curUpdateKeys = new ArrayList<Integer>();
                                            int curIndex = 0;
                                            for (int i = 0; i < updateKeys.size(); i++) {
                                                curUpdateKeys.add(updateKeys.get(curIndex));
                                                curIndex++;
                                                if (!curUpdateKeys.isEmpty() && (curIndex == updateKeys.size()
                                                        || i > 0 && curIndex % 100 == 0)) {
                                                    actTaskPrizeQualifyManager.updateChangeInfoToFinished(
                                                            activityPeriods, curUpdateKeys, changeInfo);
                                                    if (i > 0 && curIndex % 100 == 0) {
                                                        curUpdateKeys.clear();// 清空一下
                                                        Thread.sleep(100L);// 休眠100豪秒!
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.error("<--{}活动自动发放批量更新taskPrizeQualify失败:活动编号:{}",
                                            ActCompany.getCodeByKey(companyId), activityPeriods, e);
                                } finally {
                                    countDownLatch.countDown();
                                }
                            }
                        });
                    }
                    countDownLatch.await(); // 等待所有子线程执行完
                    for (String activityPeriods : actQualifyChangeInfoMap.keySet()) {
                        try {
                            prizeRecordRedoManager.updateSyncSuccessActAccount(activityPeriods);
                        } catch (Exception e) {
                            logger.error("<--{}活动发放:活动编号:{}批量更新活动重发记录失败!", companyCode, activityPeriods, e);
                        }
                    }
                }
            } else {
                logger.error("<---自动发放纪录失败(companyId:{})！code:{},msg；{}",
                        new Object[] { companyId, result.getCode(), result.getMsg() });
            }
            comAutoGenerateRecordAfterDataDeal(companyId,100L);// 休眠100豪秒!
            return result;
        } catch (Exception e) {
            logger.error("<---自动发放纪录失败(companyId:{})！系统出现异常！", companyId, e);
            return result.setRespMsg(ApiResultCode.EXCEPTION);
        }
    }*/
    
    /**
     * 功能：指定活动用户自动生成发放纪录
     */
    /*@Override
    public ApiRespResult<Void> autoGenerateRecordByAccount(Long companyId,String activityPeriods,
        String accountNo,String platform,boolean needRequiredTime) {
        String companyCode = ActCompany.getCodeByKey(companyId);
        logger.info("--->{}开始指定活动用户自动发送记录,activityPeriods:{},accountNo:{},platform:{}", companyCode,activityPeriods,accountNo,platform);
        ApiRespResult<Void> result = new ApiRespResult<Void>();
        if(companyId==null || StringUtils.isBlank(accountNo) || StringUtil.isBlank(activityPeriods)){
            return result.setRespMsg(ApiResultCode.FAIL);
        }
        try {
            ApiRespResult<Map<String, Map<Integer, List<Integer>>>> curResult = actTaskManager.autoGenerateRecordByAccount(companyId,
                activityPeriods,accountNo,platform,needRequiredTime);
            if (result.isOk()) {
                logger.info("<---{}完成指定活动用户自动发放纪录(activityPeriods:{},accountNo:{},platform:{}),谢谢参与！", companyCode,activityPeriods,accountNo,platform);
                Map<String, Map<Integer, List<Integer>>> actQualifyChangeInfoMap = curResult.getData();
                if (actQualifyChangeInfoMap != null && !actQualifyChangeInfoMap.isEmpty()) {    
                    Map<Integer, List<Integer>> actQualifyChangeInfos = actQualifyChangeInfoMap.get(activityPeriods);
                    if (actQualifyChangeInfos != null && !actQualifyChangeInfos.isEmpty()) {
                        for (Integer changeInfo : actQualifyChangeInfos.keySet()) {
                            List<Integer> curUpdateKeys = actQualifyChangeInfos.get(changeInfo);
                            if(curUpdateKeys!=null && !curUpdateKeys.isEmpty()){
                                actTaskPrizeQualifyManager.updateChangeInfoToFinished(
                                    activityPeriods, curUpdateKeys, changeInfo);
                            } 
                        }
                    }
                    try {
                        prizeRecordRedoManager.updateSyncSuccessActAccount(activityPeriods);
                    } catch (Exception e) {
                        logger.error("<--{}活动发放:活动编号:{}批量更新活动重发记录失败(活动用户自动发放中)!", companyCode, activityPeriods, e);
                    }
                     
                }
            } else {
                logger.error("<---{}指定活动用户自动发放纪录失败((activityPeriods:{},accountNo:{},platform:{})),code:{},msg；{}",
                        new Object[] { companyCode, activityPeriods,accountNo,platform,result.getCode(), result.getMsg() });
            }
            comAutoGenerateRecordAfterDataDeal(companyId,100L);// 间隔休眠100毫秒!
            return result;
        } catch (Exception e) {
            logger.error("<---指定活动用户自动发放纪录失败(companyId:{})！系统出现异常！", companyId, e);
            return result.setRespMsg(ApiResultCode.EXCEPTION);
        }
    }*/
    
	/**
	 * 功能：自动生成发放纪录完后，相关数据处理
	 */
	/*public void comAutoGenerateRecordAfterDataDeal(Long companyId,Long sleepTime) throws Exception {
	        String companyCode = ActCompany.getCodeByKey(companyId);
			// 处理join的参与状态
			actJoinQualityManager.saveParticipateAccountDataFromWaitDealToDB(companyId);
			// 发放记录：处理层级型活动已完成的层级任务的发放记录状态及取现开始时间和取现id
			if (actPrizeRecordRedis.isExistDealFinishGroupTaskData(companyCode)) {
				actPrizeRecordRedis.saveFinishGroupTaskDataToDB(companyCode);
				Thread.sleep(sleepTime);
			}
			// 发放记录：处理完成入金任务，标记最后一条发放记录
			if (actPrizeRecordRedis.isExistDealTaskLastRecordReleaseTypeData(companyCode)) {
				actPrizeRecordRedis.saveTaskLastRecordReleaseTypeDataToDB(companyCode);
				Thread.sleep(sleepTime);
			}
			// 发放记录：处理首次参与用户的结算时间修改
			if (actPrizeRecordRedis.isExistDealFirstParticipatorSettTimeData(companyCode)) {
				actPrizeRecordRedis.saveFirstParticipatorSettTimeDataToDB(companyCode);
				Thread.sleep(sleepTime);
			}
			// 处理已经发放成功的数据
			actPrizeRecordRedoRedis.saveDataFromRedisToDB(companyCode);
	}*/

	/**
	 * 功能：自动处理应发记录
	 */
	/*@Override
	public ApiRespResult<Integer> doWaitSendRecords(Long companyId) {
		ApiRespResult<Integer> result = new ApiRespResult<Integer>();
		try {
			result = actPrizeRecordManager.autoDealWaitSendRecord(companyId);
			if (result.isOk()) {
				logger.info("自动处理应发记录成功,谢谢参与！更新应发记录条数：{}", new Object[] { result.getData() });
			} else {
				logger.error("自动处理应发记录失败！code:{},msg；{}", new Object[] { result.getCode(), result.getMsg() });
			}
			return result;
		} catch (Exception e) {
			logger.error("自动处理应发记录失败！系统出现异常！", e);
			return result.setRespMsg(ApiResultCode.EXCEPTION);
		}
	}*/
	
	
	/**
     * 功能：自动处理可发记录中没有串码的记录
     */
    /*@Override
    public ApiRespResult<Integer> autoDealStringCodeSendRecord(Long companyId) {
        ApiRespResult<Integer> result = new ApiRespResult<Integer>();
        try {
            result = actPrizeRecordManager.autoDealStringCodeSendRecord(companyId);
            if (result.isOk()) {
                logger.info("自动处理可发记录中没有串码的记录,谢谢参与！更新可发记录条数：{}", new Object[] { result.getData() });
            } else {
                logger.error("自动处理可发记录中没有串码的记录失败！code:{},msg；{}", new Object[] { result.getCode(), result.getMsg() });
            }
            return result;
        } catch (Exception e) {
            logger.error("自动处理可发记录中没有串码的记录失败！系统出现异常！", e);
            return result.setRespMsg(ApiResultCode.EXCEPTION);
        }
    }*/

	/**
	 * 功能：活动进度自动统计
	 */
	/*public ApiRespResult<Void> doAutoActProgressStat(Long companyId) {
		logger.info("--->开始活动进度自动统计处理:【companyId:{}】", companyId);
		ApiRespResult<Void> result = new ApiRespResult<Void>();
		try {
			result = actProgressManager.doAutoActProgressStat(companyId);
			if (result.isOk()) {
				logger.info("<---完成活动进度自动统计处理,谢谢参与！");
			} else {
				logger.error("<---活动进度自动统计失败！code:{},msg；{}", new Object[] { result.getCode(), result.getMsg() });
			}
			return result;
		} catch (Exception e) {
			logger.error("<---活动进度自动统计失败！系统出现异常！", e);
			return result.setRespMsg(ApiResultCode.EXCEPTION);
		}
	}*/
}