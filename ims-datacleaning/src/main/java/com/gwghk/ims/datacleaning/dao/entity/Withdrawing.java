package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

public class Withdrawing {
	
    private Integer id;

    /**
     * 会员ID
     */
    private Integer membersId;

    /**
     * 交易平台
     */
    private String platform;

    /**
     * 交易账号
     */
    private String login;

    /**
     * 订单号
     */
    private String order;

    /**
     * 用户上一笔成功的订单号
     */
    private String previousOrder;

    /**
     * 提案
     */
    private String type;

    /**
     * 提案类型
     */
    private String proposalType;

    /**
     * 支付货币
     */
    private String currency;

    /**
     * 取款金额(负数)
     */
    private Float money;

    /**
     * 实扣
     */
    private Float practiceDeduct;

    /**
     * 应付金额
     */
    private Float shouldMoney;

    /**
     * 实付金额
     */
    private Float practiceMoney;

    /**
     * 活动金额
     */
    private Float activityMoney;

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 手续费
     */
    private Float fee;

    /**
     * 开仓保证金结余
     */
    private Float openMarginBalance;

    /**
     * 交易比例
     */
    private Float transactionsProportion;

    /**
     * 配置中的历史交易比例
     */
    private Float transactionsProportionConfig;

    /**
     * 取款时间
     */
    private Date depositTime;

    /**
     * 支付状态ID
     */
    private Integer payStatusId;

    /**
     * 每日第几次取款
     */
    private Byte counter;

    /**
     * 状态
     */
    private String status;

    /**
     * 自动审批状态
     */
    private String autoStatus;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 备注
     */
    private String comment;

    /**
     * 取款描述
     */
    private String feeDescription;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;
    
    private Date approveTime;
    
    private String firstWithdraw;


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
     * 交易平台
     * @return platform 交易平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 交易平台
     * @param platform 交易平台
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 交易账号
     * @return login 交易账号
     */
    public String getLogin() {
        return login;
    }

    /**
     * 交易账号
     * @param login 交易账号
     */
    public void setLogin(String login) {
        this.login = login == null ? null : login.trim();
    }

    /**
     * 订单号
     * @return order 订单号
     */
    public String getOrder() {
        return order;
    }

    /**
     * 订单号
     * @param order 订单号
     */
    public void setOrder(String order) {
        this.order = order == null ? null : order.trim();
    }

    /**
     * 用户上一笔成功的订单号
     * @return previous_order 用户上一笔成功的订单号
     */
    public String getPreviousOrder() {
        return previousOrder;
    }

    /**
     * 用户上一笔成功的订单号
     * @param previousOrder 用户上一笔成功的订单号
     */
    public void setPreviousOrder(String previousOrder) {
        this.previousOrder = previousOrder == null ? null : previousOrder.trim();
    }

    /**
     * 提案
     * @return type 提案
     */
    public String getType() {
        return type;
    }

    /**
     * 提案
     * @param type 提案
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 提案类型
     * @return proposal_type 提案类型
     */
    public String getProposalType() {
        return proposalType;
    }

    /**
     * 提案类型
     * @param proposalType 提案类型
     */
    public void setProposalType(String proposalType) {
        this.proposalType = proposalType == null ? null : proposalType.trim();
    }

    /**
     * 支付货币
     * @return currency 支付货币
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 支付货币
     * @param currency 支付货币
     */
    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    /**
     * 取款金额(负数)
     * @return money 取款金额(负数)
     */
    public Float getMoney() {
        return money;
    }

    /**
     * 取款金额(负数)
     * @param money 取款金额(负数)
     */
    public void setMoney(Float money) {
        this.money = money;
    }

    /**
     * 实扣
     * @return practice_deduct 实扣
     */
    public Float getPracticeDeduct() {
        return practiceDeduct;
    }

    /**
     * 实扣
     * @param practiceDeduct 实扣
     */
    public void setPracticeDeduct(Float practiceDeduct) {
        this.practiceDeduct = practiceDeduct;
    }

    /**
     * 应付金额
     * @return should_money 应付金额
     */
    public Float getShouldMoney() {
        return shouldMoney;
    }

