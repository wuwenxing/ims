package com.gwghk.ims.common.inf.external.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.vo.BaseVO;

public interface ActSettlementDubboService {

	/**
	 * 活动手动清算入口
	 * @param accountNo 账号
	 * @param platform 平台
	 * @param actNo 活动编号
	 * @param companyId
	 * @return
	 */
	public ApiRespResult<Void> beginUserSettlement(ActSettleTypeEnum type,String accountNo,String actNo,String platform,BaseVO base,@Company Long companyId) ;
	
}
