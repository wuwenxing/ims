package com.gwghk.ims.marketingtool.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gwghk.ims.common.dto.marketingtool.SmsDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.SmsChannelEnum;
import com.gwghk.ims.common.enums.SmsStatusEnum;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsLog;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsTpl;
import com.gwghk.ims.marketingtool.dao.inf.ImsMsgSmsLogDao;
import com.gwghk.ims.marketingtool.util.AppConfigUtil;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：短信发送业务处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年1月23日
 */
@Component
@Transactional
public class SmsManager {
	private static Logger logger = LoggerFactory.getLogger(SmsManager.class);
	
	@Autowired
	private ImsMsgSmsTplManager imsMsgSmsTplManager;
	
	@Autowired
	private ImsMsgSmsLogDao smsLogMapper;
	
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	private Lock lock = new ReentrantLock();
	
	/**
	 * 功能： 待发送的短信列表
	 */
	public void waitingSendList(String companyId){
		try{
			lock.lock();
			Map<String,Object> dataMap = new HashMap<>();
			dataMap.put("companyId", companyId);
			List<ImsMsgSmsLog>  smsLogList = smsLogMapper.findWaitingSendList(Long.valueOf(companyId));
			if(!CollectionUtils.isEmpty(smsLogList)){
				for(ImsMsgSmsLog smsLog : smsLogList){
					if(StringUtil.isEmpty(smsLog.getContent())){
						continue;
					}
					try{
						if(CompanyEnum.fx.getId().equals(companyId)){
							String fullUrl = smsLog.getReqUrl() +"?phone="+smsLog.getMobile()+"&catalog=others&platform=FX&country=China";
							String content = "";
							try {
								content = URLEncoder.encode(smsLog.getContent(),"UTF-8");
							} catch (UnsupportedEncodingException e) {
								logger.error(e.getMessage(), e);
							}
							fullUrl += "&content=" + content;
							String result = HttpClientUtil.doGet(fullUrl, null);
							logger.info("sms send waitingSendList->url:{},result:{}",fullUrl,result);
							smsLog.setSendTime(new Date());
							smsLog.setRespCode(result);
							if(StringUtil.isNotEmpty(result) && result.contains("OK")){
								smsLog.setStatus(SmsStatusEnum.success.getValue());
							}else{
								smsLog.setStatus(SmsStatusEnum.fail.getValue());
								Integer failTimes = smsLog.getFailTimes()!=null ? smsLog.getFailTimes()+1:1;
								smsLog.setFailTimes(failTimes);
							}
							smsLog.setUpdateDate(new Date());
							smsLogMapper.update(smsLog) ;
						}
					}catch(Exception e){
						logger.error(e.getMessage(),e);
					}
				}
			}
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 功能：发送短信
	 */
	public void sendSms(SmsDTO smsDTO){
		String content = smsDTO.getContent();
		if(StringUtil.isEmpty(content)){
			ImsMsgSmsTpl imsMsgSmsTpl = imsMsgSmsTplManager.findByCode(smsDTO.getTplCode(),Long.valueOf(smsDTO.getCompanyId()));
			if(imsMsgSmsTpl != null){
				Map<String,Object> tplArgs = smsDTO.getTplArgs();
				content =  StringUtil.replaceTemplateArg(imsMsgSmsTpl.getTplContent(),tplArgs == null ? new HashMap<>():tplArgs);
			}else{
				logger.warn("没有配置对应的模板!");
			}
		}
		if(CompanyEnum.fx.getId().equals(smsDTO.getCompanyId())){
			sendWithUCWeb(smsDTO.getTplCode(),smsDTO.getMobile(),content,smsDTO.getCompanyId(),
						  AppConfigUtil.getProperty("sms.ucweb.fx.url"),smsDTO.getSource(),smsDTO.getBusinessNo());
		}
	}
	
	/**
	 * 功能：使用UCWEB发送
	 * @param tplCode  模板code
	 * @param mobile   手机号
	 * @param content  内容
	 * @param reqUrl   接口地址
	 */
	private void sendWithUCWeb(String tplCode,String mobile,String content,String companyId,String reqUrl,String source,String businessNo){
		ImsMsgSmsLog smsLog = new ImsMsgSmsLog();
		smsLog.setCode(tplCode);
		smsLog.setMobile(mobile);
		smsLog.setChannel(SmsChannelEnum.UCWEB.getCode());
		smsLog.setContent(content);
		smsLog.setReqUrl(reqUrl);
		smsLog.setSource(source);
		smsLog.setBusinessNo(businessNo);
		smsLog.setEnableFlag("Y");
		smsLog.setDeleteFlag("N");
		smsLog.setCompanyId(Long.valueOf(companyId));
		smsLog.setCreateDate(new Date());
		String fullUrl = reqUrl +"?phone="+mobile+"&catalog=others&platform=FX&country=China";
		try {
			content = URLEncoder.encode(content,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		fullUrl += "&content=" + content;
		String result = HttpClientUtil.doGet(fullUrl, null);
		logger.info("sms send sendWithUCWeb->url:{},result:{}",fullUrl,result);
		smsLog.setRespCode(result);
		if(StringUtil.isNotEmpty(result) && result.contains("OK")){ //发送成功
			smsLog.setFailTimes(0);
			smsLog.setStatus(SmsStatusEnum.success.getValue());
		}else{
			smsLog.setFailTimes(1);
			smsLog.setStatus(SmsStatusEnum.fail.getValue());
		}
		smsLog.setSendTime(new Date());
		taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	try{
            		smsLogMapper.save(smsLog) ;
            	}catch(Exception e){
            		logger.error(e.getMessage(),e);
            	}
            }
	    });
	}
}