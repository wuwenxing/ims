package com.gwghk.ims.common.dto.marketingtool;

import java.io.Serializable;
import java.util.Map;

/**
 * 摘要：短信DTO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月8日
 */
public class SmsDTO implements Serializable {
	private static final long serialVersionUID = -4759702893270627235L;

	/**
	 * 手机号(必填)
	 */
	private String mobile;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 模板编号
	 */
	private String tplCode;
	
	/**
	 * 语言
	 */
	private String lang;
	
	/**
	 * 模板参数
	 */
	private Map<String,Object> tplArgs;
	
	/**
     * 来源
     */
    private String source;

    /**
     * 业务编号
     */
    private String businessNo;
	
	/**
	 * 公司ID
	 */
	private String companyId;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Map<String, Object> getTplArgs() {
		return tplArgs;
	}

	public void setTplArgs(Map<String, Object> tplArgs) {
		this.tplArgs = tplArgs;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Override
	public String toString() {
		return "[mobile=" + mobile + ", content=" + content + ", tplCode=" + tplCode + ", lang=" + lang
				+ ", tplArgs=" + tplArgs + ", source=" + source + ", businessNo=" + businessNo + ", companyId="
				+ companyId + "]";
	}
}
