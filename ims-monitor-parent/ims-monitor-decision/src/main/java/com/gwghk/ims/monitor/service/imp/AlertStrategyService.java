package com.gwghk.ims.monitor.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.monitor.dal.entity.AlertStrategy;
import com.gwghk.ims.monitor.dal.mapper.AlertStrategyDao;

import net.oschina.durcframework.easymybatis.query.Query;

@Service
public class AlertStrategyService {
	
	@Autowired
	private AlertStrategyDao alertStrategyDao;
	/**
	 * 查找监控条目下所有的策略
	 * @param itemId
	 * @return
	 */
	public List<AlertStrategy> findAlertStrategyByItemId(Long itemId){
		Query query=new Query();
		query.eq("item_id", itemId).eq("enable", 1);
		
		return alertStrategyDao.find(query);
	}
}
