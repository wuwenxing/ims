package com.gwghk.ims.marketingtool.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：App消息推送记录
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月4日
 */
@Table(name="ims_msg_app_log")
public class ImsMsgAppLog extends BaseEntity {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    /**
     * 模板code
     */
    private String code;

    /**
     * 消息ID
     */
    private String msgId;
    
    /**
     * 接收人(多个人之间使用逗号分隔)
     */
    private String recipents;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 模板内容类型(1:图片 2：图文 3; 其他)
     */
    private Integer contentType;
    
    /**
     * 内容摘要
     */
    private String summary;

    /**
     * 消息显示位置(1、小窗 2、 跑马灯 3、工具栏),多个用逗号分隔
     */
    private String msgShowPosition;

    /**
     * 跳转的链接
     */
    private String link;

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
     * 其他信息(json)
     */
    private String otherMsg;

    /**
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * 扩展字段3
     */
    private String ext3;

    /**
     * 扩展字段4
     */
    private String ext4;

    /**
     * 扩展字段5
     */
    private String ext5;
    
    /**
     * 推送app(多个app之间用,分隔)
     */
    private String pushApp;
    
    /**
     * 推送方式(all/tag/alias/device)
     */
    private String pushType;
    /**
     * 敏感数据如串码
     */
    private String sensitiveData ;
    
    /** 1营销消息  2预警消息*/
    private Integer msgType ;
    /**推送的设备  ios android pcui webui */
    private String pushDevices ;

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

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getRecipents() {
		return recipents;
	}

	public void setRecipents(String recipents) {
		this.recipents = recipents;
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

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getMsgShowPosition() {
		return msgShowPosition;
	}

	public void setMsgShowPosition(String msgShowPosition) {
		this.msgShowPosition = msgShowPosition;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getOtherMsg() {
		return otherMsg;
	}

	public void setOtherMsg(String otherMsg) {
		this.otherMsg = otherMsg;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getPushApp() {
		return pushApp;
	}

	public void setPushApp(String pushApp) {
		this.pushApp = pushApp;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	

	public String getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(String sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getPushDevices() {
		return pushDevices;
	}

	public void setPushDevices(String pushDevices) {
		this.pushDevices = pushDevices;
	}

	@Override
	public String toString() {
		return "ImsMsgAppLog [id=" + id + ", code=" + code + ", msgId=" + msgId + ", recipents=" + recipents
				+ ", title=" + title + ", content=" + content + ", contentType=" + contentType + ", summary=" + summary
				+ ", msgShowPosition=" + msgShowPosition + ", link=" + link + ", sendTime=" + sendTime + ", failTimes="
				+ failTimes + ", status=" + status + ", otherMsg=" + otherMsg + ", ext1=" + ext1 + ", ext2=" + ext2
				+ ", ext3=" + ext3 + ", ext4=" + ext4 + ", ext5=" + ext5 + ", pushApp=" + pushApp + ", pushType="
				+ pushType + ", sensitiveData=" + sensitiveData + ", msgType=" + msgType + ", pushDevices="
				+ pushDevices + "]";
	}
}