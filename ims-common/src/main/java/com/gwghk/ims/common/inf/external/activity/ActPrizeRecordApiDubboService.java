package com.gwghk.ims.common.inf.external.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;

public interface ActPrizeRecordApiDubboService {
	/**
	 * 批量释放赠金
	 * @param account
	 * @param env
	 * @param companyId
	 * @return
	 */
	public MisRespResult<Void> batchRelease(String account,String env, @Company Long companyId);
	
	/**
	 * 执行具体的发放
	 * @param prizeNo
	 * @param releaseType 默认为1 可以为空，表示如何释放赠金
	 * @param actNo
	 * @param env
	 * @param companyId
	 * @return
	 */
	public MisRespResult<Void> send(String prizeNo,Integer releaseType,String actNo,String env, @Company Long companyId);
}
