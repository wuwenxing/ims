package com.gwghk.ims.monitor.dal.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 监控服务配置
 * @author jackson.tang
 *
 */
@Table(name="monitor_machine")
public class MonitorMachine{
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 运行环境
	 */
	private String env;
	
	private String name;
	
	private String ipAddress;
	
	private String ipPort;
	
	private String contextPath;
	/**
	 * 应用ID
	 */
	private Long appId;
	/**
	 * 应用名
	 */
	@Transient
	private String appName;
	
	/**
	 * 心跳(s)
	 */
	private int heartBeatInterval;
	/**
	 * 工作时间 null表示全天，大约在12、23表示12、23时工作
	 */
	private String worktimeListStr;
	/**
	 * 监控的条目
	 */
	@Transient
	private List<MonitorItem> items;
	
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public List<MonitorItem> getItems() {
		return items;
	}
	public void setItems(List<MonitorItem> items) {
		this.items = items;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpPort() {
		return ipPort;
	}
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
	public int getHeartBeatInterval() {
		return heartBeatInterval;
	}
	public void setHeartBeatInterval(int heartBeatInterval) {
		this.heartBeatInterval = heartBeatInterval;
	}
	public String getWorktimeListStr() {
		return worktimeListStr;
	}
	public void setWorktimeListStr(String worktimeListStr) {
		this.worktimeListStr = worktimeListStr;
	}
	/**
	 * 获取基本URL
	 * @return
	 */
	public String getBaseURL() {
		return "http://"+ipAddress+":"+ipPort+contextPath;
	}
}
