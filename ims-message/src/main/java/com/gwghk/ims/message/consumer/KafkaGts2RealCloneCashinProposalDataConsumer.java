package com.gwghk.ims.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.message.consumer.handler.KafkaGts2CloneCashinProposalHandle;
import com.gwghk.unify.framework.common.util.StringUtil;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

//@Component
public class KafkaGts2RealCloneCashinProposalDataConsumer extends AbstractKafkaGts2lDataConsumer {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2RealCloneCashinProposalDataConsumer.class);

	@Autowired
	private KafkaGts2CloneCashinProposalHandle kafkaGts2CashinProposalHandle;

	public KafkaGts2RealCloneCashinProposalDataConsumer() {
		super();
		super.kafkaTopic = KafkaTopic.GTS2_REAL_CLONE_CASHIN_PROPOSAL;
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
				if(message.indexOf("\"type\":\"CRC\"")>0){
					return;
				}
				long offset = messageAndMetadata.offset();
				logger.info("handlerPartition->消费GTS2-{}-入金数据消息:{},topic:{},offset:{}", ActEnvEnum.REAL.getValue(), message,
						topic, offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						kafkaGts2CashinProposalHandle.handleMessage(message, ActEnvEnum.REAL.getValue());
					}
				} catch (Exception e) {
					logger.error("kafka消费GTS2-{}-入金数据消息处理失败。message --> {}, error --> {}", ActEnvEnum.REAL.getValue(),
							message, e);
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

	@Override
	protected String info() {
		return "消费GTS2-real-入金数据消息-t_clone_cashin_proposal";
	}
}
