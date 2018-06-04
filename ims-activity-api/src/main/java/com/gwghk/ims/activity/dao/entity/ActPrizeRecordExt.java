package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：活动物品记录对象扩展表
 * 
 * @version 1.0
 * @Date 2017年5月12日
 */
public class ActPrizeRecordExt extends BaseEntity {
	private Long id;

	/**
	 * 发放流水号
	 */
	private String recordNumber;

	/**
	 * 关联的订单流水号(争对重发)
	 */
	private String refRecordNumber;

	/**
	 * 第三方处理的流水号
	 */
	private String dealNumber;

	/**
	 * 外键,对应act_rw_task_items,act_wpdh_task_items表的id
	 */
	private Long taskItemsId;

	/**
	 * 发放到BU结算中心开始时间
	 */
	private Date dealStartTime;

	/**
	 * BU结算中心返回结果信息结束时间
	 */
	private Date dealEndTime;

	/**
	 * BU结算中心处理时间
	 */
	private Date finishTime;

	/**
	 * 用户的预期结算时间
	 */
	private Date settlementTime;

	/**
	 * ---上述的在ActPrizeRecord中都可以清掉
	 * 
	 */
	/**
	 * 发放记录的所属任务所属层级
	 */
	private Integer taskGroup;

	/**
	 * 发放记录的所属任务id(act_task_setting表外健)
	 */
	private Long taskId;

	/**
	 * 需要交易手数（对赠金的任务)需留
	 */
	private BigDecimal needTradeLots;

	/**
	 * 已完成的交易手数（对赠金的任务)需留
	 */
	private BigDecimal finishedTradeLots;

	/**
	 * 领取时的完成任务的交易id
	 */
	private Long receiveTradeId;

	/**
	 * 领取时的完成任务的时间点
	 */
	private Date receiveFinishTime;

	/**
	 * 开始记录完成手数的开始交易id
	 */
	private Long withdrawalStartTradeId;

	/**
	 * 开始记录完成手数的开始时间
	 */
	private Date withdrawalStartTime;

	/**
	 * 是否释放该笔贈金(当完成手数小于要求完成手数:0,否则为1(结算完成时，也会设置为1))
	 */
	private Integer releaseFinish;

	/**
	 * 第三方处理的流水号
	 */
	private List<String> dealNumbers;

	/**
	 * 释放类型：1：表示立即，2:表示要达到手数后才能可取
	 */
	private Integer releaseType;

	/**
	 * 完成所有手数的交易id
	 */
	private Long finishedLotsTradeId;
	/**
	 * 完成所有手数的交易时间
	 */
	private Date finishedLotsTradeTime;
	/**
	 * 已释放的赠金
	 */
	private BigDecimal releasedBonus;

	/**
	 * 订单合集
	 */
	private List<String> recordNumbers;

	/**
	 * 组内是否释放的标识
	 */
	private Integer groupRelease;

	private String result;

	/**
	 * 完成手数累计达到的交易id，用来判断结算前该笔记录是否已经释放完
	 */
	private Long cumulativeTradeId;
	/**
	 * 用来确定表
	 */
	private String actNo;

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

	public String getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}

	public Long getTaskItemsId() {
		return taskItemsId;
	}

	public void setTaskItemsId(Long taskItemsId) {
		this.taskItemsId = taskItemsId;
	}

	public Date getDealStartTime() {
		return dealStartTime;
	}

	public void setDealStartTime(Date dealStartTime) {
		this.dealStartTime = dealStartTime;
	}

	public Date getDealEndTime() {
		return dealEndTime;
	}

	public void setDealEndTime(Date dealEndTime) {
		this.dealEndTime = dealEndTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Integer getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(Integer taskGroup) {
		this.taskGroup = taskGroup;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

	public Long getReceiveTradeId() {
		return receiveTradeId;
	}

	public void setReceiveTradeId(Long receiveTradeId) {
		this.receiveTradeId = receiveTradeId;
	}

	public Date getReceiveFinishTime() {
		return receiveFinishTime;
	}

	public void setReceiveFinishTime(Date receiveFinishTime) {
		this.receiveFinishTime = receiveFinishTime;
	}

	public String getRefRecordNumber() {
		return refRecordNumber;
	}

	public void setRefRecordNumber(String refRecordNumber) {
		this.refRecordNumber = refRecordNumber;
	}

	public Integer getReleaseFinish() {
		return releaseFinish;
	}

	public void setReleaseFinish(Integer releaseFinish) {
		this.releaseFinish = releaseFinish;
	}

	public List<String> getDealNumbers() {
		return dealNumbers;
	}

	public void setDealNumbers(List<String> dealNumbers) {
		this.dealNumbers = dealNumbers;
	}

	public Integer getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(Integer releaseType) {
		this.releaseType = releaseType;
	}

	public Long getFinishedLotsTradeId() {
		return finishedLotsTradeId;
	}

	public void setFinishedLotsTradeId(Long finishedLotsTradeId) {
		this.finishedLotsTradeId = finishedLotsTradeId;
	}

	public Date getFinishedLotsTradeTime() {
		return finishedLotsTradeTime;
	}

	public void setFinishedLotsTradeTime(Date finishedLotsTradeTime) {
		this.finishedLotsTradeTime = finishedLotsTradeTime;
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

	public BigDecimal getReleasedBonus() {
		return releasedBonus;
	}

	public void setReleasedBonus(BigDecimal releasedBonus) {
		this.releasedBonus = releasedBonus;
	}

	public List<String> getRecordNumbers() {
		return recordNumbers;
	}

	public void setRecordNumbers(List<String> recordNumbers) {
		this.recordNumbers = recordNumbers;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getGroupRelease() {
		return groupRelease;
	}

	public void setGroupRelease(Integer groupRelease) {
		this.groupRelease = groupRelease;
	}

	public Long getCumulativeTradeId() {
		return cumulativeTradeId;
	}

	public void setCumulativeTradeId(Long cumulativeTradeId) {
		this.cumulativeTradeId = cumulativeTradeId;
	}

	public void setActNo(String actNo) {
		this.actNo=actNo;
	}

	public String getActNo() {
		return actNo;
	}

}