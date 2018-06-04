package com.gwghk.ims.common.enums;

/**
 * 监控枚举
 * @author jackson.tang
 *
 */
public enum MonitorEnum {
	/**
	 * QM主题 警告信息通知
	 */
	MQ_TOPIC_ALERT("ALERT_MSG_NOTIFY"),
	/**
	 * QM主题 监控数据保存
	 */
	MQ_TOPIC_PERSISTENCE("MONITOR_DATA_SAVE");
	
	private String value;
	
	MonitorEnum(String value){this.value=value;}
	public String value() {return this.value;}
}
