package com.gwghk.ims.bos.web.common.context;

import java.util.Date;

import com.gwghk.ims.common.dto.system.SystemUserDTO;
import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * 摘要：在线用户对象
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = -8265522652490711669L;

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
	 * 当前选择公司枚举类
	 */
	private CompanyEnum companyEnum;

	/**
	 * 登录用户实体
	 */
	private SystemUserDTO user;
	
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

	public CompanyEnum getCompanyEnum() {
		return companyEnum;
	}

	public void setCompanyEnum(CompanyEnum companyEnum) {
		this.companyEnum = companyEnum;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public SystemUserDTO getUser() {
		return user;
	}

	public void setUser(SystemUserDTO user) {
		this.user = user;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

}
