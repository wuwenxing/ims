package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.vo.BaseVO;

public class ActMaintenanceSettingVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

    /**
     * 活动编号
     */
	@OrderBy(columnName="activity_periods",propName="activityPeriods")
    private String activityPeriods;
    
    /**
	 * 活动名称
	 */
	@OrderBy(columnName="activity_name",propName="activityName")
	private String activityName;

    /**
     * 维护开始时间
     */
	@OrderBy(columnName="start_time",propName="startTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 维护结束时间
     */
	@OrderBy(columnName="end_time",propName="endTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
	 
    /**
     * 生效标识
     */
	@OrderBy(columnName="effective_flag",propName="effectiveFlag")
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

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getEffectiveFlag() {
		return effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}
    
}