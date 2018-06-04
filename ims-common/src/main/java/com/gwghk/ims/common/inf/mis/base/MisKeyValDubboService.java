package com.gwghk.ims.common.inf.mis.base;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.base.KeyValVO;

public interface MisKeyValDubboService {
	Map<String,Object> findPageList(KeyValVO keyValVO,@Company Long companyId);
	
	MisRespResult<KeyValVO> findById(Long id,@Company Long companyId);
	
	MisRespResult<Void> save(KeyValVO keyValVO,@Company Long companyId);
	
	MisRespResult<Void> deleteByIds(String ids,@Company Long companyId);
	
	KeyValVO findByKey(String key,@Company Long companyId);
}
