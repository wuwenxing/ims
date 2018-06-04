package com.gwghk.ims.base.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：统一序列号实体对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年7月13日
 */
@Table(name="ims_seq")
public class Seq extends BaseEntity{
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 序列code
     */
    private String seqCode;

    /**
     * 序列前缀
     */
    private String seqPrefix;

    /**
     * 初始值
     */
    private Long initVal;

    /**
     * 当前值
     */
    private Long curVal;

    /**
     * 步长(跨度) 
     */
    private Long incrVal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeqCode() {
		return seqCode;
	}

	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode;
	}

	public String getSeqPrefix() {
		return seqPrefix;
	}

	public void setSeqPrefix(String seqPrefix) {
		this.seqPrefix = seqPrefix;
	}

	public Long getInitVal() {
		return initVal;
	}

	public void setInitVal(Long initVal) {
		this.initVal = initVal;
	}

	public Long getCurVal() {
		return curVal;
	}

	public void setCurVal(Long curVal) {
		this.curVal = curVal;
	}

	public Long getIncrVal() {
		return incrVal;
	}

	public void setIncrVal(Long incrVal) {
		this.incrVal = incrVal;
	}
}