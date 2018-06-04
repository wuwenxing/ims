package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.vo.system.ParamVO;

/**
 * 任务对外封装对象
 * @author eva
 *
 */
public class ActTaskConfigVO  implements Serializable{
	 
 


	/**
	 * 
	 */
	private static final long serialVersionUID = 1521230444877733587L;

	/**
	 * 存储的属性名
	 */
	private String proName;

	 
	/**
	 * 任务编号
	 */
	 
	private String code;
	
	/**
	 * 任务名称
	 */
	private String taskName;
	
	/**
	 * 是否循环型任务，true:是，false:不是,默认false
	 */
	 
	private Boolean isCycleTask=false;
	
	/**
	 * 任务参数集合
	 */
	private List<ParamVO> params;
	
	/**
	 * 任务参数map集合 key:物品种类,value:参数配置
	 */
	private Map<String,ActTaskItemConfigVO> taskItemsMap;
	
	/**
	 * 子任务
	 */
	private ActTaskConfigVO subTaskConfig;
	
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
	 * 任务类型，系统任务还是自定义任务(ActTaskEnum)
	 */
	public Integer taskType;
	
	/**
	 * 任务限制次数,允许添加的次数,null表示不限制
	 */
	private Integer limitTimes;
	/**
	 * 是否启用
	 */
	public String enableFlag="Y";

	/**
	 * 是否删除
	 */
	public String deleteFlag="N";
	
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
 
	public List<ParamVO> getParams() {
		return params;
	}

	public void setParams(List<ParamVO> params) {
		this.params = params;
	}

	public Map<String, ActTaskItemConfigVO> getTaskItemsMap() {
		return taskItemsMap;
	}

	public void setTaskItemsMap(Map<String, ActTaskItemConfigVO> taskItemsMap) {
		this.taskItemsMap = taskItemsMap;
	}

	public ActTaskConfigVO getSubTaskConfig() {
		return subTaskConfig;
	}

	public void setSubTaskConfig(ActTaskConfigVO subTaskConfig) {
		this.subTaskConfig = subTaskConfig;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	
	
	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

 

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getLimitTimes() {
		return limitTimes;
	}

	public void setLimitTimes(Integer limitTimes) {
		this.limitTimes = limitTimes;
	}

	@Override
	public String toString() {
		return "ActTaskConfigVO [proName=" + proName + ", code=" + code
				+ ", taskName=" + taskName + ", isCycleTask=" + isCycleTask
				+ ", params=" + params + ", taskItemsMap=" + taskItemsMap
				+ ", subTaskConfig=" + subTaskConfig + ", desc=" + desc
				+ ", taskMinOrder=" + taskMinOrder + ", taskMaxOrder="
				+ taskMaxOrder + ", taskAccountType=" + taskAccountType
				+ ", taskType=" + taskType + ", limitTimes=" + limitTimes
				+ ", enableFlag=" + enableFlag + ", deleteFlag=" + deleteFlag
				+ "]";
	}

	  
	 
 
 
}
