package com.gwghk.ims.common.inf.mis.activity;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;


public interface MisActTradeRecordDubboService {
    
     MisRespResult<PageR<ActTradeRecordVO>> findPageList(ActTradeRecordVO reqDto,@Company Long companyId);
    
}
