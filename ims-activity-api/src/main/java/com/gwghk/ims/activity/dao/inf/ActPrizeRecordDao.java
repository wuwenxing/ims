package com.gwghk.ims.activity.dao.inf;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ActPrizeRecord;

public interface ActPrizeRecordDao {
	/**
	 * 更新记录
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(ActPrizeRecord record);
	
	/**
	 * 查找通过记录号码
	 * @param recordNumber
	 * @param companyId
	 * @param companyCode
	 * @return
	 */
	ActPrizeRecord selectByRecordNumber(@Param("recordNumber")String recordNumber, @Param("companyId")Long companyId, @Param("companyCode")String companyCode);
}