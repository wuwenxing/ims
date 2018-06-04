package com.gwghk.ims.monitor.notify.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.consumer.IAlertNotify;
import com.gwghk.ims.monitor.notify.dao.entity.DutyStaff;
import com.gwghk.ims.monitor.notify.utils.GwApiRespResult;
import com.gwghk.ims.monitor.notify.utils.ResourcesUtil;
import com.gwghk.ims.monitor.notify.utils.SignUtil;
import com.gwghk.ims.monitor.notify.utils.SmsReqDTO;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 短信发送服务
 * @author jackson.tang
 *
 */
public class SmsNotify implements IAlertNotify {
	private final Logger logger=LoggerFactory.getLogger(this.getClass()); 

	@Override
	public boolean send(List<DutyStaff> staffList, MonitorResultVo resultDto) {
		String content=resultDto.getAlertMsg()+" ,来源:"+resultDto.getHostname()+" >> "+resultDto.getItemName();
		return sendSms(staffList,content);
	}
	
	/**
	 * 单条发送，出现一条错误返回false，但是会继续发送短信
	 * @param officerList
	 * @param content
	 * @return
	 */
	private boolean sendSms(List<DutyStaff> officerList, String content) {
		boolean result=true;
		
		for(DutyStaff staff:officerList){
			SmsReqDTO smsReqDTO = new SmsReqDTO();
			smsReqDTO.setAppCode("ims_alert");
			smsReqDTO.setChannel("ims");
			smsReqDTO.setCompany("fx");
			smsReqDTO.setContent(content);
			smsReqDTO.setMobile(staff.getPhone());
	    	try {
	    		String reqJson = JSON.toJSONString(smsReqDTO);
	    		logger.info("sendSms->start,param:"+reqJson);
			    String url = ResourcesUtil.application.getString("common.service.gateway.url")+"/sms/custom/send";
			    String apiResult = HttpClientUtil.doPostWithJson(url,reqJson, SignUtil.getHeaderMap());
			    if(StringUtil.isNotEmpty(apiResult)){
			    	GwApiRespResult<Void> rst = JsonUtil.json2Obj(apiResult, GwApiRespResult.class);
			        if(rst != null){
			        	if(rst.isOk()){
			        		logger.info("sendSms->end,发送短信成功！！！result:"+apiResult);
			        		staff.setNotifyStatusMap("sms", "ok");
			        		continue;
			        	}else{
			        		logger.error("sendSms->end,发送短信失败！！！code:"+rst.getCode()+",msg:"+rst.getMsg());
			        	}
		            }
			    }
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
	    	staff.setNotifyStatusMap("sms", "fail");
	    	result=false;
		}
		
		return result;
	}

}
