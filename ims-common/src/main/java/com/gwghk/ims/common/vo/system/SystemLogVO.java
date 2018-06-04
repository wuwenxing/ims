package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：系统-日志请求VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemLogVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -9188813106332882881L;

	/**
	 * 用户
	 */
	private Long id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户No
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

	/**
	 * 操作内容
	 */
	private String operText;

	/**
	 * 时间
	 */
	private String startTime;
	private String endTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
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

	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

	public String getOperText() {
		return operText;
	}

	public void setOperText(String operText) {
		this.operText = operText;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "[userName=" + userName + ", userNo=" + userNo + ", descr=" + descr
				+ ", method=" + method + ", params=" + params + ", broswer=" + broswer+"]";
	}
}
