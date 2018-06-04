package com.gwghk.ims.activity.dao.inf;

import org.apache.ibatis.annotations.Param;

/**
 * 摘要：公用创建表和删除表Dao
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月18日
 */
public interface CommonTableDao {
 
    int createActImsPrizeRecordTableTable(@Param("activityPeriods") String activityPeriods);
    
    int dropActImsPrizeRecordTableIfEXISTS(@Param("activityPeriods") String activityPeriods); 
    
    int dropActImsPrizeRecordExtTableIfEXISTS(@Param("activityPeriods") String activityPeriods);
    
    int createActImsPrizeRecordExtTable(@Param("activityPeriods") String activityPeriods); 
    
    int dropActImsTaskRecordTableIfEXISTS(@Param("activityPeriods") String activityPeriods);
    
    int createActImsTaskRecordTable(@Param("activityPeriods") String activityPeriods); 
   
    int dropTableIfEXISTS(@Param("tableName") String tableName);
    
    int existTable(@Param("schema") String schema,@Param("tableName") String tableName);
}