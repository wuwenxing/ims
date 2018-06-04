package com.gwghk.ims.activity.dao.inf;

import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActTag;
import com.gwghk.ims.common.common.BaseDao;

/**
 * 摘要：用户标签DAO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月29日
 */
public interface ActTagDao extends BaseDao<ActTag> {

	Integer checkCode(Map<String, Object> map);
		
	ActTag findByCode(String code, Long companyId);
}
