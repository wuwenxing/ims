package com.gwghk.ims.mis.gateway.enums;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：t_system_role_column_auth对应view_type字段
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public enum RoleColumnsTypeEnum implements EnumIntf {
	customer_personal_info("客户信息", "customer_personal_info"),
	common_columns("公共信息", "common_columns")
	;
	
	private final String name;
	private final String code;
	
	RoleColumnsTypeEnum(String name, String code) {
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