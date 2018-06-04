package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ActRelatedCustomer {
	/**
	 * t_related_customer的ID
	 */
	private Long id;

	/**
	 * 客戶統一帳號
	 */
	private String customerNumber;

	/**
	 * 模擬客戶統一帳號
	 */
	private String demoCustomerNumber;

	/**
	 * t_company中的ID
	 */
	private Long companyId;

	private String createUser;

	private String createIp;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	private String updateUser;

	private String updateIp;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	private Integer versionNo;

	private String companyCode;

	private Date bindDate;

	/**
	 * t_related_customer的ID
	 * 
	 * @return id t_related_customer的ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * t_related_customer的ID
	 * 
	 * @param id
	 *            t_related_customer的ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getDemoCustomerNumber() {
		return demoCustomerNumber;
	}

	public void setDemoCustomerNumber(String demoCustomerNumber) {
		this.demoCustomerNumber = demoCustomerNumber;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
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

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

}