package com.gwghk.ims.message.bootstrap;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * 旧数据源清洗入口
 * @author jackson.tang
 *
 */
@Component
@Order(1)
public class ApplicationRun implements CommandLineRunner,ApplicationContextAware{
	/**
	 * 应用上下文
	 */
	private ApplicationContext appContext;
	/**
	 * 接入的具体项目
	 */
	private final String[] accessItemArr= {"AccountGroup","AccountInfo","BoDict","Customer","Group","Symbol","RelatedCustomer"};
	/**
	 * 旧数据源处理包名
	 */
	private final String accessPackageName="com.gwghk.ims.message.consumer.gts2";
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
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
	/**
	 * 接入的公司列表
	 */
	@Value("${message.companyList}")
	private String companyListStr;
	
	
	@Override
	public void run(String... args) throws Exception {
		if(companyListStr==null || companyListStr.isEmpty())
			return;
		
		InitParam initParam=new InitParam();
		initParam.setApplicationContext(appContext);
		initParam.setExternalDemoTopic(externalDemoTopic);
		initParam.setExternalRealTopic(externalRealTopic);
		initParam.setOfficeTopic(officeTopic);
		initParam.setDemoTopic(demoTopic);
		initParam.setRealTopic(realTopic);
		
		String[] companyArr=companyListStr.split(",");
		logger.info(">>>开始启动GTS2旧数据源接入服务,接入的公司有:{},具体接入的项目有:{}",companyListStr,accessItemArr);
		for(String company:companyArr) {
			String companyIdStr=CompanyEnum.getIdByCode(company);
			if(companyIdStr==null) {
				logger.error(">>>无法找到{} 对应的公司ID,该公司接入被忽略",company);
				continue;
			}
			
			initParam.setCompanyId(Long.parseLong(companyIdStr));
			for(String accessItem:accessItemArr) {
				//处理具体的接入项目的类
				String handleClass=accessPackageName+".KafkaGts2Ori"+accessItem+"DataConsumer";
				
				Class<?> cls=Class.forName(handleClass);
				//真实环境下的处理对象
				Object realEnvHandle=cls.newInstance();
				
				Method initMethod=cls.getMethod("init", InitParam.class);
				Method startMethod=cls.getMethod("start");
				initParam.setEnv("real");
				//调用初始化方法
				initMethod.invoke(realEnvHandle, initParam);
				//调用启动方法
				startMethod.invoke(realEnvHandle);
				
				//非RelatedCustomer下都有模拟环境
				if(!"RelatedCustomer".equals(accessItem)) {
					//模拟环境下的处理对象
					Object demoEnvHandle=cls.newInstance();
					initParam.setEnv("demo");
					initMethod.invoke(demoEnvHandle, initParam);
					startMethod.invoke(demoEnvHandle);
				}
			}
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext=appContext;
	}

}
