package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;

/**
 * 
 * @ClassName: ActTaskSettingParamsValJson
 * @Description: 活动任务设置动态参数值
 * @author eva
 * @date 2018年4月10日
 *
 */
public class ActTaskSettingParamsValJson {
 
	 /**
     * 单个任务项值
     */
    private String taskItemVal;
    
    /**
     * 单个任务项值单位
     */
    private String taskItemValUnit;
    
    /**
     * 单个任务项值2
     */
    private String taskItemVal2;
    
    /**
     * 单位任务项值2单位
     */
    private String taskItemVal2Unit;

    /**
     * 任务时间（当前默认小时为单位)
     */
    private BigDecimal taskItemTime;
    
    /**
     * 时间单位
     */
    private String taskItemTimeUnit;

	public String getTaskItemVal() {
		return taskItemVal;
	}

	public void setTaskItemVal(String taskItemVal) {
		this.taskItemVal = taskItemVal;
	}
 

	public String getTaskItemVal2() {
		return taskItemVal2;
	}

	public void setTaskItemVal2(String taskItemVal2) {
		this.taskItemVal2 = taskItemVal2;
	}

	 
	public String getTaskItemValUnit() {
		return taskItemValUnit;
	}

	public void setTaskItemValUnit(String taskItemValUnit) {
		this.taskItemValUnit = taskItemValUnit;
	}

	public String getTaskItemVal2Unit() {
		return taskItemVal2Unit;
	}

	public void setTaskItemVal2Unit(String taskItemVal2Unit) {
		this.taskItemVal2Unit = taskItemVal2Unit;
	}

	public BigDecimal getTaskItemTime() {
		return taskItemTime;
	}

	public void setTaskItemTime(BigDecimal taskItemTime) {
		this.taskItemTime = taskItemTime;
	}

	public String getTaskItemTimeUnit() {
		return taskItemTimeUnit;
	}

	public void setTaskItemTimeUnit(String taskItemTimeUnit) {
		this.taskItemTimeUnit = taskItemTimeUnit;
	}

}
