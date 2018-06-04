package com.gwghk.ims.common.vo.activity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 
 * 摘要：客户资料表,demo和real的共用vo
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月24日
 */
public class ActCustomerInfoVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	/**
     * 客户交易主键id
     */
    private Integer id;

    /**
     * 账户名称
     */
    private String accountNo;

    /**
     * 中文名
     */
    private String chineseName;

    /**
     * 平台
     */
    private String platform;

    /**
     * 账户币种
     */
    private String accountCurrency;

    /**
     * 账户级别，MINI:迷你，stand：标准，VIP：vip账户
     */
    private String accountLevel;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账号环境，real：真实，demo：模拟，test：测试
     */
    private String accountEnv;

    /**
     * 激活状态，Y：激活，N：未激活
     */
    private String activatedStatus;

    /**
     * 账号状态，A:启用 S:禁用 D:注销
     */
    private String accountStatus;

    /**
     * 是否全局黑名单
     */
    private String isBlacklist;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName = "register_time", propName = "registerTime", order = "desc")
    private Date registerTime;

    /**
     * 激活时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName = "activated_time", propName = "activatedTime", order = "desc")
    private Date activatedTime;

    /**
     * 平台#账户币种
     */
    private String platformCurrency;

    /**
     * 开户时间
     */
    private Date accountCreateDate;

    /**
     * 账户更新时间
     */
    private Date accountUpdateDate;
    
    /**
     * 账号类型  demo或real
     */
    private String accountType ;
    
    /**
     * 注册时间开始
     */
    private String registerStartTime ;
    /**
     * 注册时间结束
     */
    private String registerEndTime ;
    
    /**激活时间 开始*/
    private String activatedStartTime ;
    /**激活时间 结束*/
    private String activatedEndTime ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountEnv() {
		return accountEnv;
	}

	public void setAccountEnv(String accountEnv) {
		this.accountEnv = accountEnv;
	}

	public String getActivatedStatus() {
		return activatedStatus;
	}

	public void setActivatedStatus(String activatedStatus) {
		this.activatedStatus = activatedStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(String isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getActivatedTime() {
		return activatedTime;
	}

	public void setActivatedTime(Date activatedTime) {
		this.activatedTime = activatedTime;
	}

	public String getPlatformCurrency() {
		return platformCurrency;
	}

	public void setPlatformCurrency(String platformCurrency) {
		this.platformCurrency = platformCurrency;
	}

	public Date getAccountCreateDate() {
		return accountCreateDate;
	}

	public void setAccountCreateDate(Date accountCreateDate) {
		this.accountCreateDate = accountCreateDate;
	}

	public Date getAccountUpdateDate() {
		return accountUpdateDate;
	}

	public void setAccountUpdateDate(Date accountUpdateDate) {
		this.accountUpdateDate = accountUpdateDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRegisterStartTime() {
		return registerStartTime;
	}

	public void setRegisterStartTime(String registerStartTime) {
		this.registerStartTime = registerStartTime;
	}

	public String getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(String registerEndTime) {
		this.registerEndTime = registerEndTime;
	}

	public String getActivatedStartTime() {
		return activatedStartTime;
	}

	public void setActivatedStartTime(String activatedStartTime) {
		this.activatedStartTime = activatedStartTime;
	}

	public String getActivatedEndTime() {
		return activatedEndTime;
	}

	public void setActivatedEndTime(String activatedEndTime) {
		this.activatedEndTime = activatedEndTime;
	}
    
}
