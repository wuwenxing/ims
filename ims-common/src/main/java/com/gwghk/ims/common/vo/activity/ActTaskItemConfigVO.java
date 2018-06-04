package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.List;

import com.gwghk.ims.common.vo.system.ParamVO;

/**
 * 奖励物品项
 * @author eva
 *
 */
public class ActTaskItemConfigVO   implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5726144014931794807L;
	
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
	private List<ParamVO> params;
	
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

	public List<ParamVO> getParams() {
		return params;
	}

	public void setParams(List<ParamVO> params) {
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
		return "ActTaskItemConfigVO [proName=" + proName + ", itemType="
				+ itemType + ", params=" + params + ", desc=" + desc + "]";
	}
 

	 
}

