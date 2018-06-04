package com.gwghk.ims.monitor.notice;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 摘要：短信请求对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月29日
 */
public class SmsReqDTO implements Serializable{
	private static final long serialVersionUID = -2608217145581887277L;

	/**
	 * 手机号
	 */
	@NotBlank
	private String mobile;
	
	/**
	 * 应用方code
	 */
	private String appCode;
	
	/**
     * 模板编号
     */
    private String tplCode;
	
	/**
	 * 短信内容(code或content一个有值就可以)
	 */
	private String content;
	
	/**
	 * 短信模板中对应的变量参数集合
	 */
	private Map<String,Object> tplArgsData;
	
	/**
	 * 渠道
	 */
	private String channel;
	
	/**
	 * 客户端ip
	 */
	private String clientIp;
	
	/**
	 * 公司
	 */
	private String company;
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Map<String, Object> getTplArgsData() {
		return tplArgsData;
	}

	public void setTplArgsData(Map<String, Object> tplArgsData) {
		this.tplArgsData = tplArgsData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "mobile=" + mobile + ", appCode=" + appCode + ", tplCode=" + tplCode + ", content=" + content
				+ ", channel=" + channel + ", clientIp=" + clientIp + ", company="
				+ company + "";
	}
}