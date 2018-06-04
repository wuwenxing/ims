package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;

public interface MisImsPrizeRecordDubboService {
 
	MisRespResult<ImsPrizeRecordVO> findById(Integer id,String actNo,@Company Long companyId);
	
	MisRespResult<PageR<ImsPrizeRecordVO>> findPageList(ImsPrizeRecordVO vo,@Company Long companyId);

	MisRespResult<List<ImsPrizeRecordVO>> findList(ImsPrizeRecordVO vo,@Company Long companyId);
	
	MisRespResult<Void> saveOrUpdate(ImsPrizeRecordVO vo,@Company Long companyId);
	
	MisRespResult<Void> batchSave(ImsPrizeRecordVO vo,List<List<Object>> accountList,@Company Long companyId);
	
	MisRespResult<Void> batchOperate(String ids,String action,String remark,String actNo,BaseVO base,@Company Long companyId);
	
	void formateReturnData(List<ImsPrizeRecordVO> list,@Company Long companyId);

	MisRespResult<Void> saveToBatch(ImsPrizeRecordVO reqVO, List<ImsPrizeRecordVO> accountVOs, Long companyId);
 
}
