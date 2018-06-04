package com.gwghk.ims.message.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName: Gts2CloneCustomerAccountInfo
 * @Description: 接收GTS2平台客户资料数据
 * @author lawrence
 * @date 2018年3月29日
 *
 */
public class Gts2CloneCustomerAccountInfo {
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
	 * 账户级别
	 */
	private String accountLevel;

	/**
	 * 姓名
	 */
	private String chineseName;

	/**
	 * 手机号码
	 */
	private String mobilePhone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 账号环境(暂时不用该字段,用is_real做判断)
	 */
	private String customerCategory;

	/**
	 * 是否真實，否即是測試
	 */
	private Boolean isReal;

	/**
	 * 账号状态
	 */
	private String accountStatus;

	/**
	 * 注册时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date openAccountDate;

	/**
	 * 激活时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date activateTime;

	/**
	 * 证件号码
	 */
	private String idDocumentNumber;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 公司ID
	 */
	private Long companyId;

	/**
	 * 更新时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	/**
	 * 创建时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	/**
	 * 更新用户
	 */
	private String updateUser;

	/**
	 * 创建用户
	 */
	private String createUser;

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

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Date getOpenAccountDate() {
		return openAccountDate;
	}

	public void setOpenAccountDate(Date openAccountDate) {
		this.openAccountDate = openAccountDate;
	}

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

	public String getIdDocumentNumber() {
		return idDocumentNumber;
	}

	public void setIdDocumentNumber(String idDocumentNumber) {
		this.idDocumentNumber = idDocumentNumber;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsReal() {
		return isReal;
	}

	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "Gts2CloneCustomerAccountInfo [accountNo=" + accountNo + ", platform=" + platform + ", accountCurrency="
				+ accountCurrency + ", accountLevel=" + accountLevel + ", chineseName=" + chineseName + ", mobilePhone="
				+ mobilePhone + ", email=" + email + ", customerCategory=" + customerCategory + ", isReal=" + isReal
				+ ", accountStatus=" + accountStatus + ", openAccountDate=" + openAccountDate + ", activateTime="
				+ activateTime + ", idDocumentNumber=" + idDocumentNumber + ", nationality=" + nationality
				+ ", companyId=" + companyId + "]";
	}

}
