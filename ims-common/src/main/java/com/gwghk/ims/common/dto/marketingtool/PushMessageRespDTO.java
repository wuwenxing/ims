package com.gwghk.ims.common.dto.marketingtool;

import java.util.List;

/**
 * 摘要：消息推送-app响应消息
 * 
 * #push_message_response
{
	"id": 唯一UUID
	"result": 推送結果 OK/FAIL
	"channle": 推送途徑 jpush/xinge
	"ref": 推送參考號碼{
		"ios": 12345678
		"android":  12345678
	}
	"time": 1516731943511
}
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月4日
 */
public class PushMessageRespDTO {

	/**
	 *  唯一UUID
	 */
	private String id;
	
	/**
	 *  推送結果 OK/FAIL
	 */
	private String result;
	
	/**
	 *  推送途徑 jpush/xinge
	 */
	private String channle;
	
	/**
	 * 推送參考號碼
	 */
	private List<String> ref;
	
	/**
	 * 推送時間
	 */
	private Long time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getChannle() {
		return channle;
	}

	public void setChannle(String channle) {
		this.channle = channle;
	}

	public List<String> getRef() {
		return ref;
	}

	public void setRef(List<String> ref) {
		this.ref = ref;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}
