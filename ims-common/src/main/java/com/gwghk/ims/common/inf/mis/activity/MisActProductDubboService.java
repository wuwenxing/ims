package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActProductVO;

public interface MisActProductDubboService {
	
	MisRespResult<ActProductVO> findByProductCode(String productCode, Long companyId);
	
	MisRespResult<ActProductVO> findById(Long id);
	
	MisRespResult<PageR<ActProductVO>> findPageList(ActProductVO vo);
	
	MisRespResult<List<ActProductVO>> findList(ActProductVO vo);
	
	MisRespResult<Void> saveOrUpdate(ActProductVO vo);

	MisRespResult<Void> deleteByIdArray(String id);
}
