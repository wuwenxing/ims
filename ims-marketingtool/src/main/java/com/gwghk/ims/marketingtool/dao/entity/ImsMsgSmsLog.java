package com.gwghk.ims.marketingtool.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：短信记录实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月15日
 */
@Table(name="ims_msg_sms_log")
public class ImsMsgSmsLog extends BaseEntity{
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    /**
     * 模板code
     */
    private String code;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信内容
     */
    private String content;
    
    /**
     * 请求地址
     */
    private String reqUrl;

    /**
     * 发送渠道
     */
    private String channel;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 失败的次数
     */
    private Integer failTimes;

    /**
     * 发送状态
     */
    private String status;

    /**
     * 调用渠道返回的状态码
     */
    private String respCode;
    
    /**
     * 来源
     */
    private String source;

    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 敏感数据如串码
     */
    private String sensitiveData ;
    
    public ImsMsgSmsLog(){
    }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
	
	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getFailTimes() {
		return failTimes;
	}

	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
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

	public String getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(String sensitiveData) {
		this.sensitiveData = sensitiveData;
	}
	
}