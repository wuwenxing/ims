package com.gwghk.ims.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.message.consumer.handler.KafkaGts2OriCountryDictDataHandle;
import com.gwghk.unify.framework.common.util.StringUtil;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * 
 * @ClassName: KafkaCountryDictDataConsumer
 * @Description: kafka接收国家数据字典
 * @author lawrence
 * @date 2018年3月27日
 *
 */
//@Component
public class KafkaOriCountryDictDataConsumer extends AbstractKafkaGts2lDataConsumer {
	private final static Logger logger = LoggerFactory.getLogger(KafkaOriCountryDictDataConsumer.class);

	@Autowired
	private KafkaGts2OriCountryDictDataHandle kafkaGts2CountryDictDataHandle;

	/*
	 * @Autowired private DistributedLockManager distributedLockManager;
	 */

	public KafkaOriCountryDictDataConsumer() {
		super();
		super.kafkaTopic = KafkaTopic.GTS2_REAL_COUNTRY_DICT;
	}

	/**
	 * @see 处理分区
	 * @param partition
	 * @param retryCount
	 *            重试次数
	 * @param topic
	 *            主题
	 */
	protected void handlerPartition(final KafkaStream<String, String> stream, int retryCount, String topic) {
		try {
			ConsumerIterator<String, String> streamIterator = stream.iterator();
			while (streamIterator.hasNext()) {
				MessageAndMetadata<String, String> messageAndMetadata = streamIterator.next();
				String message = messageAndMetadata.message();
				long offset = messageAndMetadata.offset();
				logger.info("handlerPartition->消费GTS2国籍字典消息:{},topic:{},offset:{}", message, topic, offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						kafkaGts2CountryDictDataHandle.handleMessage(message);
					}
				} catch (Exception e) {
					logger.error("kafka消费GTS2国籍字典消息处理失败。message --> {}, error --> {}", message, e);
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

	@Override
	protected String info() {
		return "GTS2国家数据字典-t_country_dict";
	}
}
