package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActMaintenanceSettingVO;

public interface MisActMaintenanceSettingDubboService {
	
	MisRespResult<ActMaintenanceSettingVO> findById(Integer id,@Company Long companyId);
	
	MisRespResult<PageR<ActMaintenanceSettingVO>> findPageList(ActMaintenanceSettingVO dto,@Company Long companyId);
	
	MisRespResult<List<ActMaintenanceSettingVO>> findList(ActMaintenanceSettingVO dto,@Company Long companyId);
	
	MisRespResult<Void> updateEnable(int id,String enable,@Company Long companyId);
	
	MisRespResult<Void> saveOrUpdate(ActMaintenanceSettingVO dto,@Company Long companyId);
	
	MisRespResult<Void> batchEffective(String ids,BaseVO vo,@Company Long companyId);

	MisRespResult<Void> deleteByIdArray(String idArray,@Company Long companyId);
}
