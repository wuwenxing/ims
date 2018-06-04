package com.gwghk.ims.marketingtool.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.inf.external.marketingtool.FlowDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;
import com.gwghk.ims.marketingtool.manager.market.ImsMarkingService;

/**
 * 摘要:
 * 
 * @author George.li
 * @date 2018年5月31日
 * @version 1.0
 */
public class FlowDubboServiceImpl implements FlowDubboService {
	private static final Logger logger = LoggerFactory.getLogger(OnlineDubboServiceImpl.class);

	@Autowired
	@Qualifier("imsFlowManager")
	private ImsMarkingService imsFlowManager;

	@Override
	public ApiRespResult<Map<String, Object>> send(String phone, String size, String flowChannel, String ip,
			Map<String, String> extMap, Long companyId) {
		logger.info("send-->【RechargeType:{},RechargeSize:{},companyId:{}】", flowChannel, size, companyId);
		try {
			ImsRechargeLogDetailVO vo = new ImsRechargeLogDetailVO();
			vo.setPhone(phone);
			vo.setRechargeSize(size);
			vo.setCreateIp(ip);
			vo.setCompanyId(companyId);
		    vo.setExt1(extMap.get("ext1"));
		    vo.setExt2(extMap.get("ext2"));
		    vo.setExt3(extMap.get("ext3"));
		    vo.setInterfaceType(flowChannel);
			MisRespResult<Map<String, Object>> result = imsFlowManager.send(vo);
			return ImsBeanUtil.copyNotNull(new ApiRespResult<Map<String, Object>>(), result);
		} catch (Exception e) {
			logger.error("<--系统异常，【send】", e);
			return ApiRespResult.error(ApiResultCode.EXCEPTION);
		}
	}

	@Override
	public boolean callbackFromRl(String data, String ip) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean callbackFromOF(String retCode, String sporderId, String orderSuccessTime, String errMsg, String ip) {
		// TODO Auto-generated method stub
		return false;
	}

}
