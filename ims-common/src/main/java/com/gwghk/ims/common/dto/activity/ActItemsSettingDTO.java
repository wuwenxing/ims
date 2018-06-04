package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：活动物品设置DTO
 * 
 * @author
 * @version 1.0
 * @Date 2017年5月12日
 */
public class ActItemsSettingDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1029125255950777454L;

	private Long id;

	/**
	 * 奖品编号
	 */
	private String giftNumber;

	/**
	 * 奖品名称
	 */
	private String giftName;

	/**
	 * 物品临时数量
	 */
	private Integer giftTmpAmount;

	/**
	 * 物品数量
	 */
	private Integer giftAmount;

	/**
	 * 物品类型(来源于数据字典)
	 */
	private String giftType;

	/**
	 * 中奖概率(小数,如：0.2,即20%)
	 */
	private Double giftRate;

	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;

	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;

	/**
	 * 物品种类
	 */
	private String itemCategory;

	/**
	 * 物品该种类的价值数量
	 */
	private Integer itemAmount;

	/**
	 * 物品单位
	 */
	private String itemUnit;

	/**
	 * 物品价格
	 */
	private Double giftPrice;
	/**
	 * 是否正在使用
	 */
	private boolean actUseFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActUseFlag() {
		return actUseFlag;
	}

	public void setActUseFlag(boolean actUseFlag) {
		this.actUseFlag = actUseFlag;
	}

	public String getGiftNumber() {
		return giftNumber;
	}

	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getGiftTmpAmount() {
		return giftTmpAmount;
	}

	public void setGiftTmpAmount(Integer giftTmpAmount) {
		this.giftTmpAmount = giftTmpAmount;
	}

	public Integer getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(Integer giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public Double getGiftRate() {
		return giftRate;
	}

	public void setGiftRate(Double giftRate) {
		this.giftRate = giftRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public Integer getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Integer itemAmount) {
		this.itemAmount = itemAmount;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}

}