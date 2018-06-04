package com.gwghk.ims.common.vo.base;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要： 标签实体VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月29日
 */
public class ActTagVO extends BaseVO implements Serializable{
	private static final long serialVersionUID = -7126795069595728701L;

	private Long id;
	
	/**
	 * 标签编码
	 */
	private String tagCode;
	
	/**
	 * 标签名称
	 */
	private String tagName;
	
	/**
     * 标签值
     */
    private String tagVal;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagVal() {
        return tagVal;
    }

    public void setTagVal(String tagVal) {
        this.tagVal = tagVal;
    }

	@Override
	public String toString() {
		return "id=" + id + ", tagCode=" + tagCode + ", tagName=" + tagName + ", tagVal=" + tagVal;
	}
}