package com.gwghk.ims.activity.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 
 * @ClassName: ActPrizeRecordExtRedis
 * @Description: 发放记录扩展表更新
 * @author lawrence
 * @date 2017年12月8日
 *
 */
@Component
public class ActPrizeRecordRedis {
    private final static Logger logger = LoggerFactory.getLogger(ActPrizeRecordRedis.class);
    private final static String ACT_PRIZE_RECORD_EXT_UPDATE = "PRIZE_RECORD_EXT:U_";//EXT表相关数据的更新
    private final static String ACT_PRIZE_RECORD_MAIN_UPDATE = "PRIZE_RECORD_MAIN:U_";//Prize表相关数据的更新
//    private final static String ACT_PRIZE_RECORD_FAILURE_UPDATE = "PRIZE_RECORD_FAILURE:U_";
    private final static String ACT_PRIZE_RECORD_FINISHED_GROUP_UPDATE = "PRIZE_RECORD_FINISHED_GROUP:U_";// 层级任务完成更新发放状态及取现的开始id及时间
    private final static String ACT_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE_UPDATE = "ACT_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE:U_";// 层级任务完成更新发放状态及取现的开始id及时间
    private final static String ACT_PRIZE_RECORD_FIRST_PARTICIPATOR_SETT_TIME_UPDATE = "ACT_PRIZE_RECORD_FIRST_PARTICIPATOR_SETT_TIME:U_";//发放完更新首次参与用户的结算时间
    // private ExecutorService executorService =
    // Executors.newFixedThreadPool(32);
  
    @Autowired
    private RedisCacheTemplate redisCacheTemplate;
    /*
    @Autowired
    private ActPrizeRecordExtMapper actPrizeRecordExtMapper;

    @Autowired
    private ActPrizeRecordMapper actPrizeRecordMapper;

    @Autowired
    private ActPrizeRecordManager actPrizeRecordManager;
    
    @Autowired
    private ActWaitDealRecordManager actWaitDealRecordManager;
    
    @Autowired
	private ActAccountStatRedis actAccountStatRedis; 
    

    private String gddSize = "50"; */
 
    
    public void rPushRecordExt(ActPrizeRecordExt actPrizeRecordExt, String companyCode) {
        try {	
            String actPrizeRecordExtStr = JsonUtil.obj2Str(actPrizeRecordExt);
            redisCacheTemplate.rpush(ACT_PRIZE_RECORD_EXT_UPDATE + companyCode, actPrizeRecordExtStr);
        } finally {
            redisCacheTemplate.close();
        }
    }
    
    public void rPushRecordMain(ImsPrizeRecord actPrizeRecord, String companyCode) {
        try {	
            String actPrizeRecordStr = JsonUtil.obj2Str(actPrizeRecord);
            redisCacheTemplate.rpush(ACT_PRIZE_RECORD_MAIN_UPDATE + companyCode, actPrizeRecordStr);
        } finally {
            redisCacheTemplate.close();
        }
    }

