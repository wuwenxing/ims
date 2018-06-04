package com.gwghk.ims.message.dao.inf;

import java.util.Map;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.ActTradeRecord;

/**
 * 
 * @ClassName: ActivityTradeRecordMapper
 * @Description: 用户交易数据mapper
 * @author lawrence
 * @date 2017年5月22日
 *
 */
public interface ActTradeRecordDao extends BaseDao<ActTradeRecord> {
	/**
	 * 清洗当前数据
	 * @param params
	 * @return
	 */
	ActTradeRecord getCleanTrade(Map<String, Object> params);
	/**
	 * 查询存在的清洗的数据
	 * @param actTradeRecord
	 * @return
	 */
	ActTradeRecord getTrade(ActTradeRecord actTradeRecord);
}
