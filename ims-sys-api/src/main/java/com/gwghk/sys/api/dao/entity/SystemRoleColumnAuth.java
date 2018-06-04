package com.gwghk.sys.api.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：角色-能查看的某种业务的列权限
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年2月22日
 */
public class SystemRoleColumnAuth extends BaseEntity{
	private Long id;
	
	/**
	 * 角色ID
	 */
	private Long roleId;
	
	/**
	 * 数据类型
	 */
	private String viewType;
	
	/**
	 * 能查到的列(以,格式存储 如a,b,c)
	 */
	private String viewColumns;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getViewColumns() {
		return viewColumns;
	}

	public void setViewColumns(String viewColumns) {
		this.viewColumns = viewColumns;
	}
}