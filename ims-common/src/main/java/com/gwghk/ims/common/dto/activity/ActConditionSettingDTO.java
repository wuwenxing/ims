package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：活动条件DTO
 * 
 * @author
 * @version 1.0
 * @Date 2017年5月12日
 */
public class ActConditionSettingDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 5410687139301773057L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 活动编号
	 */
	private String activityPeriods;

	/**
	 * 条件类型
	 */
	private String conditionType;

	/**
	 * 条件值
	 */
	private String conditionVal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionVal() {
		return conditionVal;
	}

	public void setConditionVal(String conditionVal) {
		this.conditionVal = conditionVal;
	}

}