package com.gwghk.ims.common.dto.job;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 摘要：活动结算请求对象
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年9月19日
 */
public class ActSettleReqDTO implements Serializable {
	private static final long serialVersionUID = -2500698815200429127L;

	/** 账号 */
	private String accountNo;

	/** 平台 */
	private String platform;

	/** 账户类型：real或demo */
	private String env;

	/** 物品名称 */
	private String giftName;

	/** 物品类型(话费、流量、会员、赠金、代币、模拟币) **/
	private String giftType;

	/** 物品数量 */
	private BigDecimal giftAmount;

	/** 活动编号 **/
	private String activityPeriods;

	/** 活动名称 **/
	private String activityName;

	/** 流水号 **/
	private String recordNo;

	/** 公司Id */
	private Long companyId;

	/** 判断是否是手动结算 **/
	private boolean manual;

	private Date approveDate;
	
	private boolean withDemo;
	
	private String type;

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public boolean getManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public boolean isWithDemo() {
		return withDemo;
	}

	public void setWithDemo(boolean withDemo) {
		this.withDemo = withDemo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}