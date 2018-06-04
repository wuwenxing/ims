package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

public interface MisActTaskSettingDubboService {
	
	/**
	 * 加载任务
	 * @param vo
	 * @param companyId
	 * @return
	 */
	MisRespResult<List<ActTaskSettingVO>> findList(ActTaskSettingVO vo,@Company Long companyId);
 
 
}
