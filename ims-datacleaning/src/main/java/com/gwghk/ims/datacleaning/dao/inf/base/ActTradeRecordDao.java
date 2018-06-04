package com.gwghk.ims.datacleaning.dao.inf.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfo;
import com.gwghk.ims.datacleaning.dao.entity.ActSyncDataUpdate;
import com.gwghk.ims.datacleaning.dao.entity.ActTradeRecord;

/**
 * 
 * @ClassName: ActivityTradeRecordMapper
 * @Description: 用户交易数据mapper
 * @author lawrence
 * @date 2017年5月22日
 *
 */
public interface ActTradeRecordDao extends BaseDao<ActTradeRecord> {
	// int insertReal(Date lastUpdateDate);
	@SuppressWarnings("rawtypes")
	int insertGts2Trade(Map data);

	ActTradeRecord selectLastActTradeRecord(@Param("env") String env, @Param("source") String source,
			@Param("companyCode") String companyCode);

	/**
	 * 
	 * @MethodName: saveOrUpdateRealManual
	 * @Description: 更新或者写入真实交易数据
	 * @param actTradeRecord
	 * @return void
	 */
	void saveOrUpdateRealManual(ActTradeRecord actTradeRecord);

	/**
	 * 
	 * @MethodName: saveOrUpdateDemoManual
	 * @Description: 更新或者写入模拟交易数据
	 * @param actTradeRecord
	 * @return void
	 */
	void saveOrUpdateDemoManual(ActTradeRecord actTradeRecord);

	/**
	 * 查询所有没有推送的数据
	 * @return
	 */
	List<ActTradeRecord> findUnpushTrashRecord(@Param("env") String env);
	/**
	 * 批量更新推送状态
	 */
	int batchUpdatePushStatus(@Param("env") String env,@Param("id") Long id);
}
