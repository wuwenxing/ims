package com.gwghk.ims.common.inf.mis.marketingtool;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsLogVO;

public interface MisImsMsgSmsLogDubboService {
	Map<String,Object>  findPageList(ImsMsgSmsLogVO vo,@Company Long companyId);

	MisRespResult<List<ImsMsgSmsLogVO>> findList(ImsMsgSmsLogVO vo,@Company Long companyId);

	MisRespResult<ImsMsgSmsLogVO> findById(Long id,@Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ImsMsgSmsLogVO vo,@Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);
}
