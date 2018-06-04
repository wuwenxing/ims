package com.gwghk.ims.common.dto.marketingtool;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * 摘要：消息推送-app请求消息
 * #push_message_request
{
	"id": 唯一UUID[必填]
	"type": 推送類型[必填](sms/email/app)
	"app": 推送應用 [必填](GWFX_GTS2_APP/HX_GTS2_APP/PM_GTS_APP)
	"recipents": 接收人數組[必填](mobileNo/email/account Number) eg. ["86000001","86000002","86000003"] 
	"pushType": 推送方式[app必填](all/tag/alias/device)
	"msg": 消息体  {
		"title": 消息標題 [email/app必填]
		"content":  消息内容 [sms/email/app必填]
		"extra": 推送自定義參數[app選填] { 
			   
		}
		"channel": 推送途徑,默認jpush[app選填](jpush/xinge)
		"control": 推送控制選項[app選填]{
 			"devices": 推送設備array，默認推送全部設備[app選填](ios/android) eg.['ios', 'android']
			"ios": ios控制選項[ios選填]{
				"badge":"+1", 
				"content-available":true,
			},
			"android": android控制選項 [android選填]{
				
			}
		}
	}
	"time": 推送時間 timestamp,
	"signature": 保留字段 
}
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月4日
 */
public class PushMessageReqDTO {

	/**
	 * UUID(唯一)
	 */
	private String id;
	
	/**
	 * 推送類型[必填](sms/email/app)
	 */
	private String type;
	
	/**
	 * 推送應用 [必填](GWFX_GTS2_APP/HX_GTS2_APP/PM_GTS_APP)
	 */
	private String app;
	
	/**
	 * 接收人數組[必填](mobileNo/email/account Number) eg. ["86000001","86000002","86000003"] 
	 */
	private List<String> recipents;
	
	/**
	 * 推送方式[app必填](all/tag/alias/device)
	 */
	private String pushType;
	
	/**
	 * 消息体
	 */
	private PushMessageRequestBody msg;
	
	/**
	 *  推送時間 timestamp
	 */
	private Long time;
	
	/**
	 * 保留字段 
	 */
	private String signature;
	
	public PushMessageRequestBody newPushMessageRequestBody(){
		return new PushMessageRequestBody();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public List<String> getRecipents() {
		return recipents;
	}

	public void setRecipents(List<String> recipents) {
		this.recipents = recipents;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public PushMessageRequestBody getMsg() {
		return msg;
	}

	public void setMsg(PushMessageRequestBody msg) {
		this.msg = msg;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public class PushMessageRequestBody {
		
		/**
		 * 消息標題[email/app必填]
		 */
		private String title;
		
		/**
		 * 消息内容 [sms/email/app必填]
		 */
		private String content;
		
		/**
		 * 推送自定義參數[app選填]
		 */
		private BodyExtra extra;
		
		/**
		 * 推送途徑,默認jpush[app選填](jpush/xinge)
		 */
		private String channel;
		
		/**
		 * 推送控制選項[app選填]
		 */
		private BodyControl control;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public BodyExtra getExtra() {
			return extra;
		}

		public void setExtra(BodyExtra extra) {
			this.extra = extra;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public BodyControl getControl() {
			return control;
		}

		public void setControl(BodyControl control) {
			this.control = control;
		}

		public BodyExtra newBodyExtra(){
			return new BodyExtra();
		}
		
		public BodyControl newBodyControl(){
			return new BodyControl();
		}
	}
	
	public class BodyExtra {
		/**
		 * 消息来源
		 */
		private String source;
		
		/**
	     * 内容类型(1:图片 2：图文 3; 其他)
	     */
	    private String contentType;

	    /**
	     * 显示位置(1、小窗 2、 跑马灯 3、工具栏),多个用逗号分隔
	     */
	    private String msgShowPosition;
	    
	    /**
	     * 消息摘要
	     */
	    private String summary;
	    
	    /**
		 * 其他信息
		 */
		private String otherMsg;
	    
	    /**
	     * 扩展字段1
	     */
	    private String ext1;

	    /**
	     * 扩展字段2
	     */
	    private String ext2;

	    /**
	     * 扩展字段3
	     */
	    private String ext3;

	    /**
	     * 扩展字段4
	     */
	    private String ext4;

	    /**
	     * 扩展字段5
	     */
	    private String ext5;
	    
		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getMsgShowPosition() {
			return msgShowPosition;
		}

		public void setMsgShowPosition(String msgShowPosition) {
			this.msgShowPosition = msgShowPosition;
		}

		public String getExt1() {
			return ext1;
		}

		public void setExt1(String ext1) {
			this.ext1 = ext1;
		}

		public String getExt2() {
			return ext2;
		}

		public void setExt2(String ext2) {
			this.ext2 = ext2;
		}

		public String getExt3() {
			return ext3;
		}

		public void setExt3(String ext3) {
			this.ext3 = ext3;
		}

		public String getExt4() {
			return ext4;
		}

		public void setExt4(String ext4) {
			this.ext4 = ext4;
		}

		public String getExt5() {
			return ext5;
		}

		public void setExt5(String ext5) {
			this.ext5 = ext5;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getOtherMsg() {
			return otherMsg;
		}

		public void setOtherMsg(String otherMsg) {
			this.otherMsg = otherMsg;
		}
	}
	
	public class  BodyControl {
		/**
		 * 推送設備array，默認推送全部設備[app選填](ios/android) eg.['ios', 'android']
		 */
		private List<String> devices;
		
		/**
		 * ios控制選項[ios選填]{
				"badge":"+1", 
				"content-available":true,
			}
		 */
		private ControlIos ios;
		
		/**
		 * android控制選項 [android選填]
		 */
		private ControlAndroid android;

		public List<String> getDevices() {
			return devices;
		}

		public void setDevices(List<String> devices) {
			this.devices = devices;
		}

		public ControlIos getIos() {
			return ios;
		}

		public void setIos(ControlIos ios) {
			this.ios = ios;
		}

		public ControlAndroid getAndroid() {
			return android;
		}

		public void setAndroid(ControlAndroid android) {
			this.android = android;
		}
	}
	
	private class ControlIos{
	}
	
	private class ControlAndroid {
	}
	
	public static void main(String[] args) {
		PushMessageReqDTO dto = new PushMessageReqDTO();
		dto.setId("kj4kehh11222");
		dto.setType("app");
		dto.setApp("GWFX_GTS2_APP");
		dto.setRecipents(Lists.newArrayList("13542652215","13642652215","13842652215"));
		dto.setPushType("all");
		PushMessageRequestBody body = dto.newPushMessageRequestBody();
		body.setTitle("app消息推送");
		body.setContent("哈哈哈");
		BodyExtra extra = body.newBodyExtra();
    	body.setExtra(extra);
		dto.setMsg(body);
		dto.setTime(System.currentTimeMillis());
		dto.setSignature(null);
		System.out.println(JSON.toJSONString(dto));
	}
}
