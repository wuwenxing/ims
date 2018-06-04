package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActTaskSetting;
import com.gwghk.ims.common.common.BaseDao;

public interface ActTaskSettingDao extends BaseDao<ActTaskSetting> {
	
	List<String> listActivityPeriodsByTaskItemCode(Map<String,Object> dataMap);
}