package com.gwghk.sys.api.dao.inf;

import java.util.List;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.sys.api.dao.entity.SystemDict;

/**
 * 摘要：系统-数据字典DAO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月20日
 */
public interface SystemDictDao extends BaseDao<SystemDict> {
	
	SystemDict findByDictCode(String dictCode, Long companyId);
	
	List<SystemDict> findListByParentDictCode(String parentDictCode, Long companyId);
}
