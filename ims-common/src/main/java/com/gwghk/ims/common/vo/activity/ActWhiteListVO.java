package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.vo.BaseVO;

public class ActWhiteListVO extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = 1383834344921413547L;

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
	 * 文件路径
	 */
	private String filePath ;
	/***
	 * 文件名
	 */
	private String fileName ;
	
	/**
	 * 审批人
	 */
	private String approver;
	
	 /**
     * 审批状态
     */
    private String proposalStatus;
    
    /**
     * 审批状态
     */
    private String proposalStatusTxt;
    
    
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@OrderBy(columnName="approve_date",propName="approveDate",order="desc")
    private Date approveDate;

	
	/**
     * 是否产生发放记录
     */
    private String isHasPrizeRecord;
    
    
    private String createDateStartTime;
    
    private String createDateEndTime;
 
	public String getCreateDateStartTime() {
		return createDateStartTime;
	}

	public void setCreateDateStartTime(String createDateStartTime) {
		this.createDateStartTime = createDateStartTime;
	}

	public String getCreateDateEndTime() {
		return createDateEndTime;
	}

	public void setCreateDateEndTime(String createDateEndTime) {
		this.createDateEndTime = createDateEndTime;
	}

	public String getProposalStatusTxt() {
		return proposalStatusTxt;
	}

	public void setProposalStatusTxt(String proposalStatusTxt) {
		this.proposalStatusTxt = proposalStatusTxt;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public String getIsHasPrizeRecord() {
		return isHasPrizeRecord;
	}

	public void setIsHasPrizeRecord(String isHasPrizeRecord) {
		this.isHasPrizeRecord = isHasPrizeRecord;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

}