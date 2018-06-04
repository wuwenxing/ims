package com.gwghk.ims.common.enums;

/**
 * 摘要：活动平台
 * 
 * @author eva
 * @version 1.0
 * @Date 2017年6月22日
 */
public enum ActPlatformEnum {
	GTS("GTS"), GTS2("GTS2"), LGTS2("LGTS2"), MT4("MT4"), MT5("MT5");

	private final String code;

	ActPlatformEnum(String code) {
		this.code = code;

	}

	public String getCode() {
		return code;
	}

	public static boolean isContainsCode(String code) {
		if (code != null) {
			for (ActPlatformEnum ae : ActPlatformEnum.values()) {
				if (code.equals(ae.getCode())) {
					return true;
				}
			}
		}
		return false;
	}
}
