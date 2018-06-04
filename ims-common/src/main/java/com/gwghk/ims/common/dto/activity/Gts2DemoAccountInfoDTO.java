package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.util.Date;

/**
 * 摘要：封装Demo帐户信息
 * @author Benny.yan
 * @version 1.0
 * @Date 2017年9月6日
 */
public class Gts2DemoAccountInfoDTO implements Serializable{

	private static final long serialVersionUID = -8185722708237990445L;

	/**
	 * 账号
	 */
	private String accountNo;
	
	/**
	 * 手机号-不带区号
	 */
	private String mobilePhone;
	
	/**
	 * 姓名
	 */
	private String chineseName;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 账户币种
	 */
	private String currency;
	
	/**
	 * 账户平台
	 */
	private String platform;
	
	/**
	 * 客户统一账户
	 */
	private Long customerNumber;
	
	/**
	 * 公司ID
	 */
	private Long companyId;
	
	/**
     * 账号状态
     */
    private String accountStatus;
    
    private String accountCategory;
    
    private String accountLevel;
    
    private Date registerTime;
    
    /**
     * 当前时间
     */
    private Date now;
    
    /**
     * 可领开始时间,(单位：ms)
     */
    private Long canLotteryStartTime;
    
    /**
     * 可领截止时间,(单位：ms)
     */
    private Long canLotteryEndTime;

    /**
     * 红包有效截止时间,(单位：ms)
     */
    private Long redBagValidEndTime;
    
    
    /**
     * 可领倒计时,(单位：ms)
     */
    private Long diff1Day;
    
    /**
     * 红包有效倒计时,(单位：ms)
     */
    private Long diff7Day;
    
    /**
     * 当前时间是否在假期中 true:是;false:否 
     */
    private boolean nowInHoliday;
    
    /**
     * 当前时间是否在注册后24小时内(交易时段)true:是;false:否
     */
    private boolean nowIn24Hours;
    
    /**
     * 当前时间是否在注册七天内(自然日) true:是;false:否
     */
    private boolean nowIn7Days;
    
    
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Long getCanLotteryStartTime() {
		return canLotteryStartTime;
	}

	public void setCanLotteryStartTime(Long canLotteryStartTime) {
		this.canLotteryStartTime = canLotteryStartTime;
	}

	public Long getCanLotteryEndTime() {
		return canLotteryEndTime;
	}

	public void setCanLotteryEndTime(Long canLotteryEndTime) {
		this.canLotteryEndTime = canLotteryEndTime;
	}

	public Long getRedBagValidEndTime() {
		return redBagValidEndTime;
	}

	public void setRedBagValidEndTime(Long redBagValidEndTime) {
		this.redBagValidEndTime = redBagValidEndTime;
	}

	public Long getDiff1Day() {
		return diff1Day;
	}

	public void setDiff1Day(Long diff1Day) {
		this.diff1Day = diff1Day;
	}

	public Long getDiff7Day() {
		return diff7Day;
	}

	public void setDiff7Day(Long diff7Day) {
		this.diff7Day = diff7Day;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public boolean isNowInHoliday() {
		return nowInHoliday;
	}

	public void setNowInHoliday(boolean nowInHoliday) {
		this.nowInHoliday = nowInHoliday;
	}

	public boolean isNowIn24Hours() {
		return nowIn24Hours;
	}

	public void setNowIn24Hours(boolean nowIn24Hours) {
		this.nowIn24Hours = nowIn24Hours;
	}

	public boolean isNowIn7Days() {
		return nowIn7Days;
	}

	public void setNowIn7Days(boolean nowIn7Days) {
		this.nowIn7Days = nowIn7Days;
	}

	@Override
	public String toString() {
		return "GtsDemoAccountInfoDTO [accountNo=" + accountNo
				+ ", accountStatus=" + accountStatus + ", accountCategory="
				+ accountCategory + ", accountLevel=" + accountLevel
				+ ", registerTime=" + registerTime + ", now=" + now
				+ ", canLotteryStartTime=" + canLotteryStartTime
				+ ", canLotteryEndTime=" + canLotteryEndTime
				+ ", redBagValidEndTime=" + redBagValidEndTime + ", diff1Day="
				+ diff1Day + ", diff7Day=" + diff7Day + "]";
	}
}