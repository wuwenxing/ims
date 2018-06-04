package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.enums.AuthColumnEnum;
import com.gwghk.ims.common.util.CustomDateSerializer;
import com.gwghk.ims.common.vo.BaseVO;

public class ActAccountActiviStatVO extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = -1028363013175158319L;

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
    @AuthColumn(columnName = AuthColumnEnum.CHINESENAME)
    private String custName;

    /**
     * 客户-手机号
     */
    @AuthColumn(columnName = AuthColumnEnum.MOBILEPHONE)
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
    private String depositAmount;

    /**
     * 先赠-赠金
     */
    private Long beforeGold;

    /**
     * 先赠-释放赠金
     */
    private Long beforeReleaseGold;

    /**
     * 先赠-要求手数
     */
    private Long beforeRequiredLot;

    /**
     * 完成手数
     */
    private Long beforeFinishLot;

    /**
     * 后赠-赠金
     */
    private Long afterGold;

    /**
     * 后赠-完成手数
     */
    private Long afterFinishLot;

    /**
     * 扣回的赠金
     */
    private Long discountGold;

    /**
     * 清算状态(0: 未清算 1:待清算  2:清算中 3:清算成功 4:清算失败 )
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

    /**
     * 账号
     * @return account_no 账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 账号
     * @param accountNo 账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
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

    public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	/**
     * 客户-姓名
     * @return cust_name 客户-姓名
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 客户-姓名
     * @param custName 客户-姓名
     */
    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    /**
     * 客户-手机号
     * @return cust_mobile 客户-手机号
     */
    public String getCustMobile() {
        return custMobile;
    }

    /**
     * 客户-手机号
     * @param custMobile 客户-手机号
     */
    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile == null ? null : custMobile.trim();
    }

    /**
     * 活动编号
     * @return act_no 活动编号
     */
    public String getActNo() {
        return actNo;
    }

    /**
     * 活动编号
     * @param actNo 活动编号
     */
    public void setActNo(String actNo) {
        this.actNo = actNo == null ? null : actNo.trim();
    }

    /**
     * 活动名称
     * @return act_name 活动名称
     */
    public String getActName() {
        return actName;
    }

    /**
     * 活动名称
     * @param actName 活动名称
     */
    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    /**
     * 活动开始时间
     * @return act_start_time 活动开始时间
     */
	@JsonSerialize(using = CustomDateSerializer.class)
    public Date getActStartTime() {
        return actStartTime;
    }

    /**
     * 活动开始时间
     * @param actStartTime 活动开始时间
     */
    public void setActStartTime(Date actStartTime) {
        this.actStartTime = actStartTime;
    }

    /**
     * 活动结束时间
     * @return act_end_time 活动结束时间
     */
	@JsonSerialize(using = CustomDateSerializer.class)
    public Date getActEndTime() {
        return actEndTime;
    }

    /**
     * 活动结束时间
     * @param actEndTime 活动结束时间
     */
    public void setActEndTime(Date actEndTime) {
        this.actEndTime = actEndTime;
    }

    /**
     * 参与时间
     * @return join_time 参与时间
     */
	@JsonSerialize(using = CustomDateSerializer.class)
    public Date getJoinTime() {
        return joinTime;
    }

    /**
     * 参与时间
     * @param joinTime 参与时间
     */
    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * 入金
     * @return deposit_amount 入金
     */
    public String getDepositAmount() {
        return depositAmount;
    }

    /**
     * 入金
     * @param depositAmount 入金
     */
    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount == null ? null : depositAmount.trim();
    }

    /**
     * 先赠-赠金
     * @return before_gold 先赠-赠金
     */
    public Long getBeforeGold() {
        return beforeGold;
    }

    /**
     * 先赠-赠金
     * @param beforeGold 先赠-赠金
     */
    public void setBeforeGold(Long beforeGold) {
        this.beforeGold = beforeGold;
    }

    /**
     * 先赠-释放赠金
     * @return before_release_gold 先赠-释放赠金
     */
    public Long getBeforeReleaseGold() {
        return beforeReleaseGold;
    }

    /**
     * 先赠-释放赠金
     * @param beforeReleaseGold 先赠-释放赠金
     */
    public void setBeforeReleaseGold(Long beforeReleaseGold) {
        this.beforeReleaseGold = beforeReleaseGold;
    }

    /**
     * 先赠-要求手数
     * @return before_required_lot 先赠-要求手数
     */
    public Long getBeforeRequiredLot() {
        return beforeRequiredLot;
    }

    /**
     * 先赠-要求手数
     * @param beforeRequiredLot 先赠-要求手数
     */
    public void setBeforeRequiredLot(Long beforeRequiredLot) {
        this.beforeRequiredLot = beforeRequiredLot;
    }

    /**
     * 完成手数
     * @return before_finish_lot 完成手数
     */
    public Long getBeforeFinishLot() {
        return beforeFinishLot;
    }

    /**
     * 完成手数
     * @param beforeFinishLot 完成手数
     */
    public void setBeforeFinishLot(Long beforeFinishLot) {
        this.beforeFinishLot = beforeFinishLot;
    }

    /**
     * 后赠-赠金
     * @return after_gold 后赠-赠金
     */
    public Long getAfterGold() {
        return afterGold;
    }

    /**
     * 后赠-赠金
     * @param afterGold 后赠-赠金
     */
    public void setAfterGold(Long afterGold) {
        this.afterGold = afterGold;
    }

    /**
     * 后赠-完成手数
     * @return after_finish_lot 后赠-完成手数
     */
    public Long getAfterFinishLot() {
        return afterFinishLot;
    }

    /**
     * 后赠-完成手数
     * @param afterFinishLot 后赠-完成手数
     */
    public void setAfterFinishLot(Long afterFinishLot) {
        this.afterFinishLot = afterFinishLot;
    }

    /**
     * 扣回的赠金
     * @return discount_gold 扣回的赠金
     */
    public Long getDiscountGold() {
        return discountGold;
    }

    /**
     * 扣回的赠金
     * @param discountGold 扣回的赠金
     */
    public void setDiscountGold(Long discountGold) {
        this.discountGold = discountGold;
    }

    /**
     * 清算状态(1: 未清算 2:清算中 3:清算成功 4:清算失败 )
     * @return settle_status 清算状态(1: 未清算 2:清算中 3:清算成功 4:清算失败 )
     */
    public Integer getSettleStatus() {
        return settleStatus;
    }

    /**
     * 清算状态(1: 未清算 2:清算中 3:清算成功 4:清算失败 )
     * @param settleStatus 清算状态(1: 未清算 2:清算中 3:清算成功 4:清算失败 )
     */
    public void setSettleStatus(Integer settleStatus) {
        this.settleStatus = settleStatus;
    }

    /**
     * 清算方式(1:人工清算 2:自动清算)
     * @return settle_mode 清算方式(1:人工清算 2:自动清算)
     */
    public Integer getSettleMode() {
        return settleMode;
    }

    /**
     * 清算方式(1:人工清算 2:自动清算)
     * @param settleMode 清算方式(1:人工清算 2:自动清算)
     */
    public void setSettleMode(Integer settleMode) {
        this.settleMode = settleMode;
    }

    /**
     * 预计清算时间
     * @return about_settle_time 预计清算时间
     */
	@JsonSerialize(using = CustomDateSerializer.class)
    public Date getAboutSettleTime() {
        return aboutSettleTime;
    }

    /**
     * 预计清算时间
     * @param aboutSettleTime 预计清算时间
     */
    public void setAboutSettleTime(Date aboutSettleTime) {
        this.aboutSettleTime = aboutSettleTime;
    }

    /**
     * 实际清算时间
     * @return real_settle_time 实际清算时间
     */
	@JsonSerialize(using = CustomDateSerializer.class)
    public Date getRealSettleTime() {
        return realSettleTime;
    }

    /**
     * 实际清算时间
     * @param realSettleTime 实际清算时间
     */
    public void setRealSettleTime(Date realSettleTime) {
        this.realSettleTime = realSettleTime;
    }

}