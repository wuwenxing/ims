package com.gwghk.ims.common.dto.mall;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.dto.BaseDTO;
import com.gwghk.ims.common.util.CustomDateSerializer;

public class MallOrderDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = -7569747574635573022L;

	private Long id;

    /**
     * 流水号
     */
    private String recordNumber;

    /**
     * 活动编号
     */
    private String activityPeriods;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 任务编号
     */
    private String taskCode;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 物品编号
     */
    private String itemsCode;

    /**
     * 物品名称
     */
    private String itemsName;

    /**
     * 客户账号
     */
    private String accountNo;

    /**
     * 客户姓名
     */
    private String chineseName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 所在地区
     */
    private String region;

    /**
     * 收货地址
     */
    private String detailAddress;

    /**
     * 发货状态
     * 0 待发放 1 发放中 2 已取消 3 已出库
     */
    private Integer deliveryStatus;
    
    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryDate;

    /**
     * 快递号
     */
    private String expressNo;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 发放状态，见IssuingStatusEnum
     */
    private String issuingStatus;
    
    /**
     * 审核状态，见AuditStatusEnum
     */
    private String auditStatus;
    
    private String[] tagCodes;
    
	public String[] getTagCodes() {
		return tagCodes;
	}

	public void setTagCodes(String[] tagCodes) {
		this.tagCodes = tagCodes;
	}

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

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getItemsCode() {
		return itemsCode;
	}

	public void setItemsCode(String itemsCode) {
		this.itemsCode = itemsCode;
	}

	public String getItemsName() {
		return itemsName;
	}

	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getIssuingStatus() {
		return issuingStatus;
	}

	public void setIssuingStatus(String issuingStatus) {
		this.issuingStatus = issuingStatus;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
}