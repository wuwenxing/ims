package com.gwghk.ims.common.inf.mis.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActThirdCallRecordVO;


public interface MisActThirdCallRecordDubboService {
	
	
     MisRespResult<PageR<ActThirdCallRecordVO>> findPageList(ActThirdCallRecordVO reqDto,@Company Long companyId);

     MisRespResult<ActThirdCallRecordVO> findById(Long id,@Company Long companyId);
   
     MisRespResult<Integer> update(ActThirdCallRecordVO actThirdCallRecord,@Company Long companyId);
}