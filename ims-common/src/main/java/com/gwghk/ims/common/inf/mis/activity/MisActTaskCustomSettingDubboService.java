package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActTaskCustomSettingVO;

public interface MisActTaskCustomSettingDubboService {
	Map<String,Object> findPageList(ActTaskCustomSettingVO dto,@Company Long companyId);
	
	MisRespResult<ActTaskCustomSettingVO> findById(Long id,@Company Long companyId);
	
	MisRespResult<List<ActTaskCustomSettingVO>> findList(ActTaskCustomSettingVO dto,@Company Long companyId);
	
	MisRespResult<Void> updateEnable(String taskCodes,String enable,@Company Long companyId);
	
	MisRespResult<Void> saveOrUpdate(ActTaskCustomSettingVO dto,@Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);
}
