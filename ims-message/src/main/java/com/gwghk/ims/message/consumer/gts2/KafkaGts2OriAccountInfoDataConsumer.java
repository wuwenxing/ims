package com.gwghk.ims.message.consumer.gts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @ClassName: KafkaGts2OriAccountInfoDataConsumer
 * @Description: GTS2-账号平台数据同步
 * @author lawrence
 * @date 2017年8月17日
 *
 */
public class KafkaGts2OriAccountInfoDataConsumer extends AbstractKafkaGts2lDataConsumer{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriAccountInfoDataConsumer.class);

	private KafkaGts2OriAccountInfoDataHandle kafkaGts2OriAccountInfoDataHandle;
	
	private String companyCode;
	
	public void beforeRun(){
		companyCode=CompanyEnum.getCodeById(companyId);
		kafkaTopic=KafkaTopic.valueOf("GTS2_"+getTopicNameKey()+"_ACCOUNT");
		kafkaGts2OriAccountInfoDataHandle=applicationContext.getBean(KafkaGts2OriAccountInfoDataHandle.class);
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
				logger.info("handlerPartition->消费GTS2({})-{}账号平台数据消息:{},topic:{},offset:{}",companyCode,env.getLabelKey(),message,topic,offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						kafkaGts2OriAccountInfoDataHandle.handleMessage(message,companyCode,env.getValue());
					}
				} catch (Exception e) {
					logger.error("kafka消费GTS2({})-{}账号平台数据消息消息处理失败。message --> {}, error --> {}",companyCode,env.getLabelKey(), message, e);
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
		return "GTS2("+companyCode+")-"+env.getLabelKey()+"账号平台数据消息-t_account_info";
	}
}
