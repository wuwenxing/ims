package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2CashinProposal {
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
	private Date jointime;

	private String accountCurrency;

	private Double payAmount;

	private String payCurrency;

	private Double transAmount;

	private String transCurrency;

	private Double exchangeRate;

	private String gatewayCode;

	private String bankPayName;

	private String issueBankName;

	private String bankPayAccount;

	private String bankPayMethod;

	private String chequeNo;

	private String lockWithdrawal;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date unlockWithdrawalTime;

	private Double fee;

	private Double depositFee;

	private String informationFrom;

	private String remark;

	private String depositBankname;

	private String depositBankAccount;

	private String depositBankAccounttype;

	private String depositNationalTransfercode;

	private String depositBankAddress;

	private String depositBankCountry;

	private String depositProvince;

	private String depositBankCity;

	private String depositBankBranch;

	private String depositType;

	private Boolean flagWithdraw;

	private String flagWithdrawUpdateUser;

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

	private String cashinChannel;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date flagWithdrawUpdateDate;

	private String gatewayCurrency;

	private String referPno;

	private String status;

	private String depositFilePath;

	private String depositFileName;

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

	public Date getJointime() {
		return jointime;
	}

	public void setJointime(Date jointime) {
		this.jointime = jointime;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency == null
				? null
				: accountCurrency.trim();
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
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
		this.transCurrency = transCurrency == null
				? null
				: transCurrency.trim();
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(String gatewayCode) {
		this.gatewayCode = gatewayCode == null ? null : gatewayCode.trim();
	}

	public String getBankPayName() {
		return bankPayName;
	}

	public void setBankPayName(String bankPayName) {
		this.bankPayName = bankPayName == null ? null : bankPayName.trim();
	}

	public String getIssueBankName() {
		return issueBankName;
	}

	public void setIssueBankName(String issueBankName) {
		this.issueBankName = issueBankName == null
				? null
				: issueBankName.trim();
	}

	public String getBankPayAccount() {
		return bankPayAccount;
	}

	public void setBankPayAccount(String bankPayAccount) {
		this.bankPayAccount = bankPayAccount == null
				? null
				: bankPayAccount.trim();
	}

	public String getBankPayMethod() {
		return bankPayMethod;
	}

	public void setBankPayMethod(String bankPayMethod) {
		this.bankPayMethod = bankPayMethod == null
				? null
				: bankPayMethod.trim();
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo == null ? null : chequeNo.trim();
	}

	public String getLockWithdrawal() {
		return lockWithdrawal;
	}

	public void setLockWithdrawal(String lockWithdrawal) {
		this.lockWithdrawal = lockWithdrawal == null
				? null
				: lockWithdrawal.trim();
	}

	public Date getUnlockWithdrawalTime() {
		return unlockWithdrawalTime;
	}

	public void setUnlockWithdrawalTime(Date unlockWithdrawalTime) {
		this.unlockWithdrawalTime = unlockWithdrawalTime;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(Double depositFee) {
		this.depositFee = depositFee;
	}

	public String getInformationFrom() {
		return informationFrom;
	}

	public void setInformationFrom(String informationFrom) {
		this.informationFrom = informationFrom == null
				? null
				: informationFrom.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getDepositBankname() {
		return depositBankname;
	}

	public void setDepositBankname(String depositBankname) {
		this.depositBankname = depositBankname == null
				? null
				: depositBankname.trim();
	}

	public String getDepositBankAccount() {
		return depositBankAccount;
	}

	public void setDepositBankAccount(String depositBankAccount) {
		this.depositBankAccount = depositBankAccount == null
				? null
				: depositBankAccount.trim();
	}

	public String getDepositBankAccounttype() {
		return depositBankAccounttype;
	}

	public void setDepositBankAccounttype(String depositBankAccounttype) {
		this.depositBankAccounttype = depositBankAccounttype == null
				? null
				: depositBankAccounttype.trim();
	}

	public String getDepositNationalTransfercode() {
		return depositNationalTransfercode;
	}

	public void setDepositNationalTransfercode(
			String depositNationalTransfercode) {
		this.depositNationalTransfercode = depositNationalTransfercode == null
				? null
				: depositNationalTransfercode.trim();
	}

	public String getDepositBankAddress() {
		return depositBankAddress;
	}

	public void setDepositBankAddress(String depositBankAddress) {
		this.depositBankAddress = depositBankAddress == null
				? null
				: depositBankAddress.trim();
	}

	public String getDepositBankCountry() {
		return depositBankCountry;
	}

	public void setDepositBankCountry(String depositBankCountry) {
		this.depositBankCountry = depositBankCountry == null
				? null
				: depositBankCountry.trim();
	}

	public String getDepositProvince() {
		return depositProvince;
	}

	public void setDepositProvince(String depositProvince) {
		this.depositProvince = depositProvince == null
				? null
				: depositProvince.trim();
	}

	public String getDepositBankCity() {
		return depositBankCity;
	}

	public void setDepositBankCity(String depositBankCity) {
		this.depositBankCity = depositBankCity == null
				? null
				: depositBankCity.trim();
	}

	public String getDepositBankBranch() {
		return depositBankBranch;
	}

	public void setDepositBankBranch(String depositBankBranch) {
		this.depositBankBranch = depositBankBranch == null
				? null
				: depositBankBranch.trim();
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType == null ? null : depositType.trim();
	}

	public Boolean getFlagWithdraw() {
		return flagWithdraw;
	}

	public void setFlagWithdraw(Boolean flagWithdraw) {
		this.flagWithdraw = flagWithdraw;
	}

	public String getFlagWithdrawUpdateUser() {
		return flagWithdrawUpdateUser;
	}

	public void setFlagWithdrawUpdateUser(String flagWithdrawUpdateUser) {
		this.flagWithdrawUpdateUser = flagWithdrawUpdateUser == null
				? null
				: flagWithdrawUpdateUser.trim();
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
		this.proposalStatus = proposalStatus == null
				? null
				: proposalStatus.trim();
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

	public String getCashinChannel() {
		return cashinChannel;
	}

	public void setCashinChannel(String cashinChannel) {
		this.cashinChannel = cashinChannel == null
				? null
				: cashinChannel.trim();
	}

	public Date getFlagWithdrawUpdateDate() {
		return flagWithdrawUpdateDate;
	}

	public void setFlagWithdrawUpdateDate(Date flagWithdrawUpdateDate) {
		this.flagWithdrawUpdateDate = flagWithdrawUpdateDate;
	}

	public String getGatewayCurrency() {
		return gatewayCurrency;
	}

	public void setGatewayCurrency(String gatewayCurrency) {
		this.gatewayCurrency = gatewayCurrency == null
				? null
				: gatewayCurrency.trim();
	}

	public String getReferPno() {
		return referPno;
	}

	public void setReferPno(String referPno) {
		this.referPno = referPno == null ? null : referPno.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepositFilePath() {
		return depositFilePath;
	}

	public void setDepositFilePath(String depositFilePath) {
		this.depositFilePath = depositFilePath;
	}

	public String getDepositFileName() {
		return depositFileName;
	}

	public void setDepositFileName(String depositFileName) {
		this.depositFileName = depositFileName;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}