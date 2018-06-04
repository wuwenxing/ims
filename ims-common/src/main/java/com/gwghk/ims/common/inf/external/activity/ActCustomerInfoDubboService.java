package com.gwghk.ims.common.inf.external.activity;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.ActCustomerInfoDTO;

/**
 * 查询客户资料
 * @author jackson.tang
 *
 */
public interface ActCustomerInfoDubboService {
    /**
     * 查询客户资料
     * @param accountNo
     * @param platform
     * @param companyId
     * @return
     */
      MisRespResult<ActCustomerInfoDTO> findActCustomerInfo(String accountNo,String platform,@Company Long companyId);
    /**
     * 分页查询
     * @param dto
     * @param companyId
     * @return
     */
      MisRespResult<Map<String,Object>> findPageList(ActCustomerInfoDTO dto,@Company Long companyId);
    
}
