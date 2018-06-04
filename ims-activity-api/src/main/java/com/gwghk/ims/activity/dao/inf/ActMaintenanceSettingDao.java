package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.activity.dao.entity.ActMaintenanceSetting;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSettingWrapper;
import com.gwghk.ims.common.common.BaseDao;

public interface ActMaintenanceSettingDao extends BaseDao<ActMaintenanceSetting>{

	List<ActMaintenanceSettingWrapper> findListBySearch(Map<String,Object> map) ;
	
	/**
     * 查找正被禁用的活动记录
     */
	ActMaintenanceSetting findDisabledActMaintainByActivityPeriods(String activityPeriods) ;

	List<ActMaintenanceSetting> findAll();
}