    /**
     * 应付金额
     * @param shouldMoney 应付金额
     */
    public void setShouldMoney(Float shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    /**
     * 实付金额
     * @return practice_money 实付金额
     */
    public Float getPracticeMoney() {
        return practiceMoney;
    }

    /**
     * 实付金额
     * @param practiceMoney 实付金额
     */
    public void setPracticeMoney(Float practiceMoney) {
        this.practiceMoney = practiceMoney;
    }

    /**
     * 活动金额
     * @return activity_money 活动金额
     */
    public Float getActivityMoney() {
        return activityMoney;
    }

    /**
     * 活动金额
     * @param activityMoney 活动金额
     */
    public void setActivityMoney(Float activityMoney) {
        this.activityMoney = activityMoney;
    }

    /**
     * 活动类型
     * @return activity_type 活动类型
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * 活动类型
     * @param activityType 活动类型
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType == null ? null : activityType.trim();
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
     * 开仓保证金结余
     * @return open_margin_balance 开仓保证金结余
     */
    public Float getOpenMarginBalance() {
        return openMarginBalance;
    }

    /**
     * 开仓保证金结余
     * @param openMarginBalance 开仓保证金结余
     */
    public void setOpenMarginBalance(Float openMarginBalance) {
        this.openMarginBalance = openMarginBalance;
    }

    /**
     * 交易比例
     * @return transactions_proportion 交易比例
     */
    public Float getTransactionsProportion() {
        return transactionsProportion;
    }

    /**
     * 交易比例
     * @param transactionsProportion 交易比例
     */
    public void setTransactionsProportion(Float transactionsProportion) {
        this.transactionsProportion = transactionsProportion;
    }

    /**
     * 配置中的历史交易比例
     * @return transactions_proportion_config 配置中的历史交易比例
     */
    public Float getTransactionsProportionConfig() {
        return transactionsProportionConfig;
    }

    /**
     * 配置中的历史交易比例
     * @param transactionsProportionConfig 配置中的历史交易比例
     */
    public void setTransactionsProportionConfig(Float transactionsProportionConfig) {
        this.transactionsProportionConfig = transactionsProportionConfig;
    }

    /**
     * 取款时间
     * @return deposit_time 取款时间
     */
    public Date getDepositTime() {
        return depositTime;
    }

    /**
     * 取款时间
     * @param depositTime 取款时间
     */
    public void setDepositTime(Date depositTime) {
        this.depositTime = depositTime;
    }

    /**
     * 支付状态ID
     * @return pay_status_id 支付状态ID
     */
    public Integer getPayStatusId() {
        return payStatusId;
    }

    /**
     * 支付状态ID
     * @param payStatusId 支付状态ID
     */
    public void setPayStatusId(Integer payStatusId) {
        this.payStatusId = payStatusId;
    }

    /**
     * 每日第几次取款
     * @return counter 每日第几次取款
     */
    public Byte getCounter() {
        return counter;
    }

    /**
     * 每日第几次取款
     * @param counter 每日第几次取款
     */
    public void setCounter(Byte counter) {
        this.counter = counter;
    }

    /**
     * 状态
     * @return status 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 自动审批状态
     * @return auto_status 自动审批状态
     */
    public String getAutoStatus() {
        return autoStatus;
    }

    /**
     * 自动审批状态
     * @param autoStatus 自动审批状态
     */
    public void setAutoStatus(String autoStatus) {
        this.autoStatus = autoStatus == null ? null : autoStatus.trim();
    }

    /**
     * 操作者
     * @return operator 操作者
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 操作者
     * @param operator 操作者
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * 备注
     * @return comment 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 备注
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * 取款描述
     * @return fee_description 取款描述
     */
    public String getFeeDescription() {
        return feeDescription;
    }

    /**
     * 取款描述
     * @param feeDescription 取款描述
     */
    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription == null ? null : feeDescription.trim();
    }

    /**
     * 创建时间
     * @return ctime 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 创建时间
     * @param ctime 创建时间
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

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getFirstWithdraw() {
		return firstWithdraw;
	}

	public void setFirstWithdraw(String firstWithdraw) {
		this.firstWithdraw = firstWithdraw;
	}
     
}