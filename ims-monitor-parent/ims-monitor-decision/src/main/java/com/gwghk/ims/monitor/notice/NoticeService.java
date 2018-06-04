package com.gwghk.ims.monitor.notice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gwghk.ims.monitor.util.ResourcesUtil;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：通知相关处理(短信、邮件或其他)
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
@Component
public class NoticeService {
	private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
	
	public void notice(String emailTitle,String content){
		String alert = ResourcesUtil.application.getString("ims.alert.mode");
		if(alert.contains("sms")){   //短信告警
			sendSms(content);
		}
		if(alert.contains("email")){  //邮件告警
			sendEmail(emailTitle,content);
		}
	}
	
	/**
	 * 功能：邮件通知
	 * @param emailTitle 邮件通知标题
	 * @param content 通知内容
	 */
	@SuppressWarnings("unchecked")
	public void sendEmail(String emailTitle,String content){
		String emailStaff = ResourcesUtil.application.getString("ims.email.common.staff");
		try {
    		Map<String,String> dataMap = new HashMap<>();
    		dataMap.put("company", "ims-alert");
    		dataMap.put("recipients", emailStaff);
    		dataMap.put("title", emailTitle);
    		dataMap.put("content", content);
    		logger.info("sendEmail->start,param:"+dataMap);
		    String url = ResourcesUtil.application.getString("common.service.gateway.url")+"/email/simple/send";
		    String apiResult = HttpClientUtil.doPostWithMap(url,dataMap, SignUtil.getHeaderMap());
	        GwApiRespResult<Void> result = JsonUtil.json2Obj(apiResult, GwApiRespResult.class);
            if(result != null){
            	if(result.isOk()){
            		logger.info("sendEmail->end,发送邮件成功！！！result:"+apiResult);
            	}else{
            		logger.error("sendEmail->end,发送邮件失败！！！code:"+result.getCode()+",msg:"+result.getMsg());
            	}
            }
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 功能：短信通知
	 * @param content 通知内容
	 */
	@SuppressWarnings("unchecked")
	public void sendSms(String content){
		String smsStaff = ResourcesUtil.application.getString("ims.sms.common.staff");
		String[] smsStaffArr = smsStaff.split(",");
		if(smsStaffArr != null && smsStaffArr.length > 0){
			for(int i=0;i<smsStaffArr.length;i++){
				SmsReqDTO smsReqDTO = new SmsReqDTO();
				smsReqDTO.setAppCode("ims_alert");
				smsReqDTO.setChannel("ims");
				smsReqDTO.setCompany("fx");
				smsReqDTO.setContent(content);
				smsReqDTO.setMobile(smsStaffArr[i]);
		    	try {
		    		String reqJson = JsonUtil.obj2Str(smsReqDTO);
		    		logger.info("sendSms->start,param:"+reqJson);
				    String url = ResourcesUtil.application.getString("common.service.gateway.url")+"/sms/custom/send";
				    String apiResult = HttpClientUtil.doPostWithJson(url,reqJson, SignUtil.getHeaderMap());
				    if(StringUtil.isNotEmpty(apiResult)){
				    	GwApiRespResult<Void> result = JsonUtil.json2Obj(apiResult, GwApiRespResult.class);
				        if(result != null){
				        	if(result.isOk()){
				        		logger.info("sendSms->end,发送短信成功！！！result:"+apiResult);
				        	}else{
				        		logger.error("sendSms->end,发送短信失败！！！code:"+result.getCode()+",msg:"+result.getMsg());
				        	}
			            }
				    }
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
	}
}
