package com.gwghk.ims.marketingtool.manager.market.online;

import java.util.HashMap;
import java.util.Map;

/**
 * 话费实体对象
 * 
 * @author wayne
 */
public class OnlineInfo {
	
	/**
	 * 话费充值记录ID-对应一次充值接口调用
	 */
	private Long onlineLogId;
	/**
	 * 11位手机号码
	 */
	private String phones;
	/**
	 * 手机归属地
	 */
	private String area;
	/**
	 * 充值话费第三方交易id-对应欧飞
	 */
	private String customId;
	/**
	 * 充值话费面值-对应欧飞
	 */
	private String size;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * 业务平台
	 */
	private Long companyId;
	
	/**
	 * 对应记录的扩展字段
	 */
	private Map<String, String> extMap = new HashMap<String, String>();
	
	public Long getOnlineLogId() {
		return onlineLogId;
	}
	public void setOnlineLogId(Long onlineLogId) {
		this.onlineLogId = onlineLogId;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Map<String, String> getExtMap() {
		return extMap;
	}
	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}
