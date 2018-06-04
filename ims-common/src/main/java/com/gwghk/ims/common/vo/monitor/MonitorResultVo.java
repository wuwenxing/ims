package com.gwghk.ims.common.vo.monitor;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 监控返回结果
 * @author jackson.tang
 *
 */
public class MonitorResultVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6865383143473289194L;
	
	private String hostname;
	private String itemName;
	private Long itemId;
	/**
	 * 服务端请求的起止时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date requestDate;
	/**
	 * 服务端收到响应后的时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date responceDate;
	
	/**
	 * dataType为2时 表示监控客户端开始时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date monitorDate;
	/**
	 * 数据类型:0.表示手动发送报警 1.单独一条  2.是单独的，但是数据是一个LIST集合 3.全量
	 */
	private int dataType;
	/**
	 * 状态:-1:请求失败 0:失败 1.成功
	 */
	private int code;
	/**
	 * 监控返回数据,这里的数据可能是String 也可能是List&lt;String&gt; 或者 List&lt;Map&lt;String,Sdtring&gt;&gt; (其中必包含name、value)
	 */
	private Object data;
	/**
	 * 报警详细说明，用于通知给响应的值班人员
	 */
	private String alertMsg;
	/**
	 * 警告级别
	 */
	private int alertLevel;
	
	public MonitorResultVo() {
		requestDate=new Date();//实例化就是请求开始的时间
	}
	
	public int getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(int alertLevel) {
		this.alertLevel = alertLevel;
	}
	/**
	 * 数据类型:1.单独一条  2.是单独的，但是数据是一个LIST集合 3.全量
	 */
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Date getMonitorDate() {
		return monitorDate;
	}
	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.responceDate=new Date();//设置结果既是响应时间
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getAlertMsg() {
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getResponceDate() {
		return responceDate;
	}
	public void setResponceDate(Date responceDate) {
		this.responceDate = responceDate;
	}
}
