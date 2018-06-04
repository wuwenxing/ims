package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 星期
 */
public enum WeekDaysEnum implements EnumIntf {
	
	SUNDAY("星期日", "0"),
	MONDAY("星期一", "1"),
	TUESDAY("星期二", "2"),
	WEDNESDAY("星期三", "3"),
	THURSDAY("星期四", "4"),
	FRIDAY("星期五", "5"),
	SATURDAY("星期六", "6");
	
	private final String labelKey;
	private final String value;
	WeekDaysEnum(String key, String value) {
		this.labelKey = key;
		this.value = value;
	}
	
	public static List<WeekDaysEnum> getList(){
		List<WeekDaysEnum> result = new ArrayList<WeekDaysEnum>();
		for(WeekDaysEnum ae : WeekDaysEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(WeekDaysEnum ae : WeekDaysEnum.values()){
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
