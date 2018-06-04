package com.gwghk.sys.api.dao.inf;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.sys.api.dao.entity.SystemUser;

/**
 * 摘要：系统-用户DAO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月20日
 */
public interface SystemUserDao extends BaseDao<SystemUser> {

	SystemUser findByUserNo(String userNo);
	
	SystemUser findByUserNoAndCompanyId(String userNo, Long companyId);
	
}
