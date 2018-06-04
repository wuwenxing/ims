package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2Deal {
	
    private Integer id;

    private Integer uuid;

//    private Date uutime;

    private Long groupid;

    private Integer accountid;

    private Integer positionid;

    private Integer orderid;

    private Short direction;

    private Short status;

    private Short reason;

    private String symbol;

    private Double execvolume;

    private Double execprice;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date exectime;

    private String exectickseq;

    private Double srcvolume;

    private Double srcprice;

    private String srctickseq;

    private Double rate;

    private Double profit;

    private Double swap;

    private Double commission;

    private Double amountsrc;

    private Double amountdst;

    private String proposalno;

    private Integer proposaltype;

    private String comment;

    private Integer orderrefid;

    private Double openmargin;

    private Double openmarginmaintenance;

    private Double openmarginstopout;

    private Double openmarginrate;

    private Integer pId;

    private Integer pRelateId1;

    private Integer pRelateId2;

    private Integer orderseq;

    private String tickno;

    private String platform;
    
    private String env;
    private String op;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

//    public Date getUutime() {
//        return uutime;
//    }
//
//    public void setUutime(Date uutime) {
//        this.uutime = uutime;
//    }

    public Integer getAccountid() {
        return accountid;
    }

    public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public Integer getPositionid() {
        return positionid;
    }

    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Short getDirection() {
        return direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getReason() {
        return reason;
    }

    public void setReason(Short reason) {
        this.reason = reason;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public Double getExecvolume() {
        return execvolume;
    }

    public void setExecvolume(Double execvolume) {
        this.execvolume = execvolume;
    }

    public Double getExecprice() {
        return execprice;
    }

    public void setExecprice(Double execprice) {
        this.execprice = execprice;
    }

    public Date getExectime() {
        return exectime;
    }

    public void setExectime(Date exectime) {
        this.exectime = exectime;
    }

    public String getExectickseq() {
        return exectickseq;
    }

    public void setExectickseq(String exectickseq) {
        this.exectickseq = exectickseq == null ? null : exectickseq.trim();
    }

    public Double getSrcvolume() {
        return srcvolume;
    }

    public void setSrcvolume(Double srcvolume) {
        this.srcvolume = srcvolume;
    }

    public Double getSrcprice() {
        return srcprice;
    }

    public void setSrcprice(Double srcprice) {
        this.srcprice = srcprice;
    }

    public String getSrctickseq() {
        return srctickseq;
    }

    public void setSrctickseq(String srctickseq) {
        this.srctickseq = srctickseq == null ? null : srctickseq.trim();
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getSwap() {
        return swap;
    }

    public void setSwap(Double swap) {
        this.swap = swap;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getAmountsrc() {
        return amountsrc;
    }

    public void setAmountsrc(Double amountsrc) {
        this.amountsrc = amountsrc;
    }

    public Double getAmountdst() {
        return amountdst;
    }

    public void setAmountdst(Double amountdst) {
        this.amountdst = amountdst;
    }

    public String getProposalno() {
        return proposalno;
    }

    public void setProposalno(String proposalno) {
        this.proposalno = proposalno == null ? null : proposalno.trim();
    }

    public Integer getProposaltype() {
        return proposaltype;
    }

    public void setProposaltype(Integer proposaltype) {
        this.proposaltype = proposaltype;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getOrderrefid() {
        return orderrefid;
    }

    public void setOrderrefid(Integer orderrefid) {
        this.orderrefid = orderrefid;
    }

    public Double getOpenmargin() {
        return openmargin;
    }

    public void setOpenmargin(Double openmargin) {
        this.openmargin = openmargin;
    }

    public Double getOpenmarginmaintenance() {
        return openmarginmaintenance;
    }

    public void setOpenmarginmaintenance(Double openmarginmaintenance) {
        this.openmarginmaintenance = openmarginmaintenance;
    }

    public Double getOpenmarginstopout() {
        return openmarginstopout;
    }

    public void setOpenmarginstopout(Double openmarginstopout) {
        this.openmarginstopout = openmarginstopout;
    }

    public Double getOpenmarginrate() {
        return openmarginrate;
    }

    public void setOpenmarginrate(Double openmarginrate) {
        this.openmarginrate = openmarginrate;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getpRelateId1() {
        return pRelateId1;
    }

    public void setpRelateId1(Integer pRelateId1) {
        this.pRelateId1 = pRelateId1;
    }

    public Integer getpRelateId2() {
        return pRelateId2;
    }

    public void setpRelateId2(Integer pRelateId2) {
        this.pRelateId2 = pRelateId2;
    }

    public Integer getOrderseq() {
        return orderseq;
    }

    public void setOrderseq(Integer orderseq) {
        this.orderseq = orderseq;
    }

    public String getTickno() {
        return tickno;
    }

    public void setTickno(String tickno) {
        this.tickno = tickno == null ? null : tickno.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
    
}