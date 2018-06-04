package com.gwghk.ims.common.dto.job;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 
 * 摘要：活动查询请求
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月26日
 */
public class ActAccountActivityStatRespDTO extends BaseDTO {

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

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Long getBeforeGold() {
		return beforeGold;
	}

	public void setBeforeGold(Long beforeGold) {
		this.beforeGold = beforeGold;
	}

	public Long getBeforeReleaseGold() {
		return beforeReleaseGold;
	}

	public void setBeforeReleaseGold(Long beforeReleaseGold) {
		this.beforeReleaseGold = beforeReleaseGold;
	}

	public Long getBeforeRequiredLot() {
		return beforeRequiredLot;
	}

	public void setBeforeRequiredLot(Long beforeRequiredLot) {
		this.beforeRequiredLot = beforeRequiredLot;
	}

	public Long getBeforeFinishLot() {
		return beforeFinishLot;
	}

	public void setBeforeFinishLot(Long beforeFinishLot) {
		this.beforeFinishLot = beforeFinishLot;
	}

	public Long getAfterGold() {
		return afterGold;
	}

	public void setAfterGold(Long afterGold) {
		this.afterGold = afterGold;
	}

	public Long getAfterFinishLot() {
		return afterFinishLot;
	}

	public void setAfterFinishLot(Long afterFinishLot) {
		this.afterFinishLot = afterFinishLot;
	}

	public Long getDiscountGold() {
		return discountGold;
	}

	public void setDiscountGold(Long discountGold) {
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

}
