package com.gwghk.ims.common.vo.base;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：键值对VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月09日
 */
public class KeyValVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 6381314170263200359L;

	private Long id;

    /**
     * 键
     */
    private String dataKey;

    /**
     * 值
     */
    private String dataVal;
    
    /**
   	 * 标签
   	 */
   	private String tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataVal() {
		return dataVal;
	}

	public void setDataVal(String dataVal) {
		this.dataVal = dataVal;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "id=" + id + ", dataKey=" + dataKey + ", dataVal=" + dataVal + ", tag=" + tag;
	}
}
