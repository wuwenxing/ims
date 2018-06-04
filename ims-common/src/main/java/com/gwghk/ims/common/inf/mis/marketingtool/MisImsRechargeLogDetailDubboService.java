package com.gwghk.ims.common.inf.mis.marketingtool;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;

public interface MisImsRechargeLogDetailDubboService {
	Map<String, Object> findPageList(ImsRechargeLogDetailVO vo, @Company Long companyId);

	MisRespResult<List<ImsRechargeLogDetailVO>> findList(ImsRechargeLogDetailVO vo, @Company Long companyId);

	MisRespResult<ImsRechargeLogDetailVO> findById(Long id, @Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ImsRechargeLogDetailVO vo, @Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids, @Company Long companyId);
	
	MisRespResult<Map<String, Object>> send(ImsRechargeLogDetailVO vo, @Company Long companyId);
	
	MisRespResult<Integer> batchSend(List<ImsRechargeLogDetailVO> voList, @Company Long companyId);
}
