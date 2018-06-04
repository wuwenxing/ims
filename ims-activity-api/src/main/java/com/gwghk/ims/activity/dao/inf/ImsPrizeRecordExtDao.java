package com.gwghk.ims.activity.dao.inf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.common.common.BaseDao;

public interface ImsPrizeRecordExtDao extends BaseDao<ActPrizeRecordExt>{
	int updateByRecordNumber(ActPrizeRecordExt record);
	
	int updateBatchByDealNumbers(ActPrizeRecordExt record);
	
	int updateBatchByRecordNumbers(ActPrizeRecordExt record);
 
	/**
	 * 
	 * @MethodName: updateBatchSettlementByAccountNoAndActPeriods
	 * @Description: 批量结算发放记录
	 * @param record
	 * @return void
	 */
	int updateBatchSettlementByAccountNoAndActPeriods(VActPrizeRecord record);
	
	Integer updateBatchFlagAccountWithdrawalStart(Map<String,Object> data);
	
	Integer updateBatchSettlementTime(Map<String,Object> data);
	
	Integer updateTaskLastRecordReleaseType(Map<String,Object> data);
	
	Integer updateTaskSettleFlag(Map<String,Object> data);
	
	
	/**
	 * 根据任务code获取总取现手数(有子任务的可调用,如：累计入金及入金）
	 */
	BigDecimal getAccountTaskLotsByTaskItemCode(Map<String,Object> data);
	
	/**
     * 根据任务code获取总发放额度(有子任务的可调用,如：统计累计入金的领取额度)
     * @param data
     * @return
     */
	BigDecimal getAccountGiftAmountSumByTaskItemCode(Map<String,Object> data);
	
	/**
     * 根据任务code获取总发放额度(有子任务的可调用,如：统计累计入金的发放额度)
     * @param data
     * @return
     */
	BigDecimal getAccountIssueAmountSumByTaskItemCode(Map<String,Object> data);
 
	
	/**
     * 根据任务id获取总取现手数
     * @param data
     * @return
     */
    BigDecimal getAccountTaskLotsByTaskId(Map<String,Object> data);
	
    /**
     * 获取扩展信息
     * @param recordNumber
     * @param actNo
     * @return
     */
	ActPrizeRecordExt getActPrizeRecordExt(@Param("recordNumber")String recordNumber,@Param("actNo") String actNo);
	
	
	/**
	 * 
	 * @MethodName: updateBatchByAccountPeriodsReleaseType
	 * @Description: 当发放记录releaseType=4时，更新结算标识2
	 * @param record
	 * @return
	 * @return Integer
	 */
	Integer updateBatchByAccountPeriodsReleaseType(VActPrizeRecord record);
	
	/**
	 * 
	 * @MethodName: findReadyReleaseFinishRecord
	 * @Description: 查询到期release_finish栏位为0的记录(延迟一天)
	 * @param params
	 * @return
	 * @return List<ActPrizeRecordExt>
	 */
	List<ActPrizeRecordExt> findReadyReleaseFinishRecord(Map<String,Object> params);
}