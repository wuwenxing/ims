package com.gwghk.ims.mis.gateway.common;

import java.util.Date;

import com.gwghk.ims.common.vo.system.SystemUserVO;

/**
 * 摘要：在线用户对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public class Client implements java.io.Serializable {
	private static final long serialVersionUID = 7143909144030081927L;

	/**
	 * 用户IP
	 */
	private String ip;

	/**
	 * sessionId
	 */
	private String sessionId;

	/**
	 * 登录时间
	 */
	private Date loginDate;

	/**
	 * 登录的客户端类型
	 */
	private String clientType;
	
	/**
	 * 浏览器类型
	 */
	private String browserType;

	/**
	 * 登录用户实体
	 */
	private SystemUserVO user;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public SystemUserVO getUser() {
		return user;
	}

	public void setUser(SystemUserVO user) {
		this.user = user;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
}
