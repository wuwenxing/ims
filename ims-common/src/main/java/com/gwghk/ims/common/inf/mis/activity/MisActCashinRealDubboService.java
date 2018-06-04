package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActCashinRealVO;

public interface MisActCashinRealDubboService {
	Map<String,Object> findPageList(ActCashinRealVO vo,@Company Long companyId);
	
	MisRespResult<List<ActCashinRealVO>> findList(ActCashinRealVO vo,@Company Long companyId);
}