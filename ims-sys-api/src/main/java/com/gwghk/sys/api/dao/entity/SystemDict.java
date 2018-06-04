package com.gwghk.sys.api.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：系统-数据字典
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年8月10日
 */
public class SystemDict extends BaseEntity {
    /**
     * 主键ID
     */
    private Long dictId;

    /**
     * 字典父code
     */
    private String parentDictCode;

    /**
     * 字典code
     */
    private String dictCode;

    /**
     * 名称-简体
     */
    private String dictNameCn;

    /**
     * 名称-繁体
     */
    private String dictNameTw;

    /**
     * 名称-英文
     */
    private String dictNameEn;

    /**
     * 1:父节点，2:子节点
     */
    private String dictType;

    /**
     * 排序
     */
    private Long orderCode;

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getParentDictCode() {
		return parentDictCode;
	}

	public void setParentDictCode(String parentDictCode) {
		this.parentDictCode = parentDictCode;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictNameEn() {
		return dictNameEn;
	}

	public void setDictNameEn(String dictNameEn) {
		this.dictNameEn = dictNameEn;
	}

	public String getDictNameCn() {
		return dictNameCn;
	}

	public void setDictNameCn(String dictNameCn) {
		this.dictNameCn = dictNameCn;
	}

	public String getDictNameTw() {
		return dictNameTw;
	}

	public void setDictNameTw(String dictNameTw) {
		this.dictNameTw = dictNameTw;
	}

	public Long getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Long orderCode) {
		this.orderCode = orderCode;
	}

	@Override
	public String toString() {
		return "SystemDict [dictId=" + dictId + ", parentDictCode=" + parentDictCode + ", dictCode=" + dictCode
				+ ", dictNameCn=" + dictNameCn + ", dictType=" + dictType + ", orderCode=" + orderCode + "]";
	}
	
}