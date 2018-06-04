package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.activity.util.CustomDateSerializer;
import com.gwghk.ims.common.common.BaseEntity;

public class ImsMallItem extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * dome物品兑换行活动-每项任务设置id
     */
    private Long taskSettingId;

    /**
     * dome物品兑换行活动-每项任务设置名称
     */
    private String taskSettingName;
    
    /**
     * 活动编号
     */
    private String activityPeriods;
    
    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 关联活动物品code
     */
    private String refActItemsCode;

    /**
     * 物品名称
     */
    private String itemName;
    
    /**
     * 商城-商品编号
     */
    private String mallItemsCode;

    /**
     * 商城-商品类别
     */
    private String mallItemsType;
    
    /**
     * 商城-商品类别
     */
    private String itemsType;
    
    /**
     * 商城-商品类别显示
     */
    private String mallItemsTypeName;

    /**
     * 商城-商品名称
     */
    private String mallItemsName;

    /**
     * 商城-商品数量
     */
    private Integer mallItemsNum;
    
    /**
     * 商品-商品图片的文件名
     */
    private String imgName;

    /**
     * 商品-商品图片的url
     */
    private String imgUrl;

    /**
     * 商城-商品描述
     */
    private String mallItemsDesc;
    
    /**
     * 上架时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskSettingId() {
		return taskSettingId;
	}

	public void setTaskSettingId(Long taskSettingId) {
		this.taskSettingId = taskSettingId;
	}

	public String getTaskSettingName() {
		return taskSettingName;
	}

	public void setTaskSettingName(String taskSettingName) {
		this.taskSettingName = taskSettingName;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public String getRefActItemsCode() {
		return refActItemsCode;
	}

	public void setRefActItemsCode(String refActItemsCode) {
		this.refActItemsCode = refActItemsCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMallItemsCode() {
		return mallItemsCode;
	}

	public void setMallItemsCode(String mallItemsCode) {
		this.mallItemsCode = mallItemsCode;
	}

	public String getItemsType() {
		return itemsType;
	}

	public void setItemsType(String itemsType) {
		this.itemsType = itemsType;
	}

	public String getMallItemsType() {
		return mallItemsType;
	}

	public void setMallItemsType(String mallItemsType) {
		this.mallItemsType = mallItemsType;
	}

	public String getMallItemsTypeName() {
		return mallItemsTypeName;
	}

	public void setMallItemsTypeName(String mallItemsTypeName) {
		this.mallItemsTypeName = mallItemsTypeName;
	}

	public String getMallItemsName() {
		return mallItemsName;
	}

	public void setMallItemsName(String mallItemsName) {
		this.mallItemsName = mallItemsName;
	}

	public Integer getMallItemsNum() {
		return mallItemsNum;
	}

	public void setMallItemsNum(Integer mallItemsNum) {
		this.mallItemsNum = mallItemsNum;
	}

	public String getMallItemsDesc() {
		return mallItemsDesc;
	}

	public void setMallItemsDesc(String mallItemsDesc) {
		this.mallItemsDesc = mallItemsDesc;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
}