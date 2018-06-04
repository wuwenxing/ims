package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

public class Mt4Trades {
    private Integer ticket;

    private Integer login;

    private String symbol;

    private Integer digits;

    private Integer cmd;

    private Integer volume;

    private Date openTime;

    private Double openPrice;

    private Double sl;

    private Double tp;

    private Date closeTime;

    private Date expiration;

    private Integer reason;

    private Double convRate1;

    private Double convRate2;

    private Double commission;

    private Double commissionAgent;

    private Double swaps;

    private Double closePrice;

    private Double profit;

    private Double taxes;

    private String comment;

    private Integer internalId;

    private Double marginRate;

    private Integer timestamp;

    private Integer gwVolume;

    private Integer gwOpenPrice;

    private Integer gwClosePrice;

    private Date modifyTime;

    private Integer magic;
    
    private String tradeType;
    
    private String firstDeposit;

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public Double getTp() {
        return tp;
    }

    public void setTp(Double tp) {
        this.tp = tp;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public Double getConvRate1() {
        return convRate1;
    }

    public void setConvRate1(Double convRate1) {
        this.convRate1 = convRate1;
    }

    public Double getConvRate2() {
        return convRate2;
    }

    public void setConvRate2(Double convRate2) {
        this.convRate2 = convRate2;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getCommissionAgent() {
        return commissionAgent;
    }

    public void setCommissionAgent(Double commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public Double getSwaps() {
        return swaps;
    }

    public void setSwaps(Double swaps) {
        this.swaps = swaps;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getInternalId() {
        return internalId;
    }

    public void setInternalId(Integer internalId) {
        this.internalId = internalId;
    }

    public Double getMarginRate() {
        return marginRate;
    }

    public void setMarginRate(Double marginRate) {
        this.marginRate = marginRate;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getGwVolume() {
        return gwVolume;
    }

    public void setGwVolume(Integer gwVolume) {
        this.gwVolume = gwVolume;
    }

    public Integer getGwOpenPrice() {
        return gwOpenPrice;
    }

    public void setGwOpenPrice(Integer gwOpenPrice) {
        this.gwOpenPrice = gwOpenPrice;
    }

    public Integer getGwClosePrice() {
        return gwClosePrice;
    }

    public void setGwClosePrice(Integer gwClosePrice) {
        this.gwClosePrice = gwClosePrice;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getFirstDeposit() {
		return firstDeposit;
	}

	public void setFirstDeposit(String firstDeposit) {
		this.firstDeposit = firstDeposit;
	}
    
    
}