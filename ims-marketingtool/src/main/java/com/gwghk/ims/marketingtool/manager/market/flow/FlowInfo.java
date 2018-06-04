package com.gwghk.ims.marketingtool.manager.market.flow;

import java.util.HashMap;
import java.util.Map;

/**
 * 流量实体对象
 * 
 * @author wayne
 */
public class FlowInfo {
	/**
	 * 流量充值记录ID-对应一次充值接口调用
	 */
	private Long flowLogId;
	/**
	 * 11位手机号码
	 */
	private String phones;
	/**
	 * 手机归属地
	 */
	private String area;
	/**
	 * 充值流量第三方交易id-对应容联\欧飞
	 */
	private String customIdRl;
	/**
	 * 充值流量档位编码-对应容联\欧飞(对应面值)
	 */
	private String flowNoRl;
	/**
	 * 充值流量大小-对应容联\欧飞
	 */
	private String flowSizeRl;
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
	
	public Long getFlowLogId() {
		return flowLogId;
	}

	public void setFlowLogId(Long flowLogId) {
		this.flowLogId = flowLogId;
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

	public String getCustomIdRl() {
		return customIdRl;
	}

	public void setCustomIdRl(String customIdRl) {
		this.customIdRl = customIdRl;
	}

	public String getFlowNoRl() {
		return flowNoRl;
	}

	public void setFlowNoRl(String flowNoRl) {
		this.flowNoRl = flowNoRl;
	}

	public String getFlowSizeRl() {
		return flowSizeRl;
	}

	public void setFlowSizeRl(String flowSizeRl) {
		this.flowSizeRl = flowSizeRl;
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
