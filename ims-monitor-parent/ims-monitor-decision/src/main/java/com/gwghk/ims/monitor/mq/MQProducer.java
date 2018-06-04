package com.gwghk.ims.monitor.mq;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.MonitorEnum;
import com.gwghk.unify.framework.mq.kafka.KafkaProperties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

@Component
public class MQProducer{
    
    private  Logger LOGGER = LoggerFactory.getLogger(MQProducer.class);
    
    public static MQProducer instance = new MQProducer();// 单例类
    
    private Producer<String, String> producter = null;// 生产者类
    
    /**
     * 生产类型，默认异步(配置文件)
     * 但是优先取具体生产者的传过来的生产类型
     */
	private String producerType;

 	/**
 	 * 初始化生产类
 	 */
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
	}
    
    /**
     * 销毁
     */
	public void destroy() {
		producter.close();
	}

    public void send(MonitorEnum topic,String str) {
		try {
			KeyedMessage<String, String> msg = new KeyedMessage<String, String>(topic.value(), str);
			this.producter.send(msg);
			LOGGER.info("produce->end,send success!msg:{}",str);
		} catch (Exception e) {
			this.LOGGER.error("KafkaMessageProducer Send ERROR ...topic:{},异常信息:{}",topic, e);
		}
	}
    
	
	public static void main(String[] args) {
		 MQProducer ins = MQProducer.instance;
		 ins.init();
         try {
        	 ins.send(MonitorEnum.MQ_TOPIC_ALERT,"{account:265,sex:9}");
             Thread.sleep(2000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
	}

}