package com.gwghk.ims.activity.context;

import java.util.List;

/**
 * 奖励物品项
 * @author eva
 *
 */
public class ActTaskItemBean {
	/**
	 * 属性名
	 */
	private String proName;
 
	/**
	 * 物品类型
	 */
	private String itemType;
	
	/**
	 * 奖励物品参数集合
	 */
	private List<ParamBean> params;
	
	/**
	 * 描述
	 */
	private String desc;
 

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public List<ParamBean> getParams() {
		return params;
	}

	public void setParams(List<ParamBean> params) {
		this.params = params;
	}

	
	
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	@Override
	public String toString() {
		return "ActTaskItemBean [proName=" + proName + ", itemType=" + itemType
				+ ", params=" + params + ", desc=" + desc + "]";
	}

	 
}

