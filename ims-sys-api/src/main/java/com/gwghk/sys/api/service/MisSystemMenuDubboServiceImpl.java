package com.gwghk.sys.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemMenuVO;
import com.gwghk.sys.api.dao.entity.SystemMenu;
import com.gwghk.sys.api.manager.SystemMenuManager;

/**
 * 摘要：系统-用户服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisSystemMenuDubboServiceImpl implements MisSystemMenuDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisSystemMenuDubboServiceImpl.class);
	
	@Autowired
	private SystemMenuManager systemMenuManager;
	
	public MisRespResult<List<SystemMenuVO>> findListByCompanyId(Long companyId) {
		logger.info("findListByCompanyId-->【companyId:{}】",companyId);
		try{
			List<SystemMenu> systemMenuList = systemMenuManager.findListByCompanyId(companyId);
			return MisRespResult.success(ImsBeanUtil.copyList(systemMenuList, SystemMenuVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findListByCompanyId】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<SystemMenuVO>> findListByUserIdAndCompanyId(Long userId, Long companyId) {
		logger.info("findListByUserIdAndCompanyId->【userId:{}, companyId:{}】", userId, companyId);
		try{
			List<SystemMenu> systemMenuList = systemMenuManager.findListByUserIdAndCompanyId(userId, companyId);
			return MisRespResult.success(ImsBeanUtil.copyList(systemMenuList, SystemMenuVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findListByUserIdAndCompanyId】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<SystemMenuVO>> findListByUserNoAndCompanyId(String userNo, Long companyId) {
		logger.info("findListByUserNoAndCompanyId->【userNo:{}, companyId:{}】", userNo, companyId);
		try{
			List<SystemMenu> systemMenuList = systemMenuManager.findListByUserNoAndCompanyId(userNo, companyId);
			return MisRespResult.success(ImsBeanUtil.copyList(systemMenuList, SystemMenuVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findListByUserNoAndCompanyId】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<SystemMenuVO>> findList(SystemMenuVO vo) {
		logger.info("findList->【menuCode:{}】",vo.getMenuCode());
		try{
			List<SystemMenu> systemMenuList = systemMenuManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(systemMenuList, SystemMenuVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<SystemMenuVO>> findMenuListByRoleId(Long roleId) {
		logger.info("findMenuListByRoleId->【roleId:{}】", roleId);
		try{
			List<SystemMenu> systemMenuList = systemMenuManager.findMenuListByRoleId(roleId);
			return MisRespResult.success(ImsBeanUtil.copyList(systemMenuList, SystemMenuVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findMenuListByRoleId】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<SystemMenuVO> findById(Long menuId) {
		logger.info("findById->【menuId:{}】", menuId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemMenuVO(),systemMenuManager.findById(menuId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 只查询菜单,menu_type=1
	 */
	public MisRespResult<SystemMenuVO> findByMenuCode(String menuCode, Long companyId) {
		logger.info("findByMenuCode->【menuCode:{}, companyId:{}】", menuCode, companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemMenuVO(),systemMenuManager.findByMenuCode(menuCode, companyId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByMenuCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 查询功能类型时，使用此方法
	 * @param menuCode
	 * @param parentMenuCode
	 * @return
	 */
	public MisRespResult<SystemMenuVO> findByMenuCodeAndParentMenuCode(String menuCode, String parentMenuCode) {
		logger.info("findByMenuCodeAndParentMenuCode->【menuCode:{}, parentMenuCode:{}】", menuCode, parentMenuCode);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemMenuVO(),systemMenuManager.findByMenuCodeAndParentMenuCode(menuCode, parentMenuCode)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByMenuCodeAndParentMenuCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<SystemMenuVO>> findByParentMenuCode(String parentMenuCode, Long companyId) {
		logger.info("findByParentMenuCode->【parentMenuCode:{}, companyId:{}】", parentMenuCode, companyId);
		try{
			List<SystemMenu> systemMenuList = systemMenuManager.findByParentMenuCode(parentMenuCode, companyId);
			return MisRespResult.success(ImsBeanUtil.copyList(systemMenuList, SystemMenuVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findByParentMenuCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<Long> saveOrUpdate(SystemMenuVO vo) {
		logger.info("saveOrUpdate->【menuCode:{}, companyId:{}】", vo.getMenuCode(), vo.getCompanyId());
		try{
			Long menuId = systemMenuManager.saveOrUpdate(vo);
			return MisRespResult.success(menuId);
		}catch(Exception e){
			logger.error("<--系统异常,【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<Void> deleteByMenuCode(String menuCode, Long companyId) {
		logger.info("deleteByMenuCode->【menuCode:{}, companyId:{}】", menuCode, companyId);
		try{
			systemMenuManager.deleteByMenuCode(menuCode, companyId);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【deleteByMenuCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<Void> deleteById(Long menuId) {
		logger.info("deleteById->【menuId:{}】", menuId);
		try{
			systemMenuManager.deleteByIdArray(menuId+"");
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【deleteById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
