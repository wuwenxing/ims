package com.gwghk.ims.message.bootstrap;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 摘要：消息启动类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月18日
 */
@SpringBootApplication
@EnableScheduling
public class MessageMain {
	private static Logger logger = LoggerFactory.getLogger(MessageMain.class);
	
	public static void main(String[] args) throws Exception {
		//增加默认时区，处理GTS2发放的时间数据正常转换
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));  
		// step1:修改默认配置，优先读取conf下的application.properties
        File file = new File(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "application.properties");
        if (file.exists()) {
            System.setProperty("spring.config.location", file.getAbsolutePath());
        }
        // step2:启动SpringBoot
		SpringApplication ctx = new SpringApplication(MessageMain.class);
		ctx.setShowBanner(false);
		ctx.setWebEnvironment(true);
		Set<Object> set = new HashSet<Object>();
		set.add("classpath:spring-message-*.xml");
		/*set.add("classpath*:spring-datasource-mybatis.xml");*/
        set.add("classpath*:spring-monitor-client.xml");
		ctx.setSources(set);
		ctx.run(args);
		logger.info("MessageMain->ims message service start !!!!");
	}
}