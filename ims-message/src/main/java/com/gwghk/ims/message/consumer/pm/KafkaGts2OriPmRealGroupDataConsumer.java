package com.gwghk.ims.message.consumer.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.message.consumer.AbstractKafkaGts2lDataConsumer;
import com.gwghk.ims.message.consumer.KafkaTopic;
import com.gwghk.ims.message.consumer.handler.KafkaGts2OriGroupDataHandle;
import com.gwghk.unify.framework.common.util.StringUtil;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * 
 * @ClassName: KafkaGts2PMDemoGroupDataConsumer
 * @Description: GTS2(PM)-真实交易账号组数据同步
 * @author lawrence
 * @date 2017年8月18日
 *
 */
//@Component
public class KafkaGts2OriPmRealGroupDataConsumer extends AbstractKafkaGts2lDataConsumer{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriPmRealGroupDataConsumer.class);

	@Autowired
	private KafkaGts2OriGroupDataHandle kafkaGts2GroupDataHandle;
		
	public KafkaGts2OriPmRealGroupDataConsumer(){
		super();
		super.kafkaTopic = KafkaTopic.GTS2_PM_REAL_GROUP;
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
				logger.info("handlerPartition->消费GTS2(PM)-真实交易账号组数据消息:{},topic:{},offset:{}",message,topic,offset);
				try {
					if (StringUtil.isNotEmpty(message)) {
						kafkaGts2GroupDataHandle.handleMessage(message,CompanyEnum.pm.getCode(),ActEnvEnum.REAL.getValue());
					}
				} catch (Exception e) {
					logger.error("kafka消费GTS2(PM)-真实交易账号组数据消息消息处理失败。message --> {}, error --> {}", message, e);
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
		return "GTS2(PM)-真实交易账号组数据消息-gts2group";
	}
}
