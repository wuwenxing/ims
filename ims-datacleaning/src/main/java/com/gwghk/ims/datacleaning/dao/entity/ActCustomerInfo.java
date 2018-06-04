package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

/**
 * 
 * @ClassName: ActivityCustomerInfo
 * @Description: 客户资料数据
 * @author lawrence
 * @date 2017年5月22日
 *
 */
public class ActCustomerInfo extends BaseEntity {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 账户号码
	 */
	private String accountNo;

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 账户币种
	 */
	private String accountCurrency;

	/**
	 * 账户级别，MINI:迷你，stand：标准，VIP：vip账户
	 */
	private String accountLevel;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 账号环境，real：真实，demo：模拟，test：测试
	 */
	private String accountEnv;

	/**
	 * 激活状态，Y：激活，N：未激活
	 */
	private String activatedStatus;

	/**
	 * 账号状态，A:启用 S:禁用 D:注销
	 */
	private String accountStatus;

	/**
	 * 是否黑名单用户，Y：是，N：否
	 */
	private String accountBlack;

	/**
	 * 注册时间
	 */
	private Date registerTime;

	/**
	 * 激活时间
	 */
	private Date activatedTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 身份证
	 */
	private String idNumber;

	/**
	 * 身份证或者手机号码曾经注销过账号
	 */
	private String cancelBefore;

	/**
	 * 中文名
	 */
	private String chineseName;

	/**
	 * 来源(0:GTS2库，1:旧库)
	 */
	private String source;

	/**
	 * 平台#账户币种
	 */
	private String platformCurrency;

	/**
	 * 同步创建时间
	 */
	private Date syncCreateDate;

	private Date fieldUpdateDate;
	
	private String env;
	
	/**
	 * 数据清洗同步时间
	 */
	private Date syncUpdateDate;
	
	/**
	 * 国籍
	 */
	private String nationality;

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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountEnv() {
		return accountEnv;
	}

	public void setAccountEnv(String accountEnv) {
		this.accountEnv = accountEnv;
	}

	public String getActivatedStatus() {
		return activatedStatus;
	}

	public void setActivatedStatus(String activatedStatus) {
		this.activatedStatus = activatedStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountBlack() {
		return accountBlack;
	}

	public void setAccountBlack(String accountBlack) {
		this.accountBlack = accountBlack;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getActivatedTime() {
		return activatedTime;
	}

	public void setActivatedTime(Date activatedTime) {
		this.activatedTime = activatedTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCancelBefore() {
		return cancelBefore;
	}

	public void setCancelBefore(String cancelBefore) {
		this.cancelBefore = cancelBefore;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatformCurrency() {
		return platformCurrency;
	}

	public void setPlatformCurrency(String platformCurrency) {
		this.platformCurrency = platformCurrency;
	}

	public Date getSyncCreateDate() {
		return syncCreateDate;
	}

	public void setSyncCreateDate(Date syncCreateDate) {
		this.syncCreateDate = syncCreateDate;
	}

	public Date getFieldUpdateDate() {
		return fieldUpdateDate;
	}

	public void setFieldUpdateDate(Date fieldUpdateDate) {
		this.fieldUpdateDate = fieldUpdateDate;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public Date getSyncUpdateDate() {
		return syncUpdateDate;
	}

	public void setSyncUpdateDate(Date syncUpdateDate) {
		this.syncUpdateDate = syncUpdateDate;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}
