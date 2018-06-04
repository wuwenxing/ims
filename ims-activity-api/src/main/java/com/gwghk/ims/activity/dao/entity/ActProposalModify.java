package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.common.BaseEntity;

public class ActProposalModify extends BaseEntity {

	private Long id;

	/**
	 * 活动编号
	 */
	private String activityPeriods;

	/**
	 * 取消原因
	 */
	private String cancelReason;

	private String pno;

	/**
	 * 案狀態
	 */
	private String proposalStatus;

	/**
	 * 判決提案是否自動審批
	 */
	private Boolean isAutoApprove;

	/**
	 * 提案人
	 */
	private String proposer;

	private String preApprover;

	/**
	 * 執行人/審批人
	 */
	private String approver;

	/**
	 * 取消人
	 */
	private String canceller;

	/**
	 * 提案時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date proposalDate;

	/**
	 * 待執行時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date preApproveDate;

	/**
	 * 執行時間/審批時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date approveDate;

	/**
	 * 取消時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date cancelDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
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
		this.proposer = proposer;
	}

	public String getPreApprover() {
		return preApprover;
	}

	public void setPreApprover(String preApprover) {
		this.preApprover = preApprover;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getCanceller() {
		return canceller;
	}

	public void setCanceller(String canceller) {
		this.canceller = canceller;
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

}