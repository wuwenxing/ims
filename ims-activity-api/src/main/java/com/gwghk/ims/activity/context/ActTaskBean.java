package com.gwghk.ims.activity.context;

import java.util.List;
import java.util.Map;

 
public class ActTaskBean {
 
	/**
	 * 存储的属性名
	 */
	private String proName;
	/**
	 * 活动编号
	 */
	private String code;
	
	/**
	 * 是否循环型任务，true:是，false:不是,默认false
	 */
	private Boolean isCycleTask=false;
	
	/**
	 * 任务参数配置
	 */
	private List<ParamBean> params;
	
	/**
	 * 奖励物品配置
	 */
	private Map<String,ActTaskItemBean> taskItemsMap;
	
	/**
	 * 子任务配置
	 */
	private ActTaskBean subTaskConfig;
	
	
	/**
	 * 描述
	 */
	private String desc;


	  
	/**
	 * 任务支持的最小序号
	 */
	private Integer taskMinOrder;
 
 
	/**
	 * 任务支持的最大序号
	 */
	private Integer taskMaxOrder;
	
	/**
	 * 任务账号类型 real:支持真实账号 demo:支持模拟账号 ，为空，所有都支持
	 */
	private String taskAccountType;
	
	/**
	 * 任务限制次数,允许添加的次数,为空表示不限制
	 */
	private Integer limitTimes;
	
	/**
	 * 支持的奖励物品类型
	 */
	private String taskItemTypes;
		
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Boolean getIsCycleTask() {
		return isCycleTask;
	}


	public void setIsCycleTask(Boolean isCycleTask) {
		this.isCycleTask = isCycleTask;
	}


	public List<ParamBean> getParams() {
		return params;
	}


	public void setParams(List<ParamBean> params) {
		this.params = params;
	}
 
	public Map<String, ActTaskItemBean> getTaskItemsMap() {
		return taskItemsMap;
	}


	public void setTaskItemsMap(Map<String, ActTaskItemBean> taskItemsMap) {
		this.taskItemsMap = taskItemsMap;
	}

 
	public ActTaskBean getSubTaskConfig() {
		return subTaskConfig;
	}


	public void setSubTaskConfig(ActTaskBean subTaskConfig) {
		this.subTaskConfig = subTaskConfig;
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	 

	public String getProName() {
		return proName;
	}


	public void setProName(String proName) {
		this.proName = proName;
	}


	public Integer getTaskMinOrder() {
		return taskMinOrder;
	}


	public void setTaskMinOrder(Integer taskMinOrder) {
		this.taskMinOrder = taskMinOrder;
	}


	public Integer getTaskMaxOrder() {
		return taskMaxOrder;
	}


	public void setTaskMaxOrder(Integer taskMaxOrder) {
		this.taskMaxOrder = taskMaxOrder;
	}


	public String getTaskAccountType() {
		return taskAccountType;
	}


	public void setTaskAccountType(String taskAccountType) {
		this.taskAccountType = taskAccountType;
	}


	public Integer getLimitTimes() {
		return limitTimes;
	}


	public void setLimitTimes(Integer limitTimes) {
		this.limitTimes = limitTimes;
	}


	public String getTaskItemTypes() {
		return taskItemTypes;
	}


	public void setTaskItemTypes(String taskItemTypes) {
		this.taskItemTypes = taskItemTypes;
	}


	@Override
	public String toString() {
		return "ActTaskBean [proName=" + proName + ", code=" + code
				+ ", isCycleTask=" + isCycleTask + ", params=" + params
				+ ", taskItemsMap=" + taskItemsMap + ", subTaskConfig="
				+ subTaskConfig + ", desc=" + desc + ", taskMinOrder="
				+ taskMinOrder + ", taskMaxOrder=" + taskMaxOrder
				+ ", taskAccountType=" + taskAccountType + ", limitTimes="
				+ limitTimes + ", taskItemTypes=" + taskItemTypes + "]";
	}


 
	 
}
