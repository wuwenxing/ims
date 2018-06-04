package com.gwghk.ims.activity.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class HxMt4BonusReqDto {
	@JSONField(name = "source")
	private String source;

	@JSONField(name = "ref_order")
	private String refOrder;

	@JSONField(name = "bonus_desc")
	private String bonusDesc;

	@JSONField(name = "login")
	private String login;

	@JSONField(name = "bonus_amount")
	private BigDecimal bonusAmount;

	@JSONField(name = "released_amount")
	private BigDecimal releasedAmount;

	@JSONField(name = "locked_amount")
	private BigDecimal lockedAmount;

	@JSONField(name = "expiry_date", format = "yyyy-MM-dd HH:mm:ss")
	private Date expiryDate;

	@JSONField(name = "is_auto_approve")
	private String isAutoApprove;

	@JSONField(name = "bounty_order")
	private String bountyOrder;

	@JSONField(name = "remark")
	private String remark;

	@JSONField(name = "sid")
	private String sid;

	@JSONField(name = "deducted_reason")
	private String deductedReason;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRefOrder() {
		return refOrder;
	}

	public void setRefOrder(String refOrder) {
		this.refOrder = refOrder;
	}

	public String getBonusDesc() {
		return bonusDesc;
	}

	public void setBonusDesc(String bonusDesc) {
		this.bonusDesc = bonusDesc;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public BigDecimal getReleasedAmount() {
		return releasedAmount;
	}

	public void setReleasedAmount(BigDecimal releasedAmount) {
		this.releasedAmount = releasedAmount;
	}

	public BigDecimal getLockedAmount() {
		return lockedAmount;
	}

	public void setLockedAmount(BigDecimal lockedAmount) {
		this.lockedAmount = lockedAmount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getIsAutoApprove() {
		return isAutoApprove;
	}

	public void setIsAutoApprove(String isAutoApprove) {
		this.isAutoApprove = isAutoApprove;
	}

	public String getBountyOrder() {
		return bountyOrder;
	}

	public void setBountyOrder(String bountyOrder) {
		this.bountyOrder = bountyOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDeductedReason() {
		return deductedReason;
	}

	public void setDeductedReason(String deductedReason) {
		this.deductedReason = deductedReason;
	}

}
