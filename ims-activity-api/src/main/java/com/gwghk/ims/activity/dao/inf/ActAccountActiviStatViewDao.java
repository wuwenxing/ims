package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStatView;
import com.gwghk.ims.common.common.BaseDao;

public interface ActAccountActiviStatViewDao extends BaseDao<ActAccountActiviStatView>{
	List<ActAccountActiviStatView> findListByView(Map<String, Object> map);
	ActAccountActiviStatView findViewById(Long id);
}