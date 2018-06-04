package com.gwghk.ims.marketingtool.manager;

import java.lang.reflect.Field;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.druid.util.StringUtils;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.inf.mis.base.MisKeyValDubboService;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;
import com.gwghk.ims.common.vo.base.KeyValVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgDataVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppTpl;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgBind;
import com.gwghk.ims.marketingtool.enums.AppMsgBindTypeEnum;
import com.gwghk.ims.marketingtool.enums.AppPushMsgStatusEnum;
import com.gwghk.ims.marketingtool.kafka.producer.KafkaAppMessagePushProducer;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.gwghk.unify.framework.common.util.UUID32Generator;

/**
 * 摘要：消息处理中心入口 发送app消息Manager
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月3日
 */
@Component
@Transactional
public class ImsMsgAppManager {

	Logger logger = LoggerFactory.getLogger(ImsMsgBindManager.class) ;
	
	@Autowired
	private ImsMsgAppLogManager imsMsgAppLogManager ;
	@Autowired
	private ImsMsgAppTplManager imsMsgAppTplManager ;
	@Autowired
	private ImsMsgBindManager imsMsgBindManager ;
	@Autowired
	private MisKeyValDubboService misKeyValDubboService ;
	@Autowired
	private MisActItemsSettingDubboService misActItemsSettingDubboService ;
	@Autowired
	private KafkaAppMessagePushProducer kafkaAppMessagePushProducer ;
	
	
	private Lock lock = new ReentrantLock();
	
