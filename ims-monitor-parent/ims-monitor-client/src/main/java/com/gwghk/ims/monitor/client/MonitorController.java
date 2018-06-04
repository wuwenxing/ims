package com.gwghk.ims.monitor.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwghk.ims.common.vo.monitor.MonitorDataVo;
import com.gwghk.ims.monitor.conf.MonitorConfiguration;
import com.gwghk.ims.monitor.service.LoggerService;
import com.gwghk.ims.monitor.tools.ProcessMonitorTools;
import com.gwghk.ims.monitor.tools.SystemMonitorTools;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoggerService loggerService;
	
	/**
	 * 默认最大SPRING执行时间5秒
	 */
	@Value("${monitor.spring_max_execute_time:5000}")
	private Long SPRING_MAX_EXECUTE_TIME;
	/**
	 * 默认最大SQL执行时间5秒
	 */
	@Value("${monitor.sql_max_execute_time:5000}")
	private Long SQL_MAX_EXECUTE_TIME;
	/**
	 * 获取系统CPU MEMORY HARDDISK NETWORK 占用率
	 * @return
	 */
	@RequestMapping("/system")
	public MonitorDataVo getSystemInfo() {
		MonitorDataVo resultVo=new MonitorDataVo();
		Map<String,Object> data=new HashMap<String,Object>();
		
		Map<String,Object> item=null;
		try {
			//1.获取CPU、内存使用率
			item=SystemMonitorTools.getSystemUseRate();
			if(item==null || !item.containsKey(SystemMonitorTools.CPU_USE_RATE) || !item.containsKey(SystemMonitorTools.MEM_USE_RATE))
				throw new Exception("在获取CPU或者MEM使用率失败！");
			data.putAll(item);
			
			//2.获取硬盘占用率
			item=SystemMonitorTools.getHardDiskUseRate();
			if(item==null || !item.containsKey(SystemMonitorTools.HDISK_USE_RATE))
				throw new Exception("在获取硬盘占用率失败！");
			data.putAll(item);
			
			//3.获取网络速度
			item=SystemMonitorTools.getNetWorkSpeed();
			if(item==null || !item.containsKey(SystemMonitorTools.NET_UPL_SPEED) || !item.containsKey(SystemMonitorTools.NET_DOWN_SPEED))
				throw new Exception("在获取网络速度失败！");
			data.putAll(item);
			return resultVo.success(data);
			
		}catch(Exception e) {
			logger.error(">>>{}",e.getMessage());
			return resultVo.fail(e.getMessage());
		}
	}
	
	
	/**
	 * 获取requestTime
	 * @return
	 */
	@RequestMapping("/replyTime")
	public MonitorDataVo replyTime() {
		return new MonitorDataVo().success(null);
	}
	
	/**
	 * 通过javaMelody获取slowSpring slowSQL 
	 * @return
	 */
	@RequestMapping("/slowData")
	public MonitorDataVo getSlowData(HttpServletRequest request) {
		MonitorDataVo resultVo=new MonitorDataVo();
		Map<String,Object> data=new HashMap<String,Object>();
		
		String javaMelodyURL="http://localhost:"+request.getServerPort()+request.getContextPath()+MonitorConfiguration.JAVAMELODY_CONTEXT_PATH+"?format=json";
		String druidURL="http://localhost:"+request.getServerPort()+request.getContextPath()+MonitorConfiguration.DRUID_MONITOR_CONTEXT_PATH+"/sql.json";
		try {
			//1.获取slow spring
			List<Map<String,Object>> slowSpring= ProcessMonitorTools.getSlowSpring(javaMelodyURL, SPRING_MAX_EXECUTE_TIME);
			if(slowSpring==null)
				throw new Exception("获取Slow Spring失败");
			//2.获取slow sql
			List<Map<String,Object>> slowSQL= ProcessMonitorTools.getSlowSQL(druidURL, SQL_MAX_EXECUTE_TIME);
			if(slowSQL==null)
				throw new Exception("获取Slow SQL失败");
			
			data.put("slowSpring", slowSpring);
			data.put("slowSQL", slowSQL);
			
			return resultVo.success(data);
		}catch(Exception e) {
			logger.error(">>>{}",e.getMessage());
			return resultVo.fail(e.getMessage());
		}
	
	}
	
	/**
	 * 读取错误日志
	 * @return
	 */
	@RequestMapping("/errorLog")
	public MonitorDataVo getErrorLog(String startDate) {
		MonitorDataVo resultVo=new MonitorDataVo();
		Map<String,Object> data=new HashMap<String,Object>();
		
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start=sdf.parse(startDate);
			data.put("errorLog", ProcessMonitorTools.getErrorLogByDate(loggerService.getLogPath(),start,new Date()));
			return resultVo.success(data);
		}catch(Exception e) {
			logger.error(">>>输入参数{},错误详情:{}",startDate,e.getMessage(),e);
			return resultVo.fail(e.getMessage());
		}
	}
	
	/**
	 * 读取java虚拟机内存情况
	 * @return
	 */
	@RequestMapping("/jvm/mem/status")
	public MonitorDataVo getJvmMemStatus() {
		MonitorDataVo resultVo=new MonitorDataVo();
		Map<String,Object> data=new HashMap<String,Object>();
		try {
			return resultVo.success(ProcessMonitorTools.getJvmMemStatus());
		}catch(Exception e) {
			logger.error(">>>错误详情:{}",e.getMessage(),e);
			return resultVo.fail(e.getMessage());
		}
	}
	
	/**
	 * 删除linux /tmp/javamelody目录下的统计数据
	 * @return
	 */
	@RequestMapping("/javaMelodyCache/clean")
	public String cleanJavaMelodyCache() {
		try {
			final String[] CLEAN_JAVAMELODY_CACHE= {"/bin/sh","-c","rm -rf /tmp/javamelody/*"};
			Runtime.getRuntime().exec(CLEAN_JAVAMELODY_CACHE).destroy();
		}catch(Exception e) {
			return "fail";
		}
		
		return "true";
	}
	
}
