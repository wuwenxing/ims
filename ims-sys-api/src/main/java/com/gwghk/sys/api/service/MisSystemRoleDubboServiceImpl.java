package com.gwghk.sys.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.sys.api.dao.entity.SystemRole;
import com.gwghk.sys.api.manager.SystemRoleManager;

/**
 * 摘要：系统-角色服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisSystemRoleDubboServiceImpl implements MisSystemRoleDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisSystemRoleDubboServiceImpl.class);
	
	@Autowired
	private SystemRoleManager systemRoleManager;
	
	public MisRespResult<SystemRoleVO> findByRoleCode(String roleCode, Long companyId) {
		logger.info("findByRoleCode-->【roleCode:{}, companyId:{}】", roleCode, companyId);
		try{
			SystemRoleVO vo = new SystemRoleVO();
			vo.setRoleCode(roleCode);
			vo.setCompanyId(companyId);
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemRoleVO(), systemRoleManager.findByRoleCode(vo)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByRoleCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<PageR<SystemRoleVO>> findPageList(SystemRoleVO vo) {
		logger.info("findPageList-->【roleCode:{}, companyId:{}】", vo.getRoleCode(), vo.getCompanyId());
		try{
			PageR<SystemRoleVO> pageR = systemRoleManager.findPageList(vo);
			return MisRespResult.success(pageR);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<SystemRoleVO>> findList(SystemRoleVO vo) {
		logger.info("findList-->【roleCode:{}, companyId:{}】", vo.getRoleCode(), vo.getCompanyId());
		try{
			List<SystemRole> systemRoleList = systemRoleManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(systemRoleList, SystemRoleVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<SystemRoleVO> findById(Long id) {
		logger.info("findById-->【id:{}】", id);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemRoleVO(), systemRoleManager.findById(id)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<Long> saveOrUpdate(SystemRoleVO vo) {
		logger.info("saveOrUpdate-->【roleCode:{}, companyId:{}】", vo.getRoleCode(), vo.getCompanyId());
		try{
			Long roleId = systemRoleManager.saveOrUpdate(vo);
			return MisRespResult.success(roleId);
		}catch(Exception e){
			logger.error("<--系统异常，【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<Void> deleteByIdArray(String ids) {
		logger.info("deleteById-->【ids:{}】", ids);
		try{
			systemRoleManager.deleteByIdArray(ids);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【deleteById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
