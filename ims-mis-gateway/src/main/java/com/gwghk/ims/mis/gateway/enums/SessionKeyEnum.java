package com.gwghk.ims.mis.gateway.enums;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：session key枚举类型
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public enum SessionKeyEnum implements EnumIntf {
	client("客戶端对象", "client"),
	captcha("验证码", "captcha")
	;
	
	private final String name;
	private final String code;
	
	SessionKeyEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	
}