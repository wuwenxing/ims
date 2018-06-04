package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.system.SystemUserVO;

public interface MisSystemUserDubboService {
	
	MisRespResult<SystemUserVO> findByUserNo(String userNo);
	
	MisRespResult<SystemUserVO> findByUserNoAndCompanyId(String userNo, Long companyId);

	MisRespResult<SystemUserVO> findById(Long userId);
	
	MisRespResult<PageR<SystemUserVO>> findPageList(SystemUserVO vo);
	
	MisRespResult<List<SystemUserVO>> findList(SystemUserVO vo);
	
	MisRespResult<Long> saveOrUpdate(SystemUserVO vo);

	MisRespResult<Void> updatePassword(Long userId, String password);

	MisRespResult<Void> updateUserRole(Long userId, Long roleId);

	MisRespResult<Void> deleteByIdArray(String id);
}
