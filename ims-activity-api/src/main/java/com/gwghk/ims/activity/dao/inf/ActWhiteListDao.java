package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActWhiteList;
import com.gwghk.ims.activity.dao.entity.ActWhiteListWrapper;
import com.gwghk.ims.common.common.BaseDao;

public interface ActWhiteListDao extends BaseDao<ActWhiteList> {
	
	 List<ActWhiteListWrapper> findListBySearch(Map<String,Object> map);
	
}