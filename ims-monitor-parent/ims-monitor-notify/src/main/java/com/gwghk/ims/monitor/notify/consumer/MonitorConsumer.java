package com.gwghk.ims.monitor.notify.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.enums.MonitorEnum;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.gwghk.unify.framework.mq.kafka.KafkaConsumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

/**
 * pull模式消费
 * @author jackson.tang
 *
 */
public abstract class MonitorConsumer extends Thread{
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	/**
	 * 主题
	 */
	private MonitorEnum topic;
	
	public MonitorConsumer(MonitorEnum topic) {
		this.topic=topic;
	}
	
	@Override
	public void run(){
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic.value(), 1);
		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
		ConsumerConnector consumer = new KafkaConsumer().getInstance().getConsumer();
		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer
				.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		try {
			List<KafkaStream<String, String>> partitions = consumerMap.get(topic.value());
			while (true) {
				//分区处理
				for (final KafkaStream<String, String> partition : partitions) 
					handlePartition(partition, 0);
				
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>接收 kafka " + topic + " error --> {}", e);
			consumer.shutdown();
		}
	}
	
	/**
	 * 处理分区
	 * @param partition
	 * @param retryCount
	 */
	private void handlePartition(KafkaStream<String, String> partition, int retryCount) {
		try {
			ConsumerIterator<String, String> streamIterator = partition.iterator();
			while (streamIterator.hasNext()) {
				MessageAndMetadata<String, String> messageAndMetadata = streamIterator.next();
				String message = messageAndMetadata.message();
				long offset = messageAndMetadata.offset();
				logger.info("开始消费 监控消息!【message:{},topic:{},offset:{}】", message, topic, offset);
				if (!StringUtil.isNotEmpty(message))
					continue;
				
				try {
					MonitorResultVo dto=JSON.parseObject(message,MonitorResultVo.class);
					//处理业务逻辑
					handleMessage(dto);
				}catch(Exception e) {
					logger.error(">>>消费消息失败:{},message:{}",e.getMessage(),message,e);
				}
			}
		}catch(ConsumerTimeoutException e) {//这种异常没有多少意义
			return;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
	}
	
	/**
	 * 消息处理
	 * @param partition
	 * @param retryCount
	 * @param topic
	 */
	protected abstract void handleMessage(MonitorResultVo dto) throws Exception;
}
