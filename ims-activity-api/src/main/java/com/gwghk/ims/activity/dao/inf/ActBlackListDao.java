package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActBlackListWrapper;
import com.gwghk.ims.common.common.BaseDao;

public interface ActBlackListDao extends BaseDao<ActBlackList> {
	
    List<ActBlackListWrapper> findListBySearch(Map<String,Object> map);
}