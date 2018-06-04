package com.gwghk.ims.activity.dao.inf;

import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActTradeRecordDemo;

import net.oschina.durcframework.easymybatis.dao.CrudDao;

public interface ActTradeRecordDemoDao extends CrudDao<ActTradeRecordDemo>{
   
    Double countSumLot(Map<String,Object> data);
    
}