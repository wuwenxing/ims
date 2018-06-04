package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息推送渠道类型
 */
public enum MsgPushChannelEnum implements EnumIntf {

	SMS("短信", "SMS"),
	Email("邮件", "Email"),
	IOS("IOS", "IOS"),
	Android("安卓", "Android"),
	PCUI("PCUI", "PCUI"),
	wxChart("微信", "wxChart");

	private final String name;
	private final String code;

	MsgPushChannelEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static List<MsgPushChannelEnum> getList() {
		List<MsgPushChannelEnum> result = new ArrayList<MsgPushChannelEnum>();
		for (MsgPushChannelEnum ae : MsgPushChannelEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String code) {
		for (MsgPushChannelEnum ae : MsgPushChannelEnum.values()) {
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
