package com.gwghk.ims.common.inf.external.marketingtool;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 话费接口实现
 * @author wayne
 */
public interface OnlineDubboService {
	ApiRespResult<Map<String, Object>> send(String phone, String size, String onlineChannel, String ip, Map<String, String> extMap,
			@Company Long companyId);
	
	boolean callback(String retCode, String sporderId, String orderSuccessTime, String errMsg, String ip);
}