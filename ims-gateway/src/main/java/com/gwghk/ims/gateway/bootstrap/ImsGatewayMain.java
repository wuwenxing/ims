package com.gwghk.ims.gateway.bootstrap;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gwghk.ims.gateway.filter.XssFilter;

/**
 * 摘要：启动类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
@Configuration
@SpringBootApplication
@EnableScheduling
public class ImsGatewayMain {
	private static Logger logger = LoggerFactory.getLogger(ImsGatewayMain.class);

    public static void main(String[] args) throws Exception {
    	SpringApplication ctx = new SpringApplication(ImsGatewayMain.class);
        ctx.setShowBanner(false);
        ctx.setWebEnvironment(true);
        Set<Object> set = new HashSet<Object>();
        set.add("classpath*:spring-*.xml");
        set.add("classpath*:spring-monitor-client.xml");
        ctx.setSources(set);
        ctx.run(args);
        logger.warn("ImsGatewayMain->Ims gateway start !!!!");
    }

	/**  
     * 文件上传配置(限制上传文件最大为20M)
     */
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize("20480KB"); 	//文件最大 
        factory.setMaxRequestSize("20480KB");  //设置总上传数据总大小 
        return factory.createMultipartConfig();
    }
    
    /**
     * 跨站脚本攻击处理
     */
    @Bean  
    public FilterRegistrationBean  xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/");
        return registration;
    }
}
