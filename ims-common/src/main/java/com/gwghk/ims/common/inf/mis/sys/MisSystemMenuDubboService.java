package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.system.SystemMenuVO;

public interface MisSystemMenuDubboService {

	MisRespResult<List<SystemMenuVO>> findListByCompanyId(Long companyId);
	
	MisRespResult<List<SystemMenuVO>> findListByUserIdAndCompanyId(Long userId, Long companyId);
	
	MisRespResult<List<SystemMenuVO>> findListByUserNoAndCompanyId(String userNo, Long companyId);

	MisRespResult<List<SystemMenuVO>> findList(SystemMenuVO vo);
	
	MisRespResult<List<SystemMenuVO>> findMenuListByRoleId(Long roleId);
	
	MisRespResult<SystemMenuVO> findById(Long menuId);

	/**
	 * 只查询菜单,menu_type=1
	 */
	MisRespResult<SystemMenuVO> findByMenuCode(String menuCode, Long companyId);
	
	/**
	 * 查询功能类型时，使用此方法
	 * @param menuCode
	 * @param parentMenuCode
	 * @return
	 */
	MisRespResult<SystemMenuVO> findByMenuCodeAndParentMenuCode(String menuCode, String parentMenuCode);
	
	MisRespResult<List<SystemMenuVO>> findByParentMenuCode(String parentMenuCode, Long companyId);

	MisRespResult<Long> saveOrUpdate(SystemMenuVO vo);

	MisRespResult<Void> deleteByMenuCode(String menuCode, Long companyId);

	MisRespResult<Void> deleteById(Long menuId);
	
}
