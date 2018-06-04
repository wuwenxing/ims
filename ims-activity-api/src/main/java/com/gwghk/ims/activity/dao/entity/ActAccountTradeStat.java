package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 
 * @ClassName: ActAccountTradeStat
 * @Description: 交易统计
 * @author lawrence
 * @date 2018年1月28日
 *
 */
public class ActAccountTradeStat extends BaseEntity {
	private Long id;

	private String activityPeriods;

	private String accountNo;

	private String platform;

	/**
	 * 完成计算交易手数的交易数据ID
	 */
	private Long finishTradeId;
	/**
	 * 完成计算的最后一条交易数据的交易时间
	 */
	private Date finishTradeTime;

	private Date finishInTradeTime;

	/**
	 * 超前完成的剩余手数
	 */
	private BigDecimal surplusFinishTradeLot;

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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Long getFinishTradeId() {
		return finishTradeId;
	}

	public void setFinishTradeId(Long finishTradeId) {
		this.finishTradeId = finishTradeId;
	}

	public Date getFinishTradeTime() {
		return finishTradeTime;
	}

	public void setFinishTradeTime(Date finishTradeTime) {
		this.finishTradeTime = finishTradeTime;
	}

	public Date getFinishInTradeTime() {
		return finishInTradeTime;
	}

	public void setFinishInTradeTime(Date finishInTradeTime) {
		this.finishInTradeTime = finishInTradeTime;
	}

	public BigDecimal getSurplusFinishTradeLot() {
		return surplusFinishTradeLot;
	}

	public void setSurplusFinishTradeLot(BigDecimal surplusFinishTradeLot) {
		this.surplusFinishTradeLot = surplusFinishTradeLot;
	}

}