package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.dto.BaseDTO;

public class ActSettingDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1870473190680050362L;

	private Long id;

	/**
	 * 活动编号
	 */
	private String activityPeriods;

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 活动类型(rw:人物型, wpdh:物品兑换型, qt:其它活动类型)
	 */
	private String activityType;

	/**
	 * 活动类型-界面显示
	 */
	private String activityTypeText;
	
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	/**
	 * 活动完成天数
	 */
	private Integer finishDays;
	
	 
	/**
	 * 活动完成天数单位
	 */
    private String finishDaysUnit;

	/**
	 * 赠金有效期
	 */
	private Integer withGoldDays;
	
 
	
	/**
	 * 赠金有效期单位
	 */
    private String withGoldDaysUnit;

	/**
	 * 代币有效期
	 */
	private Integer coinDays;
	
	
	/**
	 * 代币有效期单位
	 */
    private String coinDaysUnit;
    
	/**
	 * demo账号保留余额
	 */
	private Double demoKeepAmount;
	
	/**
	 * demo账号保留余额单位
	 */
    private String demoKeepAmountUnit;

	/**
	 * 最高兑换次数
	 */
	private Integer maxExchangeCount;
	
	  /**
		 * 最高兑换次数单位
		 */
	private String maxExchangeCountUnit;

	/**
	 * 应用位置(第三方key)
	 */
	private String position;

	/**
	 * 活动url
	 */
	private String activityUrl;

	/**
	 * 活动图片
	 */
	private String activityImg;

	/**
	 * 默认0 （0：不登录 1：自动登录）
	 */
	private Integer islogin;

	/**
	 * 是否自动分发;0:非自动,1:自动
	 */
	private Integer autoHandOut;

	/**
	 * 奖励优先发放平台
	 */
	private String priorityPlatform;

	/**
	 * 自动登录jsname
	 */
	private String loginjsFunctionName;

	/**
	 * 其他信息或备注
	 */
	private String otherMsg;

	/**
	 * 0:不需要特殊设置 1特殊设置
	 */
	private String special;

	/**
	 * 提案号
	 */
	private String pno;

	/**
	 * 提案人
	 */
	private String proposer;

	/**
	 * 提案时间
	 */
	private Date proposalDate;

	/**
	 * 提案状态
	 */
	private String proposalStatus;

	/**
	 * 审批人
	 */
	private String approver;

	/**
	 * 审批时间
	 */
	private Date approveDate;

	/**
	 * 取消审批人
	 */
	private String canceller;

	/**
	 * 取消审批时间
	 */
	private Date cancelDate;

	/**
	 * 取消审批原因
	 */
	private String cancelReason;

	private Boolean isSettlement;

	/**
	 * 活动真正结算完时间
	 */
	private Date settlementDate;
	
	private String enableFlagName;
	
	private String tagCode;
	
	private String tagName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityTypeText() {
		return activityTypeText;
	}

	public void setActivityTypeText(String activityTypeText) {
		this.activityTypeText = activityTypeText;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getFinishDays() {
		return finishDays;
	}

	public void setFinishDays(Integer finishDays) {
		this.finishDays = finishDays;
	}

	public Integer getWithGoldDays() {
		return withGoldDays;
	}

	public void setWithGoldDays(Integer withGoldDays) {
		this.withGoldDays = withGoldDays;
	}

	public Integer getCoinDays() {
		return coinDays;
	}

	public void setCoinDays(Integer coinDays) {
		this.coinDays = coinDays;
	}

	public Double getDemoKeepAmount() {
		return demoKeepAmount;
	}

	public void setDemoKeepAmount(Double demoKeepAmount) {
		this.demoKeepAmount = demoKeepAmount;
	}

	public Integer getMaxExchangeCount() {
		return maxExchangeCount;
	}

	public void setMaxExchangeCount(Integer maxExchangeCount) {
		this.maxExchangeCount = maxExchangeCount;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getActivityUrl() {
		return activityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	public String getActivityImg() {
		return activityImg;
	}

	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}

	public Integer getIslogin() {
		return islogin;
	}

	public void setIslogin(Integer islogin) {
		this.islogin = islogin;
	}

	public Integer getAutoHandOut() {
		return autoHandOut;
	}

	public void setAutoHandOut(Integer autoHandOut) {
		this.autoHandOut = autoHandOut;
	}

	public String getPriorityPlatform() {
		return priorityPlatform;
	}

	public void setPriorityPlatform(String priorityPlatform) {
		this.priorityPlatform = priorityPlatform;
	}

	public String getLoginjsFunctionName() {
		return loginjsFunctionName;
	}

	public void setLoginjsFunctionName(String loginjsFunctionName) {
		this.loginjsFunctionName = loginjsFunctionName;
	}

	public String getOtherMsg() {
		return otherMsg;
	}

	public void setOtherMsg(String otherMsg) {
		this.otherMsg = otherMsg;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public Date getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(Date proposalDate) {
		this.proposalDate = proposalDate;
	}

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getCanceller() {
		return canceller;
	}

	public void setCanceller(String canceller) {
		this.canceller = canceller;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Boolean getIsSettlement() {
		return isSettlement;
	}

	public void setIsSettlement(Boolean isSettlement) {
		this.isSettlement = isSettlement;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getFinishDaysUnit() {
		return finishDaysUnit;
	}

	public void setFinishDaysUnit(String finishDaysUnit) {
		this.finishDaysUnit = finishDaysUnit;
	}

	public String getWithGoldDaysUnit() {
		return withGoldDaysUnit;
	}

	public void setWithGoldDaysUnit(String withGoldDaysUnit) {
		this.withGoldDaysUnit = withGoldDaysUnit;
	}

	public String getCoinDaysUnit() {
		return coinDaysUnit;
	}

	public void setCoinDaysUnit(String coinDaysUnit) {
		this.coinDaysUnit = coinDaysUnit;
	}

	public String getDemoKeepAmountUnit() {
		return demoKeepAmountUnit;
	}

	public void setDemoKeepAmountUnit(String demoKeepAmountUnit) {
		this.demoKeepAmountUnit = demoKeepAmountUnit;
	}

	public String getMaxExchangeCountUnit() {
		return maxExchangeCountUnit;
	}

	public void setMaxExchangeCountUnit(String maxExchangeCountUnit) {
		this.maxExchangeCountUnit = maxExchangeCountUnit;
	}

	public String getEnableFlagName() {
		return enableFlagName;
	}

	public void setEnableFlagName(String enableFlagName) {
		this.enableFlagName = enableFlagName;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}