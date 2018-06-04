package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：物品种类
 * 
 * @author eva
 * @version 1.0
 * @Date 2017年7月24日
 */
public enum ActItemCategoryEnum implements EnumIntf {

	/** 实物物品 */
	REAL("流量", "mobile_data"),
	/** 虚拟物品 */
	VIRTUAL("话费", "mobile_charge"),
	/** 虚拟物品 */
	INTERFACE("会员", "member_vip");

	private final String value;
	private final String labelKey;

	ActItemCategoryEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<ActItemCategoryEnum> getList() {
		List<ActItemCategoryEnum> result = new ArrayList<ActItemCategoryEnum>();
		for (ActItemCategoryEnum ae : ActItemCategoryEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(ActItemCategoryEnum ae : ActItemCategoryEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

}
