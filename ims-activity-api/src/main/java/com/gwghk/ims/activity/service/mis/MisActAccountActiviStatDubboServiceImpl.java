package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStatView;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.activity.manager.ActAccountActiviStatViewManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisActAccountActiviStatDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatViewVO;

/**
 * 摘要：活动参与用户
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisActAccountActiviStatDubboServiceImpl implements MisActAccountActiviStatDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisActAccountActiviStatDubboServiceImpl.class);

	@Autowired
	private ActAccountActiviStatViewManager actAccountActivityViewManager;
	@Autowired
	private ActAccountActiviStatManager actAccountActivityManager;
	
	public MisRespResult<PageR<ActAccountActiviStatViewVO>> findPageList(ActAccountActiviStatViewVO vo, @Company Long companyId) {
		logger.info("findPageList-->【AccountNo:{}, companyId:{}】", vo.getAccountNo(), vo.getCompanyId());
		try{
			PageR<ActAccountActiviStatViewVO> pageR = actAccountActivityViewManager.findPageList(vo);
			return MisRespResult.success(pageR);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<ActAccountActiviStatViewVO>> findList(ActAccountActiviStatViewVO vo, @Company Long companyId) {
		logger.info("findList-->【AccountNo:{}, companyId:{}】", vo.getAccountNo(), vo.getCompanyId());
		try{
			List<ActAccountActiviStatView> list = actAccountActivityViewManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ActAccountActiviStatViewVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<ActAccountActiviStatViewVO> findViewById(Long id, @Company Long companyId) {
		logger.info("findViewById-->【id:{}】", id);
		try{
			ActAccountActiviStatView viewObj = actAccountActivityViewManager.findViewById(id);
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActAccountActiviStatViewVO(), viewObj));
		}catch(Exception e){
			logger.error("<--系统异常,【findViewById【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<Void> saveOrUpdate(ActAccountActiviStatViewVO vo, @Company Long companyId) {
		logger.info("findViewById-->【id:{}】", vo.getId());
		try{
			return actAccountActivityManager.saveOrUpdate(vo);
		}catch(Exception e){
			logger.error("<--系统异常,【findViewById【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}