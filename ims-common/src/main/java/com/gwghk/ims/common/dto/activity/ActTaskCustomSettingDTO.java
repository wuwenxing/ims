package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;

import com.gwghk.ims.common.dto.BaseDTO;



public class ActTaskCustomSettingDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1870473190680050362L;

	private Integer id;

	 /**
     * 任务编号
     */
    private String taskCode;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 规则识别码
     */
    private String ruleCode;

    /**
     * 任务类型 1:自定义任务 2:系统内置任务
     */
    private Boolean taskType;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 任务编号
     */
    private String taskNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public Boolean getTaskType() {
		return taskType;
	}

	public void setTaskType(Boolean taskType) {
		this.taskType = taskType;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	
}