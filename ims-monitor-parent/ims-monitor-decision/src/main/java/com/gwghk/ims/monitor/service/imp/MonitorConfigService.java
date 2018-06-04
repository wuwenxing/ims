package com.gwghk.ims.monitor.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.monitor.dal.entity.AppServer;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.dal.mapper.AlertStrategyDao;
import com.gwghk.ims.monitor.dal.mapper.AppServerDao;
import com.gwghk.ims.monitor.dal.mapper.MonitorItemDao;
import com.gwghk.ims.monitor.dal.mapper.MonitorMachineDao;
import com.gwghk.ims.monitor.enums.MONITOR_TYPE;

import net.oschina.durcframework.easymybatis.query.Query;

/**
 * 获取监控配置信息
 * @author jackson.tang
 *
 */
@Service
public class MonitorConfigService {
	@Autowired
	private AppServerDao appServerDao;
	@Autowired
	private MonitorMachineDao monitorMachineDao;
	@Autowired
	private MonitorItemDao monitorItemDao;
	@Autowired
	private AlertStrategyDao monitorStrategyDao;
	
	/**
	 * 读取所有的服务配置信息
	 * @return
	 */
	public List<MonitorMachine> findConfigInfo(String env){
		Query query=Query.build();
		query.eq("env", env);
		List<MonitorMachine> machines=monitorMachineDao.find(query);
		
		for(MonitorMachine machine:machines) {
			query=Query.build().eq("app_id", machine.getAppId());
			List<MonitorItem> items=monitorItemDao.find(query);
			if(items==null)
				continue;
			
			//初始化枚举值
			for(MonitorItem item:items) {
				item.setItemTypeEnum(MONITOR_TYPE.findByVal(item.getItemType()));
			}
			
			machine.setItems(items);
		}
		
		
		return machines;
	}
	
	public Map<Long,String> findAppServerInfo(){
		List<AppServer> list=appServerDao.findAll(null);
		if(list==null)
			return null;
		
		Map<Long,String> result=new HashMap<Long,String>();
		for(AppServer item:list)
			result.put(item.getAppId(), item.getServerName());
			
		return result;
	}
	
	public List<MonitorMachine> findMachines(String env){
		return monitorMachineDao.find(Query.build().eq("env", env));
	}
}
