package com.gwghk.sys.api.dao.inf;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.sys.api.dao.entity.SystemRoleColumnAuth;

public interface SystemRoleColumnAuthDao extends BaseDao<SystemRoleColumnAuth> {
	
	/**
	 * 功能：批量删除信息
	 */
	void deleteByRoleId(Long roleId, String viewType);
	
}
