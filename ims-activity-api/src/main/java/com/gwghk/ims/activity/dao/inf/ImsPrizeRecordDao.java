package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.common.common.BaseDao;

public interface ImsPrizeRecordDao extends BaseDao<ImsPrizeRecord> {
	
	List<ImsPrizeRecord> findByIds(@Param("ids")List<String> ids,@Param("actNo")String actNo) ;
	/**
	 * 查找通过记录号码
	 * @param recordNumber
	 * @param companyId
	 * @param companyCode
	 * @return
	 */
	ImsPrizeRecord selectByRecordNumber(@Param("recordNumber")String recordNumber);
	/**
	 * 查找应发记录
	 * @param paramsPrizeRecord
	 * @return
	 */
	List<ImsPrizeRecord> findWaitPrizeRecords(ImsPrizeRecord paramsPrizeRecord);
	/**
	 * 更新发放记录的状态
	 * @param ImsPrizeRecord
	 */
	void updateGiveStatus(ImsPrizeRecord actPrizeRecord);
}