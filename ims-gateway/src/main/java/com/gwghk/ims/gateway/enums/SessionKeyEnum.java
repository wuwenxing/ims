package com.gwghk.ims.gateway.enums;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：session key枚举类型
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public enum SessionKeyEnum implements EnumIntf {
	companyId("业务类型", "companyId"),
	client("客戶端对象", "client"),
	captcha("验证码", "captcha"),
	superAdminFlag("是否是超级管理员标示(Y/N)", "superAdminFlag"),
	menuMap("拥有权限的菜单Map集合", "menuMap"),
	topTagMenu("顶部选择的页签对象", "topTagMenu");
	
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