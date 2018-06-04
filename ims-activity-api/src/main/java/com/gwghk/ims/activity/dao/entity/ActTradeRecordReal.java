package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.common.BaseEntity;

/**
 * 
 * 摘要：清洗后的交易数据
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月25日
 */
@Table(name="act_trade_record_real")
public class ActTradeRecordReal extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 持仓单号
	 */
	private Long positionNo;
	/**
	 * 订单号
	 */
	@OrderBy(columnName="order_no",propName="orderNo",order="desc")
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
	@OrderBy(columnName="trade_type",propName="tradeType",order="desc")
	private String tradeType;
	/**
	 * 交易手数
	 */
	private BigDecimal tradeLot;
	/**
	 * 交易时间
	 */
	@OrderBy(columnName="trade_time",propName="tradeTime",order="desc")
	private Date tradeTime;
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
	 * 订单来源，0：客户端，1：交易员，3：系统，5：电话，6：iPhone，7：Android，8：紧急平仓，9：Web
	 */
	private String channel ;
	/**
	 * 开仓时间
	 */
	private Date openTime ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public BigDecimal getTradeLot() {
		return tradeLot;
	}

	public void setTradeLot(BigDecimal tradeLot) {
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
}
