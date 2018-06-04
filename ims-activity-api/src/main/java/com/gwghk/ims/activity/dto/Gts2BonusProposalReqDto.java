package com.gwghk.ims.activity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: Gts2BonusProposalReqDto
 * @Description: 调用Gts2贈金接口参数
 * @author lawrence
 * @date 2017年10月19日
 *
 */
public class Gts2BonusProposalReqDto implements Serializable {

	private static final long serialVersionUID = -1615809776479624907L;
	/**
	 * 提案來源
	 */
	private String source;
	/**
	 * 第三方提案號, 不能重覆
	 */
	private String refId;
	/**
	 * 獎勵提案號, 由新增營銷獎勵接口獲得
	 */
	private String bonusPno;
	/**
	 * 獎勵描述
	 */
	private String bonusDesc;
	/**
	 * 平台账号
	 */
	private String accountNo;
	/**
	 * 平台类型
	 */
	private String platform;
	/**
	 * 獎勵總金額, 必須為releasedAmount及lockedAmount之和
	 */
	private BigDecimal bonusAmount;
	/**
	 * 已釋放金額, 不能大於bonusAmount
	 */
	private BigDecimal releasedAmount;
	/**
	 * 未釋放金額
	 */
	private BigDecimal lockedAmount;
	/**
	 * 到期時間, 如註明到期時間則當超過此時間系統會自動取消此筆獎勵
	 */
	private Date expiryDate;
	/**
	 * 是否容許客戶取款, 預設為否
	 */
	private Boolean allowWithdraw;
	/**
	 * 是否容許客戶轉賬, 預設為否
	 */
	private Boolean allowTransfer;
	/**
	 * 是否容許客戶轉客戶組, 預設為否
	 */
	private Boolean allowChangeAccountGroup;
	/**
	 * 是否自動審批, 預設為否. 非自動審批的提案需到GTS2後台人手審批
	 */
	private Boolean isAutoApprove;
	/**
	 * 釋放原因
	 */
	private String remark;
	
	/**
	 * 取消原因
	 */
	private String cancelReason;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getBonusDesc() {
		return bonusDesc;
	}
	public void setBonusDesc(String bonusDesc) {
		this.bonusDesc = bonusDesc;
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
	public Boolean getAllowWithdraw() {
		return allowWithdraw;
	}
	public void setAllowWithdraw(Boolean allowWithdraw) {
		this.allowWithdraw = allowWithdraw;
	}
	public Boolean getAllowTransfer() {
		return allowTransfer;
	}
	public void setAllowTransfer(Boolean allowTransfer) {
		this.allowTransfer = allowTransfer;
	}
	public Boolean getAllowChangeAccountGroup() {
		return allowChangeAccountGroup;
	}
	public void setAllowChangeAccountGroup(Boolean allowChangeAccountGroup) {
		this.allowChangeAccountGroup = allowChangeAccountGroup;
	}
	public Boolean getIsAutoApprove() {
		return isAutoApprove;
	}
	public void setIsAutoApprove(Boolean isAutoApprove) {
		this.isAutoApprove = isAutoApprove;
	}
	public String getBonusPno() {
		return bonusPno;
	}
	public void setBonusPno(String bonusPno) {
		this.bonusPno = bonusPno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}
