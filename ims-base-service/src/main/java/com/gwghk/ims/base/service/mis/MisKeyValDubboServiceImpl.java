package com.gwghk.ims.base.service.mis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.base.manager.KeyValManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.base.MisKeyValDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.base.KeyValVO;

/**
 * 摘要：Mis后台-键值对服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月9日
 */
@Service
public class MisKeyValDubboServiceImpl implements MisKeyValDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisKeyValDubboServiceImpl.class);
	
	@Autowired
	private KeyValManager keyValManager;
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(KeyValVO keyValVO,@Company Long companyId) {
		logger.debug("findPageList-->【{}】",keyValVO);
		try{
			return keyValManager.findPageList(keyValVO,companyId);
		}catch(Exception e){
			logger.error("<--findPageList 系统异常!!!",e);
			return null;
		}
	}
	
	/**
	 * 功能：根据键值对id->键值对信息
	 */
	public MisRespResult<KeyValVO> findById(Long id,@Company Long companyId) {
		logger.debug("findById-->【id:{}】",id);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new KeyValVO(), keyValManager.findById(id)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：保存或更新键值对
	 */
	public MisRespResult<Void> save(KeyValVO keyValVO,@Company Long companyId){
		logger.info("save-->【{}】",keyValVO);
		try{
			keyValManager.saveOrUpdate(keyValVO,companyId);
			logger.info("保存键值对记录成功！");
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：批量删除键值对(id之间使用,分割)
	 * @param ids 
	 */
	public MisRespResult<Void> deleteByIds(String ids,@Company Long companyId){
		logger.info("deleteByIds-->【{}】",ids);
		try{
			int sum = keyValManager.deleteByIds(ids);
			logger.info("批量删除键值对成功!共{}条",sum);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【deleteByIds】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：根据key->获取键值信息
	 */
	public KeyValVO findByKey(String key,@Company Long companyId){
		return ImsBeanUtil.copyNotNull(new KeyValVO(),keyValManager.findByKey(key, companyId));
	}
}