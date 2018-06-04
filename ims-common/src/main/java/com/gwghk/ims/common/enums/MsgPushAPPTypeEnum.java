package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息推送APP类型
 */
public enum MsgPushAPPTypeEnum implements EnumIntf {

	FX_GTS2("FX GTS2", "FX_GTS2"),
	FXDEMO_GTS2("FXDEMO GTS2", "FXDEMO_GTS2"),
	HX_GTS2("HX GTS2", "HX_GTS2"),
	HXFX_GTS2("HXFX GTS2", "HXFX_GTS2"),
	PM_GTS2("PM GTS2", "PM_GTS2"),
	PM_GTS("PM GTS", "PM_GTS");

	private final String name;
	private final String code;

	MsgPushAPPTypeEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static List<MsgPushAPPTypeEnum> getList() {
		List<MsgPushAPPTypeEnum> result = new ArrayList<MsgPushAPPTypeEnum>();
		for (MsgPushAPPTypeEnum ae : MsgPushAPPTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String code) {
		for (MsgPushAPPTypeEnum ae : MsgPushAPPTypeEnum.values()) {
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
