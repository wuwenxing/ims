package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：奖品记录请求对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月23日
 */
public class ActPrizeRecordReqDTO extends BaseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5080124457791407607L;

	/**
     * 活动编号
     */
    private String activityPeriods;

    /**
     * 平台账号
     */
    private String accountNo;

    /**
     * 客人电话
     */
    private String guestPhone;
    
    /**
     * 显示的总数
     */
    private Long total;

    public Long getTotal() {
    	if (null == total) {
    		total = 10L;
    	}
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}
}