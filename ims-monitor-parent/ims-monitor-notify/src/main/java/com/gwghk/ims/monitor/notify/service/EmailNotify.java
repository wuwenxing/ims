package com.gwghk.ims.monitor.notify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.enums.AlertLevelEnum;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.consumer.IAlertNotify;
import com.gwghk.ims.monitor.notify.dao.entity.DutyStaff;
import com.gwghk.ims.monitor.notify.utils.GwApiRespResult;
import com.gwghk.ims.monitor.notify.utils.ResourcesUtil;
import com.gwghk.ims.monitor.notify.utils.SignUtil;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 邮件发送方式告警
 * @author jackson.tang
 *
 */
public class EmailNotify implements IAlertNotify {
	private final Logger logger=LoggerFactory.getLogger(this.getClass()); 
	
	@Override
	public boolean send(List<DutyStaff> staffList,MonitorResultVo resultDto) {
		
		String alertLevelName=AlertLevelEnum.find(resultDto.getAlertLevel()).name();
		String title="【"+alertLevelName+"级别错误】 监控条目 -"+resultDto.getItemName()+"- 发现"+alertLevelName+"错误";
		String content=resultDto.getAlertMsg()+" ,来源:"+resultDto.getHostname()+" >> "+resultDto.getItemName();
		
		return sendMail(staffList,title,content);
	}
	
	/**
	 * 发送邮件,只要出错返回false，但是会继续执行下去
	 * @param staffList
	 * @param title
	 * @param content
	 * @return
	 */
	private boolean sendMail(List<DutyStaff> staffList,String title,String content) {
		boolean result=true;
		
		for(DutyStaff staff:staffList) {
			try {
	    		Map<String,String> dataMap = new HashMap<>();
	    		dataMap.put("company", "ims-alert");
	    		dataMap.put("recipients", staff.getEmail());
	    		dataMap.put("title", title);
	    		dataMap.put("content", content);
	    		logger.info("sendEmail->start,param:"+dataMap);
			    String url = ResourcesUtil.application.getString("common.service.gateway.url")+"/email/simple/send";
			    String apiResult = HttpClientUtil.doPostWithMap(url,dataMap, SignUtil.getHeaderMap());
			    
		        GwApiRespResult<Void> rts = JsonUtil.json2Obj(apiResult, GwApiRespResult.class);
	            if(rts != null){
	            	if(rts.isOk()){
	            		logger.info("sendEmail->end,发送邮件成功！！！result:"+apiResult);
	            		staff.setNotifyStatusMap("mail", "ok");
	            		continue;
	            	}else{
	            		logger.error("sendEmail->end,发送邮件失败！！！code:"+rts.getCode()+",msg:"+rts.getMsg());
	            	}
	            }
	            
	            
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			staff.setNotifyStatusMap("mail", "fail");
			result=false;
		}
		
		return result;
	}

}
