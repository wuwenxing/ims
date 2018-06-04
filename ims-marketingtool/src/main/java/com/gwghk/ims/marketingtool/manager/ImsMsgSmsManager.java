package com.gwghk.ims.marketingtool.manager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.SmsChannelEnum;
import com.gwghk.ims.common.enums.SmsSourceEnum;
import com.gwghk.ims.common.enums.SmsStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgDataVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgBind;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsLog;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsTpl;
import com.gwghk.ims.marketingtool.enums.AppMsgBindTypeEnum;
import com.gwghk.ims.marketingtool.util.AppConfigUtil;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * 摘要：消息处理中心入口 短信消息Manager
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月3日
 */
@Component
@Transactional
public class ImsMsgSmsManager {

	Logger logger = LoggerFactory.getLogger(ImsMsgBindManager.class) ;
	
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private ImsMsgSmsLogManager imsMsgSmsLogManager ;
	@Autowired
	private ImsMsgSmsTplManager imsMsgSmsTplManager ;
	@Autowired
	private ImsMsgBindManager imsMsgBindManager ;
	@Autowired
	private MisActItemsSettingDubboService misActItemsSettingDubboService ;
	@Autowired
	private MisActSettingDubboService misActSettingDubboService ;
	
	private Lock lock = new ReentrantLock();
	
