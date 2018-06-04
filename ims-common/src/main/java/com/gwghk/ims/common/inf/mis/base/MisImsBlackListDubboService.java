package com.gwghk.ims.common.inf.mis.base;

import java.util.Map;
import java.util.Set;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.base.ImsBlackListVO;

public interface MisImsBlackListDubboService {
	Map<String,Object> findPageList(ImsBlackListVO imsBlackListVO,@Company Long companyId);
	
	MisRespResult<ImsBlackListVO> findById(Long id,@Company Long companyId);
	
	MisRespResult<ImsBlackListVO> findByAccount(ImsBlackListVO vo, @Company Long companyId);
	
	MisRespResult<Integer> batchSave(Set<String> accounts,String ip,@Company Long companyId);
}
