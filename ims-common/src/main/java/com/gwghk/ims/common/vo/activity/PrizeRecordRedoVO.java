package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.List;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.vo.BaseVO;


/**
 * 摘要：活动账号重发记录表
 * @author eva
 * @version 1.0
 * @Date 2017年11月30日
 */
public class PrizeRecordRedoVO extends BaseVO implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 7305482067575661448L;

	private Long id;
	
	 /**
     * 过滤掉的类型
     */
    private String actType;
    
    

	 
	/**
     * 活动编号
     */
	 @OrderBy(columnName="activity_periods",propName="activityPeriods",order="desc")
    private String activityPeriods;

    /**
     * 交易账号
     */
    @OrderBy(columnName="account_no",propName="accountNo",order="desc")
    private String accountNo;
    
    /**
     * 所属平台
     */
    @OrderBy(columnName="platform",propName="platform",order="desc")
    private String platform;
    
    
    /**
     * 活动名称
     */
    @OrderBy(columnName="activity_name",propName="activityName",order="desc")
    private String activityName;
    
    /**
     * 重发状态  发放失败 待发放  发放成功
     */
    @OrderBy(columnName="redo_status",propName="redoStatus",order="desc")
    private String redoStatus;
    
    @OrderBy(columnName="add_bos",propName="addBos",order="desc")
    private Integer addBos;
    
    /**
     * 失败次数
     */
    private Integer errorNum;
    
    /**
     * 错误信息
     */
    private String errorInfo;
    
    /**
     * 标签
     */
    private List<String> tagCodes;
    
	public Long getId() {
		return id;
	}

	public List<String> getTagCodes() {
		return tagCodes;
	}

	public void setTagCodes(List<String> tagCodes) {
		this.tagCodes = tagCodes;
	}

	public void setId(Long id) {
		this.id = id;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
 

    public String getRedoStatus() {
        return redoStatus;
    }

    public void setRedoStatus(String redoStatus) {
        this.redoStatus = redoStatus;
    }

    public Integer getAddBos() {
        return addBos;
    }

    public void setAddBos(Integer addBos) {
        this.addBos = addBos;
    }

    
    public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

   
 
}