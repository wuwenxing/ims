package com.gwghk.ims.monitor.service;

import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.enums.AlertLevelEnum;
import com.gwghk.ims.common.enums.MonitorEnum;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.unify.framework.mq.kafka.KafkaProperties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 直接向监控通知服务发送告警
 * @author jackson.tang
 *
 */
@Service
public class AlertNotifyService{
	private  Logger logger = LoggerFactory.getLogger(AlertNotifyService.class);
    /**
     * 服务的上下文
     */
	@Value("${server.context-path:/}")
	private String contextPath;
	/**
	 * 服务端口
	 */
	@Value("${server.port:8080}")
	private String ipPort;
	
	private String ipAddress;
	
    private Producer<String, String> producter = null;// 生产者类
    
    /**
     * 生产类型，默认异步(配置文件)
     * 但是优先取具体生产者的传过来的生产类型
     */
	private String producerType;

 	/**
 	 * 初始化MQ生产类
 	 */
	@PostConstruct
    public void init() {
    	Properties props = new Properties();
        props.put("serializer.class", KafkaProperties.getInstance().getProducerConf("serializer.class"));
        props.put("key.serializer.class", KafkaProperties.getInstance().getProducerConf("key.serializer.class"));
        if (null != KafkaProperties.getInstance().getProducerConf("partitioner.class"))
            props.put("partitioner.class", KafkaProperties.getInstance().getProducerConf("partitioner.class"));
        props.put("request.required.acks", KafkaProperties.getInstance().getProducerConf("request.required.acks"));
        props.put("metadata.broker.list", KafkaProperties.getInstance().getProducerConf("metadata.broker.list"));
        if (StringUtils.isBlank(this.producerType)) {
        	props.put("producer.type", KafkaProperties.getInstance().getProducerConf("producer.type"));
        } else {
        	props.put("producer.type", this.producerType);
        }
        props.put("batch.num.messages", KafkaProperties.getInstance().getProducerConf("batch.num.messages"));
        props.put("queue.buffering.max.ms", KafkaProperties.getInstance().getProducerConf("queue.buffering.max.ms"));
        props.put("queue.buffering.max.messages", KafkaProperties.getInstance().getProducerConf("queue.buffering.max.messages"));
        props.put("queue.enqueue.timeout.ms", KafkaProperties.getInstance().getProducerConf("queue.enqueue.timeout.ms"));
        props.put("compression.codec", KafkaProperties.getInstance().getProducerConf("compression.codec"));
        producter = new Producer<String, String>(new ProducerConfig(props));
        
        //获取本服务IP地址
        try {
			ipAddress=InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
    
    /**
     * 销毁
     */
	protected void destroy() {
		producter.close();
	}

	/**
	 * 直接发送报警
	 * @param monitorItemId 监控项目id 可以去数据库表 monitor_item 表查询
	 * @param itemName 监控项目名字 可以去数据库表 monitor_item 表查询
	 * @param level 严重级别
	 * @param alertMsg 发送具体的告警文本
	 * @param attachData 附加用于排查问题的数据，可以为空
	 */
    public void alert(Long monitorItemId,String itemName,AlertLevelEnum level,String alertMsg,Object attachData) {
		try {
			Date curDate=new Date();
			MonitorResultVo result=new MonitorResultVo();
			result.setAlertLevel(level.value());
			result.setAlertMsg(alertMsg);
			result.setCode(1);
			result.setItemId(monitorItemId);
			result.setRequestDate(curDate);
			result.setResponceDate(curDate);
			result.setMonitorDate(curDate);
			result.setItemName(itemName);
			result.setHostname(ipAddress+":"+ipPort);
			result.setData(0);
			result.setData(attachData);
			String json=JSON.toJSONString(result);
			
			KeyedMessage<String, String> msg = new KeyedMessage<String, String>(MonitorEnum.MQ_TOPIC_ALERT.value(), json);
			this.producter.send(msg);
			logger.info("produce->end,send success!msg:{}",json);
		} catch (Exception e) {
			logger.error("KafkaMessageProducer Send ERROR ...topic:{},异常信息:{}",MonitorEnum.MQ_TOPIC_ALERT.value(), e);
		}
	}
}
