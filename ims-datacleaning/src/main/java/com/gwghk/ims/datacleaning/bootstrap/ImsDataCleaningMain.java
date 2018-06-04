package com.gwghk.ims.datacleaning.bootstrap;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 摘要：数据清洗入口类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月9日
 */
@SpringBootApplication
@EnableScheduling
public class ImsDataCleaningMain {
	private static Logger logger = LoggerFactory.getLogger(ImsDataCleaningMain.class);
	
	public static void main(String[] args) throws Exception {
		// step1:修改默认配置，优先读取conf下的application.properties
        File file = new File(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "application.properties");
        if (file.exists()) {
            System.setProperty("spring.config.location", file.getAbsolutePath());
        }
        // step2:启动SpringBoot
		SpringApplication ctx = new SpringApplication(ImsDataCleaningMain.class);
		ctx.setShowBanner(false);
		ctx.setWebEnvironment(true);
		Set<Object> set = new HashSet<Object>();
		set.add("classpath:spring-datacleaning-*.xml");
		set.add("classpath*:spring-monitor-client.xml");
		ctx.setSources(set);
		ctx.run(args);
		logger.warn("ImsDataCleaningMain->ims data cleaning start !!!!");
	}
}