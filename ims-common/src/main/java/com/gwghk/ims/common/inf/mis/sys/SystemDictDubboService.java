package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.system.SystemDictVO;

public interface SystemDictDubboService {
	
	MisRespResult<SystemDictVO> findByDictCode(String dictCode, Long companyId);
	
	MisRespResult<List<SystemDictVO>> findListByParentDictCode(String parentDictCode, Long companyId);
	
}
