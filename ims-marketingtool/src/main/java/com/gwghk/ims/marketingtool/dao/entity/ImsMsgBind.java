package com.gwghk.ims.marketingtool.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：消息绑定实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月15日
 */
@Table(name="ims_msg_bind")
public class ImsMsgBind extends BaseEntity{
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    /**
     * 绑定编号
     */
    private String bindCode;

    /**
     * 短信模板code
     */
    private String smsCode;

    /**
     * app模板code
     */
    private String appCode;

    /**
     * 绑定类型  item(物品) | activity(活动)
     */
    private String bindType;

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

	/**
     * 绑定编号
     * @return bind_code 绑定编号
     */
    public String getBindCode() {
        return bindCode;
    }

    /**
     * 绑定编号
     * @param bindCode 绑定编号
     */
    public void setBindCode(String bindCode) {
        this.bindCode = bindCode == null ? null : bindCode.trim();
    }

    /**
     * 短信模板code
     * @return sms_code 短信模板code
     */
    public String getSmsCode() {
        return smsCode;
    }

    /**
     * 短信模板code
     * @param smsCode 短信模板code
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode == null ? null : smsCode.trim();
    }

    /**
     * app模板code
     * @return app_code app模板code
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * app模板code
     * @param appCode app模板code
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    /**
     * 绑定类型  item(物品) | activity(活动)
     * @return bind_type 绑定类型  item(物品) | activity(活动)
     */
    public String getBindType() {
        return bindType;
    }

    /**
     * 绑定类型  item(物品) | activity(活动)
     * @param bindType 绑定类型  item(物品) | activity(活动)
     */
    public void setBindType(String bindType) {
        this.bindType = bindType == null ? null : bindType.trim();
    }

    /**
     * 扩展字段1
     * @return ext1 扩展字段1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 扩展字段1
     * @param ext1 扩展字段1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 扩展字段2
     * @return ext2 扩展字段2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 扩展字段2
     * @param ext2 扩展字段2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * 扩展字段3
     * @return ext3 扩展字段3
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 扩展字段3
     * @param ext3 扩展字段3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    /**
     * 扩展字段4
     * @return ext4 扩展字段4
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * 扩展字段4
     * @param ext4 扩展字段4
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * 扩展字段5
     * @return ext5 扩展字段5
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * 扩展字段5
     * @param ext5 扩展字段5
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }

}