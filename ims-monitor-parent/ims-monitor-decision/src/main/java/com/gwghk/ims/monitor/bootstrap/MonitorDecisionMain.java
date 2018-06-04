package com.gwghk.ims.monitor.bootstrap;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 摘要：启动类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月12日
 */
@SpringBootApplication
@EnableScheduling
public class MonitorDecisionMain {
	private static Logger logger = LoggerFactory.getLogger(MonitorDecisionMain.class);
	
	public static void main(String[] args) throws Exception {
		// step1:修改默认配置，优先读取conf下的application.properties
        File file = new File(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "application.properties");
        if (file.exists()) {
            System.setProperty("spring.config.location", file.getAbsolutePath());
        }
        // step2:启动SpringBoot
		SpringApplication ctx = new SpringApplication(MonitorDecisionMain.class);
		ctx.setShowBanner(false);
		ctx.setWebEnvironment(true);
		Set<Object> set = new HashSet<Object>();
		set.add("classpath:spring-monitor-*.xml");
		ctx.setSources(set);
		ctx.run(args);
		logger.info("MonitorMain->ims minitor service start !!!!");
	}
	
	
}