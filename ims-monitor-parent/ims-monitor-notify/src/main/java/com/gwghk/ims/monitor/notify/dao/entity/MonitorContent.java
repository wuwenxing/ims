package com.gwghk.ims.monitor.notify.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Table(name="monitor_content")
public class MonitorContent {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long itemId;
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date requestDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date responceDate;
	private String dataJson;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getResponceDate() {
		return responceDate;
	}
	public void setResponceDate(Date responceDate) {
		this.responceDate = responceDate;
	}
	public String getDataJson() {
		return dataJson;
	}
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	
}