	/**
	 * 功能： 待发送的App消息列表
	 */
	public void waitingSendList(Long companyId) {
		if(!isEnableAppPush(companyId)){
			return;
		}	
		try {
			lock.lock();
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("companyId", companyId);
			List<ImsMsgAppLog> appPushMsgLogList = imsMsgAppLogManager.findWaitingSendList(companyId);
			if (!CollectionUtils.isEmpty(appPushMsgLogList)) {
				for (ImsMsgAppLog appPushMsgLog : appPushMsgLogList) {
					if (StringUtil.isEmpty(appPushMsgLog.getTitle()) || StringUtil.isEmpty(appPushMsgLog.getContent())
							|| (appPushMsgLog.getSendTime() != null && new Date().getTime() < appPushMsgLog.getSendTime().getTime())) {
						continue;
					}
					try {
						String msgId = appPushMsgLog.getMsgId(), 
							   recipents = StringUtil.isNullOrEmpty(appPushMsgLog.getRecipents()) ? "" : appPushMsgLog.getRecipents(),
							   title = appPushMsgLog.getTitle(), content = appPushMsgLog.getContent(),
							   otherMsg = appPushMsgLog.getOtherMsg(), msgShowPosition = appPushMsgLog.getMsgShowPosition(),
							   ext1 = appPushMsgLog.getExt1();
						Integer contentType = appPushMsgLog.getContentType();
						ApiRespResult<String> apiResult = kafkaAppMessagePushProducer.pushKafkaAppMessage(appPushMsgLog.getPushApp(),appPushMsgLog.getPushType(),
							   msgId,recipents,title,content,appPushMsgLog.getSummary(),otherMsg,contentType != null?contentType.toString():null, 
							   msgShowPosition,ext1, companyId);
						if (apiResult != null && apiResult.isOk()) {
							String respMsgId = apiResult.getData();
							if (StringUtil.isNotEmpty(respMsgId)) {
								logger.info("ims->app msg sending for response!respMsgId:{}", respMsgId);
								appPushMsgLog.setStatus(AppPushMsgStatusEnum.sending.getValue());
							} else {
								logger.error("msg->app msg sending fail!");
								appPushMsgLog.setStatus(AppPushMsgStatusEnum.fail.getValue());
								Integer failTimes = appPushMsgLog.getFailTimes() != null
										? appPushMsgLog.getFailTimes() + 1 : 1;
								appPushMsgLog.setFailTimes(failTimes);
							}
							appPushMsgLog.setUpdateDate(new Date());
							
							imsMsgAppLogManager.saveOrUpdate(appPushMsgLog);
									
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 发送模板消息
	 * @param recipents 接收人 多个用,隔开
	 * @param sendTime 发送消息时间 yyyy-MM-dd HH:mm:ss
	 * @param msgBind 绑定消息的对象
	 * @param msgData 消息模板赋值对象
	 * @param companyId
	 * @return
	 */
	public MisRespResult<Void> sendAppMsgByTpl(String recipents,String sendTime, ImsMsgBindVO msgBind,ImsMsgDataVO msgData,Long companyId){
		MisRespResult<Void> mis = new MisRespResult<Void>() ;
		if(null == msgBind || StringUtils.isEmpty(msgBind.getBindCode()) || StringUtils.isEmpty(msgBind.getAppCode())){
			mis.setRespMsg(MisResultCode.Msg40005) ;
			return mis;
		}
		if(!isEnableAppPush(companyId)){
			mis.setRespMsg(MisResultCode.Msg40001) ;
			return mis;
		}
        if(!isEnablePushItem(msgBind.getBindCode(),companyId)){
        	mis.setRespMsg(MisResultCode.Msg40002) ;
			return mis;
        }
        String appCode = msgBind.getAppCode() ;
        //根据绑定编号或者模板编号，获取模板对象
        if(!StringUtils.isEmpty(msgBind.getBindCode())){
        	ImsMsgBind imsMsgBind  = imsMsgBindManager.findByBindCode(msgBind.getBindCode()); 
        	if(null != imsMsgBind){
        		appCode = imsMsgBind.getAppCode(); 
        	}
        }
        if(StringUtils.isEmpty(appCode)){
        	mis.setRespMsg(MisResultCode.Msg40003) ;
			return mis;
        }
        ImsMsgAppTpl appTpl = imsMsgAppTplManager.findByCode(appCode) ;
        if(null == appTpl){
        	mis.setRespMsg(MisResultCode.Msg40004) ;
			return mis;
        }
        Map<String,Object> tplArgs = getTplArgs(appTpl.getTplContent(), msgData); 
        
		String content = StringUtil.replaceTemplateArg(appTpl.getTplContent(),tplArgs == null ? new HashMap<>():tplArgs),
			   summary = StringUtil.replaceTemplateArg(appTpl.getSummary()==null?"":appTpl.getSummary(),tplArgs == null ? new HashMap<>():tplArgs),
			   title = appTpl.getTplName(),
			   otherMsg = appTpl.getOtherMsg(),
			   msgShowPosition = appTpl.getMsgShowPosition(),
			   pushApp = appTpl.getPushApp(),
			   pushType = appTpl.getPushType();
	    Integer contentType = appTpl.getTplContentType();
	    String[] recipentArr = new String[]{""} ;
	    if(!StringUtils.isEmpty(recipents)){
	    	recipentArr = recipents.split(",");
	    }
		if (recipentArr != null && recipentArr.length > 0) {
			for (String recipent : recipentArr) {
				ImsMsgAppLog ims = new ImsMsgAppLog() ;
				ims.setPushType(pushType);
				ims.setPushApp(pushApp);
				ims.setRecipents(recipent);
				ims.setTitle(title);
				ims.setContent(content);
				ims.setOtherMsg(otherMsg);
				ims.setContentType(contentType);
				ims.setMsgShowPosition(msgShowPosition);
				ims.setSummary(summary);
				ims.setSendTime(DateUtil.stringToDate(sendTime,DateUtil.DATETIME_PATTERN));
				ims.setExt1(appTpl.getExt1());
				ims.setCode(appCode);
				doPushAppMsg(ims, companyId);
			}
		}
		return mis ;
	}
	
	/**
	 * 功能：发送自定义app消息
	 * @param pushApp 推送app(多个用,分隔)FX GTS2 APP,HX GTS2 APP,FXDEMO GTS2 APP,PM GTS APP 
	 * @param pushType 推送方式(all/tag/alias/device)
	 * @param recipents 接收人(多个用,分隔)
	 * @param title 标题
	 * @param content 内容
	 * @param otherMsg 其他扩展信息(json格式)
	 * @param contentType 模板内容类型(1:图片 2：图文 3; 其他)
	 * @param msgShowPosition 消息显示位置(1、弹出窗  2、 跑马灯 3、工具栏),多个用逗号分隔
	 * @param summary 内容摘要
	 * @param sendTime 发送时间
	 * @param ext1 扩展字段，可以保存物品编号，活动编号
	 * @param companyId
	 * @return
	 */
	public MisRespResult<Void> sendAppMsgByCustom(ImsMsgAppLog ims,Long companyId) {
		MisRespResult<Void> mis = new MisRespResult<Void>() ;
		if(!isEnableAppPush(companyId)){
			mis.setRespMsg(MisResultCode.Msg40001) ;
			return mis;
		}
	    if(!isEnablePushItem(ims.getExt1(),companyId)){
	    	mis.setRespMsg(MisResultCode.Msg40002) ;
			return mis;
        }
	    
	    String[] recipentArr = new String[]{""} ;
	    if(!StringUtils.isEmpty(ims.getRecipents())){
	    	recipentArr = ims.getRecipents().split(",") ;
	    }
		if (recipentArr != null && recipentArr.length > 0) {
			for (String recipent : recipentArr) {
				ims.setRecipents(recipent) ;
				doPushAppMsg(ims,companyId) ;
			}
		}
		return mis ;
	}
	
	/**
	 * 功能：是否启用App 消息推送
	 */
	private boolean isEnableAppPush(Long companyId){
		KeyValVO keyVal = misKeyValDubboService.findByKey("isEnableAppPush", companyId) ;
		return keyVal != null && "0".equals(keyVal.getDataVal()) ? false : true;
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
                    /*ActSetting actSetting = actSettingMapper.getByActivityPeriods(bindCode);
                    if(actSetting==null){
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：活动不存在【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                    //禁用
                    if("N".equals(actSetting.getEnableFlag())){
                        logger.info("isEnablePushItem->判断绑定编号失效中，原因：活动禁用【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }
                    //加上完成天数
                    if(actSetting.getEndTime()!=null && actSetting.getFinishDays()!=null && actSetting.getFinishDays()>0){
                        actSetting.setEndTime(ActDateUtil.addDay(actSetting.getEndTime(), actSetting.getFinishDays()));
                    }
                    // 时间限制：
                    if (actSetting.getEndTime() != null && curTime.after(actSetting.getEndTime())) { // 时间过期
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：活动时间过期【bindCode:{}】",new Object[]{bindCode});
                       return false;
                    } else if (actSetting.getStartTime() != null && curTime.before(actSetting.getStartTime())) { // 时间未到
                        logger.error("isEnablePushItem->判断绑定编号失效中，原因：活动时间未到【bindCode:{}】",new Object[]{bindCode});
                        return false;
                    }*/
                }
            }
        }
        return true;
    }
    
    private void doPushAppMsg(ImsMsgAppLog imsMsgAppLog, Long companyId){
    	ImsMsgAppLog appPushMsgLog = ImsBeanUtil.copyNotNull(new ImsMsgAppLog(), imsMsgAppLog);
		appPushMsgLog.setOtherMsg(HtmlUtils.htmlUnescape(appPushMsgLog.getOtherMsg()));
		appPushMsgLog.setContentType(appPushMsgLog.getContentType());
		appPushMsgLog.setSendTime(null == appPushMsgLog.getSendTime()?new Date():appPushMsgLog.getSendTime());
		appPushMsgLog.setEnableFlag("Y");
		appPushMsgLog.setDeleteFlag("N");
		appPushMsgLog.setCompanyId(companyId);
		appPushMsgLog.setCreateDate(new Date());
		appPushMsgLog.setUpdateDate(new Date());
		appPushMsgLog.setMsgId(UUID32Generator.generate());
		appPushMsgLog.setFailTimes(0);
		appPushMsgLog.setStatus(AppPushMsgStatusEnum.waiting.getValue());
		try {
			imsMsgAppLogManager.saveOrUpdate(appPushMsgLog) ;
		} catch (Exception e) {
			logger.info("doPushAppMsg Exception:{}",new Object[]{e});
		}
		logger.info("push app msg waiting !【{}】", appPushMsgLog);
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