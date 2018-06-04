package com.gwghk.ims.common.inf.external.prize;

import com.gwghk.ims.common.annotation.Company;

/**
 * 
* @ClassName: ActAccountActStatDubboService
* @Description: 用户参与活动统计(提供第三方数据源)
* @author lawrence
* @date 2018年4月23日
*
 */
public interface ActAccountActStatDubboService {
	/**
	 * 
	 * @MethodName: doStatRecord
	 * @Description: 活动统计
	 * @param companyId
	 * @return void
	 */
	void doStatRecord(@Company Long companyId);
}
