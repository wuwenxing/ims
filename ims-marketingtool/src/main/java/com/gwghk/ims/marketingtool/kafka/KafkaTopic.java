package com.gwghk.ims.marketingtool.kafka;

public enum KafkaTopic {
	/**app消息推送-app请求消息*/
	PUSH_MESSAGE_REQUEST("push_message_request","app消息推送-app请求消息"),
	/**app消息推送-app响应消息*/
	PUSH_MESSAGE_RESPONSE("push_message_response","app消息推送-app响应消息"),
	;
	
	private String value;
	private String name;

	KafkaTopic(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
