package com.gwghk.ims.common.inf.external.marketingtool;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 流量接口实现
 * @author wayne
 */
public interface FlowDubboService {
	ApiRespResult<Map<String, Object>> send(String phone, String size, String flowChannel, String ip, Map<String, String> extMap,@Company Long companyId);
	
	boolean callbackFromRl(String data, String ip);

	boolean callbackFromOF(String retCode, String sporderId, String orderSuccessTime, String errMsg, String ip);
}
