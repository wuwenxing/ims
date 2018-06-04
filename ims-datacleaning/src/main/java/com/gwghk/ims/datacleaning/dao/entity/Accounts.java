package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

public class Accounts {
    private Integer id;

    /**
     * 会员ID
     */
    private Integer membersId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 平台账号
     */
    private String login;

    private String order;

    private String paysn;

    private String payorigin;

    /**
     * 支付方式
     */
    private String paymode;

    private String paycard;

    private String paybank;

    private String payaddr;

    private String payname;

    private String payno;

    /**
     * 支付货币
     */
    private String paycurrency;

    private Float cny;

    private Float hkd;

    private Float usd;

    private Float usdCny;

    private Float cnyUsd;

    private Float usdHkd;

    private Float hkdUsd;

    private Float hkdCny;

    private Float cnyHkd;

    /**
     * 手续费
     */
    private Float fee;

    /**
     * 支付状态
     */
    private String paystatus;

    /**
     * 支付时间
     */
    private Integer paytime;

    /**
     * 下单时间
     */
    private Integer ordertime;

    /**
     * 存款时间
     */
    private Date depositTime;

    /**
     * 处理工号
     */
    private String operator;

    /**
     * 描述
     */
    private String description;

    /**
     * 订单创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;
    
    /**
     * 首次存款
     */
    private String firstDeposit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 会员ID
     * @return members_id 会员ID
     */
    public Integer getMembersId() {
        return membersId;
    }

    /**
     * 会员ID
     * @param membersId 会员ID
     */
    public void setMembersId(Integer membersId) {
        this.membersId = membersId;
    }

    /**
     * 平台
     * @return platform 平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 平台
     * @param platform 平台
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 渠道
     * @return channel 渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 渠道
     * @param channel 渠道
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    /**
     * 平台账号
     * @return login 平台账号
     */
    public String getLogin() {
        return login;
    }

    /**
     * 平台账号
     * @param login 平台账号
     */
    public void setLogin(String login) {
        this.login = login == null ? null : login.trim();
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order == null ? null : order.trim();
    }

    public String getPaysn() {
        return paysn;
    }

    public void setPaysn(String paysn) {
        this.paysn = paysn == null ? null : paysn.trim();
    }

    public String getPayorigin() {
        return payorigin;
    }

    public void setPayorigin(String payorigin) {
        this.payorigin = payorigin == null ? null : payorigin.trim();
    }

    /**
     * 支付方式
     * @return paymode 支付方式
     */
    public String getPaymode() {
        return paymode;
    }

    /**
     * 支付方式
     * @param paymode 支付方式
     */
    public void setPaymode(String paymode) {
        this.paymode = paymode == null ? null : paymode.trim();
    }

    public String getPaycard() {
        return paycard;
    }

    public void setPaycard(String paycard) {
        this.paycard = paycard == null ? null : paycard.trim();
    }

    public String getPaybank() {
        return paybank;
    }

    public void setPaybank(String paybank) {
        this.paybank = paybank == null ? null : paybank.trim();
    }

    public String getPayaddr() {
        return payaddr;
    }

    public void setPayaddr(String payaddr) {
        this.payaddr = payaddr == null ? null : payaddr.trim();
    }

    public String getPayname() {
        return payname;
    }

    public void setPayname(String payname) {
        this.payname = payname == null ? null : payname.trim();
    }

    public String getPayno() {
        return payno;
    }

    public void setPayno(String payno) {
        this.payno = payno == null ? null : payno.trim();
    }

    /**
     * 支付货币
     * @return paycurrency 支付货币
     */
    public String getPaycurrency() {
        return paycurrency;
    }

    /**
     * 支付货币
     * @param paycurrency 支付货币
     */
    public void setPaycurrency(String paycurrency) {
        this.paycurrency = paycurrency == null ? null : paycurrency.trim();
    }

    public Float getCny() {
        return cny;
    }

    public void setCny(Float cny) {
        this.cny = cny;
    }

    public Float getHkd() {
        return hkd;
    }

    public void setHkd(Float hkd) {
        this.hkd = hkd;
    }

    public Float getUsd() {
        return usd;
    }

    public void setUsd(Float usd) {
        this.usd = usd;
    }

    public Float getUsdCny() {
        return usdCny;
    }

    public void setUsdCny(Float usdCny) {
        this.usdCny = usdCny;
    }

    public Float getCnyUsd() {
        return cnyUsd;
    }

    public void setCnyUsd(Float cnyUsd) {
        this.cnyUsd = cnyUsd;
    }

    public Float getUsdHkd() {
        return usdHkd;
    }

    public void setUsdHkd(Float usdHkd) {
        this.usdHkd = usdHkd;
    }

    public Float getHkdUsd() {
        return hkdUsd;
    }

    public void setHkdUsd(Float hkdUsd) {
        this.hkdUsd = hkdUsd;
    }

    public Float getHkdCny() {
        return hkdCny;
    }

    public void setHkdCny(Float hkdCny) {
        this.hkdCny = hkdCny;
    }

    public Float getCnyHkd() {
        return cnyHkd;
    }

    public void setCnyHkd(Float cnyHkd) {
        this.cnyHkd = cnyHkd;
    }

    /**
     * 手续费
     * @return fee 手续费
     */
    public Float getFee() {
        return fee;
    }

    /**
     * 手续费
     * @param fee 手续费
     */
    public void setFee(Float fee) {
        this.fee = fee;
    }

    /**
     * 支付状态
     * @return paystatus 支付状态
     */
    public String getPaystatus() {
        return paystatus;
    }

    /**
     * 支付状态
     * @param paystatus 支付状态
     */
    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus == null ? null : paystatus.trim();
    }

    /**
     * 支付时间
     * @return paytime 支付时间
     */
    public Integer getPaytime() {
        return paytime;
    }

    /**
     * 支付时间
     * @param paytime 支付时间
     */
    public void setPaytime(Integer paytime) {
        this.paytime = paytime;
    }

    /**
     * 下单时间
     * @return ordertime 下单时间
     */
    public Integer getOrdertime() {
        return ordertime;
    }

    /**
     * 下单时间
     * @param ordertime 下单时间
     */
    public void setOrdertime(Integer ordertime) {
        this.ordertime = ordertime;
    }

    /**
     * 存款时间
     * @return deposit_time 存款时间
     */
    public Date getDepositTime() {
        return depositTime;
    }

    /**
     * 存款时间
     * @param depositTime 存款时间
     */
    public void setDepositTime(Date depositTime) {
        this.depositTime = depositTime;
    }

    /**
     * 处理工号
     * @return operator 处理工号
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 处理工号
     * @param operator 处理工号
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * 描述
     * @return description 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 订单创建时间
     * @return ctime 订单创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 订单创建时间
     * @param ctime 订单创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 修改时间
     * @return mtime 修改时间
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * 修改时间
     * @param mtime 修改时间
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

	public String getFirstDeposit() {
		return firstDeposit;
	}

	public void setFirstDeposit(String firstDeposit) {
		this.firstDeposit = firstDeposit;
	}
    
    
}