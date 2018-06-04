package com.gwghk.ims.message.consumer.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.message.consumer.AbstractKafkaGts2lDataConsumer;
import com.gwghk.ims.message.consumer.KafkaTopic;
import com.gwghk.ims.message.consumer.handler.KafkaGts2OriAccountInfoDataHandle;
import com.gwghk.unify.framework.common.util.StringUtil;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * 
 * @ClassName: KafkaGts2OriFxRealAccountInfoDataConsumer
 * @Description: GTS2(FX)-真实账号平台数据同步
 * @author lawrence
 * @date 2017年8月17日
 *
 */
//@Component
public class KafkaGts2OriFxRealAccountInfoDataConsumer extends AbstractKafkaGts2lDataConsumer{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriFxRealAccountInfoDataConsumer.class);

	@Autowired
	private KafkaGts2OriAccountInfoDataHandle kafkaGts2OriAccountInfoDataHandle;
		
	public KafkaGts2OriFxRealAccountInfoDataConsumer(){
		super();
		super.kafkaTopic = KafkaTopic.GTS2_FX_REAL_ACCOUNT;
	}

	/**
	 * @see 处理分区
	 * @param stream
	 * @param retryCount 重试次数
	 * @param topic 主题 
	 */
	protected void handlerPartition(final KafkaStream<String, String> stream, int retryCount,String topic) {
		try {
			ConsumerIterator<String, String> streamIterator = stream.iterator();
			while (streamIterator.hasNext()) {
				MessageAndMetadata<String,String> messageAndMetadata = streamIterator.next();
				String message = messageAndMetadata.message();
				long offset = messageAndMetadata.offset();
				logger.info("handlerPartition->消费GTS2(FX)-真实账号平台数据消息:{},topic:{},offset:{}",message,topic,offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						kafkaGts2OriAccountInfoDataHandle.handleMessage(message,CompanyEnum.fx.getCode(),ActEnvEnum.REAL.getValue());
					}
				} catch (Exception e) {
					logger.error("kafka消费GTS2(FX)-真实账号平台数据消息消息处理失败。message --> {}, error --> {}", message, e);
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
		return "GTS2(FX)-真实账号平台数据消息-t_account_info";
	}
}
