package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import com.gwghk.ims.common.common.BaseEntity;

public class ActBlackListWrapper extends BaseEntity {
	
	private Long id;

	/**
	 * 交易账号
	 */
	private String accountNo;

	/**
	 * 手机账号
	 */
	private String mobile;

	private String platform;

	/**
	 * 真实还是模拟账号
	 */
	private String env;

	/**
	 * 活动编号
	 */
	private String activityPeriods;
	
	/**
	 * 活动名称
	 */
	private String activityName;
	
	
	/**
	 * 审批人
	 */
	private String approver;
	
	 /**
     * 审批状态
     */
    private String proposalStatus;
    
    /**
     * 审批时间
     */
    private Date approveDate;
    
    

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Override
	public String toString() {
		return "ActBlackList [id=" + id + ", accountNo=" + accountNo + ", mobile=" + mobile + ", platform=" + platform
				+ ", env=" + env + ", activityPeriods=" + activityPeriods + "]";
	}

}