package com.gwghk.ims.common.inf.external.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;

public interface ActivityApiDubboService {

	/**
	 * 活动统一入口
	 * @param accountNo 账号
	 * @param platform 平台
	 * @param accType 账号类型 real|demo
	 * @param companyId
	 * @return
	 */
	public ApiRespResult<Void> autoPrizeRecord(String accountNo,String platform,String accType,@Company Long companyId) ;
	
	/**
	 * 物品兑换型任务入口
	 * @param taskItemId
	 * @param accountNo
	 * @param platform
	 * @param companyId
	 * @return
	 */
	public ApiRespResult<Void> handPrizeRecord(Integer taskItemId,String accountNo,String platform,@Company Long companyId) ;
}
