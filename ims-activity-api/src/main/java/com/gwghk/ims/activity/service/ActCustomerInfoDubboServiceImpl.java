package com.gwghk.ims.activity.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.manager.ActCustomerInfoManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.dto.activity.ActCustomerInfoDTO;
import com.gwghk.ims.common.inf.external.activity.ActCustomerInfoDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;

/**
 * 
 * 摘要：清洗后的客户信息
 * 
 * @author
 * @version 1.0
 * @Date 2017年10月28日
 */
@Service
public class ActCustomerInfoDubboServiceImpl implements ActCustomerInfoDubboService {

	private static final Logger logger = LoggerFactory.getLogger(ActCustomerInfoDubboServiceImpl.class);

	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager;


	/**
	 * 获得模拟账号信息
	 * 
	 * @param accountNo
	 * @param platform
	 * @param companyId
	 * @return
	 */
	@Override
	public MisRespResult<ActCustomerInfoDTO> findActCustomerInfo(String accountNo, String platform, Long companyId) {
		logger.info("ActCustomerInfoServiceImpl listDemoActCustomerInfo start----");
		try{
			ActCustomerInfo actCustomerInfo = actCustomerInfoManager.findByAccountNo(accountNo, platform) ;
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActCustomerInfoDTO(), actCustomerInfo)) ;
		}catch(Exception e){
			logger.error("<--listDemoActCustomerInfo 系统异常!!!",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：分页查询
	 */
	@Override
	public MisRespResult<Map<String,Object>> findPageList(ActCustomerInfoDTO dto, Long companyId) {
		MisRespResult<Map<String,Object>> result= new MisRespResult<Map<String,Object>>() ;
		try {
			logger.info("ActCustomerInfoServiceImpl findPageList---");
			Map<String,Object> map = actCustomerInfoManager.findPageList(ImsBeanUtil.copyNotNull(new ActCustomerInfoVO(), dto)) ;
			result.setData(map) ;
		} catch (Exception e) {
			logger.error("<--findPageList 系统异常!!!", e);
			result.setRespMsg(MisResultCode.EXCEPTION) ;
			result.setData(null) ;
		}
		return result;
	}

}
