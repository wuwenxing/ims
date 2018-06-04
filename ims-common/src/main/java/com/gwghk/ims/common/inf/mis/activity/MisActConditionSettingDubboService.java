package com.gwghk.ims.common.inf.mis.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;

public interface MisActConditionSettingDubboService {
	/**
	 * 获得指定活动的参与条件
	 * @param activityPeriods
	 * @param companyId
	 * @return
	 */
	MisRespResult<ActConditionSettingVO> findByActivityPeriods(String activityPeriods,@Company Long companyId);
}
