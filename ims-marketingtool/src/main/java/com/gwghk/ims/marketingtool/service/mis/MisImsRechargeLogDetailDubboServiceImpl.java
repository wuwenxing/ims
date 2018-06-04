package com.gwghk.ims.marketingtool.service.mis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsRechargeLogDetailDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsRechargeLogDetail;
import com.gwghk.ims.marketingtool.manager.ImsRechargeLogDetailManager;
import com.gwghk.ims.marketingtool.manager.market.ImsMarkingProxyManager;

@Service
public class MisImsRechargeLogDetailDubboServiceImpl implements MisImsRechargeLogDetailDubboService{

	private static final Logger logger = LoggerFactory.getLogger(MisImsRechargeLogDetailDubboServiceImpl.class);

	@Autowired
	private ImsMarkingProxyManager imsMarkingProxyManager;
	
	@Autowired
	private ImsRechargeLogDetailManager imsRechargeLogDetailManager;

	public Map<String,Object> findPageList(ImsRechargeLogDetailVO vo, @Company Long companyId) {
		try {
			return imsRechargeLogDetailManager.findPageList(vo);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	public MisRespResult<List<ImsRechargeLogDetailVO>> findList(ImsRechargeLogDetailVO vo, @Company Long companyId) {
		try {
			List<ImsRechargeLogDetail> list = imsRechargeLogDetailManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsRechargeLogDetailVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsRechargeLogDetailVO> findById(Long id, @Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(
					ImsBeanUtil.copyNotNull(new ImsRechargeLogDetailVO(), imsRechargeLogDetailManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> saveOrUpdate(ImsRechargeLogDetailVO vo, @Company Long companyId) {
		logger.info("saveOrUpdate-->【RechargeType:{},RechargeSize:{}】", vo.getRechargeType(),vo.getRechargeSize());
		try {
			imsRechargeLogDetailManager.saveOrUpdate(vo);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> deleteByIds(String ids, @Company Long companyId) {
		logger.info("deleteByIds-->【ids:{}】", ids);
		try {
			imsRechargeLogDetailManager.deleteByIds(ids);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Map<String, Object>> send(ImsRechargeLogDetailVO vo, @Company Long companyId) {
		logger.info("send-->【RechargeType:{},RechargeSize:{},companyId:{}】", vo.getRechargeType(),vo.getRechargeSize(),companyId);
		try {
			return imsMarkingProxyManager.send(vo);
		} catch (Exception e) {
			logger.error("<--系统异常，【send】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<Integer> batchSend(List<ImsRechargeLogDetailVO> voList, @Company Long companyId) {
		logger.info("batchSend-->【companyId:{},ImsRechargeLogDetailVO size:{}】", companyId,voList.size());
		int count=0;
		try {
			for(ImsRechargeLogDetailVO vo:voList) {
				MisRespResult<Map<String, Object>> msgMap=imsMarkingProxyManager.send(vo);
				if(msgMap.getCode().equals(MisResultCode.OK.getCode())){
					count++;
				}
			}	
			return MisRespResult.success(count); 
		} catch (Exception e) {
			logger.error("<--系统异常，【send】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
