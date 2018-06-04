package com.gwghk.ims.monitor.notify.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gwghk.ims.monitor.notify.consumer.AlertConsumer;
import com.gwghk.ims.monitor.notify.consumer.PersistenceConsumer;
import com.gwghk.ims.monitor.notify.service.AlertNotifyRecordService;
import com.gwghk.ims.monitor.notify.service.DutyStaffService;
import com.gwghk.ims.monitor.notify.service.MonitorContentService;

/**
 * 服务启动入口
 * @author jackson.tang
 *
 */
@Component
@Order(1)
public class ApplicationRun implements CommandLineRunner{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	/**
	 * 最小通知时间间隔(单位天),默认1天
	 */
	@Value("${notify.minInterval:1}")
	private int minNotifyInterval;
	/**
	 * 致命通知方式定义，默认sms、email
	 */
	@Value("${notify.strategy.fatal:sms,email}")
	private String fatalNotifyStrategy;
	/**
	 * 警告性质的通知方式定义，默认mail
	 */
	@Value("${notify.strategy.warn:email}")
	private String warnNotifyStrategy;
	/**
	 * 监控数据何时过期，单位天，默认30天后过期
	 */
	@Value("${notify.data.expiredDay:30}")
	private int dataExpiredDay;
	
	@Autowired
	private DutyStaffService dutyStaffService;
	@Autowired
	private MonitorContentService monitorContentService;
	@Autowired
	private AlertNotifyRecordService alertNotifyRecordService;
	
	@Override
	public void run(String... args) throws Exception {
		//1.启动告警线程，用来发送短信、发送邮件，并记录发送记录
		logger.info(">>>开始告警消费线程----");
		AlertConsumer alertConsumer=new AlertConsumer();
		alertConsumer.setFatalNotifyStrategy(fatalNotifyStrategy);
		alertConsumer.setMinNotifyInterval(minNotifyInterval);
		alertConsumer.setWarnNotifyStrategy(warnNotifyStrategy);

		alertConsumer.setDutyStaffService(dutyStaffService);
		alertConsumer.setAlertNotifyRecordService(alertNotifyRecordService);
		
		alertConsumer.initAlertStrategy();
		alertConsumer.start();
		
		//2.启动持久化线程，用来保存报警数据
		logger.info(">>>开始持久化消费者线程----");
		PersistenceConsumer persistenceConsumer=new PersistenceConsumer();
		persistenceConsumer.setMonitorContentService(monitorContentService);
		persistenceConsumer.setDataExpiredDay(dataExpiredDay);
		persistenceConsumer.start();
	}

}
