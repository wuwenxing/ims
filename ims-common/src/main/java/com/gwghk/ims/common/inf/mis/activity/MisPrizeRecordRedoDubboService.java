package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.PrizeRecordRedoVO;


public interface MisPrizeRecordRedoDubboService {


    
     MisRespResult<PageR<PrizeRecordRedoVO>> findPageList(PrizeRecordRedoVO reqDto,@Company Long companyId);

	 MisRespResult<Void> updateResend(List<Long> ids,@Company Long companyId);

	 MisRespResult<Void> deleteByIds(List<Long> ids,@Company Long companyId);
}
