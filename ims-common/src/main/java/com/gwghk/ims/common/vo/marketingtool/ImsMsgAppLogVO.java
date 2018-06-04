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
 * 摘要：app消息内容VO
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
public class ImsMsgAppLogVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 529195307383145475L;

	private Integer id;
	/**
	 * 多个id用,隔开
	 */
	private String ids ;

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
     * 模板内容类型(1:图片 2：图文 3; 其他)
     */
    private String contentTypeTxt;
    
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
    
    /** 1营销消息  2预警消息*/
    private Integer msgType ;
    private String msgTypeTxt ;
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
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	

	public String getContentTypeTxt() {
		return contentTypeTxt;
	}

	public void setContentTypeTxt(String contentTypeTxt) {
		this.contentTypeTxt = contentTypeTxt;
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
	

	public String getMsgTypeTxt() {
		return msgTypeTxt;
	}

	public void setMsgTypeTxt(String msgTypeTxt) {
		this.msgTypeTxt = msgTypeTxt;
	}

	@Override
	public String toString() {
		return "ImsMsgAppLogVO [id=" + id + ", ids=" + ids + ", code=" + code + ", msgId=" + msgId + ", recipents="
				+ recipents + ", title=" + title + ", content=" + content + ", contentType=" + contentType
				+ ", contentTypeTxt=" + contentTypeTxt + ", summary=" + summary + ", msgShowPosition=" + msgShowPosition
				+ ", link=" + link + ", sendTime=" + sendTime + ", failTimes=" + failTimes + ", status=" + status
				+ ", otherMsg=" + otherMsg + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + ", ext4=" + ext4
				+ ", ext5=" + ext5 + ", pushApp=" + pushApp + ", pushType=" + pushType + ", sensitiveData="
				+ sensitiveData + ", startTimeStr=" + startTimeStr + ", endTimeStr=" + endTimeStr + ", msgType="
				+ msgType + ", pushDevices=" + pushDevices + "]";
	}
	
}