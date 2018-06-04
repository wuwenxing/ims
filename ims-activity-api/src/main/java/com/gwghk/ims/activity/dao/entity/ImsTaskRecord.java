package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 
 * 摘要：保存任务的完成记录
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月7日
 */
@Table(name="ims_task_record")
public class ImsTaskRecord extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	 * 任务设置主键ID
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
	 * 活动编号
	 */
	private String actNo ;
	/**
	 * 任务物品表主键ID
	 */
	private Long taskItemId ;
	/**
	 * 任务物品
	 */
	private String itemNo ;
	/**
	 * 物品种类
	 */
	private String itemType ;
	/**
	 * 任务编号
	 */
	private String taskCode ;
	/**
	 * 任务参数--手数
	 */
	private BigDecimal taskParamLot ;
	/**
	 * 任务参数--金额
	 */
	private BigDecimal taskParamAmount ;
	/**
	 * 任务完成手数
	 */
	private BigDecimal finishLot ;
	/**
	 * 任务完成金额（亏损金额）
	 */
	private BigDecimal finishAmount ;
	
	/**
	 * 任务完成次数
	 */
	private Integer finishCount;
	
	/**
	 * 任务发放金额
	 */
	private BigDecimal itemAmount ;
	/**
	 * 任务发放金额单位
	 */
	private String itemAmountUnit ;
	
	/**
	 * 要求交易手数
	 */
	private BigDecimal needTradeLot ;
	
	 /**
     * 等额价值（目前只有：模拟币）
     */
    private BigDecimal equalValue;
	
	/**
	 * 该任务是否发放记录 -1不可发放 0待发放 1到任务时间发放 2已发放
	 */
	private Integer isPrizeRecord ;
	/**
	 * 任务完成时间
	 */
	private Date taskFinishTime ;
	/**
	 * 发放记录时间，如果为空则为任务完成时间
	 */
	private Date taskRecordTime ;
	
	/**
	 * 标注活动是否结束了，表示不能在进行
	 */
	private boolean isTaskEnd ;
	
	/**
	 * 分组
	 */
	private Integer taskGroup;
	/**
	 * 账号类型 demo|real
	 */
	private String accountType ;
	
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
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public BigDecimal getTaskParamLot() {
		return taskParamLot;
	}
	public void setTaskParamLot(BigDecimal taskParamLot) {
		this.taskParamLot = taskParamLot;
	}
	public BigDecimal getTaskParamAmount() {
		return taskParamAmount;
	}
	public void setTaskParamAmount(BigDecimal taskParamAmount) {
		this.taskParamAmount = taskParamAmount;
	}
	public BigDecimal getFinishAmount() {
		return finishAmount;
	}
	public void setFinishAmount(BigDecimal finishAmount) {
		this.finishAmount = finishAmount;
	}
	public BigDecimal getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}
	public BigDecimal getFinishLot() {
		return finishLot ;
	}
	public void setFinishLot(BigDecimal finishLot) {
		this.finishLot = finishLot;
	}
	public String getItemAmountUnit() {
		return itemAmountUnit;
	}
	public void setItemAmountUnit(String itemAmountUnit) {
		this.itemAmountUnit = itemAmountUnit;
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
	public BigDecimal getNeedTradeLot() {
		return needTradeLot==null?new BigDecimal(0):needTradeLot;
	}
	public void setNeedTradeLot(BigDecimal needTradeLot) {
		this.needTradeLot = needTradeLot;
	}
	public BigDecimal getEqualValue() {
		return equalValue;
	}
	public void setEqualValue(BigDecimal equalValue) {
		this.equalValue = equalValue;
	}
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
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
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(Integer taskGroup) {
		this.taskGroup = taskGroup;
	}

	public Long getTaskItemId() {
		return taskItemId;
	}
	public void setTaskItemId(Long taskItemId) {
		this.taskItemId = taskItemId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(Integer finishCount) {
		this.finishCount = finishCount;
	}
	
}
