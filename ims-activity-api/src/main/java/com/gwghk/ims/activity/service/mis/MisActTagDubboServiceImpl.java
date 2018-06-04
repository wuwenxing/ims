package com.gwghk.ims.activity.service.mis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.manager.ActTagManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.base.MisActTagDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.base.ActTagVO;

/**
 * 摘要：Mis标签服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月9日
 */
@Service
public class MisActTagDubboServiceImpl implements MisActTagDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisActTagDubboServiceImpl.class);
	
	@Autowired
	private ActTagManager actTagManager;
	
	/**
	 * 功能：分页查询
	 */
	public MisRespResult<PageR<ActTagVO>> findPageList(ActTagVO userTagVO,Long companyId) {
		try{
			return MisRespResult.success(actTagManager.findPageList(userTagVO,companyId));
		}catch(Exception e){
			logger.error("<--findPageList 系统异常!!!",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：根据Id->查询用户标签
	 */
	public MisRespResult<ActTagVO> findById(Long id,Long companyId) {
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActTagVO(), actTagManager.findById(id,companyId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：保存用户标签
	 */
	public MisRespResult<Void> save(ActTagVO  userTagVO,Long companyId){
		logger.info("save-->【{},{}】",userTagVO,companyId);
		try{
			actTagManager.save(userTagVO,companyId);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：批量删除用户标签(id之间使用,分割)
	 */
	public MisRespResult<Void> deleteByIds(String ids,Long companyId){
		logger.info("deleteByIds-->【{},{}】",ids);
		try{
			actTagManager.deleteByIds(ids,companyId);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【deleteByIds】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：根据code->查询用户标签
	 */
	@Override
	public MisRespResult<ActTagVO> findByCode(String code, Long companyId) {
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActTagVO(), actTagManager.findByCode(code,companyId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}