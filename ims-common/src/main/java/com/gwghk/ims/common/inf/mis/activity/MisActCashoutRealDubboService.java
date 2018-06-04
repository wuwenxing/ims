package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActCashoutRealVO;

public interface MisActCashoutRealDubboService {
     Map<String,Object> findPageList(ActCashoutRealVO vo,@Company Long companyId);
 	
     MisRespResult<List<ActCashoutRealVO>> findList(ActCashoutRealVO vo,@Company Long companyId);
}