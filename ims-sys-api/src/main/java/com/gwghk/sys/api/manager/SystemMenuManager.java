package com.gwghk.sys.api.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.system.SystemMenuVO;
import com.gwghk.sys.api.dao.entity.SystemMenu;
import com.gwghk.sys.api.dao.inf.SystemMenuDao;

/**
 * 摘要：系统-菜单业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemMenuManager {

	@Autowired
	private SystemMenuDao systemMenuDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<SystemMenuVO> findPageList(SystemMenuVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(SystemMenu.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<SystemMenu>(this.findList(vo)), new PageR<SystemMenuVO>(),SystemMenuVO.class);
	}

	/**
	 * 功能：根据查询条件->查询菜单列表
	 */
	public List<SystemMenu> findList(SystemMenuVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("parentMenuCode", vo.getParentMenuCode());
		map.put("menuCode", vo.getMenuCode());
		map.put("menuType", vo.getMenuType());
		map.put("menuItem", vo.getMenuItem());
		map.put("menuNameCn", vo.getMenuNameCn());
		map.put("menuNameEn", vo.getMenuNameEn());
		map.put("menuNameTw", vo.getMenuNameTw());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return systemMenuDao.findListByMap(map);
	}

	/**
	 * 功能：根据查询条件->查询菜单列表
	 */
	public List<SystemMenu> findMenuListByRoleId(Long roleId) {
		return systemMenuDao.findMenuListByRoleId(roleId);
	}
	
	/**
	 * 功能：根据查询条件->查询菜单列表
	 */
	public List<SystemMenu> findListByCompanyId(Long companyId) {
		List<SystemMenu> menuList = systemMenuDao.findListByCompanyId(CompanyEnum.fx.getLongId());
		return menuList;
	}
	
	/**
	 * 功能：根据查询条件->查询菜单列表
	 */
	public List<SystemMenu> findListByUserIdAndCompanyId(Long userId, Long companyId) {
		List<SystemMenu> menuList = systemMenuDao.findListByUserIdAndCompanyId(userId, CompanyEnum.fx.getLongId());
		return menuList;
	}
	
	/**
	 * 功能：根据查询条件->查询菜单列表
	 */
	public List<SystemMenu> findListByUserNoAndCompanyId(String userNo, Long companyId) {
		List<SystemMenu> menuList = systemMenuDao.findListByUserNoAndCompanyId(userNo, CompanyEnum.fx.getLongId());
		return menuList;
	}
	
	/**
	 * 功能：根据菜单id->菜单信息
	 */
	public SystemMenu findById(Long menuId) {
		return systemMenuDao.findObject(menuId);
	}

	/**
	 * 功能：根据菜单编号->获取菜单信息，只查询menu_type=1
	 */
	public SystemMenu findByMenuCode(String menuCode, Long companyId) {
		return systemMenuDao.findByMenuCode(menuCode);
	}

	/**
	 * 功能：根据菜单编号->获取菜单信息
	 */
	public SystemMenu findByMenuCodeAndParentMenuCode(String menuCode, String parentMenuCode) {
		return systemMenuDao.findByMenuCodeAndParentMenuCode(menuCode, parentMenuCode);
	}

	/**
	 * 功能：根据菜单编号->获取菜单信息
	 */
	public List<SystemMenu> findByParentMenuCode(String parentMenuCode, Long companyId) {
		return systemMenuDao.findListByParentMenuCode(parentMenuCode);
	}

	/**
	 * 功能：新增或修改时保存菜单信息
	 */
	public Long saveOrUpdate(SystemMenuVO vo) throws Exception {
		if(null != vo){
			vo.setCompanyId(CompanyEnum.fx.getLongId());
		}
		if (null == vo.getMenuId()) {
			SystemMenu menu = new SystemMenu();
			systemMenuDao.save(ImsBeanUtil.copyNotNull(menu, vo));
			vo.setMenuId(menu.getMenuId());
		} else {
			// 更新子节点,判断编号是否修改，修改则更新子节点的父编号
			SystemMenu oldSystemMenu = findById(vo.getMenuId());
			if(StringUtils.isNotBlank(oldSystemMenu.getMenuCode()) 
					&& !oldSystemMenu.getMenuCode().equals(vo.getMenuCode())){
				systemMenuDao.updateMenuCodeByParentMenuCode(vo.getMenuCode(), oldSystemMenu.getMenuCode());
			}
			// 更新当前节点
			ImsBeanUtil.copyNotNull(oldSystemMenu,vo);
			systemMenuDao.update(oldSystemMenu);
		}
		return vo.getMenuId();
	}

	/**
	 * 功能：删除菜单信息
	 */
	public void deleteByMenuCode(String menuCode, Long companyId) {
		systemMenuDao.deleteByMenuCode(menuCode);
	}
	
	/**
	 * 功能：批量删除菜单信息
	 */
	public void deleteByIdArray(String ids) {
		systemMenuDao.deleteBatch(Arrays.asList(ids.split(",")));
	}

}