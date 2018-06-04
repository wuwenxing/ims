package com.gwghk.ims.activity.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 封装参与条件所有相关属性信息
 * @author eva
 *
 */
public class ActConditionSettingWrapper extends BaseEntity {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 活动编号
	 */
	private String activityPeriods;

	/**
	 * 条件类型
	 */
	private String conditionType;

	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 是否账号唯一 true:唯一，false:可允许不唯 一
	 */
	private Boolean accountOnly;

	/**
	 * 是否允许注销过账号参加活动 true:支持 ,false:
	 */
	private Boolean allowCancelAccount;

	/**
	 * 账号级别
	 */
	private String accountLevel;

	/**
	 * 活动平台 GTS2,MT4
	 */
	private String platforms;

	/**
	 * 平台+币种(GTS2#USD,MT4#CNH)
	 */
	private String platformsCcy;

	/**
	 * 平台币种eg:platformsCcy=(GTS2#CNY,MT4#CNH) ,ccy=CNY 
	 */
	private String ccy;

	
	/**
	 * 交易产品
	 */
	private String items;

	/**
	 * 账号注册开始日期
	 */
	private Date registerStartTime;

	/**
	 * 账号注册结束日期
	 */
	private Date registerEndTime;

	/**
	 * 只允许白名单用户参加活动 true:是 ,false:否
	 */
	private Boolean allowWhiteUsers;

	/**
	 * 白名单文件名
	 */
	private String whiteListFileName;

	/**
	 * 黑名单文件名
	 */
	private String blackListFileName;

	/**
	 * 是否允许取款
	 */
	private Boolean allowWithdrawals;

	/**
	 * 账号激活日期:开始时间
	 */
	private Date activateStartTime;

	/**
	 * 账号激活日期:结束时间
	 */
	private Date activateEndTime;

	/**
	 * 账户状态 ActAccountStatus数据字典
	 */
	private String accountStatus;
	
	
	 /**
     * 设置用于模拟账号的交易产品
     */
    private List<String> demoProducts;

    /**
     * 设置用于真实账号的交易产品
     */
    private List<String> realProducts;

    /**
     * 参与的活动平台
     */
    private Set<String> platformsSet;
	
 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Boolean getAccountOnly() {
		return accountOnly;
	}

	public void setAccountOnly(Boolean accountOnly) {
		this.accountOnly = accountOnly;
	}

	public Boolean getAllowCancelAccount() {
		return allowCancelAccount;
	}

	public void setAllowCancelAccount(Boolean allowCancelAccount) {
		this.allowCancelAccount = allowCancelAccount;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	public String getPlatformsCcy() {
		return platformsCcy;
	}

	public void setPlatformsCcy(String platformsCcy) {
		this.platformsCcy = platformsCcy;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Date getRegisterStartTime() {
		return registerStartTime;
	}

	public void setRegisterStartTime(Date registerStartTime) {
		this.registerStartTime = registerStartTime;
	}

	public Date getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(Date registerEndTime) {
		this.registerEndTime = registerEndTime;
	}

	public Boolean getAllowWhiteUsers() {
		return allowWhiteUsers;
	}

	public void setAllowWhiteUsers(Boolean allowWhiteUsers) {
		this.allowWhiteUsers = allowWhiteUsers;
	}

	public String getWhiteListFileName() {
		return whiteListFileName;
	}

	public void setWhiteListFileName(String whiteListFileName) {
		this.whiteListFileName = whiteListFileName;
	}

	public String getBlackListFileName() {
		return blackListFileName;
	}

	public void setBlackListFileName(String blackListFileName) {
		this.blackListFileName = blackListFileName;
	}

	public Boolean getAllowWithdrawals() {
		return allowWithdrawals;
	}

	public void setAllowWithdrawals(Boolean allowWithdrawals) {
		this.allowWithdrawals = allowWithdrawals;
	}

	public Date getActivateStartTime() {
		return activateStartTime;
	}

	public void setActivateStartTime(Date activateStartTime) {
		this.activateStartTime = activateStartTime;
	}

	public Date getActivateEndTime() {
		return activateEndTime;
	}

	public void setActivateEndTime(Date activateEndTime) {
		this.activateEndTime = activateEndTime;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public List<String> getDemoProducts() {
		return demoProducts;
	}

	public void setDemoProducts(List<String> demoProducts) {
		this.demoProducts = demoProducts;
	}

	public List<String> getRealProducts() {
		return realProducts;
	}

	public void setRealProducts(List<String> realProducts) {
		this.realProducts = realProducts;
	}

	public Set<String> getPlatformsSet() {
		return platformsSet;
	}

	public void setPlatformsSet(Set<String> platformsSet) {
		this.platformsSet = platformsSet;
	}

	 
 
}