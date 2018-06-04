package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.common.BaseEntity;

public class ActAccountActiviStat extends BaseEntity {

	private Long id;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 平台
     */
    private String platform;

    /**
     * 真实模拟
     */
    private String env;

    /**
     * 客户-姓名
     */
    private String custName;

    /**
     * 客户-手机号
     */
    private String custMobile;

    /**
     * 活动编号
     */
    private String actNo;

    /**
     * 活动名称
     */
    private String actName;

    /**
     * 活动开始时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actStartTime;

    /**
     * 活动结束时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actEndTime;

    /**
     * 参与时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date joinTime;

    /**
     * 入金
     */
    private BigDecimal depositAmount;

    /**
     * 先赠-赠金
     */
    private BigDecimal beforeGold;

    /**
     * 先赠-释放赠金
     */
    private BigDecimal beforeReleaseGold;

    /**
     * 先赠-要求手数
     */
    private BigDecimal beforeRequiredLot;

    /**
     * 完成手数
     */
    private BigDecimal beforeFinishLot;

    /**
     * 后赠-赠金
     */
    private BigDecimal afterGold;

    /**
     * 后赠-完成手数
     */
    private BigDecimal afterFinishLot;

    /**
     * 扣回的赠金
     */
    private BigDecimal discountGold;

    /**
     * 清算状态(0: 未清算 1:待清算 2:清算中 3:清算成功 4:清算失败 )
     */
    private Integer settleStatus;

    /**
     * 清算方式(1:人工清算 2:自动清算)
     */
    private Integer settleMode;

    /**
     * 预计清算时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date aboutSettleTime;

    /**
     * 实际清算时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date realSettleTime;

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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Date getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public BigDecimal getBeforeGold() {
		return beforeGold;
	}

	public void setBeforeGold(BigDecimal beforeGold) {
		this.beforeGold = beforeGold;
	}

	public BigDecimal getBeforeReleaseGold() {
		return beforeReleaseGold;
	}

	public void setBeforeReleaseGold(BigDecimal beforeReleaseGold) {
		this.beforeReleaseGold = beforeReleaseGold;
	}

	public BigDecimal getBeforeRequiredLot() {
		return beforeRequiredLot;
	}

	public void setBeforeRequiredLot(BigDecimal beforeRequiredLot) {
		this.beforeRequiredLot = beforeRequiredLot;
	}

	public BigDecimal getBeforeFinishLot() {
		return beforeFinishLot;
	}

	public void setBeforeFinishLot(BigDecimal beforeFinishLot) {
		this.beforeFinishLot = beforeFinishLot;
	}

	public BigDecimal getAfterGold() {
		return afterGold;
	}

	public void setAfterGold(BigDecimal afterGold) {
		this.afterGold = afterGold;
	}

	public BigDecimal getAfterFinishLot() {
		return afterFinishLot;
	}

	public void setAfterFinishLot(BigDecimal afterFinishLot) {
		this.afterFinishLot = afterFinishLot;
	}

	public BigDecimal getDiscountGold() {
		return discountGold;
	}

	public void setDiscountGold(BigDecimal discountGold) {
		this.discountGold = discountGold;
	}

	public Integer getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(Integer settleStatus) {
		this.settleStatus = settleStatus;
	}

	public Integer getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(Integer settleMode) {
		this.settleMode = settleMode;
	}

	public Date getAboutSettleTime() {
		return aboutSettleTime;
	}

	public void setAboutSettleTime(Date aboutSettleTime) {
		this.aboutSettleTime = aboutSettleTime;
	}

	public Date getRealSettleTime() {
		return realSettleTime;
	}

	public void setRealSettleTime(Date realSettleTime) {
		this.realSettleTime = realSettleTime;
	}

	public void initBaseParam() {
		setBeforeGold(BigDecimal.ZERO);
		setBeforeRequiredLot(BigDecimal.ZERO);
		setBeforeReleaseGold(BigDecimal.ZERO);
		setBeforeFinishLot(BigDecimal.ZERO);
		setAfterGold(BigDecimal.ZERO);
		setDiscountGold(BigDecimal.ZERO);
		setDepositAmount(BigDecimal.ZERO);
	}
}