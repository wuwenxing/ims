package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.system.SystemRoleColumnAuthVO;

public interface MisSystemRoleColumnAuthDubboService {
	
	MisRespResult<List<SystemRoleColumnAuthVO>> findList(SystemRoleColumnAuthVO vo);
	
	MisRespResult<Void> saveOrUpdateByRoleId(SystemRoleColumnAuthVO vo);

}