	/**
	 * 功能： 待发送的短信消息列表
	 */
	public void waitingSendList(Long companyId) {
		try{
			lock.lock();
			Map<String,Object> dataMap = new HashMap<>();
			dataMap.put("companyId", companyId);
			List<ImsMsgSmsLog>  smsLogList = imsMsgSmsLogManager.findWaitingSendList(companyId);
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
							imsMsgSmsLogManager.saveOrUpdate(smsLog);
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
	 * 功能：使用消息模板发送短信消息
	 * @param mobile 手机号
	 * @param source 来源   发放记录|人工发放
	 * @param businessNo 业务编号
	 * @param sendTime 发送时间
	 * @param msgBind 
	 * @param msgData
	 * @param companyId
	 * @return
	 */
	public MisRespResult<Void> sendSmsMsgByTpl(String mobile,String source,String businessNo,String sendTime, ImsMsgBindVO msgBind,ImsMsgDataVO msgData,Long companyId){
		MisRespResult<Void> mis = new MisRespResult<Void>() ;
		if(null == msgBind){
			mis.setRespMsg(MisResultCode.Msg40005) ;
			return mis;
		}
        if(!isEnablePushItem(msgBind.getBindCode(),companyId)){
        	mis.setRespMsg(MisResultCode.Msg40002) ;
			return mis;
        }
        String smsCode = msgBind.getSmsCode() ;
        //根据绑定编号或者模板编号，获取模板对象
        if(!StringUtils.isEmpty(msgBind.getBindCode())){
        	ImsMsgBind imsMsgBind  = imsMsgBindManager.findByBindCode(msgBind.getBindCode()); 
        	if(null != imsMsgBind){
        		smsCode = imsMsgBind.getAppCode(); 
        	}
        }
        if(StringUtils.isEmpty(smsCode)){
        	mis.setRespMsg(MisResultCode.Msg40006) ;
			return mis;
        }
        ImsMsgSmsTpl smsTpl = imsMsgSmsTplManager.findByCode(smsCode,companyId);
        if(null == smsTpl){
        	mis.setRespMsg(MisResultCode.Msg40007) ;
			return mis;
        }
       
        String content = smsTpl.getTplContent() ;
		if(StringUtil.isEmpty(content)){
			Map<String,Object> tplArgs = getTplArgs(smsTpl.getTplContent(), msgData); 
			content =  StringUtil.replaceTemplateArg(smsTpl.getTplContent(),tplArgs == null ? new HashMap<>():tplArgs);
		}
		if(CompanyEnum.fx.getId().equals(companyId)){
			sendWithUCWeb(smsCode,mobile,content,companyId,
						  AppConfigUtil.getProperty("sms.ucweb.fx.url"),source,businessNo,null);
		}else{
			mis.setRespMsg(MisResultCode.Msg40008) ;
			return mis ;
		}
		return mis ;
	}
	
	/**
	 * 功能：发送自定义短信消息
	 * @param mobile 手机号
	 * @param content 内容
	 * @param source 来源
	 * @param businessNo 业务编号
	 * @param companyId
	 * @return
	 */
	public MisRespResult<Void> sendSmsMsgByCustom(String mobile,String content,String businessNo,String remark,Long companyId) {
		String source = SmsSourceEnum.ARTIFICIAL_REISSUE.getValue(); 
		MisRespResult<Void> mis = new MisRespResult<Void>() ;
		if(CompanyEnum.fx.getId().equals(companyId.toString())){
			sendWithUCWeb(null,mobile,content,companyId,
						  AppConfigUtil.getProperty("sms.ucweb.fx.url"),source,businessNo,remark);
		}else{
			mis.setRespMsg(MisResultCode.Msg40008) ;
			return mis ;
		}
		return mis ;
	}
	
	/**
	 * 功能：使用UCWEB发送
	 * @param tplCode  模板code
	 * @param mobile   手机号
	 * @param content  内容
	 * @param reqUrl   接口地址
	 */
	private void sendWithUCWeb(String tplCode,String mobile,String content,Long companyId,String reqUrl,String source,String businessNo,String remark){
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
		smsLog.setCompanyId(companyId);
		smsLog.setRemark(remark);
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
            		imsMsgSmsLogManager.saveOrUpdate(smsLog);
            	}catch(Exception e){
            		logger.error(e.getMessage(),e);
            	}
            }
	    });
	}
	
	/**
     * 功能：判断绑定编号 是否生效中
     *  true:生效中
     *  false;已禁用或已失效或数量不足
     */
    private boolean isEnablePushItem(String bindCode,Long companyId){
        if(StringUtil.isNotEmpty(bindCode)){
            ImsMsgBind tAppPushMsgItem = imsMsgBindManager.findByBindCode(bindCode) ;
            if(tAppPushMsgItem!=null){
                Date curTime = new Date();
                if(AppMsgBindTypeEnum.bind_item.getValue().equals(tAppPushMsgItem.getBindType())){
                    //物品数量不足、禁用、失效都返回false;
                    MisRespResult<ActItemsSettingVO> resItem = misActItemsSettingDubboService.findByItemNumber(bindCode, null, companyId) ;
                    if(resItem.isNotOk()){
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：物品不存在【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                    ActItemsSettingVO actItemsSetting = resItem.getData() ;
                    // 是否禁用
                    if (!"Y".equals(actItemsSetting.getEnableFlag())) {// 禁用
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：物品已禁用【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                    // 数量是否足够
                    else if (ActItemTypeEnum.REAL.getCode().equals(actItemsSetting.getItemType())
                        || ActItemTypeEnum.VIRTUAL.getCode().equals(actItemsSetting.getItemType())
                        || ActItemTypeEnum.INTERFACE.getCode().equals(actItemsSetting.getItemType())) {// 实物/虚拟物品/接口物品，有数品数量限制
                        if (actItemsSetting.getItemUsableAmount() ==null || actItemsSetting.getItemUsableAmount() <= 0) {
                            logger.error("isEnablePushItem->判断绑定编号失效中，原因：物品数量不足【bindCode:{}】",new Object[]{bindCode});
                            return false;
                        }
                    }
                    // 时间限制：
                    else if (actItemsSetting.getEndDate() != null && curTime.after(actItemsSetting.getEndDate())) { // 时间过期
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：物品时间过期【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    } else if (actItemsSetting.getStartDate() != null && curTime.before(actItemsSetting.getStartDate())) { // 时间未到
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：物品时间未到【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                }else if(AppMsgBindTypeEnum.bind_activity.getValue().equals(tAppPushMsgItem.getBindType())){
                    //活动已失效都返回false;
                    MisRespResult<ActSettingDetailsVO> res = misActSettingDubboService.findByActivityPeriods(bindCode, companyId) ;
                    if(res.isNotOk()){
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：活动不存在【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                    ActSettingVO actSetting = res.getData().getActBaseInfo();
                    //禁用
                    if("N".equals(actSetting.getEnableFlag())){
                        logger.info("isEnablePushItem->判断绑定编号失效中，原因：活动禁用【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                    //加上完成天数
                    if(actSetting.getEndTime()!=null && actSetting.getFinishDays()!=null && actSetting.getFinishDays()>0){
                        actSetting.setEndTime(DateUtil.addDay(actSetting.getEndTime(), actSetting.getFinishDays()));
                    }
                    // 时间限制：
                    if (actSetting.getEndTime() != null && curTime.after(actSetting.getEndTime())) { // 时间过期
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：活动时间过期【bindCode:{}】",new Object[]{bindCode});
                       return false;
                    } else if (actSetting.getStartTime() != null && curTime.before(actSetting.getStartTime())) { // 时间未到
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：活动时间未到【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 模板key与字段值绑定
     * @param msgContent
     * @param actDemoPrizeRecord
     * @return
     */
    private Map<String,Object> getTplArgs(String msgContent,ImsMsgDataVO msgData){
		Class<? extends ImsMsgDataVO> cls = msgData.getClass() ;
		Map<String, Object> tplArgs = new HashMap<>();
		Pattern p = Pattern.compile("\\$\\{(.*?)\\}"); 
		Matcher m = p.matcher(msgContent) ;
		while(m.find()){
			try{
				String name = m.group(1) ;
				Field f = cls.getDeclaredField(name) ;
				f.setAccessible(true);
				Object val = f.get(msgData) ;
				if(val instanceof String){
					if(val.toString().startsWith("FX_")){
						val = val.toString().split("_")[1] ;
					}
				}
				if(null == val)val = "" ;
				tplArgs.put(name,val) ;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return tplArgs ;
	}
}