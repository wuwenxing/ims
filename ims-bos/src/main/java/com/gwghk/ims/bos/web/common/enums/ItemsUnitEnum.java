package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：物品单位
 * 
 * @author 
 * @version 1.0
 * @Date 2017年7月24日
 */
public enum ItemsUnitEnum implements EnumIntf {

	M("M", "M"),
	G("G", "G"),
	RMB("元", "RMB"),
	USD("美元", "USD"),
	Month("个月", "Month"),
	lot("手", "lot");
	
	private final String value;
	private final String labelKey;
	ItemsUnitEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<ItemsUnitEnum> getList() {
		List<ItemsUnitEnum> result = new ArrayList<ItemsUnitEnum>();
		for (ItemsUnitEnum ae : ItemsUnitEnum.values()) {
			result.add(ae);
		}
		return result;
	}
	
	public static List<ItemsUnitEnum> getList_1() {
		List<ItemsUnitEnum> result = new ArrayList<ItemsUnitEnum>();
		result.add(ItemsUnitEnum.M);
		result.add(ItemsUnitEnum.G);
		return result;
	}
	
	public static String format(String labelKey){
		for(ItemsUnitEnum ae : ItemsUnitEnum.values()){
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
