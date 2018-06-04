package com.gwghk.ims.base.service.mis;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.base.manager.ImsBlackListManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.base.MisImsBlackListDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.base.ImsBlackListVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：全局黑名单服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月4日
 */
@Service
public class MisImsBlackListDubboServiceImpl implements MisImsBlackListDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisImsBlackListDubboServiceImpl.class);
	
	@Autowired
	private ImsBlackListManager imsBlackListManager;
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ImsBlackListVO imsBlackListVO,@Company Long companyId) {
		logger.info("findPageList-->【{}】",imsBlackListVO);
		try{
			return imsBlackListManager.findPageList(imsBlackListVO,companyId);
		}catch(Exception e){
			logger.error("<--findPageList 系统异常!!!",e);
			return null;
		}
	}
	
	/**
	 * 功能：根据id->查询全局黑名单
	 */
	public MisRespResult<ImsBlackListVO> findById(Long id, @Company Long companyId) {
		logger.info("findById-->【id:{},companyId:{}】", id, companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsBlackListVO(), imsBlackListManager.findById(id)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：根据账号->查询全局黑名单
	 */
	public MisRespResult<ImsBlackListVO> findByAccount(ImsBlackListVO vo, @Company Long companyId) {
		logger.info("findById-->【accountNo:{},companyId:{}】", vo.getAccountNo(), companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsBlackListVO(), imsBlackListManager.findListByMap(vo)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByAccount】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * 功能：保存全局黑名单
	 */
	public MisRespResult<Integer> batchSave(Set<String> accounts,String ip,@Company Long companyId){
		logger.info("batchSave-->【{},{}】",JsonUtil.obj2Str(accounts),companyId);
		try{
			int sum = imsBlackListManager.saveBatch(accounts,ip,companyId);
			logger.info("批量保存全局黑名单成功!共{}条",sum);
			return MisRespResult.success(sum);
		}catch(Exception e){
			logger.error("<--系统异常,【batchSave】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}