package com.gwghk.ims.message.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2CloneCashinProposal {
	/**
	 * 交易帳號
	 */
	private String accountNo;
	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 公司ID
	 */
	private Long companyId;

	/**
	 * 存款类型, 對應 cusTranType 100(存款), 101 (转账转入) ,102 (人工存款), 200 (取款), 201
	 * (转账转出), 299 (取消取款), 300 (额度调整), 301 (额度调整-存款调整 ),302 (额度调整-取款调整 ),303
	 * (额度调整-取消取款), 304(额度调整-手续费调整), 305(额度调整-系统清零调整 ),306 (额度调整-账号转款调整), 307(
	 * 额度调整-故障调整), 308 (额度调整-其他调整),350 (转移-仅存MT4总盈亏), 400 (手续费), 401 (存款手续费),
	 * 402 (取款手续费), 1001 (营销奖励送出 ),1002 (营销奖励扣回)
	 */
	private Integer cusTranType;
	/**
	 * 订单号
	 */
	private String pno;

	/**
	 * 入金金额
	 */
	private BigDecimal transAmount;
	/**
	 * 執行時間/審批時間
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date approveDate;

	/**
	 * 提案狀態
	 */
	private String proposalStatus;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getCusTranType() {
		return cusTranType;
	}

	public void setCusTranType(Integer cusTranType) {
		this.cusTranType = cusTranType;
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

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	@Override
	public String toString() {
		return "Gts2CloneCashinProposal [accountNo=" + accountNo + ", platform=" + platform + ", companyId=" + companyId
				+ ", cusTranType=" + cusTranType + ", pno=" + pno + ", transAmount=" + transAmount + ", approveDate="
				+ approveDate + ", proposalStatus=" + proposalStatus + "]";
	}

}
