package com.gwghk.ims.monitor.conf;

import javax.servlet.Filter;
import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.gwghk.ims.monitor.service.LoggerService;

/**
 * druid javamelody监控配置
 * @author jackson.tang
 *
 */
@Configuration
public class MonitorConfiguration {
    public static final String JAVAMELODY_CONTEXT_PATH="/monitoring";
    public static final String DRUID_MONITOR_CONTEXT_PATH="/druid-monitor";
	
    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle2(){
       //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
       ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),DRUID_MONITOR_CONTEXT_PATH+"/*");
       
       //添加初始化参数：initParams
       
       //白名单：
       //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
       //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
       //servletRegistrationBean.addInitParameter("deny","192.168.1.73");
       //登录查看信息的账号密码.
       //servletRegistrationBean.addInitParameter("loginUsername","admin");
       //servletRegistrationBean.addInitParameter("loginPassword","123456");
       //是否能够重置数据.
       servletRegistrationBean.addInitParameter("resetEnable","false");
       return servletRegistrationBean;
    }
    
    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter2(){
       
       FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
       
       //添加过滤规则.
       filterRegistrationBean.addUrlPatterns("/*");
       
       //添加不需要忽略的格式信息.
       filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,"+DRUID_MONITOR_CONTEXT_PATH+"/*");
       return filterRegistrationBean;
    }

    /**
     * 注册javamelody监控
     * @return
     */
    @Bean
    public HttpSessionListener javaMelodyListener(){
        return new net.bull.javamelody.SessionListener();
    }

    /**
     * 注册javamelody监控
     * @return
     */
    @Bean
    public Filter javaMelodyFilter(){
    	return new net.bull.javamelody.MonitoringFilter();
    }
}