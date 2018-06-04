package com.gwghk.ims.monitor.notify.dao.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;
import com.gwghk.ims.common.util.CustomDateSerializer;

@Table(name="duty_staff")
public class DutyStaff {
	private Long staffId;
	@Transient
	private String name;
	@Transient
	private List<Long> itemId;
	@Transient
	private String phone;
	@Transient
	private String email;
	@Transient
	private String otherContactJson;
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	@Transient
	private Date lastNotifyDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	@Transient
	private Date createdDate;
	
	/**
	 * 发送状态,例如{'sms':'ok','email':'fail'}
	 */
	private Map<String,Object> notifyStatusMap=new HashMap<String,Object>();
	
	public Map<String, Object> getNotifyStatusMap() {
		return notifyStatusMap;
	}

	public void setNotifyStatusMap(String name,String status) {
		notifyStatusMap.put(name, status);
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public List<Long> getItemId() {
		return itemId;
	}

	public void setItemId(List<Long> itemId) {
		this.itemId = itemId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtherContactJson() {
		return otherContactJson;
	}

	public void setOtherContactJson(String otherContactJson) {
		this.otherContactJson = otherContactJson;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastNotifyDate() {
		return lastNotifyDate;
	}

	public void setLastNotifyDate(Date lastNotifyDate) {
		this.lastNotifyDate = lastNotifyDate;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
