package com.gwghk.sys.api.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：系统-菜单-角色
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年8月10日
 */
public class SystemMenuRole extends BaseEntity {
	private Long menuRoleId;
	
	private Long menuId;

	private Long roleId;

	public Long getMenuRoleId() {
		return menuRoleId;
	}

	public void setMenuRoleId(Long menuRoleId) {
		this.menuRoleId = menuRoleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}