package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 
 * 摘要：任务维护设置
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月29日
 */
public class ActMaintenanceSetting extends BaseEntity{
    private Integer id;

    /**
     * 活动编号
     */
    private String activityPeriods;

    /**
     * 维护开始时间
     */
    private Date startTime;

    /**
     * 维护结束时间
     */
    private Date endTime;
    
    /**
     * 生效标识
     */
    private String effectiveFlag ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 活动编号
     * @return activity_periods 活动编号
     */
    public String getActivityPeriods() {
        return activityPeriods;
    }

    /**
     * 活动编号
     * @param activityPeriods 活动编号
     */
    public void setActivityPeriods(String activityPeriods) {
        this.activityPeriods = activityPeriods == null ? null : activityPeriods.trim();
    }

    /**
     * 维护开始时间
     * @return start_time 维护开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 维护开始时间
     * @param startTime 维护开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 维护结束时间
     * @return end_time 维护结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 维护结束时间
     * @param endTime 维护结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public String getEffectiveFlag() {
		return effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}
    
}