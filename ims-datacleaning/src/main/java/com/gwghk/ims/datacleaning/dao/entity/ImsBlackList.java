package com.gwghk.ims.datacleaning.dao.entity;

/**
 * 
 * @ClassName: ActGroupBlackAccount
 * @Description: 集团黑名单对象
 * @author lawrence
 * @date 2018年2月2日
 *
 */
public class ImsBlackList extends BaseEntity {

	private Long id;
	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}