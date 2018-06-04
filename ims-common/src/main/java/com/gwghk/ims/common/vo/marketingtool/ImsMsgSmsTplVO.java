package com.gwghk.ims.common.vo.marketingtool;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：短信模板VO
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
public class ImsMsgSmsTplVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -2506937761424971633L;

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
     * 发送地址,多个地址之间用逗号分隔
     */
    private String sendUrl;

    /**
     * 扩展字段
     */
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;
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

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
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
}