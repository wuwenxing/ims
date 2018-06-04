package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.vo.BaseVO;

public class ActCashinRealVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 7853333330840689309L;

	private Long id;

	/**
	 * 账户号码
	 */
	private String accountNo;

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 订单号
	 */
	private String pno;
	/**
	 * 入金金额
	 */
	private BigDecimal transAmount;
	/**
	 * 订单时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@OrderBy(columnName = "approve_date", propName = "approveDate", order = "desc")
	private Date approveDate;

	/**
	 * 是否首次入金
	 */
	private String firstDeposit;

	/**
	 * 入金开始时间
	 */
	private String startApproveDate;

	/**
	 * 入金结束时间
	 */
	private String endApproveDate;

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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getFirstDeposit() {
		return firstDeposit;
	}

	public void setFirstDeposit(String firstDeposit) {
		this.firstDeposit = firstDeposit;
	}

	public String getStartApproveDate() {
		return startApproveDate;
	}

	public void setStartApproveDate(String startApproveDate) {
		this.startApproveDate = startApproveDate;
	}

	public String getEndApproveDate() {
		return endApproveDate;
	}

	public void setEndApproveDate(String endApproveDate) {
		this.endApproveDate = endApproveDate;
	}
}