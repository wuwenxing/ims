package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatViewVO;

public interface MisActAccountActiviStatDubboService {
	
	MisRespResult<PageR<ActAccountActiviStatViewVO>> findPageList(ActAccountActiviStatViewVO vo, @Company Long companyId);
	
	MisRespResult<List<ActAccountActiviStatViewVO>> findList(ActAccountActiviStatViewVO vo, @Company Long companyId);
	
	MisRespResult<ActAccountActiviStatViewVO> findViewById(Long id, @Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ActAccountActiviStatViewVO vo, @Company Long companyId);
	
}
