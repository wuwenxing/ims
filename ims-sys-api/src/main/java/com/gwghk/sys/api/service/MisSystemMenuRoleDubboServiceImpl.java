package com.gwghk.sys.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuRoleDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemMenuRoleVO;
import com.gwghk.sys.api.dao.entity.SystemMenuRole;
import com.gwghk.sys.api.manager.SystemMenuRoleManager;

/**
 * 摘要：系统-用户服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisSystemMenuRoleDubboServiceImpl implements MisSystemMenuRoleDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisSystemMenuRoleDubboServiceImpl.class);
	
	@Autowired
	private SystemMenuRoleManager systemMenuRoleManager;
	
	public MisRespResult<List<SystemMenuRoleVO>> findList(SystemMenuRoleVO vo) {
		logger.info("findList-->【roleId:{}, companyId:{}】", vo.getRoleId(), vo.getCompanyId());
		try{
			List<SystemMenuRole> list = systemMenuRoleManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, SystemMenuRoleVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 新增或者更新-角色与菜单关联信息
	 */
	public MisRespResult<Void> saveOrUpdateByRoleId(SystemMenuRoleVO vo) {
		logger.info("saveOrUpdate->【roleId:{}, companyId:{}】", vo.getRoleId(), vo.getCompanyId());
		try{
			systemMenuRoleManager.saveOrUpdateByRoleId(vo);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
