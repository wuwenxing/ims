package com.gwghk.sys.api.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：系统-日志实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年9月23日
 */
public class SystemLog extends BaseEntity {
	private Long id;
	
	/**
	 * 用户名
	 */
	private String userNo;
	
	/**
	 * 描述
	 */
	private String descr;

	/**
	 * 日志模块类型
	 */
	private String logType;
	
	/**
	 * 操作方法
	 */
	private String method;
	
	/**
	 * 请求参数
	 */
	private String params;
	
	/**
	 * 浏览器
	 */
	private String broswer;
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
	
}
