package com.gwghk.sys.api.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemMenuRoleVO;
import com.gwghk.sys.api.dao.entity.SystemMenuRole;
import com.gwghk.sys.api.dao.inf.SystemMenuDao;
import com.gwghk.sys.api.dao.inf.SystemMenuRoleDao;

/**
 * 摘要：系统-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemMenuRoleManager {

	@Autowired
	private SystemMenuRoleDao systemMenuRoleDao;
	@Autowired
	private SystemMenuDao systemMenuDao;

	/**
	 * 功能：根据查询条件->查询列表
	 * @param vo
	 * @return
	 */
	public List<SystemMenuRole> findList(SystemMenuRoleVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", vo.getRoleId());
		map.put("menuId", vo.getMenuId());
		return systemMenuRoleDao.findListByMap(map);
	}
	
	/**
	 * 功能：新增或者更新-角色与菜单关联信息
	 */
	public void saveOrUpdateByRoleId(SystemMenuRoleVO vo) throws Exception {
		// 先删除旧数据
		this.deleteByRoleId(vo.getRoleId());
		// 再新增关联数据
		if(StringUtils.isNotBlank(vo.getMenuCodes())){
			for(String menuCode: vo.getMenuCodes().split(",")){
				SystemMenuRole obj = ImsBeanUtil.copyNotNull(new SystemMenuRole(), vo);
				obj.setMenuId(systemMenuDao.findByMenuCode(menuCode).getMenuId());
				systemMenuRoleDao.save(obj);
			}
		}else{
			if(StringUtils.isNotBlank(vo.getMenuIds())){
				for(String menuId: vo.getMenuIds().split(",")){
					SystemMenuRole obj = ImsBeanUtil.copyNotNull(new SystemMenuRole(), vo);
					obj.setMenuId(Long.parseLong(menuId));
					systemMenuRoleDao.save(obj);
				}
			}
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByRoleId(Long roleId) {
		systemMenuRoleDao.deleteByRoleId(roleId);
	}
	
}