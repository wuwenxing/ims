package com.gwghk.sys.api.bootstrap;

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
 * @Date 2017年8月8日
 */
@SpringBootApplication
@EnableScheduling
public class ImsSysMain {
	private static Logger logger = LoggerFactory.getLogger(ImsSysMain.class);

    public static void main(String[] args) throws Exception {
    	SpringApplication ctx = new SpringApplication(ImsSysMain.class);
        ctx.setShowBanner(false);
        ctx.setWebEnvironment(true);
        Set<Object> set = new HashSet<Object>();
        set.add("classpath*:spring-sys-*.xml");
        set.add("classpath*:spring-monitor-client.xml");
        ctx.setSources(set);
        ctx.run(args);
        logger.warn("ImsSysApi->ims sys api start !!!!");
    }
}
