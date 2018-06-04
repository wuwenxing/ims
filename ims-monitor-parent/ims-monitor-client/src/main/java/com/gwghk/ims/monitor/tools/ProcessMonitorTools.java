package com.gwghk.ims.monitor.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gwghk.unify.framework.common.util.HttpClientUtil;

/**
 * 监控进程工具,其中及借助的是javaMelody对spring 以及druid对于jdbc监控
 * @author jackson.tang
 *
 */
public class ProcessMonitorTools {
	private static Logger logger=LoggerFactory.getLogger(SystemMonitorTools.class);
	
	/**
	 * 获取本进程下所有缓慢的SPRING,如下
	 * {
	 *		name: "ImsActivityMain$$EnhancerBySpringCGLIB$$ac9efffb.javaMelodyListener",
	 *		id: "spring24f4fef874d006b1047ed22596e4064ebafc9002",
	 *		hits: 1,
	 *		durationsSum: 2,
	 *		durationsSquareSum: 4,
	 *		maximum: 2,
	 *		cpuTimeSum: 0,
	 *		systemErrors: 0,
	 *		responseSizesSum: -1,
	 *		childHits: 0,
	 *		childDurationsSum: 0
	 *	}
	 * @param url javaMelody地址
	 * @param maxSlowValue
	 * @return
	 */
	public static List<Map<String,Object>> getSlowSpring(String url,Long maxSlowValue){
		try {
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			
			String json = HttpClientUtil.doGet(url,null);
			@SuppressWarnings("unchecked")
			Map<String,Object> data=(Map<String,Object>)JSON.parse(json);
			if(data==null)
				return null;
			
			Map springMonitorItem=null;
			List<Map> list=(List<Map>)data.get("list");
			
			//寻找Spring监控条目
			for(Map monitorItem:list) {
				if("spring".equals(monitorItem.get("name"))) {
					springMonitorItem=monitorItem;
					break;
				}
			}
			
			//读出所有的Bean调用时间数据
			if(springMonitorItem==null)
				return null;
			
			List<List> requests=(List<List>)springMonitorItem.get("requests");
			Map<String,Object> statusMap=null;
			for(List request:requests) {
				if(request.size()<=0)
					continue;
				statusMap=(Map<String,Object>)request.get(1);
				if(((String)statusMap.get("name")).trim().startsWith("$Proxy"))//这种数据没有多少意义
					continue;
				
				Long hit=Long.parseLong(statusMap.get("hits").toString());
				if(hit<=0)//忽略没有调用过的方法
					continue;
				
				Long timeSum=Long.parseLong(statusMap.get("durationsSum").toString());
				//判断是否大于平均值
				if(timeSum/hit>maxSlowValue)
					result.add(statusMap);
			}
			
			return result;
		}catch(Exception e) {
			logger.error(">>>获取本机 Slow Spring监控数据异常",e);
			return null;
		}
	}
	
	/**
	 * 获取本进程下所有缓慢的SQL,如下
	 * {
	 * 		ExecuteAndResultSetHoldTime: 0,
	 * 		LastErrorMessage:null,
	 * 		InputStreamOpenCount: 0,
	 *		BatchSizeTotal: 0,
	 *		FetchRowCountMax: 0,
	 *		ErrorCount: 0,
	 *		BatchSizeMax: 0,
	 *		URL: null,
	 *		Name: null,
	 *		LastErrorTime: null,
	 *		ReaderOpenCount: 0,
	 *		EffectedRowCountMax: 0,
	 *		LastErrorClass: null,
	 *		InTransactionCount: 0,
	 *		LastErrorStackTrace: null,
	 *		ResultSetHoldTime: 0,
	 *		TotalTime: 66,
	 *		ID: 3,
	 *		ConcurrentMax: 1,
	 *		RunningCount: 0,
	 *		FetchRowCount: 0,
	 *		MaxTimespanOccurTime: "2018-04-13 12:58:01",
	 *		LastSlowParameters: null,
	 *		ReadBytesLength: 0,
	 *		DbType: "mysql",
	 *		DataSource: null,
	 *		SQL: "set names utf8mb4;",
	 *		HASH: 8867765243502968000,
	 *		LastError: null,
	 *		MaxTimespan: 23,
	 *		BlobOpenCount: 0,
	 *		ExecuteCount: 3,
	 *		EffectedRowCount: 0,
	 *		ReadStringLength: 0,
	 *		File: null,
	 *		ClobOpenCount: 0,
	 *		LastTime: "2018-04-13 12:58:01"
	 *
	 *	}
	 * 
	 * 
	 * @param url druid SQL监控地址
	 * @param maxSlowValue
	 * @return
	 */
	public static List<Map<String,Object>> getSlowSQL(String url,Long maxSlowValue){
		try {
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			
			String json = HttpClientUtil.doGet(url,null);
			@SuppressWarnings("unchecked")
			Map<String,Object> data=(Map<String,Object>)JSON.parse(json);
			if(data==null)
				return null;
			
			//读取监控sql数据
			List<Map<String,Object>> list=(List<Map<String,Object>>)data.get("Content");
			for(Map<String,Object> statusMap:list) {
				
				Long executeCount=Long.parseLong(statusMap.get("ExecuteCount").toString());
				if(executeCount<=0)
					continue;
				
				Long totalTime=Long.parseLong(statusMap.get("TotalTime").toString());
				//比较是否超过最大平均值
				if(totalTime/executeCount > maxSlowValue) {
					statusMap.remove("EffectedRowCountHistogram");
					statusMap.remove("Histogram");
					statusMap.remove("ExecuteAndResultHoldTimeHistogram");
					statusMap.remove("FetchRowCountHistogram");
					
					result.add(statusMap);
				}
			}
			
			return result;
		}catch(Exception e) {
			logger.error(">>>获取本机 Slow SQL监控数据异常",e);
			return null;
		}
	}
	
