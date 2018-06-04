package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;

/**
 * 
 * @ClassName: ActSettingParamsValJson
 * @Description: 活动基本动态参数值
 * @author eva
 * @date 2018年4月10日
 *
 */
public class ActSettingParamsValJson {
 
	/**
	 * 赠金有效期
	 */
	private Integer withGoldDays;

	/**
	 * 代币有效期
	 */
	private Integer coinDays;
 

	/**
	 * demo账号保留余额
	 */
	private BigDecimal demoKeepAmount;

	/**
	 * 最高兑换次数
	 */
	private Integer maxExchangeCount;
	
	/**
	 * 奖励优先发放平台
	 */
	private String priorityPlatform;

 
	/**
	 * 赠金有效期单位
	 */
    private String withGoldDaysUnit;
 
	
	/**
	 * 代币有效期单位
	 */
    private String coinDaysUnit;
 
	/**
	 * demo账号保留余额单位
	 */
    private String demoKeepAmountUnit;
 
	/**
	 * 最高兑换次数单位
	 */
    private String maxExchangeCountUnit;
    
    /**
     * 入金型任务释放类型
     */
    private Integer releaseType;

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

	public BigDecimal getDemoKeepAmount() {
		return demoKeepAmount;
	}

	public void setDemoKeepAmount(BigDecimal demoKeepAmount) {
		this.demoKeepAmount = demoKeepAmount;
	}

	public Integer getMaxExchangeCount() {
		return maxExchangeCount;
	}

	public void setMaxExchangeCount(Integer maxExchangeCount) {
		this.maxExchangeCount = maxExchangeCount;
	}

	public String getPriorityPlatform() {
		return priorityPlatform;
	}

	public void setPriorityPlatform(String priorityPlatform) {
		this.priorityPlatform = priorityPlatform;
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

	public Integer getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(Integer releaseType) {
		this.releaseType = releaseType;
	}
	
}
