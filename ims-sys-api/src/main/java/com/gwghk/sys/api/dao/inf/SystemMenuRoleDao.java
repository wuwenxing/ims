package com.gwghk.sys.api.dao.inf;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.sys.api.dao.entity.SystemMenuRole;

public interface SystemMenuRoleDao extends BaseDao<SystemMenuRole> {
	
	/**
	 * 功能：批量删除信息
	 */
	void deleteByRoleId(Long roleId);
}
