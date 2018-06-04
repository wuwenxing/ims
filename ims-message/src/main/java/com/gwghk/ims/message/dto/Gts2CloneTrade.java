package com.gwghk.ims.message.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName: Gts2CloneTrade
 * @Description: 接收GTS2交易数据
 * @author lawrence
 * @date 2018年3月30日
 *
 */
public class Gts2CloneTrade {
	/**
	 * 交易帳號
	 */
	private String accountNo;
	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 成交单号
	 */
	private Long positionIdInt;
	/**
	 * 持仓ID
	 */
	private Long dealPIdInt;
	/**
	 * 产品类型
	 */
	private String displayName;
	/**
	 * 公司ID
	 */
	private Long companyId;
	/**
	 * 开平仓( OPEN , CLOSE )
	 */
	private String reportType;
	/**
	 * 交易手数
	 */
	private BigDecimal tradeVolume;
	/**
	 * 盈亏
	 */
	private BigDecimal profit;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 利息
	 */
	private BigDecimal swap;
	/**
	 * 交易时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date tradeTime;
	/**
	 * 1 :市价建仓,2:限价(挂单),4:停损(挂单),
	 * 81：市价建仓，82：限价,84:停损,
	 * 8:市价平仓  ，16：挂单，32:挂单,64系统强平,128:部分平仓,160:到期强平,161:交易员强平,162:EMT强平
	 */
	private Integer dealReason;

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
	
	public Long getPositionIdInt() {
		return positionIdInt;
	}

	public void setPositionIdInt(Long positionIdInt) {
		this.positionIdInt = positionIdInt;
	}

	public Long getDealPIdInt() {
		return dealPIdInt;
	}

	public void setDealPIdInt(Long dealPIdInt) {
		this.dealPIdInt = dealPIdInt;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public BigDecimal getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(BigDecimal tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getSwap() {
		return swap;
	}

	public void setSwap(BigDecimal swap) {
		this.swap = swap;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Integer getDealReason() {
		return dealReason;
	}

	public void setDealReason(Integer dealReason) {
		this.dealReason = dealReason;
	}

	@Override
	public String toString() {
		return "Gts2CloneTrade [accountNo=" + accountNo + ", platform=" + platform + ", positionIdInt=" + positionIdInt
				+ ", dealPIdInt=" + dealPIdInt + ", displayName=" + displayName + ", companyId=" + companyId
				+ ", reportType=" + reportType + ", tradeVolume=" + tradeVolume + ", profit=" + profit + ", commission="
				+ commission + ", swap=" + swap + ", tradeTime=" + tradeTime + ", dealReason=" + dealReason + "]";
	}

	
}
