package com.gwghk.sys.api.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemRoleColumnAuthVO;
import com.gwghk.sys.api.dao.entity.SystemRoleColumnAuth;
import com.gwghk.sys.api.dao.inf.SystemRoleColumnAuthDao;

/**
 * 摘要：系统-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemRoleColumnAuthManager {

	@Autowired
	private SystemRoleColumnAuthDao systemRoleColumnAuthDao;

	/**
	 * 功能：根据查询条件->查询列表
	 * @param vo
	 * @return
	 */
	public List<SystemRoleColumnAuth> findList(SystemRoleColumnAuthVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", vo.getRoleId());
		map.put("viewType", vo.getViewType());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("companyId", vo.getCompanyId());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return systemRoleColumnAuthDao.findListByMap(map);
	}
	
	/**
	 * 功能：新增或修改时保存信息-角色与列关联关系
	 */
	public void saveOrUpdateByRoleId(SystemRoleColumnAuthVO vo) throws Exception {
		// 先删除旧数据
		this.deleteByRoleId(vo.getRoleId(), vo.getViewType());
		// 再新增关联数据
		if(StringUtils.isBlank(vo.getViewColumns())){
			// 如果为空，代表全部未选择，不需新增关联关系
		}else{
			if (null == vo.getId()) {
				systemRoleColumnAuthDao.save(ImsBeanUtil.copyNotNull(new SystemRoleColumnAuth(), vo));
			} else {
				SystemRoleColumnAuth oldRoleColumnAuth = systemRoleColumnAuthDao.findObject(vo.getId());
				ImsBeanUtil.copyNotNull(oldRoleColumnAuth,vo);
				systemRoleColumnAuthDao.update(oldRoleColumnAuth);
			}
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByRoleId(Long roleId, String viewType) {
		systemRoleColumnAuthDao.deleteByRoleId(roleId, viewType);
	}
	
}