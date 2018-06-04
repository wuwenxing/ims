package com.gwghk.ims.marketingtool.kafka.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.dto.marketingtool.PushMessageReqDTO;
import com.gwghk.ims.common.dto.marketingtool.PushMessageReqDTO.BodyExtra;
import com.gwghk.ims.common.dto.marketingtool.PushMessageReqDTO.PushMessageRequestBody;
import com.gwghk.ims.marketingtool.kafka.KafkaTopic;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.gwghk.unify.framework.mq.kafka.KafkaProducer;

/**
 * 摘要：App消息推送生产者
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月4日
 */
public class KafkaAppMessagePushProducer extends KafkaProducer {
	private static Logger logger = LoggerFactory.getLogger(KafkaAppMessagePushProducer.class);

	/**
	 * 功能：通过Kafka推送 app消息
	 * @param pushApp 推送的app平台](例如：GWFX_GTS2_APP/HX_GTS2_APP/PM_GTS_APP ...)
	 * @param pushType 推送类型(all/tag/alias/device)
	 * @param msgId 消息ID
	 * @param recipents  接收人(mobileNo/email/account Number),建议传手机号
	 * @param title  标题
	 * @param content  内容
	 * @param summary  内容摘要
	 * @param otherMsg  其他信息
	 * @param contentType  模板内容类型(1:图片 2：图文 3; 其他)
	 * @param msgShowPosition  消息显示位置(1、小窗 2、 跑马灯 3、工具栏),多个用逗号分隔
	 * @param ext1    扩展字段1
	 * @param companyId  公司ID
	 */
	public ApiRespResult<String> pushKafkaAppMessage(String pushApp,String pushType,String msgId,String recipents,String title,String content,
		   String summary,String otherMsg, String contentType, String msgShowPosition, String ext1, Long companyId) {
		ApiRespResult<String> result = new ApiRespResult<String>();
		try {
			PushMessageReqDTO pushMessageRequest = new PushMessageReqDTO();
			pushMessageRequest.setId(msgId);
			pushMessageRequest.setType("app");
			pushMessageRequest.setApp(pushApp);
			pushMessageRequest.setPushType(StringUtil.isNotEmpty(pushType)?pushType:"all");
			pushMessageRequest.setRecipents(StringUtil.isNotEmpty(recipents) ? Lists.newArrayList(recipents) : null);
			PushMessageRequestBody msg = pushMessageRequest.newPushMessageRequestBody();
			msg.setTitle(title);
			msg.setContent(content);
			
			//扩展信息处理
			BodyExtra extra = msg.newBodyExtra();
			extra.setSource("ims");
			extra.setOtherMsg(otherMsg);
			extra.setContentType(contentType);
			extra.setSummary(summary);
			extra.setMsgShowPosition(msgShowPosition);
			extra.setExt1(ext1);
			msg.setExtra(extra);
			
			pushMessageRequest.setMsg(msg);
			pushMessageRequest.setTime(System.currentTimeMillis());
			String message = JsonUtil.obj2Str(pushMessageRequest);
			logger.info("ims->app!topic:{},msg:{}", KafkaTopic.PUSH_MESSAGE_REQUEST.getValue(), message);
			produce(KafkaTopic.PUSH_MESSAGE_REQUEST.getValue(), message);
			return result.setData(msgId).setRespMsg(ApiResultCode.OK);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return result.setData(null).setRespMsg(ApiResultCode.EXCEPTION);
		}
	}
	
	public void produce(String topic, String pushMessageRequestMsg) {
		try {
			logger.info("开始推送消息-Ims>App!【topic:{},msg:{}】", topic,pushMessageRequestMsg);
			super.produce(topic, pushMessageRequestMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
