package com.gwghk.ims.common.dto.activity;

import java.math.BigDecimal;

/**
 * 
 * @ClassName: KafkaActPrizeBaseData
 * @Description: kafka消息发送主题(提供给各个BU结算中心使用)
 * @author lawrence
 * @date 2017年7月25日
 *
 */
public class ActPrizeBaseReqDTO {
	
	/** 公司Id */
    private Long companyId;
    /** 平台 */
    private String platform;
    /** 账号资料 */
    private String accountNo;
    /** 物品名称*/
    private String giftName;
    /** 物品类型(话费、流量、会员、赠金、代币、模拟币) **/
    private String giftType;
    /** 物品数量 */
    private BigDecimal giftAmount;
//    /** 可否取款 */
//    private boolean withdraw;
    /** 活动编号 **/
    private String activityPeriods;
    /** 活动名称 **/
    private String activityName;
    /** 流水号  **/
    private String recordNo;
    
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
//	public boolean isWithdraw() {
//		return withdraw;
//	}
//	public void setWithdraw(boolean withdraw) {
//		this.withdraw = withdraw;
//	}
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
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	
}
