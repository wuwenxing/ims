package com.gwghk.ims.monitor.service.imp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.vo.monitor.MonitorDataVo;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.service.inf.IProcessMonitor;
import com.gwghk.unify.framework.common.util.HttpClientUtil;

/**
 * 进程监控
 * @author jackson.tang
 *
 */
@Service
public class ProcessMonitorService implements IProcessMonitor{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public MonitorResultVo getRequestTime(MonitorMachine machine,MonitorItem item) {
		String url=machine.getBaseURL()+"/monitor/replyTime";
		MonitorResultVo result=new MonitorResultVo();
		result.setRequestDate(new Date());
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
			
			
			result.setCode(1);
			//计算请求的时间间隔
			result.setData((new Date().getTime()-result.getRequestDate().getTime())/1000+"s");
			
			return result;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(-1);
			result.setAlertMsg("监控系统异常:"+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 获取集合类型的监控数据
	 * @param machine
	 * @param item
	 * @param name 监控的名字有： slowSpring slowSQL errorLog
	 * @return
	 */
	private MonitorResultVo getCollectTypeData(MonitorMachine machine,MonitorItem item,String name,String paramStr) {
		String url=machine.getBaseURL()+"/monitor";
		MonitorResultVo result=new MonitorResultVo();
		result.setHostname(machine.getIpAddress()+":"+machine.getIpPort());
		result.setItemId(item.getItemId());
		result.setItemName(item.getName());
		
		int dataType=0;
		if("slowSQL".equals(name) || "slowSpring".equals(name)) {
			dataType=3;
			url+="/slowData";
		}else if("errorLog".equals(name)) {
			dataType=2;
			url+="/errorLog";
		}
		else
			return null;
		
		//全部结果记录
		result.setDataType(dataType);
		
		try {//paramStr="startDate=2018-04-17%2016:20:07"
			if(paramStr!=null)
				url+="?"+paramStr;
			
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
			result.setData(map.get(name));
			result.setCode(1);
			
			return result;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(-1);
			result.setAlertMsg("监控系统异常:"+e.getMessage());
		}
		
		return result;
	}

	@Override
	public MonitorResultVo getSlowSpring(MonitorMachine machine,MonitorItem item) {
		MonitorResultVo resultVo=getCollectTypeData(machine,item,"slowSpring",null);
		
		if(resultVo.getCode()!=1 || resultVo.getData()==null)
			return resultVo;
		
		List<Map<String,Object>> rtsData=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list=(List<Map<String,Object>>)resultVo.getData();
		Map<String,Object> rstMap=null;
		for(Map<String,Object> map:list) {
			rstMap=new HashMap<String,Object>();
			
			rstMap.put("name", map.get("name"));
			
			long hits=Long.parseLong(map.get("hits").toString());
			long durationsSum=Long.parseLong(map.get("durationsSum").toString());
			if(hits<=0)
				continue;
			
			//计算平均值
			rstMap.put("value", durationsSum/(hits*1000)+"s");
			long max=Long.parseLong(map.get("maximum").toString());
			//记录最大值
			rstMap.put("max", max/1000+"s");
			
			rtsData.add(rstMap);
		}
		resultVo.setData(rtsData);	
		return resultVo;
	}

	@Override
	public MonitorResultVo getSlowSQL(MonitorMachine machine,MonitorItem item) {
		MonitorResultVo resultVo=getCollectTypeData(machine,item,"slowSQL",null);
		
		if(resultVo.getCode()!=1 || resultVo.getData()==null)
			return resultVo;
		
		List<Map<String,Object>> rtsData=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list=(List<Map<String,Object>>)resultVo.getData();
		Map<String,Object> rstMap=null;
		for(Map<String,Object> map:list) {
			rstMap=new HashMap<String,Object>();
			
			rstMap.put("name", map.get("SQL"));
			
			long hits=Long.parseLong(map.get("ExecuteCount").toString());
			long durationsSum=Long.parseLong(map.get("TotalTime").toString());
			if(hits<=0)
				continue;
			
			//计算平均值
			rstMap.put("value", durationsSum/(hits*1000)+"s");
			long max=Long.parseLong(map.get("MaxTimespan").toString());
			//记录最大值
			rstMap.put("max", max/1000+"s");
			rstMap.put("MaxTimeOccurTime", map.get("MaxTimespanOccurTime"));
			rtsData.add(rstMap);
		}
		
		resultVo.setData(rtsData);
		return resultVo;
	}
	
	@Override
	public MonitorResultVo getErrorLog(MonitorMachine machine,MonitorItem item) {
		long time=new Date().getTime()-1000L*item.getRequestInterval();
		Date startDate=new Date(time);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
		return getCollectTypeData(machine,item,"errorLog","startDate="+sdf.format(startDate));
	}
	
	@Override
	public MonitorResultVo getJvmMem(MonitorMachine machine,MonitorItem item) {
		String url=machine.getBaseURL()+"/monitor/jvm/mem/status";
		MonitorResultVo result=new MonitorResultVo();
		result.setRequestDate(new Date());
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
			
			
			result.setCode(1);
			//计算使用率
			//example:usedMemory=767MB, maxMemory=853MB, freeMemory=86MB
			Map<String,Object> map=(Map<String,Object>)data.getData();
			Float total=Float.parseFloat(map.get("maxMemory").toString().replaceAll("[a-zA-Z]+", ""));
			Float used=Float.parseFloat(map.get("usedMemory").toString().replaceAll("[a-zA-Z]+", ""));
			DecimalFormat df=new DecimalFormat("0.00");
			
			result.setData(df.format(used*100/total)+"%");
			
			return result;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(-1);
			result.setAlertMsg("监控系统异常:"+e.getMessage());
		}
		
		return result;
	}

}
