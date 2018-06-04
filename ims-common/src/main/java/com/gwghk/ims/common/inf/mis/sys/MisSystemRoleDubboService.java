package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.system.SystemRoleVO;

public interface MisSystemRoleDubboService {

	MisRespResult<SystemRoleVO> findByRoleCode(String roleCode, Long companyId);

	MisRespResult<SystemRoleVO> findById(Long id);

	MisRespResult<PageR<SystemRoleVO>> findPageList(SystemRoleVO vo);

	MisRespResult<List<SystemRoleVO>> findList(SystemRoleVO vo);

	MisRespResult<Long> saveOrUpdate(SystemRoleVO vo);

	MisRespResult<Void> deleteByIdArray(String userIdArray);
}
