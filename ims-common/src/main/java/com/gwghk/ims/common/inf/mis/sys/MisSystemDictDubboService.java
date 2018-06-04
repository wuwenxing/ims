package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.system.SystemDictVO;

public interface MisSystemDictDubboService {
	
	MisRespResult<SystemDictVO> findById(Long dictId);
	
	MisRespResult<SystemDictVO> findByDictCode(String dictCode, Long companyId);
	
	MisRespResult<List<SystemDictVO>> findListByParentDictCode(String parentDictCode, Long companyId);

	MisRespResult<PageR<SystemDictVO>> findPageList(SystemDictVO vo);
	
	MisRespResult<List<SystemDictVO>> findList(SystemDictVO vo);

	MisRespResult<Void> saveOrUpdate(SystemDictVO vo);
	
	MisRespResult<Void> deleteByIdArray(String idArray);
}
