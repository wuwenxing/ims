package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.util.CustomDateSerializer;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：活动条件VO
 * 
 * @author
 * @version 1.0
 * @Date 2018年4月2日
 */
public class ActConditionSettingVO  extends BaseVO implements Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 5444995677376260655L;

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
	 * 条件值(用来查询)
	 */
	private String conditionVal;
	
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
	 * 平台+币种(GTS2#USD,MT4#CNH)
	 */
	private String platformsCcy;
	
	/**
	 * 活动平台 GTS2,MT4
	 */
	private String platforms;
 
	/**
	 * 币种 USD,CNY --CNH也是CNY一种
	 */
	private String ccy;

 
	/**
	 * 交易产品
	 */
	private String items;

	/**
	 * 账号注册开始日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerStartTime;

	/**
	 * 账号注册结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerEndTime;

	/**
	 * 只允许白名单用户参加活动 1:是 ,0:否
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date activateStartTime;

	/**
	 * 账号激活日期:结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

	 

	public Integer getAllowWithdrawals() {
		return allowWithdrawals;
	}

	public void setAllowWithdrawals(Integer allowWithdrawals) {
		this.allowWithdrawals = allowWithdrawals;
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

	public String getConditionVal() {
		return conditionVal;
	}

	public void setConditionVal(String conditionVal) {
		this.conditionVal = conditionVal;
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
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

	@Override
	public String toString() {
		return "ActConditionSettingVO [id=" + id + ", activityPeriods="
				+ activityPeriods + ", conditionType=" + conditionType
				+ ", conditionVal=" + conditionVal + ", accountType="
				+ accountType + ", accountOnly=" + accountOnly
				+ ", allowCancelAccount=" + allowCancelAccount
				+ ", accountLevel=" + accountLevel + ", platformsCcy="
				+ platformsCcy + ", platforms=" + platforms + ", ccy=" + ccy
				+ ", items=" + items + ", registerStartTime="
				+ registerStartTime + ", registerEndTime=" + registerEndTime
				+ ", allowWhiteUsers=" + allowWhiteUsers
				+ ", whiteListFileName=" + whiteListFileName
				+ ", whiteListFilePath=" + whiteListFilePath
				+ ", blackListFileName=" + blackListFileName
				+ ", blackListFilePath=" + blackListFilePath
				+ ", allowWithdrawals=" + allowWithdrawals
				+ ", activateStartTime=" + activateStartTime
				+ ", activateEndTime=" + activateEndTime + ", accountStatus="
				+ accountStatus + "]";
	}
 
	 
}