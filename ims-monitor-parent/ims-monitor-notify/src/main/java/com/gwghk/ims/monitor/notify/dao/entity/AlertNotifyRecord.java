package com.gwghk.ims.monitor.notify.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;
import com.gwghk.ims.common.util.CustomDateSerializer;

@Table(name="alert_notify_record")
public class AlertNotifyRecord {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long staffId;
	//告警发送状态json
	private String notifyStatusJson;
	private String alertContent;
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date alertDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getNotifyStatusJson() {
		return notifyStatusJson;
	}
	public void setNotifyStatusJson(String notifyStatusJson) {
		this.notifyStatusJson = notifyStatusJson;
	}
	public String getAlertContent() {
		return alertContent;
	}
	public void setAlertContent(String alertContent) {
		this.alertContent = alertContent;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getAlertDate() {
		return alertDate;
	}
	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}
	
	
}
