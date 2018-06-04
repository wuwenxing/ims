package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.common.common.BaseDao;

public interface ActSettingDao extends BaseDao<ActSetting> {
	final String VALID_ACT_ALL="0";
	final String VALID_ACT_EXCLUDE_FORBID="1";
	final String VALID_ACT_EXCLUDE_FORBID_AND_EXPIRE="2";
	
	
	ActSetting findByactivityPeriods(String activityPeriods, Long companyId);
	
	/**
	 * 寻找活动时间结束但是没有结算的(非其它审批通过的) 所有活动
	 * @return
	 */
	List<ActSetting> findReadySettleActSetting();
	/**
	 * 查找所有有效活动  1.所有活动 2.不包含禁用 3.不包含禁用和过期
	 * @return
	 */
	List<ActSetting> findValidActivity(String excludeFlag);
	
	
}