package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * 摘要：模拟账户增加或扣除金额-实体
 * 
 * @author wayne.wu
 * @version 1.0
 * @Date 2017年12月30日
 */
public class DemoCashAdjustDto implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
     * 平台账号
     */
    private String accountNo;

	/**
     * 平台类型
     */
	private String platform;

	/**
     * 账户类型（demo或者real）
     */
	private String env;
    
    /**
     * 模拟金额
     */
	private BigDecimal amount;
    
    /**
     * 模拟保留金
     */
	private BigDecimal demoKeepAmount;
    
    /**
     * 新增1、扣除0
     */
	private String operateType;

    /**
     * 公司id
     */
    private Long companyId;

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

	public Long getCompanyId() {
		return companyId;
	}

	public String getCompanyCode() {
		return CompanyEnum.getCodeById(this.getCompanyId()) ;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDemoKeepAmount() {
		return demoKeepAmount;
	}

	public void setDemoKeepAmount(BigDecimal demoKeepAmount) {
		this.demoKeepAmount = demoKeepAmount;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
    
}