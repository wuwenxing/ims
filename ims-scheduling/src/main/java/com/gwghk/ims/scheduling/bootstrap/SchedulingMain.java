package com.gwghk.ims.scheduling.bootstrap;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 摘要：调度系统入口启动类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月18日
 */

//禁用数据库的自动配置
@SpringBootApplication(exclude= {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class}) 
@EnableScheduling
public class SchedulingMain {
	private static Logger logger = LoggerFactory.getLogger(SchedulingMain.class);
	
	public static void main(String[] args) throws Exception {
		// step1:修改默认配置，优先读取conf下的application.properties
        File file = new File(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "application.properties");
        if (file.exists()) {
            System.setProperty("spring.config.location", file.getAbsolutePath());
        }
        // step2:启动SpringBoot
		SpringApplication ctx = new SpringApplication(SchedulingMain.class);
		ctx.setShowBanner(false);
		ctx.setWebEnvironment(true);
		Set<Object> set = new HashSet<Object>();
		set.add("classpath:spring-scheduling-*.xml");
		set.add("classpath*:spring-monitor-client.xml");
		ctx.setSources(set);
		ctx.run(args);
		logger.info("SchedulingMain->ims scheduling service start !!!!");
	}
}