package com.gwghk.ims.common.inf.mis.marketingtool;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;

public interface MisImsMsgBindDubboService {
	Map<String,Object> findPageList(ImsMsgBindVO vo,@Company Long companyId);

	MisRespResult<List<ImsMsgBindVO>> findList(ImsMsgBindVO vo,@Company Long companyId);

	MisRespResult<ImsMsgBindVO> findById(Long id,@Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ImsMsgBindVO vo,@Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);

}
