package com.gwghk.ims.message.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.message.bootstrap.InitParam;
import com.gwghk.unify.framework.mq.kafka.KafkaConsumer;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public abstract class AbstractKafkaGts2lDataConsumer {
	private final static Logger logger = LoggerFactory.getLogger(AbstractKafkaGts2lDataConsumer.class);

	protected KafkaTopic kafkaTopic;

	@Value("${gts2.kafka.externalDemoTopic}")
	private String externalDemoTopic;
	@Value("${gts2.kafka.externalRealTopic}")
	private String externalRealTopic;

	@Value("${gts2.kafka.officeTopic}")
	private String officeTopic;

	@Value("${gts2.kafka.demoTopic}")
	private String demoTopic;
	@Value("${gts2.kafka.realTopic}")
	private String realTopic;
	//接入的公司id
	protected Long companyId;
	//环境
	protected ActEnvEnum env;
	//应用上下文:用来获取spring bean
	protected ApplicationContext applicationContext;

	@PostConstruct
	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				beforeRun();
				logger.info(">>>>>>>>>>>>开始接收 kafka " + info());
				String topic = kafkaTopic.getValue();
				if ("external".equals(kafkaTopic.getType())) {
					if (ActEnvEnum.REAL.getValue().equals(kafkaTopic.getEnv())) {
						topic = topic.replace("{0}", externalRealTopic);
					} else if (ActEnvEnum.DEMO.getValue().equals(kafkaTopic.getEnv())) {
						topic = topic.replace("{0}", externalDemoTopic);
					}
				} else if ("office".equals(kafkaTopic.getType())) {
					topic = topic.replace("{0}", officeTopic);
				} else {
					if (ActEnvEnum.REAL.getValue().equals(kafkaTopic.getEnv())) {
						topic = topic.replace("{0}", realTopic);
					} else if (ActEnvEnum.DEMO.getValue().equals(kafkaTopic.getEnv())) {
						topic = topic.replace("{0}", demoTopic);
					}
				}

				logger.info("topic:" + topic);
				Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
				topicCountMap.put(topic, 1);
				StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
				StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
				ConsumerConnector consumer = new KafkaConsumer().getInstance().getConsumer();
				Map<String, List<KafkaStream<String, String>>> consumerMap = consumer
						.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
				try {
					List<KafkaStream<String, String>> partitions = consumerMap.get(topic);
					while (true) {
						for (final KafkaStream<String, String> partition : partitions) {
							handlerPartition(partition, 0, topic);
						}
						Thread.sleep(5000);
					}
				} catch (Exception e) {
					logger.error(">>>>>>>>>>>>接收 kafka " + info() + " error --> {}", e);
					consumer.shutdown();
				}
			}
		}).start();

	}
	
	/**
	 * 手动初始化参数
	 * @param param
	 */
	public void init(InitParam param) {
		this.externalDemoTopic=param.getExternalDemoTopic();
		this.externalRealTopic=param.getExternalRealTopic();
		this.officeTopic=param.getOfficeTopic();
		this.demoTopic=param.getDemoTopic();
		this.realTopic=param.getRealTopic();
		this.companyId=param.getCompanyId();
		this.env=ActEnvEnum.valueOf(param.getEnv().toUpperCase());
		this.applicationContext=param.getApplicationContext();
	}
	
	/**
	 * run前置处理
	 */
	protected void beforeRun() {}
	/**
	 * 获取kafkaTopic名字的关键key值
	 * @return
	 */
	protected String getTopicNameKey() {return (CompanyEnum.getCodeById(companyId)+"_"+env.getValue()).toUpperCase();}

	protected abstract void handlerPartition(final KafkaStream<String, String> partition, int retryCount, String topic);

	protected abstract String info();
}
