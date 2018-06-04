package com.gwghk.ims.gateway.common;

import java.util.Date;

/**
 * 摘要：当前登录用户上下文对象 登录后设置对应的信息，用于后续调用
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public class UserContext implements java.io.Serializable {
	private static final long serialVersionUID = -8265522652490711669L;
	private static transient ThreadLocal<UserContext> instance = new ThreadLocal<UserContext>();

	private String loginIp;
	private String loginNo;
	private String loginName;
	private Date   loginDate;
	private String sessionId;
	private String clientType;
	private String browserType;
	private Long   companyId;
	private Long   roleId;
	private String companyCode;
	
	public static void set(UserContext userContext) {
		instance.set(userContext);
	}

	public static UserContext get() {
		UserContext userContext = instance.get();
		if (userContext != null) {
			return userContext;
		}
		return null;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "[loginIp=" + loginIp + ", loginNo=" + loginNo + ", loginName=" + loginName + ", loginDate="
				+ loginDate + ", sessionId=" + sessionId + ", clientType=" + clientType + ", browserType=" + browserType
				+ ", companyId=" + companyId + ", roleId=" + roleId + ", companyCode=" + companyCode + "]";
	}
}
