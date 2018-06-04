package com.gwghk.ims.monitor.thread;



import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.enums.AlertLevelEnum;
import com.gwghk.ims.common.enums.MonitorEnum;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.bootstrap.ApplicationRun;
import com.gwghk.ims.monitor.dal.entity.AlertStrategy;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.enums.MONITOR_TYPE;
import com.gwghk.ims.monitor.mq.MQProducer;
import com.gwghk.ims.monitor.service.imp.AlertStrategyService;

/**
 * 监控指定的条目线程,监控流程的基本骨架
 * @author jackson.tang
 *
 */
public abstract class MonitorThread extends Thread{
	protected static final Logger logger = LoggerFactory.getLogger(MonitorThread.class);
	/**
	 * 默认请求间隔为60秒
	 */
	private static final int DEFAULT_REQUEST_INTERVAL = 60;
	/**
	 * 刷新的最小时间间隔为1个小时，否则为无效配置，不启用。
	 * 默认监控条目刷新时间24个小时
	 * 
	 */
	private final int ITEM_STRATEGY_DATA_REFRESH_TIME=86400;
	/**
	 * 最大请求失败次数
	 */
	private int MAX_REQUEST_FAIL_COUNT=10;
	
	private final String BUSINESS_MONITOR_METHOD="getMonitorInfo";
	/**
	 * 监控条目配置信息
	 */
	private MonitorItem item;
	/**
	 * 监控服务器相关信息
	 */
	private MonitorMachine machine;

	/**
	 * MQ生产者
	 */
	private MQProducer mq;
	/**
	 * 获取监控数据的处理者
	 */
	private Object handler;
	/**
	 * 监控条目策略判定MAP
	 */
	private Map<Long,JudgeAlertStack> alertStackMap=new HashMap<Long,JudgeAlertStack>();
	/**
	 * 监控条目刷新的时间
	 */
	private Date itemStrategyRefreshDate;
	/**
	 * 监控策略服务
	 */
	private AlertStrategyService alertStrategyService;
	
	public MonitorThread(MonitorItem item) {
		this.item=item;
	}
	
	public void setAlertStrategyService(AlertStrategyService alertStrategyService) {
		this.alertStrategyService = alertStrategyService;
	}

	public void setMachine(MonitorMachine machine) {
		this.machine=machine;
	}
	
	
	public void setMQ(MQProducer mq) {
		this.mq=mq;
	}
	
	public void setHandler(Object handler) {
		this.handler=handler;
	}
	
	/**
	 * 请求失败数 最小是10次
	 * @param maxVal
	 */
	public void setMaxRequestFail(int maxVal) {
		if(maxVal<10)
			return;
		
		MAX_REQUEST_FAIL_COUNT=maxVal;
	}
	
	@Override
	public void run() {
		//寻找具体业务处理方法
		Method method=null;
		String methodName=BUSINESS_MONITOR_METHOD;
		try {
			if(item.getItemTypeEnum()==MONITOR_TYPE.SYSTEM || item.getItemTypeEnum()==MONITOR_TYPE.PROCESS)
				methodName=item.getInterfaceName();
				
			method=handler.getClass().getMethod(methodName, MonitorMachine.class,MonitorItem.class);
		}catch(Exception e) {
			logger.error(">>>{}:监控条目【{}】无法找到具体的处理方法",machine.getIpAddress()+":"+machine.getIpPort(),item.getName(),e);
			return;
		}
		
		//开始指定的条目
		logger.info(">>>{}:监控条目【{}】线程启动",machine.getIpAddress()+":"+machine.getIpPort(),item.getName());
		int requestInterval=item.getRequestInterval();
		if(requestInterval<1 ||
				"getErrorLog".equals(item.getInterfaceName()) && requestInterval<60) //日志请求时间间隔必须大于60秒
			requestInterval=DEFAULT_REQUEST_INTERVAL;
		
		int requestFailCount=0;
		while(ApplicationRun.getRunStatus()==1) {
			try {
				before();
				
				//获取监控数据
				MonitorResultVo result=(MonitorResultVo)method.invoke(handler, machine,item);
				
				//连续调用请求失败达到最大值可以视为 被监控的机器已经挂了(目标机器返回的数据格式错误也视为系统挂了）
				if(result.getCode()==0 || result.getCode()==-1) {
					requestFailCount+=1;//requestFailCount=111
					if(requestFailCount>MAX_REQUEST_FAIL_COUNT) {
						result.setAlertMsg("被监控的机器["+machine.getAppId()+":"+machine.getIpPort()+"]连续请求"+MAX_REQUEST_FAIL_COUNT+"次均失败！");
						result.setAlertLevel(AlertLevelEnum.FATAL.value());
						mq.send(MonitorEnum.MQ_TOPIC_ALERT,JSON.toJSONString(result));
						mq.send(MonitorEnum.MQ_TOPIC_PERSISTENCE,JSON.toJSONString(result));
						requestFailCount=0;
					}
				}
				
				
				if(result.getCode()==1 && item.getStrategies()!=null && item.getStrategies().size()>0) {
					requestFailCount=0;//清除连续请求失败计数
					
					//判定数据时候达到报警
					if(judgeAlert(item.getStrategies(),result)) {//result里面的一些状态发生了变化
						
						//将报警条目发送给相关的值班人员
						mq.send(MonitorEnum.MQ_TOPIC_ALERT,JSON.toJSONString(result));
						logger.info(">>>{}:监控条目【{}】发出报警,已经发出通知请求,具体内容:{}",machine.getIpAddress()+":"+machine.getIpPort(),JSON.toJSONString(result));
					}
					
				}
				
				sleep(requestInterval*1000);
			} catch (Exception e) {
				logger.error(">>>{}:【{}】线程执行出错:{}，退出本线程",machine.getIpAddress()+":"+machine.getIpPort(),item.getName(),e.getMessage(),e);
				break;
			}
			
		}
	}
	
