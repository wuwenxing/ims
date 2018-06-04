package com.gwghk.ims.common.vo.system;

import java.io.Serializable;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：系统-角色-查看列请求VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemRoleColumnAuthVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -3841433542642764058L;

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
