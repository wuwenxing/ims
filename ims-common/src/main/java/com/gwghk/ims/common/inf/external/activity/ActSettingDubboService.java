package com.gwghk.ims.common.inf.external.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.ActSettingDetailsDTO;
/**
 * 查询活动信息
 * @author jackson.tang
 *
 */
public interface ActSettingDubboService {
	/**
	 * 查询活动信息 
	 * @param activityPeriods 活动编号
	 * @param companyId
	 * @return
	 */
	MisRespResult<ActSettingDetailsDTO> findByActivityPeriods(String activityPeriods, @Company Long companyId);

}
