package com.gwghk.ims.common.dto.job;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：活动兑奖请求参数
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月25日
 */
public class ActTaskExchangeReqDTO extends BaseDTO{
	/**
     * 任务-物品Id
     */
    private Long taskItemId;
    
    /**
     * 平台
     */
    private String platform;
    
	/**
     * 账号
     */
    private String accountNo;
    
    /**
     * 活动编号
     */
    private String activityPeriods;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getActivityPeriods() {
		return activityPeriods;
	}

	public void setActivityPeriods(String activityPeriods) {
		this.activityPeriods = activityPeriods;
	}

	public Long getTaskItemId() {
		return taskItemId;
	}

	public void setTaskItemId(Long taskItemId) {
		this.taskItemId = taskItemId;
	}
}
