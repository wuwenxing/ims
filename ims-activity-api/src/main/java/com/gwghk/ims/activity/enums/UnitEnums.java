package com.gwghk.ims.activity.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 摘要：单位枚举
 * 
 * @author warren
 * @version 1.0
 * @Date 2017年10月26日
 */
public enum UnitEnums {
	/** 美元 */
	USD("USD", "美元"),
	/** 人民币 */
	RMB("RMB", "元"),
	/** % */
	PERCENT("%", "%"),
	/** 个月 */
	MONTH("Month", "个月"),
	/** 流量（M） */
	FLOW_M("M", "M"),
	/** 流量（G） */
	FLOW_G("G", "G");

	private final String code;
	private final String name;

	UnitEnums(String _code, String name) {
		this.code = _code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static List<UnitEnums> getList() {
		List<UnitEnums> result = new ArrayList<UnitEnums>();
		for (UnitEnums ae : UnitEnums.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String getNameByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (UnitEnums ae : UnitEnums.values()) {
				if (code.equals(ae.getCode())) {
					return ae.getName();
				}
			}
		}
		return "";
	}
}
