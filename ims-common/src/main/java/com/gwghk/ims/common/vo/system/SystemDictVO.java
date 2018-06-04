package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：系统-数据字典请求VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
public class SystemDictVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 4615823606390424759L;

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

}
