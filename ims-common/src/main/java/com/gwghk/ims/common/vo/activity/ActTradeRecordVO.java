package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.util.CustomDateSerializer;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：清洗后的交易数据实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月25日
 */
public class ActTradeRecordVO extends BaseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 918289454910177077L;

	private Long id;
	
	/**
	 * 账号
	 */
	private String accountNo;
	
	/**
	 * 产品
	 */
	private String product;
	
	/**
	 * 平台
	 */
	private String platform;
	
	/**
	 * 平仓手数
	 */
	private BigDecimal closeLot;
	
	/**
	 * 盈亏
	 */
	private BigDecimal profit;
	
	/**
	 * 平仓单号
	 */
	private Long closeOrderNo;
	
	/**
	 * 持仓Id
	 */
	private Long positionId;
	
	/**
	 * 开仓时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date openTime;
	
	/**
	 * 平仓时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date closeTime;
	
	/**
	 * 交易手数
	 */
	private BigDecimal tradeLot;
	
	/**
	 * 交易时间
	 */
	@OrderBy(columnName="trade_time",propName="tradeTime",order="asc")
	private Date tradeTime;
	
	/**
	 * 持仓单号
	 */
	private Long positionNo;
	/**
	 * 订单号
	 */
	private Long orderNo;
	
	/**
	 * 开平仓方向(in,out)
	 */
	private String tradeType;
	
	private String tradeStartTime ;
	
	private String tradeEndTime ;
	/**
	 * demo或real
	 */
	private String env ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public BigDecimal getCloseLot() {
		return closeLot;
	}

	public void setCloseLot(BigDecimal closeLot) {
		this.closeLot = closeLot;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Long getCloseOrderNo() {
		return closeOrderNo;
	}

	public void setCloseOrderNo(Long closeOrderNo) {
		this.closeOrderNo = closeOrderNo;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public BigDecimal getTradeLot() {
		return tradeLot;
	}

	public void setTradeLot(BigDecimal tradeLot) {
		this.tradeLot = tradeLot;
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

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeStartTime() {
		return tradeStartTime;
	}

	public void setTradeStartTime(String tradeStartTime) {
		this.tradeStartTime = tradeStartTime;
	}

	public String getTradeEndTime() {
		return tradeEndTime;
	}

	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
	
}