   /* public Map<String, Long> saveExtDataFromRedisToDB(String companyCode) {
        return saveExtDataFromRedisToDB(null, companyCode);
    }

    public Map<String, Long> saveExtDataFromRedisToDB(Integer max, String companyCode) {
        Map<String, Long> result = new HashMap<>();
        max = (max == null ? Integer.valueOf(gddSize) : max);
        try {
            // 判断redis中是否有数据，没数据直接返回
            long total = redisCacheTemplate.llen(ACT_PRIZE_RECORD_EXT_UPDATE + companyCode);
            if (0 == total) {
                result.put("total", 0L);
                result.put("curSysnSize", 0L);
                return result;
            }
            long curSysnSize = total > max ? max : total;
            for (int i = 0; i < curSysnSize; i++) {
                String msg = redisCacheTemplate.lpop(ACT_PRIZE_RECORD_EXT_UPDATE + companyCode);
                ActPrizeRecordExt actPrizeRecordExt = JsonUtil.json2Obj(msg, ActPrizeRecordExt.class);
                if (actPrizeRecordExt.getRecordNumbers() != null && !actPrizeRecordExt.getRecordNumbers().isEmpty()) {
                    actPrizeRecordExtMapper.updateBatchByRecordNumbers(actPrizeRecordExt);
                } else {
                    actPrizeRecordExtMapper.updateByRecordNumber(actPrizeRecordExt);
                }
                
                if(actPrizeRecordExt.getReleaseFinish()!=null && actPrizeRecordExt.getReleaseFinish() == 2){
                	logger.info("{}处理扣回贈金统计通知",companyCode);
                	if (actPrizeRecordExt.getRecordNumbers() != null && !actPrizeRecordExt.getRecordNumbers().isEmpty()) {
                		for(String recordNumber:actPrizeRecordExt.getRecordNumbers()){
                			ActPrizeRecord itemPrizeRecord = actPrizeRecordMapper.getActPriceRecord(recordNumber, companyCode);
                			if(itemPrizeRecord!=null){
                				Map<String,Object> dataParams = new HashMap<String,Object>();
    							dataParams.put("accountNo", itemPrizeRecord.getAccountNo());
    							dataParams.put("platform", itemPrizeRecord.getGuestPlatForm());
    							dataParams.put("activityPeriods", itemPrizeRecord.getActivityPeriods());
                    			actAccountStatRedis.rPushAccount(dataParams, companyCode);
                			}
                		}
                    } else {
            			ActPrizeRecord itemPrizeRecord = actPrizeRecordMapper.getActPriceRecord(actPrizeRecordExt.getRecordNumber(), companyCode);
            			if(itemPrizeRecord!=null){
            				Map<String,Object> dataParams = new HashMap<String,Object>();
							dataParams.put("accountNo", itemPrizeRecord.getAccountNo());
							dataParams.put("platform", itemPrizeRecord.getGuestPlatForm());
							dataParams.put("activityPeriods", itemPrizeRecord.getActivityPeriods());
                			actAccountStatRedis.rPushAccount(dataParams, companyCode);
            			}
                    }
                }

            }
            result.put("total", total);
            result.put("curSysnSize", curSysnSize);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("total", 0L);
            result.put("curSysnSize", 0L);
            return result;
        } finally {
            redisCacheTemplate.close();
        }
    }
    
    public Map<String, Long> savePrizeMainDataFromRedisToDB(String companyCode) {
        return savePrizeMainDataFromRedisToDB(null, companyCode);
    }

    public Map<String, Long> savePrizeMainDataFromRedisToDB(Integer max, String companyCode) {
        Map<String, Long> result = new HashMap<>();
        max = (max == null ? Integer.valueOf(gddSize) : max);
        try {
            // 判断redis中是否有数据，没数据直接返回
            long total = redisCacheTemplate.llen(ACT_PRIZE_RECORD_MAIN_UPDATE + companyCode);
            if (0 == total) {
                result.put("total", 0L);
                result.put("curSysnSize", 0L);
                return result;
            }
            long curSysnSize = total > max ? max : total;
            for (int i = 0; i < curSysnSize; i++) {
                String msg = redisCacheTemplate.lpop(ACT_PRIZE_RECORD_MAIN_UPDATE + companyCode);
                ActPrizeRecord actPrizeRecord = JsonUtil.json2Obj(msg, ActPrizeRecord.class);
                if (actPrizeRecord.getRecordNumbers() != null) {
                    actPrizeRecordMapper.updateBatchByRecordNumbers(actPrizeRecord);
                } 
            }
            result.put("total", total);
            result.put("curSysnSize", curSysnSize);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("total", 0L);
            result.put("curSysnSize", 0L);
            return result;
        } finally {
            redisCacheTemplate.close();
        }
    }

    *//**----------发放完，本层级型任务都已完成，需标记更新发放记录的发放状态及取现开始时间和取现开始id**//*
    *//**
     * 发放完，本层级型任务都已完成，需标记更新发放记录的发放状态及取现开始时间和取现开始id
     * 
     * @param dto
     * @param companyCode
     *//*
    public void rPushPrizeFinishedGroupTask(ActFinishedGroupTaskVO dto, String companyCode) {
        String waitDealInfo = JsonUtil.obj2Str(dto);
        try {
            redisCacheTemplate.rpush(ACT_PRIZE_RECORD_FINISHED_GROUP_UPDATE + companyCode, waitDealInfo);
        } catch (Exception e) {// 存放到待处理DB中
            actWaitDealRecordManager.addActWaitDealRecord(dto.getActivityPeriods(),dto.getAccountNo(), dto.getPlatform(), ActWaitDealTypeEnum.U_PRIZE_RECORD_FINISHED_GROUP_TASK.getCode(), waitDealInfo, null, ActCompany.getKeyByCode(companyCode));
        } finally {
            redisCacheTemplate.close();
        }
    }

    *//**
     * 是否存在需要处理的数据
     * @return
     *//*
    public boolean isExistDealFinishGroupTaskData(String companyCode){
        try {
            // 判断redis中和内存中是否有数据，没数据直接返回
            long total = redisCacheTemplate.llen(ACT_PRIZE_RECORD_FINISHED_GROUP_UPDATE + companyCode);
            if(total>0){
                return true;
            }
        }catch(Exception e){
        }
        int count = actWaitDealRecordManager.listActWaitDealRecordCount(ActCompany.getKeyByCode(companyCode),ActWaitDealTypeEnum.U_PRIZE_RECORD_FINISHED_GROUP_TASK.getCode());
        return count>0?true:false;
    }
    
    public Map<String, Long> saveFinishGroupTaskDataToDB(String companyCode) {
        Map<String, Long> result = saveDataFromRedisToDB(null,companyCode,ACT_PRIZE_RECORD_FINISHED_GROUP_UPDATE);
        Map<String, Long> result2 = saveDataFromWaitDealToDB(null,companyCode,ActWaitDealTypeEnum.U_PRIZE_RECORD_FINISHED_GROUP_TASK.getCode());
        result.put("total", result.get("total") + result2.get("total"));
        result.put("curSysnSize", result.get("curSysnSize") + result2.get("curSysnSize"));
        return result;
    }
 
     
    *//**----------发放完入金型任务需要标记最后一条发放记录的ReleaseType为4**//*
    *//**
     * 发放完入金型任务需要标记最后一条发放记录的ReleaseType为4
     * 
     * @param dto
     * @param companyCode
     *//*
    public void rPushPrizeTaskLastRecordReleaseType(ActTaskLastRecordReleaseTypeVO dto, String companyCode) {
        String waitDealInfo = JsonUtil.obj2Str(dto);
        try {
            redisCacheTemplate.rpush(ACT_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE_UPDATE + companyCode, waitDealInfo);
        } catch (Exception e) {// 存放到内存中
            actWaitDealRecordManager.addActWaitDealRecord(dto.getActivityPeriods(),dto.getAccountNo(), dto.getPlatform(), ActWaitDealTypeEnum.U_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE.getCode(), waitDealInfo, null, ActCompany.getKeyByCode(companyCode));
        } finally {
            redisCacheTemplate.close();
        }
    }

    *//**
     * 是否存在需要处理的数据:发放完入金型任务需要标记最后一条发放记录的ReleaseType为4
     * @return
     *//*
    public boolean isExistDealTaskLastRecordReleaseTypeData(String companyCode){
        try {
            // 判断redis中和内存中是否有数据，没数据直接返回
            long total = redisCacheTemplate.llen(ACT_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE_UPDATE + companyCode);
            if(total>0){
                return true;
            }
        }catch(Exception e){
        }
        int count = actWaitDealRecordManager.listActWaitDealRecordCount(ActCompany.getKeyByCode(companyCode),ActWaitDealTypeEnum.U_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE.getCode());
        return count>0?true:false;
        
    }
    *//**
     * 处理发放完入金型任务需要标记最后一条发放记录的ReleaseType为4
     * 
     * @param dto
     * @param companyCode
     *//*
    public Map<String, Long> saveTaskLastRecordReleaseTypeDataToDB(String companyCode) {
        Map<String, Long> result = saveDataFromRedisToDB(null,companyCode,ACT_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE_UPDATE);
        Map<String, Long> result2 = saveDataFromWaitDealToDB(null,companyCode,ActWaitDealTypeEnum.U_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE.getCode());
        result.put("total", result.get("total") + result2.get("total"));
        result.put("curSysnSize", result.get("curSysnSize") + result2.get("curSysnSize"));
        return result;
    }
 
    *//**----------发放完更新首次参与用户的结算时间**//*
    *//**
     * 添加发放完更新首次参与用户的结算时间
     * 
     * @param dto
     * @param companyCode
     *//*
    public void rPushPrizeFirstParticipatorSettTime(ActParticipateAccountVO dto,String companyCode) {
        String waitDealInfo = JsonUtil.obj2Str(dto);
        try {
            redisCacheTemplate.rpush(ACT_PRIZE_RECORD_FIRST_PARTICIPATOR_SETT_TIME_UPDATE + companyCode, waitDealInfo);
        } catch (Exception e) {//存放到数据库中
            actWaitDealRecordManager.addActWaitDealRecord(dto.getActivityPeriods(),dto.getAccountNo(), dto.getPlatform(), ActWaitDealTypeEnum.U_FIRST_PARTICIPATOR_SETT_TIME.getCode(), waitDealInfo, null, ActCompany.getKeyByCode(companyCode));
        } finally {
            redisCacheTemplate.close();
        }
    }
    
    *//**
     * 是否存在需要处理的数据
     * @return
     *//*
    public boolean isExistDealFirstParticipatorSettTimeData(String companyCode){
        try {
            // 判断redis中和DB待处理中是否有数据，没数据直接返回
            long total = redisCacheTemplate.llen(ACT_PRIZE_RECORD_FIRST_PARTICIPATOR_SETT_TIME_UPDATE + companyCode);
            if(total>0){
                return true;
            }
        }catch(Exception e){
        }
        int count = actWaitDealRecordManager.listActWaitDealRecordCount(ActCompany.getKeyByCode(companyCode),ActWaitDealTypeEnum.U_FIRST_PARTICIPATOR_SETT_TIME.getCode());
        return count>0?true:false;
    }
    
    *//**
     * 处理发放完更新首次参与用户的结算时间
     * 
     * @param dto
     * @param companyCode
     *//*
    public Map<String, Long> saveFirstParticipatorSettTimeDataToDB(String companyCode) {
        Map<String, Long> result = saveDataFromRedisToDB(null,companyCode,ACT_PRIZE_RECORD_FIRST_PARTICIPATOR_SETT_TIME_UPDATE);
        Map<String, Long> result2 = saveDataFromWaitDealToDB(null,companyCode,ActWaitDealTypeEnum.U_FIRST_PARTICIPATOR_SETT_TIME.getCode());
        result.put("total", result.get("total") + result2.get("total"));
        result.put("curSysnSize", result.get("curSysnSize") + result2.get("curSysnSize"));
        return result;
    }
 
    
    *//**
     * 处理redis中的记录
     * @param max
     * @param companyCode
     * @return
     *//*
    private Map<String, Long> saveDataFromRedisToDB(Integer max, String companyCode,String redisPreKey) {
        Map<String, Long> result = new HashMap<>();
        max = (max == null ? Integer.valueOf(gddSize) : max);
        try {
            // 判断redis中是否有数据，没数据直接返回
            long total = redisCacheTemplate.llen(redisPreKey + companyCode);
            if (0 == total) {
                result.put("total", 0L);
                result.put("curSysnSize", 0L);
                return result;
            }
            long curSysnSize = total > max ? max : total;
            List<String> failMsgs = new ArrayList<String>();
            for (int i = 0; i < curSysnSize; i++) {
                String msg = redisCacheTemplate.lpop(redisPreKey + companyCode);
                try {
                    excDealWaitDealRecord(msg,null,redisPreKey);
                } catch (Exception e) {
                    failMsgs.add(msg);
                    logger.error("<<<<saveDataFromRedisToDB 处理失败,msg=" + msg, e);
                }
            }
            if(!failMsgs.isEmpty()){
                for(String msg : failMsgs){
                    redisCacheTemplate.rpush(redisPreKey + companyCode, msg);
                }
            }
            result.put("total", total);
            result.put("curSysnSize", curSysnSize);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("total", 0L);
            result.put("curSysnSize", 0L);
            return result;
        } finally {
            redisCacheTemplate.close();
        }
    }
    
    *//**
     * 处理待发放记录--DB待处理记录
     * 
     * @param max
     * @param companyCode
     * @return
     *//*
    private Map<String, Long> saveDataFromWaitDealToDB(Integer max,String companyCode,String waitDealType) {
        Map<String, Long> result = new HashMap<>();
        long total = 0;
        long curSysnSize = 0;
         max   = (max == null ? Integer.valueOf(gddSize) : max);
        ActWaitDealRecord prizeRecordRedo = new ActWaitDealRecord();
        prizeRecordRedo.setCompanyId(ActCompany.getKeyByCode(companyCode));
        prizeRecordRedo.setWaitDealType(waitDealType);
        List<ActWaitDealRecord>  waitList = actWaitDealRecordManager.listActWaitDealRecord(prizeRecordRedo);
        if(CollectionUtils.isNotEmpty(waitList)){
            curSysnSize = waitList.size() > max ? max : waitList.size();
            List<Long> deleteIds = new ArrayList<Long>();
            for (int i = 0; i < curSysnSize; i++) {
                ActWaitDealRecord record  = waitList.get(i);
                if(StringUtil.isNotBlank(record.getWaitDealInfo())){
                    try {
                        //实际处理内容
                        excDealWaitDealRecord(record.getWaitDealInfo(),waitDealType,null);
                        deleteIds.add(record.getId());
                    } catch (Exception e) {
                        logger.error("<<<<saveDataFromWaitDealToDB 处理失败,msg=" + record.getWaitDealInfo(), e);
                    }
                }else{
                    deleteIds.add(record.getId());
                }
            }
            if(CollectionUtils.isNotEmpty(deleteIds)){
                actWaitDealRecordManager.deleteByIds(deleteIds);
            }
        }
        result.put("total", total);
        result.put("curSysnSize", curSysnSize);
        return result;
    }
   
    //实际执行的处理
    private void excDealWaitDealRecord(String waitDealInfo,String waitDealType,String redisPreKey){
        //首次参与用户结算时间更新
        if(ActWaitDealTypeEnum.U_FIRST_PARTICIPATOR_SETT_TIME.getCode().equals(waitDealType)||ACT_PRIZE_RECORD_FIRST_PARTICIPATOR_SETT_TIME_UPDATE.equals(redisPreKey)){
            ActParticipateAccountVO dto = JsonUtil.json2Obj(waitDealInfo, ActParticipateAccountVO.class);
            actPrizeRecordManager.updateBatchSettlementTime(dto.getActivityPeriods(), dto.getAccountNo(),dto.getPlatform(), dto.getCompanyId(),dto.getSettlementTime());
          logger.info(String.format("<<<首次参与用户结算时间更新,activityPeriods:%s,accountNo:%s,platform:%s",dto.getActivityPeriods(),dto.getAccountNo(),dto.getPlatform()));
        }
        //更新入金任务的最后一条赠金记录的releaseType
        else if(ActWaitDealTypeEnum.U_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE.getCode().equals(waitDealType)||ACT_PRIZE_RECORD_LAST_TASK_RELEASE_TYPE_UPDATE.equals(redisPreKey)){
            ActTaskLastRecordReleaseTypeVO dto = JsonUtil.json2Obj(waitDealInfo, ActTaskLastRecordReleaseTypeVO.class);
            actPrizeRecordManager.updateTaskLastRecordReleaseType(dto);
             logger.info(String.format("<<<标记任务的最后一条发放记录,activityPeriods:%s,accountNo:%s,platform:%s,taskItemCode:%s",dto.getActivityPeriods(),dto.getAccountNo(),dto.getPlatform(),dto.getTaskItemCode()));
        }
        
        //本层级型任务都已完成，需标记更新发放记录的发放状态及取现开始时间和取现开始id
        else if(ActWaitDealTypeEnum.U_PRIZE_RECORD_FINISHED_GROUP_TASK.getCode().equals(waitDealType)||ACT_PRIZE_RECORD_FINISHED_GROUP_UPDATE.equals(redisPreKey)){
            ActFinishedGroupTaskVO dto = JsonUtil.json2Obj(waitDealInfo, ActFinishedGroupTaskVO.class);
            actPrizeRecordManager.updateBatchFlagActFinishedGroupTask(dto);
             logger.info(String.format("<<<标记层级任务发放记录,activityPeriods:%s,accountNo:%s,platform:%s,taskGroup:%s",dto.getActivityPeriods(),dto.getAccountNo(),dto.getPlatform(),dto.getTaskGroup()));
        }
      
    }*/
}
