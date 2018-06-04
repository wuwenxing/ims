package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2CashoutProposal {
    private Long id;

    private Long gts2CustomerId;

    private Long gts2AccountId;

    private Long accountGroupId;

    private String accountNo;

    private String customerNo;

    private String agentCode;

    private Integer cusTranType;

    private String platform;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expireDate;

    private Double payAmount;

    private String accountCurrency;

    private String payCurrency;

    private Double transAmount;

    private String transCurrency;

    private Double exchangeRate;

    private String bank;

    private String bankAccountNumber;

    private String bankPayMethod;

    private Double fee;

    private String informationFrom;

    private String remark;

    private String withdrewBankname;

    private String withdrewBankAccount;

    private String withdrewBankAccounttype;

    private String withdrewNationalTransfercode;

    private String withdrewBankAddress;

    private String withdrewBankCountry;

    private String withdrewProvince;

    private String withdrewBankCity;

    private String withdrewBankBranch;

    private String pno;

    private String proposalStatus;

    private Boolean isAutoApprove;

    private String proposer;

    private String preApprover;

    private String approver;

    private String canceller;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date proposalDate;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date preApproveDate;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date cancelDate;

    private Long customerGroupId;

    private String createUser;

    private String createIp;
    
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String updateUser;

    private String updateIp;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private Integer versionNo;

    private Boolean withdrawAll;

    private String cashoutChannel;

    private String referPno;

    private Double actualExchangeRate;

    private Double actualPayAmount;

    private String withdrewBankAccountName;

    private String prevMatchedPno;

    private Integer dailyCount;

    private Double marginRatio;

    private Double marginCredit;
    
    private String withdrewFilePath;

    private String withdrewFileName;
    
    private String env;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAccountGroupId() {
        return accountGroupId;
    }

    public void setAccountGroupId(Long accountGroupId) {
        this.accountGroupId = accountGroupId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode == null ? null : agentCode.trim();
    }

    public Integer getCusTranType() {
        return cusTranType;
    }

    public void setCusTranType(Integer cusTranType) {
        this.cusTranType = cusTranType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency == null ? null : accountCurrency.trim();
    }

    public String getPayCurrency() {
        return payCurrency;
    }

    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency == null ? null : payCurrency.trim();
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransCurrency() {
        return transCurrency;
    }

    public void setTransCurrency(String transCurrency) {
        this.transCurrency = transCurrency == null ? null : transCurrency.trim();
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber == null ? null : bankAccountNumber.trim();
    }

    public String getBankPayMethod() {
        return bankPayMethod;
    }

    public void setBankPayMethod(String bankPayMethod) {
        this.bankPayMethod = bankPayMethod == null ? null : bankPayMethod.trim();
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getInformationFrom() {
        return informationFrom;
    }

    public void setInformationFrom(String informationFrom) {
        this.informationFrom = informationFrom == null ? null : informationFrom.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getWithdrewBankname() {
        return withdrewBankname;
    }

    public void setWithdrewBankname(String withdrewBankname) {
        this.withdrewBankname = withdrewBankname == null ? null : withdrewBankname.trim();
    }

    public String getWithdrewBankAccount() {
        return withdrewBankAccount;
    }

    public void setWithdrewBankAccount(String withdrewBankAccount) {
        this.withdrewBankAccount = withdrewBankAccount == null ? null : withdrewBankAccount.trim();
    }

    public String getWithdrewBankAccounttype() {
        return withdrewBankAccounttype;
    }

    public void setWithdrewBankAccounttype(String withdrewBankAccounttype) {
        this.withdrewBankAccounttype = withdrewBankAccounttype == null ? null : withdrewBankAccounttype.trim();
    }

    public String getWithdrewNationalTransfercode() {
        return withdrewNationalTransfercode;
    }

    public void setWithdrewNationalTransfercode(String withdrewNationalTransfercode) {
        this.withdrewNationalTransfercode = withdrewNationalTransfercode == null ? null : withdrewNationalTransfercode.trim();
    }

    public String getWithdrewBankAddress() {
        return withdrewBankAddress;
    }

    public void setWithdrewBankAddress(String withdrewBankAddress) {
        this.withdrewBankAddress = withdrewBankAddress == null ? null : withdrewBankAddress.trim();
    }

    public String getWithdrewBankCountry() {
        return withdrewBankCountry;
    }

    public void setWithdrewBankCountry(String withdrewBankCountry) {
        this.withdrewBankCountry = withdrewBankCountry == null ? null : withdrewBankCountry.trim();
    }

    public String getWithdrewProvince() {
        return withdrewProvince;
    }

    public void setWithdrewProvince(String withdrewProvince) {
        this.withdrewProvince = withdrewProvince == null ? null : withdrewProvince.trim();
    }

    public String getWithdrewBankCity() {
        return withdrewBankCity;
    }

    public void setWithdrewBankCity(String withdrewBankCity) {
        this.withdrewBankCity = withdrewBankCity == null ? null : withdrewBankCity.trim();
    }

    public String getWithdrewBankBranch() {
        return withdrewBankBranch;
    }

    public void setWithdrewBankBranch(String withdrewBankBranch) {
        this.withdrewBankBranch = withdrewBankBranch == null ? null : withdrewBankBranch.trim();
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno == null ? null : pno.trim();
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus == null ? null : proposalStatus.trim();
    }

    public Boolean getIsAutoApprove() {
        return isAutoApprove;
    }

    public void setIsAutoApprove(Boolean isAutoApprove) {
        this.isAutoApprove = isAutoApprove;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    public String getPreApprover() {
        return preApprover;
    }

    public void setPreApprover(String preApprover) {
        this.preApprover = preApprover == null ? null : preApprover.trim();
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    public String getCanceller() {
        return canceller;
    }

    public void setCanceller(String canceller) {
        this.canceller = canceller == null ? null : canceller.trim();
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public Date getPreApproveDate() {
        return preApproveDate;
    }

    public void setPreApproveDate(Date preApproveDate) {
        this.preApproveDate = preApproveDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
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

    public Boolean getWithdrawAll() {
        return withdrawAll;
    }

    public void setWithdrawAll(Boolean withdrawAll) {
        this.withdrawAll = withdrawAll;
    }

    public String getCashoutChannel() {
        return cashoutChannel;
    }

    public void setCashoutChannel(String cashoutChannel) {
        this.cashoutChannel = cashoutChannel == null ? null : cashoutChannel.trim();
    }

    public String getReferPno() {
        return referPno;
    }

    public void setReferPno(String referPno) {
        this.referPno = referPno == null ? null : referPno.trim();
    }

    public Double getActualExchangeRate() {
        return actualExchangeRate;
    }

    public void setActualExchangeRate(Double actualExchangeRate) {
        this.actualExchangeRate = actualExchangeRate;
    }

    public Double getActualPayAmount() {
        return actualPayAmount;
    }

    public void setActualPayAmount(Double actualPayAmount) {
        this.actualPayAmount = actualPayAmount;
    }

    public String getWithdrewBankAccountName() {
        return withdrewBankAccountName;
    }

    public void setWithdrewBankAccountName(String withdrewBankAccountName) {
        this.withdrewBankAccountName = withdrewBankAccountName == null ? null : withdrewBankAccountName.trim();
    }

    public String getPrevMatchedPno() {
        return prevMatchedPno;
    }

    public void setPrevMatchedPno(String prevMatchedPno) {
        this.prevMatchedPno = prevMatchedPno == null ? null : prevMatchedPno.trim();
    }

    public Integer getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(Integer dailyCount) {
        this.dailyCount = dailyCount;
    }

    public Double getMarginRatio() {
        return marginRatio;
    }

    public void setMarginRatio(Double marginRatio) {
        this.marginRatio = marginRatio;
    }

    public Double getMarginCredit() {
        return marginCredit;
    }

    public void setMarginCredit(Double marginCredit) {
        this.marginCredit = marginCredit;
    }

	public String getWithdrewFilePath() {
		return withdrewFilePath;
	}

	public void setWithdrewFilePath(String withdrewFilePath) {
		this.withdrewFilePath = withdrewFilePath;
	}

	public String getWithdrewFileName() {
		return withdrewFileName;
	}

	public void setWithdrewFileName(String withdrewFileName) {
		this.withdrewFileName = withdrewFileName;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	@Override
	public String toString() {
		return "Gts2CashoutProposal [id=" + id + ", gts2CustomerId=" + gts2CustomerId + ", gts2AccountId="
				+ gts2AccountId + ", accountGroupId=" + accountGroupId + ", accountNo=" + accountNo + ", customerNo="
				+ customerNo + ", agentCode=" + agentCode + ", cusTranType=" + cusTranType + ", platform=" + platform
				+ ", expireDate=" + expireDate + ", payAmount=" + payAmount + ", accountCurrency=" + accountCurrency
				+ ", payCurrency=" + payCurrency + ", transAmount=" + transAmount + ", transCurrency=" + transCurrency
				+ ", exchangeRate=" + exchangeRate + ", bank=" + bank + ", bankAccountNumber=" + bankAccountNumber
				+ ", bankPayMethod=" + bankPayMethod + ", fee=" + fee + ", informationFrom=" + informationFrom
				+ ", remark=" + remark + ", withdrewBankname=" + withdrewBankname + ", withdrewBankAccount="
				+ withdrewBankAccount + ", withdrewBankAccounttype=" + withdrewBankAccounttype
				+ ", withdrewNationalTransfercode=" + withdrewNationalTransfercode + ", withdrewBankAddress="
				+ withdrewBankAddress + ", withdrewBankCountry=" + withdrewBankCountry + ", withdrewProvince="
				+ withdrewProvince + ", withdrewBankCity=" + withdrewBankCity + ", withdrewBankBranch="
				+ withdrewBankBranch + ", pno=" + pno + ", proposalStatus=" + proposalStatus + ", isAutoApprove="
				+ isAutoApprove + ", proposer=" + proposer + ", preApprover=" + preApprover + ", approver=" + approver
				+ ", canceller=" + canceller + ", proposalDate=" + proposalDate + ", preApproveDate=" + preApproveDate
				+ ", approveDate=" + approveDate + ", cancelDate=" + cancelDate + ", customerGroupId=" + customerGroupId
				+ ", createUser=" + createUser + ", createIp=" + createIp + ", createDate=" + createDate
				+ ", updateUser=" + updateUser + ", updateIp=" + updateIp + ", updateDate=" + updateDate
				+ ", versionNo=" + versionNo + ", withdrawAll=" + withdrawAll + ", cashoutChannel=" + cashoutChannel
				+ ", referPno=" + referPno + ", actualExchangeRate=" + actualExchangeRate + ", actualPayAmount="
				+ actualPayAmount + ", withdrewBankAccountName=" + withdrewBankAccountName + ", prevMatchedPno="
				+ prevMatchedPno + ", dailyCount=" + dailyCount + ", marginRatio=" + marginRatio + ", marginCredit="
				+ marginCredit + ", withdrewFilePath=" + withdrewFilePath + ", withdrewFileName=" + withdrewFileName
				+ ", env=" + env + "]";
	}

}