package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: BonusReqDto
 * @Description: 贈金对象
 * @author lawrence
 * @date 2018年1月8日
 *
 */
public class BonusReqDTO implements Serializable {

	private static final long serialVersionUID = -1615809776479624907L;
	/**
	 * 提案來源
	 */
	private String source;
	/**
	 * 订单号
	 */
	private String recordNumber;
	/**
	 * 第三方提案號, 不能重覆(新增时或者扣除贈金时，这个值可与recordNumber相等，但是释放时，必须是唯一订单号)
	 */
	private String refId;
	
	/**
	 * 獎勵提案號, 由新增營銷獎勵接口獲得(释放或者扣除时，此订单号必填)
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
	 * 釋放原因
	 */
	private String remark;

	/**
	 * 取消原因
	 */
	private String cancelReason;

	/**
	 * 公司ID
	 */
	private Long companyId;
	/**
	 * 活动编号 
	 */
	private String ActivityPeriods;
	
    /**
     * 是否允许转组 (0:不允许,1：允许)
     */
    private Integer turnGroup;
    /**
     * 是否允许转账 (0:不允许,1：允许)
     */
    private Integer transfer;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getActivityPeriods() {
		return ActivityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		ActivityPeriods = activityPeriods;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Integer getTurnGroup() {
		return turnGroup;
	}

	public void setTurnGroup(Integer turnGroup) {
		this.turnGroup = turnGroup;
	}

	public Integer getTransfer() {
		return transfer;
	}

	public void setTransfer(Integer transfer) {
		this.transfer = transfer;
	}

}
