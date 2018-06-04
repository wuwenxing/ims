package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：系统-角色请求VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemRoleVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -9188813106332882881L;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 编号
	 */
	private String roleCode;

	/**
	 * 名称
	 */
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