	/**
	 * 获取错误日志，并不一定准确，当一个logger文件刚刚超过大小新建的logger文件这个时候读出来的数据就是空,另外这个命令存在问题sed -n /date1/,/date2/p log这种命令是匹配，
	 * 而不是比较，如果数据不存在，那么返回空,所以这里只能用分钟
	 * @param logPath 检查确实是一个文件路径，防止注入命令
	 * @param startDate
	 * @param date
	 * @return
	 */
	public static List<String> getErrorLogByDate(String logPath, Date startDate, Date endDate) {
		try {
			logger.info(">>>log file path:{}",logPath);
			
			if(startDate==null || endDate==null)
				return null;
			//logPath="/root/tmp123/ims-activity-api-dev-20180417/lib/logs/ims-activity-api.log";
			//必须是一个文件路径， 如果里面包含命令就危险了
			Pattern p=Pattern.compile("(?:/[\\.a-z0-9_-]+)+/[a-z0-9_-]+\\.log");
			Matcher matcher=p.matcher(logPath);
			if(!matcher.matches())
				return null;
			
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			/**
			 * 这条命令输出为如下：如果给出的startDate比日志里面的最小时间还小是无法读取出来的
			 * 	2017-10-27 10:21:30.428 ERROR [DEFAULT.rjzjActQualifyStatisticsJob_Worker-1][:] - <---结束同步【入金贈金型活动-参与资格与用户活动统计数据】,系统异常!!!
			 *	2017-10-27 10:21:30.420 ERROR [DEFAULT.cjActQualifyStatisticsJob_Worker-1][:] - <---结束同步【层级型活动-参与资格与用户活动统计数据】,系统异常!!!
			 *	2017-10-27 10:21:30.476 ERROR [DEFAULT.rwActQualifyStatisticsJob_Worker-1][:] - <---结束同步【任务型活动-参与资格与用户活动统计数据】,系统异常!!!
			 *	2017-10-27 10:22:00.305 ERROR [DEFAULT.rjzjActQualifyStatisticsJob_Worker-1][:] - <---结束同步【入金贈金型活动-参与资格与用户活动统计数据】,系统异常!!!
			 *	2017-10-27 10:22:00.321 ERROR [DEFAULT.rwActQualifyStatisticsJob_Worker-1][:] - <---结束同步【任务型活动-参与资格与用户活动统计数据】,系统异常!!!
			 *	2017-10-27 10:22:00.403 ERROR [DEFAULT.cjActQualifyStatisticsJob_Worker-1][:] - <---结束同步【层级型活动-参与资格与用户活动统计数据】,系统异常!!!
			 *
			 */
			String[] errorLogCmd={"/bin/sh","-c","sed -n '/"+sdf.format(startDate)+"/,/"+sdf.format(endDate)+"/p' "+logPath+" | grep ERROR"};
			List<String> result=new ArrayList<String>();

			Process process=Runtime.getRuntime().exec(errorLogCmd);
			BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line=null;

			while((line=reader.readLine())!=null) 
				result.add(line.trim());
			
			reader.close();
			process.destroy();
			return result;
		}catch(Exception e) {
			logger.error(">>>获取本机 ErrorLog 监控数据异常",e);
			return null;
		}
	}

	/**
	 * 获取虚拟机内存状况
	 * @return
	 */
	public static Map<String, Object> getJvmMemStatus() {
		
		Map<String,Object> map = new HashMap<String,Object>();
        long maxMem = Runtime.getRuntime().maxMemory()/1024/1024;
        long freeMem = Runtime.getRuntime().freeMemory()/1024/1024;
        long usedMem = maxMem - freeMem;
        map.put("maxMemory", maxMem + "MB");
        map.put("usedMemory", usedMem + "MB");
        map.put("freeMemory", freeMem + "MB");
        
        return map;
	}
}
