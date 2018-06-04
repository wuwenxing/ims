package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.common.BaseEntity;

public class ActWhiteList extends BaseEntity {
	
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

}