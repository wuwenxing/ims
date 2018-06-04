package com.gwghk.ims.common.inf.external.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActTradeRecordDTO;


public interface ActTradeRecordDubboService {
    
     MisRespResult<PageR<ActTradeRecordDTO>> findPageList(ActTradeRecordDTO reqDto,@Company Long companyId);
    
}
