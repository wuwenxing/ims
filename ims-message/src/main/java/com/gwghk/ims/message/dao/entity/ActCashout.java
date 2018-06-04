package com.gwghk.ims.message.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: ActCashout
 * @Description: 用户出金记录
 * @author lawrence
 * @date 2017年6月6日
 *
 */
public class ActCashout extends BaseEntity {

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
	 * 出金金额
	 */
	private BigDecimal transAmount;
	/**
	 * 订单时间
	 */
	private Date approveDate;

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

}
