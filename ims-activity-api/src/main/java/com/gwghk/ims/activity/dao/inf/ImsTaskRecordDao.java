package com.gwghk.ims.activity.dao.inf;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.common.common.BaseDao;

public interface ImsTaskRecordDao extends BaseDao<ImsTaskRecord> {
	
	Date findMaxTaskFinishTime(@Param("actNo")String actNo,@Param("accNo")String accNo,@Param("platform")String platform,@Param("taskSettingId")Integer taskSettingId,@Param("subTaskSettingId")Integer subTaskSettingId,@Param("taskGroup")Integer taskGroup) ;
	
	List<ImsTaskRecord> findNeedRecordList(@Param("actNo")String actNo) ;
	/**
	 * 查找通过id
	 * @param id
	 * @param actNo
	 * @return
	 */
	ImsTaskRecord findById(@Param("id")int id,@Param("actNo") String actNo);
}