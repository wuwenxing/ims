package com.gwghk.ims.datacleaning.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: ActCashin
 * @Description: 用户入金记录
 * @author lawrence
 * @date 2017年6月6日
 *
 */
public class ActCashin extends BaseEntity {

	/**
	 * ID
	 */
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
	private Date approveDate;
	
	/**
	 * 是否首次入金成功
	 */
	private String firstDeposit;
	
	/**
	 * 来源(0:GTS2库，1:旧库)
	 */
	private String source;
	

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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	
}
