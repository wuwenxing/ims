package com.gwghk.ims.common.enums;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActEnv
 * @Description: 参与活动的环境
 * @author lawrence
 * @date 2017年6月5日
 *
 */
public enum ActEnvEnum {
	/**真实*/
	REAL("real", "真实"),
	/**模拟*/
	DEMO("demo", "模拟");

	private final String value;
	private final String labelKey;

	ActEnvEnum(String value, String labelKey) {
		this.value = value;
		this.labelKey = labelKey;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
	public static String getLabelKeyByValue(String value) {
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		}
		for(ActEnvEnum ae : ActEnvEnum.values()) {
			if (value.equals(ae.getValue())) {
				return ae.getLabelKey();
			}
		}
		return null;
	}
}