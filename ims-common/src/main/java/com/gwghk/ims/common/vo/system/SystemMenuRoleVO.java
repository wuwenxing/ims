package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：系统-菜单-角色VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年8月10日
 */
public class SystemMenuRoleVO extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = -9188813106332882881L;
	
	private Long menuRoleId;
	
	private Long menuId;

	private Long roleId;

	private String menuCodes;

	private String menuIds;

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

	public String getMenuCodes() {
		return menuCodes;
	}

	public void setMenuCodes(String menuCodes) {
		this.menuCodes = menuCodes;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	
}