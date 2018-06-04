package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;

import com.gwghk.ims.common.dto.BaseDTO;

public class ActProposalModifyDetailDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 4794425552537452483L;

	private Long id;

	private String pno;

	/**
	 * 修改的类型
	 */
	private String modifyType;

	/**
	 * PO對應的名稱如: accountStatus
	 */
	private String fieldName;

	/**
	 * PO對應的類型，如:character,timestamp等
	 */
	private String fieldType;

	/**
	 * 更新前的值
	 */
	private String fromValue;

	/**
	 * 更新後的值
	 */
	private String toValue;

	/**
	 * 更新前對應表的version_no
	 */
	private Integer fromVerionNo;

	/**
	 * 更新後對應表的version_no
	 */
	private Integer toVerionNo;

	/**
	 * 该修改属性所属的记录ID
	 */
	private Integer recordId;

	/**
	 * 该修改属性所属记录ID的父ID,多级以/分隔，,eg:
	 * -1/55/23活动下的任务修改，record_id为任务Id,record_arent_id为活动ID
	 */
	private String recordParentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFromValue() {
		return fromValue;
	}

	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}

	public String getToValue() {
		return toValue;
	}

	public void setToValue(String toValue) {
		this.toValue = toValue;
	}

	public Integer getFromVerionNo() {
		return fromVerionNo;
	}

	public void setFromVerionNo(Integer fromVerionNo) {
		this.fromVerionNo = fromVerionNo;
	}

	public Integer getToVerionNo() {
		return toVerionNo;
	}

	public void setToVerionNo(Integer toVerionNo) {
		this.toVerionNo = toVerionNo;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getRecordParentId() {
		return recordParentId;
	}

	public void setRecordParentId(String recordParentId) {
		this.recordParentId = recordParentId;
	}

}