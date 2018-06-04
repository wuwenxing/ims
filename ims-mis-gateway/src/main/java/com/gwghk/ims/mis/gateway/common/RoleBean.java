package com.gwghk.ims.mis.gateway.common;

import java.util.List;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.vo.system.SystemRoleVO;

/**
 * 角色Bean对象
 */
public class RoleBean{
	
	private Long companyId;
	private List<SystemRoleVO> roleList;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyCode() {
		if(null != companyId){
			return CompanyEnum.getCodeById(companyId);
		}
		return null;
	}

	public String getCompanyName() {
		if(null != companyId){
			CompanyEnum temp = CompanyEnum.findById(companyId);
			if(null != temp){
				return temp.getName();
			}
		}
		return null;
	}
	
	public List<SystemRoleVO> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<SystemRoleVO> roleList) {
		this.roleList = roleList;
	}
	
}