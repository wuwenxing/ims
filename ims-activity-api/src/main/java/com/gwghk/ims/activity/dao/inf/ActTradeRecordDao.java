package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActTradeRecord;
import com.gwghk.ims.common.common.BaseDao;

public interface ActTradeRecordDao extends BaseDao<ActTradeRecord>{
   
    Double getSumLot(Map<String,Object> data);

	List<ActTradeRecord> getTradeRecordList(Map<String, Object> params);
    
}