package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：任务管理VO
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月28日
 */
public class ActTaskCustomSettingVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1870473190680050362L;

	private Long id;

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
    private Integer taskType;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 查询开始时间
     */
    private String startDate ;
    /**
     * 查询结束时间
     */
    private String endDate ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}