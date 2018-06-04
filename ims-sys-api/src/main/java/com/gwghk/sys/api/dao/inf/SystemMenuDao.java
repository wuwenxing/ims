package com.gwghk.sys.api.dao.inf;

import java.util.List;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.sys.api.dao.entity.SystemMenu;

/**
 * 摘要：系统-菜单DAO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月20日
 */
public interface SystemMenuDao extends BaseDao<SystemMenu> {
	
	List<SystemMenu> findListByCompanyId(Long companyId);
	
	List<SystemMenu> findListByUserIdAndCompanyId(Long userId, Long companyId);
	
	List<SystemMenu> findListByUserNoAndCompanyId(String userNo, Long companyId);
	
	List<SystemMenu> findMenuListByRoleId(Long roleId);
	
	SystemMenu findByMenuCode(String menuCode);
	
	SystemMenu findByMenuCodeAndParentMenuCode(String menuCode, String parentMenuCode);
	
	List<SystemMenu> findListByParentMenuCode(String parentDictCode);
	
	int deleteByMenuCode(String menuCode);
	
	int updateMenuCodeByParentMenuCode(String menuCode, String parentMenuCode);
	
}
