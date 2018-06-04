package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机流量通道枚举
 */
public enum RechargeTypeEnum {

	flow("流量", "flow"),
	online("话费", "online");

	private final String name;
	private final String code;

	RechargeTypeEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static List<RechargeTypeEnum> getList() {
		List<RechargeTypeEnum> result = new ArrayList<RechargeTypeEnum>();
		for (RechargeTypeEnum ae : RechargeTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String code) {
		for (RechargeTypeEnum ae : RechargeTypeEnum.values()) {
			if (ae.getCode().equals(code)) {
				return ae.getName();
			}
		}
		return code;
	}
	
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	
}
