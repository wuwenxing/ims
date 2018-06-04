package com.gwghk.ims.common.vo.base;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：全局黑名单VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月4日
 */
public class ImsBlackListVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1336271775858244945L;

	private Long id;

	/**
	 * 账号
	 */
	private String accountNo;

	public ImsBlackListVO() {
		
	}
	
	public ImsBlackListVO(String accountNo, Long companyId) {
		super();
		this.accountNo = accountNo;
		super.setCompanyId(companyId);
	}

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

	@Override
	public String toString() {
		return "[id=" + id + ", accountNo=" + accountNo + "]";
	}
}
