package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2AccountGroup {
    private Long id;

    private Long companyId;

    private String platform;

    private String code;

    private String displayName;

    private String accountGroupStatus;

    private String accountLevelCode;

    private String accountStatusCode;

    private Long tradeGroupId;

    private String tradeAccountStatus;

    private String currency;

    private String remark;

    private Boolean enableDeposit;

    private Boolean enableWithdraw;

    private Boolean enableTransferIn;

    private Boolean enableTransferOut;

    private Boolean enableAdjustment;

    private String activateTotalDepositCurrency;

    private Double activateTotalDepositAmount;

    private Long activateAccountGroupId;

    private Boolean activateCountAllAccount;

    private Long upgradeAccountGroupId;

    private String upgradeTotalDepositCurrency;

    private Double upgradeTotalDepositAmount;

    private Boolean upgradeCountAllAccount;

    private Long downgradeAccountGroupId;

    private Boolean useCompanySetting;

    private Boolean deleted;

    private String createUser;

    private String createIp;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String updateUser;

    private String updateIp;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private Integer versionNo;

    private String tradeMode;

    private String checkTradeMode;

    private String checkHoldPosition;

    private Double marginCreditRatio;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public String getAccountGroupStatus() {
        return accountGroupStatus;
    }

    public void setAccountGroupStatus(String accountGroupStatus) {
        this.accountGroupStatus = accountGroupStatus == null ? null : accountGroupStatus.trim();
    }

    public String getAccountLevelCode() {
        return accountLevelCode;
    }

    public void setAccountLevelCode(String accountLevelCode) {
        this.accountLevelCode = accountLevelCode == null ? null : accountLevelCode.trim();
    }

    public String getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(String accountStatusCode) {
        this.accountStatusCode = accountStatusCode == null ? null : accountStatusCode.trim();
    }

    public Long getTradeGroupId() {
        return tradeGroupId;
    }

    public void setTradeGroupId(Long tradeGroupId) {
        this.tradeGroupId = tradeGroupId;
    }

    public String getTradeAccountStatus() {
        return tradeAccountStatus;
    }

    public void setTradeAccountStatus(String tradeAccountStatus) {
        this.tradeAccountStatus = tradeAccountStatus == null ? null : tradeAccountStatus.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getEnableDeposit() {
        return enableDeposit;
    }

    public void setEnableDeposit(Boolean enableDeposit) {
        this.enableDeposit = enableDeposit;
    }

    public Boolean getEnableWithdraw() {
        return enableWithdraw;
    }

    public void setEnableWithdraw(Boolean enableWithdraw) {
        this.enableWithdraw = enableWithdraw;
    }

    public Boolean getEnableTransferIn() {
        return enableTransferIn;
    }

    public void setEnableTransferIn(Boolean enableTransferIn) {
        this.enableTransferIn = enableTransferIn;
    }

    public Boolean getEnableTransferOut() {
        return enableTransferOut;
    }

    public void setEnableTransferOut(Boolean enableTransferOut) {
        this.enableTransferOut = enableTransferOut;
    }

    public Boolean getEnableAdjustment() {
        return enableAdjustment;
    }

    public void setEnableAdjustment(Boolean enableAdjustment) {
        this.enableAdjustment = enableAdjustment;
    }

    public String getActivateTotalDepositCurrency() {
        return activateTotalDepositCurrency;
    }

    public void setActivateTotalDepositCurrency(String activateTotalDepositCurrency) {
        this.activateTotalDepositCurrency = activateTotalDepositCurrency == null ? null : activateTotalDepositCurrency.trim();
    }

    public Double getActivateTotalDepositAmount() {
        return activateTotalDepositAmount;
    }

    public void setActivateTotalDepositAmount(Double activateTotalDepositAmount) {
        this.activateTotalDepositAmount = activateTotalDepositAmount;
    }

    public Long getActivateAccountGroupId() {
        return activateAccountGroupId;
    }

    public void setActivateAccountGroupId(Long activateAccountGroupId) {
        this.activateAccountGroupId = activateAccountGroupId;
    }

    public Boolean getActivateCountAllAccount() {
        return activateCountAllAccount;
    }

    public void setActivateCountAllAccount(Boolean activateCountAllAccount) {
        this.activateCountAllAccount = activateCountAllAccount;
    }

    public Long getUpgradeAccountGroupId() {
        return upgradeAccountGroupId;
    }

    public void setUpgradeAccountGroupId(Long upgradeAccountGroupId) {
        this.upgradeAccountGroupId = upgradeAccountGroupId;
    }

    public String getUpgradeTotalDepositCurrency() {
        return upgradeTotalDepositCurrency;
    }

    public void setUpgradeTotalDepositCurrency(String upgradeTotalDepositCurrency) {
        this.upgradeTotalDepositCurrency = upgradeTotalDepositCurrency == null ? null : upgradeTotalDepositCurrency.trim();
    }

    public Double getUpgradeTotalDepositAmount() {
        return upgradeTotalDepositAmount;
    }

    public void setUpgradeTotalDepositAmount(Double upgradeTotalDepositAmount) {
        this.upgradeTotalDepositAmount = upgradeTotalDepositAmount;
    }

    public Boolean getUpgradeCountAllAccount() {
        return upgradeCountAllAccount;
    }

    public void setUpgradeCountAllAccount(Boolean upgradeCountAllAccount) {
        this.upgradeCountAllAccount = upgradeCountAllAccount;
    }

    public Long getDowngradeAccountGroupId() {
        return downgradeAccountGroupId;
    }

    public void setDowngradeAccountGroupId(Long downgradeAccountGroupId) {
        this.downgradeAccountGroupId = downgradeAccountGroupId;
    }

    public Boolean getUseCompanySetting() {
        return useCompanySetting;
    }

    public void setUseCompanySetting(Boolean useCompanySetting) {
        this.useCompanySetting = useCompanySetting;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode == null ? null : tradeMode.trim();
    }

    public String getCheckTradeMode() {
        return checkTradeMode;
    }

    public void setCheckTradeMode(String checkTradeMode) {
        this.checkTradeMode = checkTradeMode == null ? null : checkTradeMode.trim();
    }

    public String getCheckHoldPosition() {
        return checkHoldPosition;
    }

    public void setCheckHoldPosition(String checkHoldPosition) {
        this.checkHoldPosition = checkHoldPosition == null ? null : checkHoldPosition.trim();
    }

    public Double getMarginCreditRatio() {
        return marginCreditRatio;
    }

    public void setMarginCreditRatio(Double marginCreditRatio) {
        this.marginCreditRatio = marginCreditRatio;
    }

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
    
}