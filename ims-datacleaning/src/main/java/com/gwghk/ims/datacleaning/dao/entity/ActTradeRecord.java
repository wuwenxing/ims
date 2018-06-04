package com.gwghk.ims.datacleaning.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: ActivityTradeRecord
 * @Description: 账号交易资料数据
 * @author lawrence
 * @date 2017年5月19日
 *
 */
public class ActTradeRecord extends BaseEntity {

	private Long id;

	/**
	 * 持仓单号
	 */
	private Long positionNo;
	/**
	 * 订单号
	 */
	private Long orderNo;
	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 账号
	 */
	private String accountNo;
	/**
	 * 产品(USDCNY)
	 */
	private String product;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 开平仓方向(in,out)
	 */
	private String tradeType;
	/**
	 * 交易手数
	 */
	private Double tradeLot;
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
	private Date tradeTime;

	/**
	 * 来源(0:GTS2库，1:旧库)
	 */
	private String source;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Double getTradeLot() {
		return tradeLot;
	}

	public void setTradeLot(Double tradeLot) {
		this.tradeLot = tradeLot;
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

	public Long getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(Long positionNo) {
		this.positionNo = positionNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


}
