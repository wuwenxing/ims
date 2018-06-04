package com.gwghk.ims.mis.gateway.common;

import java.util.List;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.vo.system.SystemMenuVO;

/**
 * 菜单Bean对象
 */
public class MenuBean{

	private String roleId;
	private List<SystemMenuVO> menuList;
	private Long companyId;
	
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
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<SystemMenuVO> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<SystemMenuVO> menuList) {
		this.menuList = menuList;
	}
	
}