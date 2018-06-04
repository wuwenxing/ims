package com.gwghk.sys.api.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：系统-角色实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年8月10日
 */
public class SystemRole extends BaseEntity{
	private Long roleId;

	private String roleCode;

	private String roleName;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}