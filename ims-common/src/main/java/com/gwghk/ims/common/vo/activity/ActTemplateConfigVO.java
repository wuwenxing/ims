package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.vo.system.ParamVO;

/**
 * 活动模板配置
 * @author eva
 *
 */
public class ActTemplateConfigVO  implements Serializable{
	
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4628974675526683184L;

	/**
	 * 活动基本信息动态参数
	 */
	private List<ParamVO> baseParams; 
	
	/**
	 * 活动参与条件动态参数
	 */
	private Map<String,List<ParamVO>> condParams;
	
	/**
	 * 活动下的任务物品动态配置
	 */
	private Map<String,ActTaskConfigVO>  taskConfigs;
	
	
    
	public List<ParamVO> getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(List<ParamVO> baseParams) {
		this.baseParams = baseParams;
	}

	public Map<String, List<ParamVO>> getCondParams() {
		return condParams;
	}

	public void setCondParams(Map<String, List<ParamVO>> condParams) {
		this.condParams = condParams;
	}

	public Map<String, ActTaskConfigVO> getTaskConfigs() {
		return taskConfigs;
	}

	public void setTaskConfigs(Map<String, ActTaskConfigVO> taskConfigs) {
		this.taskConfigs = taskConfigs;
	}

	 
	  
}