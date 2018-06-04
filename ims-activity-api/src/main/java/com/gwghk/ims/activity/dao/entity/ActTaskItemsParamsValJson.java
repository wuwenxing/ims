package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;

/**
 * 
 * @ClassName: ActTaskItemsParamsValJson
 * @Description: 奖励物品动态参数值
 * @author eva
 * @date 2018年4月10日
 *
 */
public class ActTaskItemsParamsValJson {
	  /**
     * 物品参数值，可能是对应的物品金额或百分比或接口公式
     */
    private String itemParamVal;
    /**
     * 物品参数单位USD/RMB
     */
    private String itemParamValUnit;

    /**
     * 最多领取次数
     */
    private Integer receiveMaxNum;
    
    /**
     * 最多领取次数单位
     */
    private String receiveMaxNumUnit;


    /**
     * 最高领取金额
     */
    private BigDecimal receiveMaxMoney;
    
    /**
     * 最高领取金额单位
     */
    private String receiveMaxMoneyUnit;

    /**
     * 交易手数
     */
    private BigDecimal tradeNum;
    
    /**
     * 交易手数单位
     */
    private String tradeNumUnit;
     
    /**
     * 接口物品y参数值
     */
    private BigDecimal infParamY;
    /**
     * 接口物品z参数值
     */
    private BigDecimal infParamZ;

    /**
     * 接品物品最高额度
     */
    private BigDecimal infParamMax;

    /**
     * 接口物品最高额度单位 M/G/Month/RMB/USD
     */
    private String infParamMaxUnit;
    
    /**
     * 等 额值
     */
    private BigDecimal equalValue;
    
  
    
    /**
     * 频率时间  1天1次
     */
    private BigDecimal frequencyTime;
    
    /**
     * 频率时间要求 单位
     */
    private String frequencyTimeUnit;
    
    /**
     * 频率时间内的个数
     */
    private Integer frequencyNum;
    
    /**
     * 频率时间内的个数单位
     */
    private String frequencyNumUnit;
    
    /**
     * 物品抽中的概率
     */
    private BigDecimal itemProbability;
 

	public String getItemParamVal() {
		return itemParamVal;
	}

	public void setItemParamVal(String itemParamVal) {
		this.itemParamVal = itemParamVal;
	}

	public String getItemParamValUnit() {
		return itemParamValUnit;
	}

	public void setItemParamValUnit(String itemParamValUnit) {
		this.itemParamValUnit = itemParamValUnit;
	}

	public Integer getReceiveMaxNum() {
		return receiveMaxNum;
	}

	public void setReceiveMaxNum(Integer receiveMaxNum) {
		this.receiveMaxNum = receiveMaxNum;
	}

	public BigDecimal getReceiveMaxMoney() {
		return receiveMaxMoney;
	}

	public void setReceiveMaxMoney(BigDecimal receiveMaxMoney) {
		this.receiveMaxMoney = receiveMaxMoney;
	}

	public BigDecimal getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(BigDecimal tradeNum) {
		this.tradeNum = tradeNum;
	}

	public String getTradeNumUnit() {
		return tradeNumUnit;
	}

	public void setTradeNumUnit(String tradeNumUnit) {
		this.tradeNumUnit = tradeNumUnit;
	}

	public BigDecimal getInfParamY() {
		return infParamY;
	}

	public void setInfParamY(BigDecimal infParamY) {
		this.infParamY = infParamY;
	}

	public BigDecimal getInfParamZ() {
		return infParamZ;
	}

	public void setInfParamZ(BigDecimal infParamZ) {
		this.infParamZ = infParamZ;
	}

	public BigDecimal getInfParamMax() {
		return infParamMax;
	}

	public void setInfParamMax(BigDecimal infParamMax) {
		this.infParamMax = infParamMax;
	}

	public String getInfParamMaxUnit() {
		return infParamMaxUnit;
	}

	public void setInfParamMaxUnit(String infParamMaxUnit) {
		this.infParamMaxUnit = infParamMaxUnit;
	}

	public BigDecimal getEqualValue() {
		return equalValue;
	}

	public void setEqualValue(BigDecimal equalValue) {
		this.equalValue = equalValue;
	}

	public BigDecimal getFrequencyTime() {
		return frequencyTime;
	}

	public void setFrequencyTime(BigDecimal frequencyTime) {
		this.frequencyTime = frequencyTime;
	}

	public String getFrequencyTimeUnit() {
		return frequencyTimeUnit;
	}

	public void setFrequencyTimeUnit(String frequencyTimeUnit) {
		this.frequencyTimeUnit = frequencyTimeUnit;
	}

	public Integer getFrequencyNum() {
		return frequencyNum;
	}

	public void setFrequencyNum(Integer frequencyNum) {
		this.frequencyNum = frequencyNum;
	}

	public BigDecimal getItemProbability() {
		return itemProbability;
	}

	public void setItemProbability(BigDecimal itemProbability) {
		this.itemProbability = itemProbability;
	}

	public String getReceiveMaxMoneyUnit() {
		return receiveMaxMoneyUnit;
	}

	public void setReceiveMaxMoneyUnit(String receiveMaxMoneyUnit) {
		this.receiveMaxMoneyUnit = receiveMaxMoneyUnit;
	}

	public String getFrequencyNumUnit() {
		return frequencyNumUnit;
	}

	public void setFrequencyNumUnit(String frequencyNumUnit) {
		this.frequencyNumUnit = frequencyNumUnit;
	}

	public String getReceiveMaxNumUnit() {
		return receiveMaxNumUnit;
	}

	public void setReceiveMaxNumUnit(String receiveMaxNumUnit) {
		this.receiveMaxNumUnit = receiveMaxNumUnit;
	}
    
    

}