	/**
	 * 开始调用监控请求的时候做点什么,1.刷新监控条目对应的策略
	 */
	private void before() {
		//加载监控条目所应用的策略
		if(this.itemStrategyRefreshDate==null || 
			//如果条目策略过期则刷新数据
			(ITEM_STRATEGY_DATA_REFRESH_TIME>3600 && this.itemStrategyRefreshDate!=null && new Date().getTime()-itemStrategyRefreshDate.getTime()>ITEM_STRATEGY_DATA_REFRESH_TIME ) ) {
			
			itemStrategyRefreshDate=new Date();
			item.setStrategies(alertStrategyService.findAlertStrategyByItemId(item.getItemId()));
			alertStackMap.clear();
			
			//初始化监控条目策略判定MAP
			for(AlertStrategy config:item.getStrategies()) 
				alertStackMap.put(config.getStrategyId(), new JudgeAlertStack());
			
			//初始化判定策略的时间容器,默认时间容器大小是请求间隔*重现次数
			for(AlertStrategy strategy:item.getStrategies()) {
				if(strategy.getTimeRange()==null || strategy.getTimeRange()<1)
					strategy.setTimeRange(item.getRequestInterval()*strategy.getRecurrenceCount());
			}
			
		}
		
	}

	/**
	 * 判断当前的监控内容是否达到了报警水平线(只有其中一个策略满足条件 立即返回true 同时清除监控条目策略判定MAP)
	 * @param strategies
	 * @param result 这个里面的状态会发生变化
	 * @return
	 */
	protected boolean judgeAlert(List<AlertStrategy> strategies, MonitorResultVo resultVo) {
		boolean alert=false;
		
		String interfaceName=item.getInterfaceName();
		String resultJson=JSON.toJSONString(resultVo);
		
		try {
			for(AlertStrategy strategy:strategies) {
				
				//根据给定的策略判断数据是否达到预警
				alert=dataDecision(interfaceName, strategy, resultVo);
				
				//保存警告数据
				if(alert) {
					//设置警告级别
					resultVo.setAlertLevel(strategy.getAlertLevel());
					alertStackMap.get(strategy.getStrategyId()).setStack(resultVo);;
					resultJson=JSON.toJSONString(resultVo);
					mq.send(MonitorEnum.MQ_TOPIC_PERSISTENCE,resultJson);
					logger.info(">>>{}:监控条目【{}】发出报警,已经发出保存请求,具体内容:{}",machine.getIpAddress()+":"+machine.getIpPort(),resultVo.getItemName(),resultJson);
				}
				
				JudgeAlertStack stack=alertStackMap.get(strategy.getStrategyId());
				
				//这里是用来判断是否发出报警，当在指定的时间范围内 并且 达到重新次数才认为报警
				if(stack.getStack().size()>0 &&  new Date().getTime()-stack.getStartDate().getTime()<=strategy.getTimeRange()  && 
						stack.getStack().size()>=strategy.getRecurrenceCount()) {
					clearAlertStackMap();//清除数据
					return true;
				}
				
			}
			
			return false;
		}catch(Exception e) {
			logger.error(">>>{}:监控条目【{}】,入参详情:strategies【{}】,result【{}】",JSON.toJSONString(strategies),resultJson);
			logger.error(">>>{}:监控条目【{}】在作出判断的时候出错了",machine.getIpAddress()+":"+machine.getIpPort(),item.getName(),e);
			return false;
		}
	}
	
	/**
	 * 根据给定的策略判断数据是否达到预警
	 * @param interfaceName
	 * @param strategy
	 * @param resultVo
	 * @return
	 */
	protected abstract boolean dataDecision(String interfaceName,AlertStrategy strategy,MonitorResultVo resultVo);
		
	
	/**
	 * 清理 监控条目策略判定MAP
	 */
	private void clearAlertStackMap() {
		for(Long strategyId:alertStackMap.keySet()) 
			alertStackMap.get(strategyId).clear();
	}
	
}
