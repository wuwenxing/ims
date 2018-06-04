package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2AccountInfo {
    private Long id;

    private Long companyId;

    private Long gts2CustomerId;

    private Long gts2AccountId;

    private String agentCode;

    private Long accountGroupId;

    private String accountType;

    private String currency;

    private String salePerson;

    private String inviteCode;

    private String accountNo;

    private String informationFrom;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creditAuthorizeDate;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date applicationDate;

    private String cashinTradeNo;

    private String lockWithdrawal;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date firstAttemptedDepositTime;

    private String attemptedDeposit;

    private String billCollectionMethod;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date cgseActiveDate;

    private String cgseProcessStatus;

    private String cgseStatus;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date activateTime;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date migrateDataActivateTime;

    private String platform;

    private String createUser;

    private String createIp;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String updateUser;

    private String updateIp;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private Integer versionNo;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date openAccountDate;

    private String migrateType;

    private String remark;
    
	private String env;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getGts2CustomerId() {
        return gts2CustomerId;
    }

    public void setGts2CustomerId(Long gts2CustomerId) {
        this.gts2CustomerId = gts2CustomerId;
    }

    public Long getGts2AccountId() {
        return gts2AccountId;
    }

    public void setGts2AccountId(Long gts2AccountId) {
        this.gts2AccountId = gts2AccountId;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode == null ? null : agentCode.trim();
    }

    public Long getAccountGroupId() {
        return accountGroupId;
    }

    public void setAccountGroupId(Long accountGroupId) {
        this.accountGroupId = accountGroupId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getSalePerson() {
        return salePerson;
    }

    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson == null ? null : salePerson.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getInformationFrom() {
        return informationFrom;
    }

    public void setInformationFrom(String informationFrom) {
        this.informationFrom = informationFrom == null ? null : informationFrom.trim();
    }

    public Date getCreditAuthorizeDate() {
        return creditAuthorizeDate;
    }

    public void setCreditAuthorizeDate(Date creditAuthorizeDate) {
        this.creditAuthorizeDate = creditAuthorizeDate;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getCashinTradeNo() {
        return cashinTradeNo;
    }

    public void setCashinTradeNo(String cashinTradeNo) {
        this.cashinTradeNo = cashinTradeNo == null ? null : cashinTradeNo.trim();
    }

    public String getLockWithdrawal() {
        return lockWithdrawal;
    }

    public void setLockWithdrawal(String lockWithdrawal) {
        this.lockWithdrawal = lockWithdrawal == null ? null : lockWithdrawal.trim();
    }

    public Date getFirstAttemptedDepositTime() {
        return firstAttemptedDepositTime;
    }

    public void setFirstAttemptedDepositTime(Date firstAttemptedDepositTime) {
        this.firstAttemptedDepositTime = firstAttemptedDepositTime;
    }

    public String getAttemptedDeposit() {
        return attemptedDeposit;
    }

    public void setAttemptedDeposit(String attemptedDeposit) {
        this.attemptedDeposit = attemptedDeposit == null ? null : attemptedDeposit.trim();
    }

    public String getBillCollectionMethod() {
        return billCollectionMethod;
    }

    public void setBillCollectionMethod(String billCollectionMethod) {
        this.billCollectionMethod = billCollectionMethod == null ? null : billCollectionMethod.trim();
    }

    public Date getCgseActiveDate() {
        return cgseActiveDate;
    }

    public void setCgseActiveDate(Date cgseActiveDate) {
        this.cgseActiveDate = cgseActiveDate;
    }

    public String getCgseProcessStatus() {
        return cgseProcessStatus;
    }

    public void setCgseProcessStatus(String cgseProcessStatus) {
        this.cgseProcessStatus = cgseProcessStatus == null ? null : cgseProcessStatus.trim();
    }

    public String getCgseStatus() {
        return cgseStatus;
    }

    public void setCgseStatus(String cgseStatus) {
        this.cgseStatus = cgseStatus == null ? null : cgseStatus.trim();
    }

    public Date getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(Date activateTime) {
        this.activateTime = activateTime;
    }

    public Date getMigrateDataActivateTime() {
        return migrateDataActivateTime;
    }

    public void setMigrateDataActivateTime(Date migrateDataActivateTime) {
        this.migrateDataActivateTime = migrateDataActivateTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
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

    public Date getOpenAccountDate() {
        return openAccountDate;
    }

    public void setOpenAccountDate(Date openAccountDate) {
        this.openAccountDate = openAccountDate;
    }

    public String getMigrateType() {
        return migrateType;
    }

    public void setMigrateType(String migrateType) {
        this.migrateType = migrateType == null ? null : migrateType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
  
    
}