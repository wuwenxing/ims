package com.gwghk.ims.common.inf.mis.marketingtool;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;

public interface MisImsMsgAppLogDubboService {
	Map<String,Object> findPageList(ImsMsgAppLogVO vo,@Company Long companyId);

	MisRespResult<List<ImsMsgAppLogVO>> findList(ImsMsgAppLogVO vo,@Company Long companyId);

	MisRespResult<ImsMsgAppLogVO> findById(Integer id,@Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ImsMsgAppLogVO vo,@Company Long companyId);
	
	MisRespResult<Void> resend(ImsMsgAppLogVO vo,@Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);

}
