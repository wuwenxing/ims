package com.gwghk.ims.common.inf.mis.marketingtool;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppTplVO;

public interface MisImsMsgAppTplDubboService {
	Map<String,Object> findPageList(ImsMsgAppTplVO vo,@Company Long companyId);

	MisRespResult<List<ImsMsgAppTplVO>> findList(ImsMsgAppTplVO vo,@Company Long companyId);

	MisRespResult<ImsMsgAppTplVO> findById(Long id,@Company Long companyId);

	MisRespResult<Void> saveOrUpdate(ImsMsgAppTplVO vo,@Company Long companyId);

	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);
}
