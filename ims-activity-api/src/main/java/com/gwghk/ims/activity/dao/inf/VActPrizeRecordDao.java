package com.gwghk.ims.activity.dao.inf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.common.common.BaseDao;

public interface VActPrizeRecordDao extends BaseDao<VActPrizeRecord>{
    List<VActPrizeRecord> listHasDealActPriceRecord(Map<String, Object> recordData);

    VActPrizeRecord getPreviosRecord(VActPrizeRecord vActPrizeRecord);

    List<VActPrizeRecord> getAccountActPrizeRecord(VActPrizeRecord vActPrizeRecord);
    
    Integer isExistNoFinishedRecord(Map<String,Object> map);
    
    Date getMinWithdrawalStartTime(Map<String,Object> map);
    
    VActPrizeRecord getTaskLastFinishedLotTradeRecord(Map<String,Object> map);
    
    List<VActPrizeRecord> findWithGoldTradePrizeRecord(VActPrizeRecord vActPrizeRecord);
    
    VActPrizeRecord getFinishTradeLot(VActPrizeRecord vActPrizeRecord);
    
    VActPrizeRecord getTokenCoinSum(VActPrizeRecord vActPrizeRecord);
    
    List<VActPrizeRecord> findSettlementWithGoldTradePrizeRecord(VActPrizeRecord vActPrizeRecord);
    
    List<VActPrizeRecord> findSettlementTokenCoinTradePrizeRecord(VActPrizeRecord vActPrizeRecord);
    
    List<VActPrizeRecord> findUnfinishedWithGoldRecordByAccountNo(VActPrizeRecord vActPrizeRecord);
    
    List<VActPrizeRecord> findUnfinishedTokenCoinByAccountNo(VActPrizeRecord vActPrizeRecord);
    
    List<String> findAccountNoWithGoldTradePrizeRecord(VActPrizeRecord vActPrizeRecord);
    
    /**
     * 更新发放状态
     * @param vActPrizeRecord
     * @return
     */
    int updateGivedStatus(VActPrizeRecord vActPrizeRecord);
    /**
     * 
     * @MethodName: findZeroFinishTradeLot
     * @Description: 查询real发放中的发放记录，并且当前完成手数为0的发放记录
     * @param vActPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findZeroFinishTradeLot(VActPrizeRecord vActPrizeRecord);
    
    /**
     * 
     * @MethodName: findDemoZeroFinishTradeLot
     * @Description: 查询demo发放中的发放记录，并且当前完成手数为0的发放记录
     * @param vActPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findDemoZeroFinishTradeLot(VActPrizeRecord vActPrizeRecord);
    
    /**
     * 
     * @MethodName: findSuccessWithGoldTradePrizeRecordLimitType3
     * @Description: 查询该层级累计入金任务中,任务完成，releaseType=3可以做释放的发放记录
     * @param vActPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findSuccessWithGoldTradePrizeRecordLimitType3(VActPrizeRecord vActPrizeRecord);
    
    /**
     * 
     * @MethodName: findSettlementWithGoldTradePrizeRecordLimitType3
     * @Description: 查询结算时间到达时，releaseType=3的可以做释放的发放记录
     * @param vActPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findSettlementWithGoldTradePrizeRecordLimitType3(VActPrizeRecord vActPrizeRecord);
    
    List<VActPrizeRecord> findPrizeRecordListByExample(VActPrizeRecord vActPrizeRecord);
    
    /**
     * 
     * @MethodName: findDemoTokenCoinReadySend
     * @Description: 查询demo代币待发放记录
     * @param companyCode
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findDemoTokenCoinReadySend(@Param("companyCode")String companyCode);
    
    /**
     * 
     * @MethodName: findRealTokenCoinReadySend
     * @Description: 查询demo代币待发放记录
     * @param companyCode
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findRealTokenCoinReadySend(@Param("companyCode")String companyCode);
    
    /**
     * 
     * @MethodName: findUnfinishedRecordByAccountNo
     * @Description: 查询客户平台活动内的处于待处理的发放记录
     * @param params
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findUnfinishedRecordByAccountNo(Map<String,Object> params);
    
    /**
     * 
     * @MethodName: findByAccountNoAndActPeriods
     * @Description: 查找用户的发放记录
     * @param actPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findByAccountNoAndActPeriods(VActPrizeRecord actPrizeRecord);
    
    /**
     * 
     * @MethodName: findSettlementWaitingPrize
     * @Description: 查找已经到达结算时间的处于等待中的发放记录(一般是层级任务的发放记录)
     * @param VActPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findSettlementWaitingPrize(VActPrizeRecord actPrizeRecord);
    
    /**
     * 
     * @MethodName: findAbnormalPrizeRecord
     * @Description: 查找异常订单
     * @param params
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findAbnormalPrizeRecord(Map<String,Object> params);
    
    /**
     * 
     * @MethodName: findSuccessPrizeRecord
     * @Description: 查找延后一天，release_finsih=0的记录,排除赠金记录
     * @param params
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findSuccessPrizeRecord(Map<String,Object> params);
    
    
    /**
     * 
     * @MethodName: findSettlementGoldCoinUnRelativeRecord
     * @Description: 查询结算时间到达未做demo-real关联关系的数据列表(贈金，代币)
     * @param actPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findSettlementGoldCoinUnRelativeRecord(VActPrizeRecord actPrizeRecord);
    
    /**
     * 
     * @MethodName: findSettlementAnalogCoinUnRelativeRecord
     * @Description: 查询结算时间到达未做demo-real关联关系的数据列表(模拟币)
     * @param actPrizeRecord
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findSettlementAnalogCoinUnRelativeRecord(VActPrizeRecord actPrizeRecord);
    
    /**
     * 查找发放记录条数
     * @param params
     * @return
     */
    Integer getPrizeCount(Map<String,Object> params);
    
    /**
     * 
     * @MethodName: findGoldAndCoinPrizeByAccount
     * @Description: 查询账号发放记录
     * @param params
     * @return
     * @return List<VActPrizeRecord>
     */
    List<VActPrizeRecord> findGoldAndCoinPrizeByAccount(Map<String,Object> params);
	/**
	 * 查找指定类型所有的已发送成功的记录
	 * @param account
	 * @param itemType
	 * @param actNo
	 * @return
	 */
	List<VActPrizeRecord> findListByAccount(@Param("accountNo")String accountNo,@Param("itemType")String itemType,@Param("actNo")String actNo );
	/**
	 * 通过编号查找
	 * @param recordNumber
	 * @param actNo
	 * @return
	 */
	VActPrizeRecord getActPriceRecord(@Param("recordNumber")String recordNumber,@Param("actNo")String actNo);

}