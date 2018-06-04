package com.gwghk.ims.common.inf.mis.sys;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.system.SystemMenuRoleVO;

public interface MisSystemMenuRoleDubboService {


	MisRespResult<List<SystemMenuRoleVO>> findList(SystemMenuRoleVO vo);
	
	/**
	 * 新增或者更新-角色与菜单关联信息
	 * @return
	 */
	MisRespResult<Void> saveOrUpdateByRoleId(SystemMenuRoleVO vo);

}
