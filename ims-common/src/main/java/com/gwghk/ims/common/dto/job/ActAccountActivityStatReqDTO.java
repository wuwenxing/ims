package com.gwghk.ims.common.dto.job;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 
 * 摘要：活动查询请求
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月26日
 */
public class ActAccountActivityStatReqDTO extends BaseDTO {

	/**
	 * 账户号码
	 */
	private String accountNo;
	/**
	 * 活动开始时间
	 */
	private String  beginTime ;
	/**
	 * 活动结束时间
	 */
	private String  endTime ;
	/**
	 * 活动状态  0进行中  1已结束
	 */
	private Integer  actState ;
	
	private String companyCode ;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getActState() {
		return actState;
	}

	public void setActState(Integer actState) {
		this.actState = actState;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
