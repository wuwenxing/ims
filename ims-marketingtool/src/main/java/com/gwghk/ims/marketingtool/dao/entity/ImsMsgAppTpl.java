package com.gwghk.ims.marketingtool.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：APP消息推送模板
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月7日
 */
@Table(name="ims_msg_app_tpl")
public class ImsMsgAppTpl extends BaseEntity {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    /**
     * 代码
     */
    private String code;

    /**
     * 语言
     */
    private String lang;

    /**
     * 模板名称
     */
    private String tplName;

    /**
     * 模板内容
     */
    private String tplContent;

    /**
     * 模板内容类型(1:图片 2：图文 3; 其他)
     */
    private Integer tplContentType;
    
    /**
     * 内容摘要
     */
    private String summary;

    /**
     * 消息显示位置(1、弹出窗  2、 跑马灯 3、工具栏),多个用逗号分隔
     */
    private String msgShowPosition;

    /**
     * 跳转地址,多个地址之间用逗号分隔
     */
    private String link;
    
    /**
     * 其他信息
     */
    private String otherMsg;
    
    /**
     * 推送app(多个app之间用,分隔)
     */
    private String pushApp;
    
    /**
     * 推送方式(all/tag/alias/device)
     */
    private String pushType;
    
    /**
     * 消息类型 1-2营销， 2-预警消息
     * */
    private Integer msgType;
    
    

	/**
     * 推送设备(多个设备之间用,分隔)
     * */
    private String pushDevices;
    
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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getTplContent() {
		return tplContent;
	}

	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}

	public Integer getTplContentType() {
		return tplContentType;
	}

	public void setTplContentType(Integer tplContentType) {
		this.tplContentType = tplContentType;
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
}