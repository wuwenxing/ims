package com.gwghk.ims.common.inf.external.prize;

import java.util.Date;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 摘要：FX-新客活动 后赠处理
 * @author warren
 * @version 1.0
 * @Date 2018年4月16日
 */
public interface ActFxNewCustTempDubboService {
	
	/**
	 * 功能：统计后赠资格
	 * @param activityPeriodsKey 活动编号的键值的key
	 * @param ruleCode
	 * @param companyId
	 * @return
	 */
	ApiRespResult<Void> statqualify(String activityPeriodsKey, String ruleCode, @Company Long companyId, Date curDate);
	
	/**
	 * 功能：生成发放记录
	 * @param activityPeriodsKey 活动编号的键值的key
	 * @param ruleCode
	 * @param companyId
	 * @return
	 */
	ApiRespResult<Void> statAward(String activityPeriodsKey, String ruleCode,@Company Long companyId, Date curDate);
}
