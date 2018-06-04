package com.gwghk.ims.monitor.notify.consumer;

import java.util.Date;
/**
 * 告警记录
 * @author jackson.tang
 *
 */
public class AlertRecord {
	/**
	 * 告警级别
	 */
	public int alertLevel;
	/**
	 * 告警内容
	 */
	public String alertMsg;
	/**
	 * 告警时间
	 */
	public Date notifyDate;

	public Date getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public int getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(int alertLevel) {
		this.alertLevel = alertLevel;
	}
	
	
}
