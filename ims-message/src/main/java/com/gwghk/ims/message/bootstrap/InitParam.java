package com.gwghk.ims.message.bootstrap;

import org.springframework.context.ApplicationContext;
/**
 * 旧数据源相关的初始化参数
 * @author jackson.tang
 *
 */
public class InitParam {
	private String externalDemoTopic;
	private String externalRealTopic;
	private String officeTopic;
	private String demoTopic;
	private String realTopic;
	private Long companyId;
	private String env;
	private ApplicationContext applicationContext;
	
	public String getExternalDemoTopic() {
		return externalDemoTopic;
	}
	public void setExternalDemoTopic(String externalDemoTopic) {
		this.externalDemoTopic = externalDemoTopic;
	}
	public String getExternalRealTopic() {
		return externalRealTopic;
	}
	public void setExternalRealTopic(String externalRealTopic) {
		this.externalRealTopic = externalRealTopic;
	}
	public String getOfficeTopic() {
		return officeTopic;
	}
	public void setOfficeTopic(String officeTopic) {
		this.officeTopic = officeTopic;
	}
	public String getDemoTopic() {
		return demoTopic;
	}
	public void setDemoTopic(String demoTopic) {
		this.demoTopic = demoTopic;
	}
	public String getRealTopic() {
		return realTopic;
	}
	public void setRealTopic(String realTopic) {
		this.realTopic = realTopic;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	
}
