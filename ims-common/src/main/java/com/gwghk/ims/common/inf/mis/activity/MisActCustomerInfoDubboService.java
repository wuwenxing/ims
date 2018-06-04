package com.gwghk.ims.common.inf.mis.activity;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;


public interface MisActCustomerInfoDubboService {
    
      MisRespResult<ActCustomerInfoVO> findActCustomerInfo(String accountNo,String platform,@Company Long companyId);
    
      MisRespResult<Map<String,Object>> findPageList(ActCustomerInfoVO vo,@Company Long companyId);
    
}
