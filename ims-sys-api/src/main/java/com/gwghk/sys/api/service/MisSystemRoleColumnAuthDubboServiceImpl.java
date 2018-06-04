package com.gwghk.sys.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleColumnAuthDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemRoleColumnAuthVO;
import com.gwghk.sys.api.dao.entity.SystemRoleColumnAuth;
import com.gwghk.sys.api.manager.SystemRoleColumnAuthManager;

/**
 * 摘要：系统-列权限实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisSystemRoleColumnAuthDubboServiceImpl implements MisSystemRoleColumnAuthDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisSystemRoleColumnAuthDubboServiceImpl.class);
	
	@Autowired
	private SystemRoleColumnAuthManager systemRoleColumnAuthManager;

	public MisRespResult<List<SystemRoleColumnAuthVO>> findList(SystemRoleColumnAuthVO vo) {
		logger.info("findList-->【RoleId:{}, companyId:{}】", vo.getRoleId(), vo.getCompanyId());
		try{
			List<SystemRoleColumnAuth> systemUserList = systemRoleColumnAuthManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(systemUserList, SystemRoleColumnAuthVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<Void> saveOrUpdateByRoleId(SystemRoleColumnAuthVO vo) {
		logger.info("saveOrUpdate-->【id:{}, viewType:{}】", vo.getId(), vo.getViewType());
		try{
			systemRoleColumnAuthManager.saveOrUpdateByRoleId(vo);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}
