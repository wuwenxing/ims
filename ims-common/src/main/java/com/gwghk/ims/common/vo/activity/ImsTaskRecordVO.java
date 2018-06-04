package com.gwghk.ims.common.vo.activity;

import java.math.BigDecimal;
import java.util.Date;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 
 * 摘要：保存任务的完成记录
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月7日
 */
public class ImsTaskRecordVO extends BaseVO{
	private static final long serialVersionUID = 1L;
	
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	private String actNo;
	
	private Integer id;
	/**
	 * 账号
	 */
	private String accountNo ;
	/**
	 * 平台
	 */
	private String platform ;
	/**
	 * 任务设置ID
	 */
	private Integer actTaskSettingId ;
	/**
	 * 子任务ID
	 */
	private Integer subTaskSettingId ;
	/**
	 * 最后一个子任务
	 */
	private Boolean lastSubTask ;
	/**
	 * 物品编号
	 */
	private String itemNo ;
	/**
	 * 任务参数--手数
	 */
	private Integer taskParamLot ;
	/**
	 * 任务参数--金额
	 */
	private BigDecimal taskParamAmount ;
	/**
	 * 任务完成手数
	 */
	private Integer finishLot ;
	/**
	 * 任务完成金额（亏损金额）
	 */
	private BigDecimal finishAmount ;
	/**
	 * 任务发放金额
	 */
	private BigDecimal paymentAmount ;
	
	/**
	 * 该任务是否发放记录 -1不可发放 0未发放 1需更新任务 2已发放
	 */
	private Integer isPrizeRecord ;
	/**
	 * 任务完成时间
	 */
	@OrderBy(columnName="task_finish_time",propName="taskFinishTime",order="desc")
	private Date taskFinishTime ;
	
	
	/***
	 * 分组序号，用于层级型任务
	 */
	private Integer taskGroup;
	
	/**
	 * 发放记录时间，如果为空则为任务完成时间
	 */
	private Date taskRecordTime ;
	
	
	/**
     * 任务完成开始时间-条件
     */
    private String startTimeStr;
    /**
     * 任务完成结束时间-条件
     */
    private String endTimeStr;
	
	/**
	 * 标注活动是否结束了，表示不能在进行
	 */
	private boolean isTaskEnd ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Integer getActTaskSettingId() {
		return actTaskSettingId;
	}
	public void setActTaskSettingId(Integer actTaskSettingId) {
		this.actTaskSettingId = actTaskSettingId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getTaskParamLot() {
		return taskParamLot;
	}
	public void setTaskParamLot(Integer taskParamLot) {
		this.taskParamLot = taskParamLot;
	}
	public BigDecimal getTaskParamAmount() {
		return taskParamAmount;
	}
	public void setTaskParamAmount(BigDecimal taskParamAmount) {
		this.taskParamAmount = taskParamAmount;
	}
	public Integer getFinishLot() {
		return finishLot;
	}
	public void setFinishLot(Integer finishLot) {
		this.finishLot = finishLot;
	}
	public BigDecimal getFinishAmount() {
		return finishAmount;
	}
	public void setFinishAmount(BigDecimal finishAmount) {
		this.finishAmount = finishAmount;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getIsPrizeRecord() {
		return isPrizeRecord;
	}
	public void setIsPrizeRecord(Integer isPrizeRecord) {
		this.isPrizeRecord = isPrizeRecord;
	}
	public Date getTaskFinishTime() {
		return taskFinishTime;
	}
	public void setTaskFinishTime(Date taskFinishTime) {
		this.taskFinishTime = taskFinishTime;
	}
	public boolean isTaskEnd() {
		return isTaskEnd;
	}
	public void setTaskEnd(boolean isTaskEnd) {
		this.isTaskEnd = isTaskEnd;
	}
	public Integer getSubTaskSettingId() {
		return subTaskSettingId;
	}
	public void setSubTaskSettingId(Integer subTaskSettingId) {
		this.subTaskSettingId = subTaskSettingId;
	}
	public Boolean getLastSubTask() {
		return lastSubTask;
	}
	public void setLastSubTask(Boolean lastSubTask) {
		this.lastSubTask = lastSubTask;
	}
	public Date getTaskRecordTime() {
		return taskRecordTime;
	}
	public void setTaskRecordTime(Date taskRecordTime) {
		this.taskRecordTime = taskRecordTime;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Integer getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(Integer taskGroup) {
		this.taskGroup = taskGroup;
	}
	
}
