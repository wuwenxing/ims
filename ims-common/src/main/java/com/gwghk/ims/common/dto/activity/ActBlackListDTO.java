package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;

import com.gwghk.ims.common.dto.BaseDTO;

public class ActBlackListDTO extends BaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1383834344921413547L;

	private Long id;

	/**
	 * 交易账号
	 */
	private String accountNo;

	/**
	 * 手机账号
	 */
	private String mobile;

	private String platform;

	/**
	 * 真实还是模拟账号
	 */
	private String env;

	/**
	 * 活动编号
	 */
	private String activityPeriods;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

}