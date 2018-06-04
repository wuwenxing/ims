package com.gwghk.ims.common.inf.external.marketingtool;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;

/**
 * 摘要：消息推送服务接口
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年1月23日
 */
public interface AppPushMsgDubboService {
	void waitingSendList(@Company Long companyId);
	
	void sendTplMsg(String tplCode,String recipents,Map<String,String> tplArgs,String sendTime,String ext1,String companyId);
	
	void sendCustomMsg(String pushApp,String pushType,String recipents,String title,String content,String otherMsg,
					   String contentType,String msgShowPosition,String summary,String sendTime,
					   String ext1,String companyId);
}
