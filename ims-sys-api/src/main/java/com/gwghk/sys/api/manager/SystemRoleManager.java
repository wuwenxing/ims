package com.gwghk.sys.api.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.sys.api.dao.entity.SystemRole;
import com.gwghk.sys.api.dao.inf.SystemRoleDao;

/**
 * 摘要：系统-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemRoleManager {

	@Autowired
	private SystemRoleDao systemRoleDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<SystemRoleVO> findPageList(SystemRoleVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(SystemRole.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<SystemRole>(this.findList(vo)), new PageR<SystemRoleVO>(),SystemRoleVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 * @param vo
	 * @return
	 */
	public List<SystemRole> findList(SystemRoleVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleCode", vo.getRoleCode());
		map.put("roleName", vo.getRoleName());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("companyId", vo.getCompanyId());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return systemRoleDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public SystemRole findById(Long id) {
		return systemRoleDao.findObject(id);
	}

	/**
	 * 功能：根据code->获取信息
	 */
	public SystemRole findByRoleCode(SystemRoleVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleCode", vo.getRoleCode());
		map.put("companyId", vo.getCompanyId());
		return systemRoleDao.findObjectByMap(map);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public Long saveOrUpdate(SystemRoleVO vo) throws Exception {
		if (null == vo.getRoleId()) {
			SystemRole role = new SystemRole();
			systemRoleDao.save(ImsBeanUtil.copyNotNull(role, vo));
			vo.setRoleId(role.getRoleId());
		} else {
			SystemRole oldSystemRole = systemRoleDao.findObject(vo.getRoleId());
			ImsBeanUtil.copyNotNull(oldSystemRole,vo);
			systemRoleDao.update(oldSystemRole);
		}
		return vo.getRoleId();
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String ids) {
		systemRoleDao.deleteBatch(Arrays.asList(ids.split(",")));
	}

}