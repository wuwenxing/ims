package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;

import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.enums.AuthColumnEnum;
import com.gwghk.ims.common.vo.BaseVO;

public class ActStringCodeVO extends BaseVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -9068862277130817072L;

	private Long id;

    /**
     * 活动编号
     */
    private String activityPeriods;
    
    /**
     * 活动名称
     * */
    private String activityName;

    public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 所属的物品编号
     */
    private String itemNumber;
    
    /**
     * 物品名称
     */
    private String itemName;

    /**
     * 串码
     */
    @AuthColumn(columnName = AuthColumnEnum.STRINGCODE,isDecrypt=true)
    private String stringCode;

    /**
     * 串码状态(1未使用\2已使用)
     */
    private Integer status;

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
     * 任务标题
     * @return task_title 任务标题
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * 任务标题
     * @param taskTitle 任务标题
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle == null ? null : taskTitle.trim();
    }

    /**
     * 所属的物品编号
     * @return item_number 所属的物品编号
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * 所属的物品编号
     * @param itemNumber 所属的物品编号
     */
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    /**
     * 串码
     * @return string_code 串码
     */
    public String getStringCode() {
        return stringCode;
    }

    /**
     * 串码
     * @param stringCode 串码
     */
    public void setStringCode(String stringCode) {
        this.stringCode = stringCode == null ? null : stringCode.trim();
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
     * 账号
     * @return account_no 账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 账号
     * @param accountNo 账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "id=" + id + ", activityPeriods="
				+ activityPeriods + ", taskTitle=" + taskTitle
				+ ", itemNumber=" + itemNumber + ", itemName=" + itemName
				+ ", stringCode=" + stringCode + ", status=" + status
				+ ", accountNo=" + accountNo;
	}
 
	
}