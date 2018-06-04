package com.gwghk.sys.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.sys.MisSystemUserDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.sys.api.dao.entity.SystemUser;
import com.gwghk.sys.api.manager.SystemUserManager;

/**
 * 摘要：系统-用户服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisSystemUserDubboServiceImpl implements MisSystemUserDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisSystemUserDubboServiceImpl.class);
	
	@Autowired
	private SystemUserManager systemUserManager;
	
	public MisRespResult<SystemUserVO> findByUserNo(String userNo) {
		logger.info("findByUserNo-->【UserNo:{}】", userNo);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemUserVO(), systemUserManager.findByUserNo(userNo)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByUserNo】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<SystemUserVO> findByUserNoAndCompanyId(String userNo, Long companyId) {
		logger.info("findByUserNoAndCompanyId-->【UserNo:{}, companyId:{}】", userNo, companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemUserVO(), systemUserManager.findByUserNoAndCompanyId(userNo, companyId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByUserNoAndCompanyId】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<PageR<SystemUserVO>> findPageList(SystemUserVO vo) {
		logger.info("findPageList-->【UserNo:{}, companyId:{}】", vo.getUserNo(), vo.getCompanyId());
		try{
			PageR<SystemUserVO> pageR = systemUserManager.findPageList(vo);
			return MisRespResult.success(pageR);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<SystemUserVO>> findList(SystemUserVO vo) {
		logger.info("findList-->【UserNo:{}, companyId:{}】", vo.getUserNo(), vo.getCompanyId());
		try{
			List<SystemUser> systemUserList = systemUserManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(systemUserList, SystemUserVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<Void> updatePassword(Long userId, String password) {
		logger.info("updatePassword-->【userId:{},password:{}】", userId, password);
		try{
			systemUserManager.updatePassword(userId, password);
			logger.info("<--更新密码成功!!!");
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【updatePassword】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<Void> updateUserRole(Long userId, Long roleId) {
		logger.info("updateUserRole-->【userId:{},roleId:{}】", userId, roleId);
		try{
			systemUserManager.updateUserRole(userId, roleId);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【updateUserRole】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<SystemUserVO> findById(Long userId) {
		logger.info("findById-->【userId:{}】", userId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemUserVO(), systemUserManager.findById(userId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Long> saveOrUpdate(SystemUserVO vo) {
		logger.info("saveOrUpdate-->【UserNo:{}】", vo.getUserNo());
		try{
			Long userId = systemUserManager.saveOrUpdate(vo);
			return MisRespResult.success(userId);
		}catch(Exception e){
			logger.error("<--系统异常，【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> deleteByIdArray(String ids) {
		logger.info("deleteById-->【ids:{}】", ids);
		try{
			systemUserManager.deleteByIdArray(ids);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【deleteById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}