package com.gwghk.ims.base.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：全局黑名单实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月4日
 */
@Table(name="ims_black_list")
public class ImsBlackList extends BaseEntity{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    /**
     * 账号
     */
    private String accountNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}