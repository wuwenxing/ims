package com.gwghk.ims.monitor.notice;

import java.io.Serializable;

/**
 * 摘要：简单邮件请求对象(没有模板,邮件内容只有title和content)
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年12月8日
 */
public class EmailSimpleReqDTO implements Serializable{

	private static final long serialVersionUID = 6203296253092310572L;

	/**
	 * 第三方使用者
	 */
	private String company;
	
	/**
	 * 收件人(收件人之间用,连接)
	 */
	private String recipients;
	
	/**
	 * 邮件标题
	 */
	private String title;
	
	/**
	 * 邮件内容
	 */
	private String content;

	/**
	 * 是否异步发送(默认同步发送)
	 */
	private boolean asyn = false;
	
	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isAsyn() {
		return asyn;
	}

	public void setAsyn(boolean asyn) {
		this.asyn = asyn;
	}

	@Override
	public String toString() {
		return "[company=" + company + ", recipients=" + recipients + ", title=" + title
				+ ", content=" + content + ", asyn=" + asyn + "]";
	}
}
