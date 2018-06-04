package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActTaskItems;
import com.gwghk.ims.activity.dao.entity.ActTaskItemsWrapper;
import com.gwghk.ims.common.common.BaseDao;

public interface ActTaskItemsDao extends BaseDao<ActTaskItems> {
	
	List<ActTaskItemsWrapper>  findListWrapperByMap(Map<String,Object> map);
}