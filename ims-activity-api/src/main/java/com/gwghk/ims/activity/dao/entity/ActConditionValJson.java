package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.util.CustomDateSerializer;

/**
 * 
 * @ClassName: ActConditionValJson
 * @Description: 参与条件需要json格式保存的属性值
 * @author eva
 * @date 2018年4月2日
 *
 */
public class ActConditionValJson {
	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 是否账号唯一 1:唯一，0:可允许不唯 一
	 */
	private Integer accountOnly;

	/**
	 * 是否允许注销过账号参加活动 1:支持 ,0:
	 */
	private Integer allowCancelAccount;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerStartTime;

	/**
	 * 账号注册结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerEndTime;

	/**
	 * 只允许白名单用户参加活动 true:是 ,false:否
	 */
	private Integer allowWhiteUsers;

	/**
	 * 白名单文件名
	 */
	private String whiteListFileName;

	/**
	 * 白名单地址
	 */
	
	private String whiteListFilePath;

	/**
	 * 黑名单文件名
	 */
	private String blackListFileName;
	
	/**
	 * 黑名单地址
	 */
	private String blackListFilePath;

	/**
	 * 是否允许取款
	 */
	private Integer allowWithdrawals;

	/**
	 * 账号激活日期:开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date activateStartTime;

	/**
	 * 账号激活日期:结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date activateEndTime;

	/**
	 * 账户状态 ActAccountStatus数据字典
	 */
	private String accountStatus;
	
	

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getRegisterStartTime() {
		return registerStartTime;
	}

	public void setRegisterStartTime(Date registerStartTime) {
		this.registerStartTime = registerStartTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(Date registerEndTime) {
		this.registerEndTime = registerEndTime;
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

	public String getPlatformsCcy() {
		return platformsCcy;
	}

	public void setPlatformsCcy(String platformsCcy) {
		this.platformsCcy = platformsCcy;
	}
 

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getActivateStartTime() {
		return activateStartTime;
	}

	public void setActivateStartTime(Date activateStartTime) {
		this.activateStartTime = activateStartTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
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

	public String getWhiteListFilePath() {
		return whiteListFilePath;
	}

	public void setWhiteListFilePath(String whiteListFilePath) {
		this.whiteListFilePath = whiteListFilePath;
	}

	public String getBlackListFilePath() {
		return blackListFilePath;
	}

	public void setBlackListFilePath(String blackListFilePath) {
		this.blackListFilePath = blackListFilePath;
	}

	public Integer getAccountOnly() {
		return accountOnly;
	}

	public void setAccountOnly(Integer accountOnly) {
		this.accountOnly = accountOnly;
	}

	public Integer getAllowCancelAccount() {
		return allowCancelAccount;
	}

	public void setAllowCancelAccount(Integer allowCancelAccount) {
		this.allowCancelAccount = allowCancelAccount;
	}

	public Integer getAllowWhiteUsers() {
		return allowWhiteUsers;
	}

	public void setAllowWhiteUsers(Integer allowWhiteUsers) {
		this.allowWhiteUsers = allowWhiteUsers;
	}

	public Integer getAllowWithdrawals() {
		return allowWithdrawals;
	}

	public void setAllowWithdrawals(Integer allowWithdrawals) {
		this.allowWithdrawals = allowWithdrawals;
	}
  
}
