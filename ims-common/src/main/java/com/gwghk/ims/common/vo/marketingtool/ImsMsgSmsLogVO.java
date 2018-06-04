package com.gwghk.ims.common.vo.marketingtool;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.enums.AuthColumnEnum;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 
 * 摘要：短信发送记录VO
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */

public class ImsMsgSmsLogVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -5045176032593623253L;

	private Integer id;

    /**
     * 模板code
     */
    private String code;

    /**
     * 手机号
     */
    @AuthColumn(columnName=AuthColumnEnum.MOBILEPHONE)
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@OrderBy(columnName="send_time",propName="sendTime",order="desc")
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
    @AuthColumn(columnName=AuthColumnEnum.STRINGCODE,refColumn="content")
    private String sensitiveData ;
    
    /**
     * 发送时间开始
     */
    private String startTimeStr;
    /**
     * 发送时间结束
     */
    private String endTimeStr;
    
    public ImsMsgSmsLogVO(){
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(String sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

	@Override
	public String toString() {
		return "ImsMsgSmsLogVO [id=" + id + ", code=" + code + ", mobile=" + mobile + ", content=" + content
				+ ", reqUrl=" + reqUrl + ", channel=" + channel + ", sendTime=" + sendTime + ", failTimes=" + failTimes
				+ ", status=" + status + ", respCode=" + respCode + ", source=" + source + ", businessNo=" + businessNo
				+ ", sensitiveData=" + sensitiveData + ", startTimeStr=" + startTimeStr + ", endTimeStr=" + endTimeStr
				+ "]";
	}
	
}