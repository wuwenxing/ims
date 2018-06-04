package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordWaitingVO;

public interface MisImsPrizeRecordWaitingDubboService {
 
	MisRespResult<ImsPrizeRecordWaitingVO> findById(Integer id,@Company Long companyId);
	
	MisRespResult<PageR<ImsPrizeRecordWaitingVO>> findPageList(ImsPrizeRecordWaitingVO vo,@Company Long companyId);

	MisRespResult<List<ImsPrizeRecordWaitingVO>> findList(ImsPrizeRecordWaitingVO vo,@Company Long companyId);
 
}
