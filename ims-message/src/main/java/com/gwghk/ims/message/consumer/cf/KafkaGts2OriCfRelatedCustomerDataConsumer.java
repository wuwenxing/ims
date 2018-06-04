package com.gwghk.ims.message.consumer.cf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.message.consumer.AbstractKafkaGts2lDataConsumer;
import com.gwghk.ims.message.consumer.KafkaTopic;
import com.gwghk.ims.message.consumer.handler.KafkaGts2RelatedCustomerDataHandle;
import com.gwghk.unify.framework.common.util.StringUtil;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * 
* @ClassName: KafkaGts2CFRelatedCustomerDataConsumer
* @Description: GTS2(CF)-真实账户模拟账号关联数据同步
* @author lawrence
* @date 2018年1月9日
*
 */
//@Component
public class KafkaGts2OriCfRelatedCustomerDataConsumer extends AbstractKafkaGts2lDataConsumer{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriCfRelatedCustomerDataConsumer.class);

	@Autowired
	private KafkaGts2RelatedCustomerDataHandle kafkaGts2RelatedCustomerDataHandle;
		
	public KafkaGts2OriCfRelatedCustomerDataConsumer(){
		super();
		super.kafkaTopic = KafkaTopic.GTS2_CF_REAL_RELATED_CUSTOMER;
	}

	/**
	 * @see 处理分区
	 * @param partition
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
				logger.info("handlerPartition->消费GTS2(CF)-真实账户模拟账号关联数据同步消息:{},topic:{},offset:{}",message,topic,offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						//kafkaGts2RelatedCustomerDataHandle.handleMessage(message,CompanyEnum.cf.getCode(),ActEnvEnum.REAL.getValue());
						kafkaGts2RelatedCustomerDataHandle.handleMessage(message);
					}
				} catch (Exception e) {
					logger.error("kafka消费GTS2(CF)-真实账号平台组数据消息消息处理失败。message --> {}, error --> {}", message, e);
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
		return "GTS2(CF)-真实账户模拟账号关联数据同步消息-t_related_customer";
	}
}
