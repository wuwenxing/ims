package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName: ActPrizeShortData
 * @Description: 调用api时，发放记录快照
 * @author lawrence
 * @date 2017年11月15日
 *
 */
public class ActPrizeShortData {
	private Long id;
	/**
	 * 发放流水号(程序产生)
	 */
	private String recordNumber;

	/**
	 * 关联的订单流水号(针对重发)
	 */
	private String refRecordNumber;

	/**
	 * 第三方流水号
	 */
	private String dealNumber;
	/**
	 * 客户所属平台
	 */
	private String guestPlatForm;

	/**
	 * 平台账号
	 */
	private String accountNo;

	/**
	 * 发放额度
	 */
	private BigDecimal giftAmount;

	/**
	 * 真实还是模拟账号
	 */
	private String env;

	/**
	 * 需要交易手数（对赠金的任务)需留
	 */
	private BigDecimal needTradeLots;

	/**
	 * 已完成的交易手数（对赠金的任务)需留
	 */
	private BigDecimal finishedTradeLots;

	/**
	 * 多出的订单号(一般是主从关系的订单使用)
	 */
	private String otherRecordNumber;

	/**
	 * 多出的订单号(一般是主从关系的订单使用,第三方返回的订单号)
	 */
	private String otherDealNumber;

	/** 公司Id */
	private Long companyId;

	private String companyCode;

	/**
	 * 开始记录完成手数的开始交易id
	 */
	private Long withdrawalStartTradeId;

	/**
	 * 开始记录完成手数的开始时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date withdrawalStartTime;

	/**
	 * 结算时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date settlementTime;

	/**
	 * 释放类型：1：表示立即，2:表示要达到手数后才能可取
	 */
	private Integer releaseType;

	/**
	 * 活动编号
	 */
	private String activityPeriods;

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 贈金类型
	 */
	private String giftType;
	
	/**
	 * 绑定关联账号
	 */
	private String refAccountNo;
	
	/**
	 * 物品可使用-结束时间(用于发放奖品后的结束使用时间)
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date useEndTime;
	
	/**
     * 是否允许转组 (0:不允许,1：允许)
     */
    private Integer turnGroup;
    /**
     * 是否允许转账 (0:不允许,1：允许)
     */
    private Integer transfer;
    
    private BigDecimal exchangeRate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getRefRecordNumber() {
		return refRecordNumber;
	}

	public void setRefRecordNumber(String refRecordNumber) {
		this.refRecordNumber = refRecordNumber;
	}

	public String getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public BigDecimal getNeedTradeLots() {
		return needTradeLots;
	}

	public void setNeedTradeLots(BigDecimal needTradeLots) {
		this.needTradeLots = needTradeLots;
	}

	public BigDecimal getFinishedTradeLots() {
		return finishedTradeLots;
	}

	public void setFinishedTradeLots(BigDecimal finishedTradeLots) {
		this.finishedTradeLots = finishedTradeLots;
	}

	public String getOtherRecordNumber() {
		return otherRecordNumber;
	}

	public void setOtherRecordNumber(String otherRecordNumber) {
		this.otherRecordNumber = otherRecordNumber;
	}

	public String getOtherDealNumber() {
		return otherDealNumber;
	}

	public void setOtherDealNumber(String otherDealNumber) {
		this.otherDealNumber = otherDealNumber;
	}

	public String getGuestPlatForm() {
		return guestPlatForm;
	}

	public void setGuestPlatForm(String guestPlatForm) {
		this.guestPlatForm = guestPlatForm;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Long getWithdrawalStartTradeId() {
		return withdrawalStartTradeId;
	}

	public void setWithdrawalStartTradeId(Long withdrawalStartTradeId) {
		this.withdrawalStartTradeId = withdrawalStartTradeId;
	}

	public Date getWithdrawalStartTime() {
		return withdrawalStartTime;
	}

	public void setWithdrawalStartTime(Date withdrawalStartTime) {
		this.withdrawalStartTime = withdrawalStartTime;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Integer getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(Integer releaseType) {
		this.releaseType = releaseType;
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

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getRefAccountNo() {
		return refAccountNo;
	}

	public void setRefAccountNo(String refAccountNo) {
		this.refAccountNo = refAccountNo;
	}

	public Date getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(Date useEndTime) {
		this.useEndTime = useEndTime;
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

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

}