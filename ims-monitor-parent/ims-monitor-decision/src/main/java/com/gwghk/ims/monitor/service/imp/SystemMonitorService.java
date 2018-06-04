package com.gwghk.ims.monitor.service.imp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.vo.monitor.MonitorDataVo;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.service.inf.ISystemMonitor;
import com.gwghk.unify.framework.common.util.HttpClientUtil;

/**
 * 系统监控
 * @author jackson.tang
 *
 */
@Service
public class SystemMonitorService implements ISystemMonitor{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 获取系统监控信息
	 * @param machine
	 * @param item
	 * @param name 具体需要监控条目：cpu memory hardDisk uploadSpeed downloadSpeed
	 * @return
	 */
	private MonitorResultVo getSytemInfo(MonitorMachine machine,MonitorItem item,String name) {
		String url=machine.getBaseURL()+"/monitor/system";
		MonitorResultVo result=new MonitorResultVo();
		result.setHostname(machine.getIpAddress()+":"+machine.getIpPort());
		result.setItemId(item.getItemId());
		result.setItemName(item.getName());
		//单行结果记录
		result.setDataType(1);
		
		try {
			String json = HttpClientUtil.doGet(url,null);
			if(json==null)
				throw new Exception("HTTP请求失败");
			MonitorDataVo data=JSON.parseObject(json, MonitorDataVo.class);
			result.setMonitorDate(data.getOcurDate());
			
			//监控接口错误直接返回
			if(data.getCode()==0){
				result.setCode(0);
				result.setAlertMsg(data.getMsg());
				return result;
			}
			
			Map<String,Object> map=(Map<String,Object>)data.getData();
			result.setCode(1);
			result.setData(map.get(name));
			
			return result;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(-1);
			result.setAlertMsg("监控系统异常:"+e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public MonitorResultVo getCPU(MonitorMachine machine,MonitorItem item) {
		return getSytemInfo(machine,item,"cpu");
	}

	@Override
	public MonitorResultVo getMemory(MonitorMachine machine,MonitorItem item) {
		return getSytemInfo(machine,item,"memory");
	}
	
	@Override
	public MonitorResultVo getUploadSpeed(MonitorMachine machine,MonitorItem item) {
		return getSytemInfo(machine,item,"uploadSpeed");
	}
	
	@Override
	public MonitorResultVo getDownloadSpeed(MonitorMachine machine,MonitorItem item) {
		return getSytemInfo(machine,item,"downloadSpeed");
	}

	@Override
	public MonitorResultVo getHardDisk(MonitorMachine machine,MonitorItem item) {
		return getSytemInfo(machine,item,"hardDisk");
	}

}
