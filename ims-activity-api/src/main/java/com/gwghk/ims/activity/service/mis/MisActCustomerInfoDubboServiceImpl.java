package com.gwghk.ims.activity.service.mis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.manager.ActCustomerInfoManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisActCustomerInfoDubboService;
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
public class MisActCustomerInfoDubboServiceImpl implements MisActCustomerInfoDubboService {

	private static final Logger logger = LoggerFactory.getLogger(MisActCustomerInfoDubboServiceImpl.class);

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
	public MisRespResult<ActCustomerInfoVO> findActCustomerInfo(String accountNo, String platform,@Company Long companyId) {
		logger.info("ActCustomerInfoServiceImpl listDemoActCustomerInfo start----");
		try{
			ActCustomerInfo actCustomerInfo = actCustomerInfoManager.findByAccountNo(accountNo, platform) ;
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActCustomerInfoVO(), actCustomerInfo)) ;
		}catch(Exception e){
			logger.error("<--listDemoActCustomerInfo 系统异常!!!",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：分页查询
	 */
	@Override
	public MisRespResult<Map<String,Object>> findPageList(ActCustomerInfoVO vo,@Company Long companyId) {
		MisRespResult<Map<String,Object>> result= new MisRespResult<Map<String,Object>>() ;
		try {
			logger.info("ActCustomerInfoServiceImpl findPageList---");
			Map<String,Object> map = actCustomerInfoManager.findPageList(vo) ;
			result.setData(map) ;
		} catch (Exception e) {
			logger.error("<--findPageList 系统异常!!!", e);
			result.setRespMsg(MisResultCode.EXCEPTION) ;
			result.setData(null) ;
		}
		return result;
	}

}
