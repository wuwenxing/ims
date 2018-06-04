package com.gwghk.ims.monitor.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gwghk.ims.monitor.config.MonitorConfig;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.mq.MQProducer;
import com.gwghk.ims.monitor.service.imp.AlertStrategyService;
import com.gwghk.ims.monitor.service.inf.IBusinessMonitor;
import com.gwghk.ims.monitor.service.inf.IProcessMonitor;
import com.gwghk.ims.monitor.service.inf.ISystemMonitor;
import com.gwghk.ims.monitor.thread.MonitorItemThread;
import com.gwghk.ims.monitor.thread.MonitorThread;

/**
 * 监控服务
 * @author jackson.tang
 *
 */
@Component
@Order(1)
public class ApplicationRun implements CommandLineRunner {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	/**
	 * 开启或者停止
	 */
	private static int runStatus=1;
	/**
	 * 保存所有的线程引用
	 */
	private Map<Long,MonitorThread> threadMap=new HashMap<Long,MonitorThread>();
	
	@Autowired
	private MonitorConfig config;
	
	@Autowired
	private MQProducer mq;
	@Autowired
	private ISystemMonitor systemMonitor;
	@Autowired
	private IProcessMonitor processMonitor;
	@Autowired
	private IBusinessMonitor businessMonitor;
	@Autowired
	private AlertStrategyService alertStrategyService;
	
	@Value("${env}")
	private String env;
	
	public boolean init() {
		//1.加载监控配置
		logger.info(">>>开始加载监控配置信息");
		if(env==null) {
			logger.error(">>>初始化的时候无法找到环境变量 env");
			return false;
		}
		
		config.load(env);
		//2.初始化kafka
		logger.info(">>>初始化MQ");
		mq.init();
		
		return true;
	}
	
	public synchronized static void stop() {
		ApplicationRun.runStatus=0;
	}
	
	@Override
	public void run(String... args) throws Exception {
		if(!init())
			return;
		
		logger.info(">>>初始化完成，开始启动监控服务");
		List<MonitorMachine> machines=config.getMachines();
		
		MonitorThread pThread=null;;
		for(MonitorMachine machine:machines) {
			logger.info(">>>开始监控主机【{}:{}】",machine.getIpAddress(),machine.getIpPort());
			for(MonitorItem item:machine.getItems()) {
				pThread=new MonitorItemThread(item);
				pThread.setMQ(mq);
				pThread.setMachine(machine);
				pThread.setAlertStrategyService(alertStrategyService);
				
				switch(item.getItemTypeEnum()) {
				case SYSTEM:
					pThread.setHandler(systemMonitor);
					break;
				case PROCESS:
					pThread.setHandler(processMonitor);
					break;
				case BUSINESS:
					pThread.setHandler(businessMonitor);
					break;
				default:
					logger.error(">>>监控项目【{}】配置不正确",item.getName());
					continue;
				}
				
				pThread.start();
				threadMap.put(item.getItemId(), pThread);
			}
		}
		
		logger.info(">>>监控服务启动OK");
	}
	
	public static int getRunStatus() {
		return ApplicationRun.runStatus;
	}

}
