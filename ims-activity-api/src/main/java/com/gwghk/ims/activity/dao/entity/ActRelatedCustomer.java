package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import com.gwghk.ims.common.common.BaseEntity;

public class ActRelatedCustomer extends BaseEntity{

	 /**
     * t_related_customer的ID
     */
    private Long id;

    /**
     * 客戶統一帳號
     */
    private String customerNumber;

    /**
     * 模擬客戶統一帳號
     */
    private String demoCustomerNumber;

    /**
     * demo-real账号绑定的关联时间
     */
    private Date bindDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getDemoCustomerNumber() {
		return demoCustomerNumber;
	}

	public void setDemoCustomerNumber(String demoCustomerNumber) {
		this.demoCustomerNumber = demoCustomerNumber;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
    
}
