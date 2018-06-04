package com.gwghk.ims.common.inf.external.marketingtool;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.dto.marketingtool.SmsDTO;

/**
 * 摘要：短信服务接口
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年1月23日
 */
public interface SmsDubboService {
	void sendSms(SmsDTO smsDTO);
	
	void waitingSendList(@Company Long companyId);
}
