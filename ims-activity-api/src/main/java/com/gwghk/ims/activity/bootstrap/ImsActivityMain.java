package com.gwghk.ims.activity.bootstrap;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 摘要：API入口启动类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月8日
 */
@SpringBootApplication
@EnableScheduling
public class ImsActivityMain {
	private static Logger logger = LoggerFactory.getLogger(ImsActivityMain.class);

    public static void main(String[] args) throws Exception {
    	SpringApplication ctx = new SpringApplication(ImsActivityMain.class);
        ctx.setShowBanner(false);
        ctx.setWebEnvironment(true);
        Set<Object> set = new HashSet<Object>();
        set.add("classpath*:spring-activity-*.xml");
        /*set.add("classpath*:spring-datasource-mybatis.xml");*/
        set.add("classpath*:spring-monitor-client.xml");
        ctx.setSources(set);
        ctx.run(args);
        logger.warn("ImsActivityApi->ims activity api start !!!!");
    }
}
