package com.gwghk.ims.marketingtool.kafka.consumer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.dto.marketingtool.PushMessageRespDTO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;
import com.gwghk.ims.marketingtool.dao.inf.ImsMsgAppLogDao;
import com.gwghk.ims.marketingtool.enums.AppPushMsgStatusEnum;
import com.gwghk.ims.marketingtool.kafka.KafkaTopic;
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
 * 摘要：消费APP推送的结果
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月4日
 */
@Component
public class AppMessagePushConsumer {
	private final static Logger logger = LoggerFactory.getLogger(AppMessagePushConsumer.class);

	@Autowired
	private ImsMsgAppLogDao imsMsgAppLogDao;
	
	@PostConstruct
	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String topic = KafkaTopic.PUSH_MESSAGE_RESPONSE.getValue();
				logger.info("app消息消费启动中!topic:{}",topic);
				Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
				topicCountMap.put(topic, 1);
				StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
				StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
				ConsumerConnector consumer = new KafkaConsumer().getInstance().getConsumer();
				Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
				try {
					List<KafkaStream<String, String>> partitions = consumerMap.get(topic);
					while (true) {
						for (final KafkaStream<String, String> partition : partitions) {
							handlerPartition(partition,0,topic);
						}
						Thread.sleep(5000);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					consumer.shutdown();
				}
			}
		}).start();
	}
	
	/**
	 * @see 处理分区
	 * @param partition
	 * @param retryCount :重试次数
	 */
	protected void handlerPartition(final KafkaStream<String, String> stream,int retryCount,String topic) {
		try {
			ConsumerIterator<String, String> streamIterator = stream.iterator();
			while (streamIterator.hasNext()) {
				MessageAndMetadata<String,String> messageAndMetadata = streamIterator.next();
				String message = messageAndMetadata.message();
				long offset = messageAndMetadata.offset();
				logger.info("开始消费APP消息-App>Ims!【message:{},topic:{},offset:{}】",message,topic,offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						PushMessageRespDTO pushMessageResponse = JSON.parseObject(message, PushMessageRespDTO.class);
						ImsMsgAppLog appPushMsgLog = imsMsgAppLogDao.getByProperty("msg_id",pushMessageResponse.getId());
						if(appPushMsgLog != null){
							if("OK".equalsIgnoreCase(pushMessageResponse.getResult())){
								appPushMsgLog.setStatus(AppPushMsgStatusEnum.success.getValue());
							}else{
								appPushMsgLog.setStatus(AppPushMsgStatusEnum.fail.getValue());
								Integer failTimes = appPushMsgLog.getFailTimes()!=null ? appPushMsgLog.getFailTimes()+1:1;
								appPushMsgLog.setFailTimes(failTimes);
							}
							appPushMsgLog.setUpdateDate(new Date());
							imsMsgAppLogDao.save(appPushMsgLog) ;
							logger.info("消费APP消息结果!【msgId:{},result:{}】",pushMessageResponse.getId(),pushMessageResponse.getResult());
						}else{
							logger.warn("消费APP消息不存在!【msgId:{}】",pushMessageResponse.getId());
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					return;
				}
			}
		} catch (Exception e) {
			if (e instanceof ConsumerTimeoutException) {
				logger.debug("一段时间没有检测到刷新信息.");
			} else {
				logger.error("刷新处理出现异常 --> {}", e);
			}
		}
	}
}
