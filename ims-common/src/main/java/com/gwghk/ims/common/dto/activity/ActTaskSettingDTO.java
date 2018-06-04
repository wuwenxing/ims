package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.util.ImsBigDecimalUtil;
import com.gwghk.ims.common.vo.BaseVO;

public class ActTaskSettingDTO extends BaseVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3210765280741806005L;

	
	private Long id;

    /**
     * 活动编号
     */
    private String activityPeriods;
    
    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 单个任务项code
     */
    private String taskItemCode;
    
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 单个任务项值单
     */
    private String taskItemVal;
    /**
     * 单个任务项值单位
     */
    private String taskItemValUnit;
    
    /**
     * 单个任务项值2
     */
    private String taskItemVal2;
    
    /**
     * 单位任务项值2单位
     */
    private String taskItemVal2Unit;

    /**
     * 任务时间（当前默认小时为单位)
     */
    private BigDecimal taskItemTime;
    
    /**
     * 时间单位
     */
    private String taskItemTimeUnit;


    /**
     * 任务序号
     */
    private Integer taskOrder;

    /**
     * 所属组/层级
     */
    private Integer taskGroup;

    /**
     * 父任务id
     */
    private Long parentId;

    

    /**
     * 任务描述
     */
    private String taskDesc;
    
    
    /**
     * 任务全名（eg:真实账号激活100元1小时)
     */
    private String taskFullName;
    
 
    
    /**
     * 子集任务
     */
    private List<ActTaskSettingDTO> subTaskSettings;
    
    /**
     * 活动下的物品集合
     */
    private List<ActTaskItemsDTO> taskItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 活动编号
     * 
     * @return activity_periods 活动编号
     */
    public String getActivityPeriods() {
        return activityPeriods;
    }

    /**
     * 活动编号
     * 
     * @param activityPeriods 活动编号
     */
    public void setActivityPeriods(String activityPeriods) {
        this.activityPeriods = activityPeriods == null ? null : activityPeriods.trim();
    }

    /**
     * 单个任务项code
     * 
     * @return task_item_code 单个任务项code
     */
    public String getTaskItemCode() {
        return taskItemCode;
    }

    /**
     * 单个任务项code
     * 
     * @param taskItemCode 单个任务项code
     */
    public void setTaskItemCode(String taskItemCode) {
        this.taskItemCode = taskItemCode == null ? null : taskItemCode.trim();
    }

    
    /**
     * 任务描述
     * 
     * @return taskDesc 任务描述
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * 任务描述
     * 
     * @param taskDesc 任务描述
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public BigDecimal getTaskItemTime() {
    	if(taskItemTime!=null){
			return ImsBigDecimalUtil.formatComma2BigDecimal(taskItemTime);
		}
        return taskItemTime;
    }

    public void setTaskItemTime(BigDecimal taskItemTime) {
        this.taskItemTime = taskItemTime;
    }

    public Integer getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(Integer taskOrder) {
        this.taskOrder = taskOrder;
    }

    public Integer getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(Integer taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getTaskItemVal2() {
        return taskItemVal2;
    }

    public void setTaskItemVal2(String taskItemVal2) {
        this.taskItemVal2 = taskItemVal2;
    }

    

    public String getTaskFullName() {
		return taskFullName;
	}

	public void setTaskFullName(String taskFullName) {
		this.taskFullName = taskFullName;
	}

	public List<ActTaskSettingDTO> getSubTaskSettings() {
        return this.subTaskSettings == null ? new ArrayList<ActTaskSettingDTO>():this.subTaskSettings;
    }

    public void setSubTaskSettings(
        List<ActTaskSettingDTO> subTaskSettings) {
        this.subTaskSettings = subTaskSettings;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public List<ActTaskItemsDTO> getTaskItems() {
		return taskItems;
	}

	public void setTaskItems(List<ActTaskItemsDTO> taskItems) {
		this.taskItems = taskItems;
	}
 
	public String getTaskItemValUnit() {
		return taskItemValUnit;
	}

	public void setTaskItemValUnit(String taskItemValUnit) {
		this.taskItemValUnit = taskItemValUnit;
	}

	public String getTaskItemVal2Unit() {
		return taskItemVal2Unit;
	}

	public void setTaskItemVal2Unit(String taskItemVal2Unit) {
		this.taskItemVal2Unit = taskItemVal2Unit;
	}

	public String getTaskItemTimeUnit() {
		return taskItemTimeUnit;
	}

	public void setTaskItemTimeUnit(String taskItemTimeUnit) {
		this.taskItemTimeUnit = taskItemTimeUnit;
	}

	public String getTaskItemVal() {
		return taskItemVal;
	}

	public void setTaskItemVal(String taskItemVal) {
		this.taskItemVal = taskItemVal;
	}

	public String getTaskName() {
		return taskName;
	}
	
	 
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
 
}