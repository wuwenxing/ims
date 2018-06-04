package com.gwghk.ims.base.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：键值对实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月9日
 */
@Table(name="ims_key_val")
public class KeyVal extends BaseEntity{
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
		return "KeyVal [id=" + id + ", dataKey=" + dataKey + ", dataVal=" + dataVal + ", tag=" + tag + "]";
	}
	
}