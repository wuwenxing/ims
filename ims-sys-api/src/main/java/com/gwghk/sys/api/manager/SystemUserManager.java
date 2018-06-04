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
import com.gwghk.ims.common.util.MD5;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.sys.api.dao.entity.SystemUser;
import com.gwghk.sys.api.dao.inf.SystemUserDao;

/**
 * 摘要：系统-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemUserManager {

	@Autowired
	private SystemUserDao systemUserDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<SystemUserVO> findPageList(SystemUserVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(SystemUser.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<SystemUser>(this.findList(vo)), new PageR<SystemUserVO>(),SystemUserVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 * @param vo
	 * @return
	 */
	public List<SystemUser> findList(SystemUserVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", vo.getUserNo());
		map.put("userName", vo.getUserName());
		map.put("email", vo.getEmail());
		map.put("position", vo.getPosition());
		map.put("telephone", vo.getTelephone());
		map.put("remark", vo.getRemark());
		map.put("roleId", vo.getRoleId());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("companyId", vo.getCompanyId());
		map.put("orderByClause", PageCustomizedUtil.sort(SystemUser.class, vo.getSort(), vo.getOrder()));
		map.put("isSuperAdmin", vo.isSuperAdmin());
		return systemUserDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->信息
	 */
	public SystemUser findById(Long userId) {
		return systemUserDao.findObject(userId);
	}

	/**
	 * 功能：根据账号->获取信息
	 */
	public SystemUser findByUserNoAndCompanyId(String userNo, Long companyId) {
		return systemUserDao.findByUserNoAndCompanyId(userNo, companyId);
	}

	/**
	 * 功能：根据账号->获取信息
	 */
	public SystemUser findByUserNo(String userNo) {
		return systemUserDao.findByUserNo(userNo);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public Long saveOrUpdate(SystemUserVO vo) throws Exception {
		if (null == vo.getUserId()) {
			vo.setPassword(MD5.getMd5(vo.getPassword()));
			SystemUser user = new SystemUser();
			systemUserDao.save(ImsBeanUtil.copyNotNull(user, vo));
			vo.setUserId(user.getUserId());
		} else {
			SystemUser oldSystemUser = findById(vo.getUserId());
			ImsBeanUtil.copyNotNull(oldSystemUser,vo);
			systemUserDao.update(oldSystemUser);
		}
		return vo.getUserId();
	}

	/**
	 * 功能：更新密码
	 */
	public void updatePassword(Long userId, String password) {
		SystemUser entity = findById(userId);
		entity.setPassword(MD5.getMd5(password));
		systemUserDao.update(entity);
	}

	/**
	 * 功能：更新角色
	 */
	public void updateUserRole(Long userId, Long roleId) {
		SystemUser entity = findById(userId);
		entity.setRoleId(roleId);
		systemUserDao.update(entity);
	}
	
	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		systemUserDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}