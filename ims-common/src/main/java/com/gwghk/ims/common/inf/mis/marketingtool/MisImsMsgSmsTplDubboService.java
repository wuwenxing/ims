package com.gwghk.ims.common.inf.mis.marketingtool;

import java.util.List;
import java.util.Map;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsTplVO;

public interface MisImsMsgSmsTplDubboService {
	Map<String,Object> findPageList(ImsMsgSmsTplVO vo,@Company Long companyId);

	MisRespResult<List<ImsMsgSmsTplVO>> findList(ImsMsgSmsTplVO vo,@Company Long companyId);

	MisRespResult<ImsMsgSmsTplVO> findById(Long id,@Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ImsMsgSmsTplVO vo,@Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);
}
