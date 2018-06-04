package com.gwghk.ims.activity.context;

import java.util.List;
import java.util.Map;


/**
 * 活动模板
 * @author eva
 *
 */
public class ActivityConfigBean {
	
	private String activityType;
	
	private List<ParamBean> actBaseParams;

	private Map<String,List<ParamBean>> actCondtionParamsMap;
	
	private Map<String,ActTaskBean> actTaskConfigsMap;
	
	/**
	 * 描述
	 */
	private String desc;
	
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public String getDesc() {
		return desc;
	}

	  
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<ParamBean> getActBaseParams() {
		return actBaseParams;
	}

	public void setActBaseParams(List<ParamBean> actBaseParams) {
		this.actBaseParams = actBaseParams;
	}

	public Map<String, List<ParamBean>> getActCondtionParamsMap() {
		return actCondtionParamsMap;
	}

	public void setActCondtionParamsMap(
			Map<String, List<ParamBean>> actCondtionParamsMap) {
		this.actCondtionParamsMap = actCondtionParamsMap;
	}

	public Map<String, ActTaskBean> getActTaskConfigsMap() {
		return actTaskConfigsMap;
	}

	public void setActTaskConfigsMap(Map<String, ActTaskBean> actTaskConfigsMap) {
		this.actTaskConfigsMap = actTaskConfigsMap;
	}

	@Override
	public String toString() {
		return "ActivityConfigBean [activityType=" + activityType
				+ ", actBaseParams=" + actBaseParams
				+ ", actCondtionParamsMap=" + actCondtionParamsMap
				+ ", actTaskConfigsMap=" + actTaskConfigsMap + ", desc=" + desc
				+ "]";
	}
 
 
}

 
