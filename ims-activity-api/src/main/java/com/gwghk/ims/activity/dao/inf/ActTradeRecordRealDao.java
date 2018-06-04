package com.gwghk.ims.activity.dao.inf;

import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActTradeRecordReal;

import net.oschina.durcframework.easymybatis.dao.CrudDao;

public interface ActTradeRecordRealDao extends CrudDao<ActTradeRecordReal>{
   
    Double countSumLot(Map<String,Object> data);
    
}