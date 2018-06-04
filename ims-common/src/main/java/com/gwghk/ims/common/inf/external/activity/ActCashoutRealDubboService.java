package com.gwghk.ims.common.inf.external.activity;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.ActCashoutRealDTO;
/**
 * 出金查询
 * @author jackson.tang
 *
 */
public interface ActCashoutRealDubboService {
	/**
	 * 分页查询 
	 * @param dto
	 * @param companyId
	 * @return
	 */
     Map<String,Object> findPageList(ActCashoutRealDTO dto,@Company Long companyId);
 	/**
 	 * 查询列表
 	 * @param dto
 	 * @param companyId
 	 * @return
 	 */
     MisRespResult<List<ActCashoutRealDTO>> findList(ActCashoutRealDTO dto,@Company Long companyId);
}