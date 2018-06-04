package com.gwghk.ims.activity.dao.entity;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.util.CustomDateSerializer;

/**
 * 摘要：活动修改提案视图对象
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2017年5月17日
 */
public class ActProposalModifyWrapper extends BaseEntity {
    
    @OrderBy(columnName = "id", propName = "id", order = "asc")
    private Long id;
    /**
     * 提案号
     */
    @OrderBy(columnName="pno",propName="pno",order="asc")
    private String pno;

    /**
     * 活动编号
     */
    private String activityPeriods;

    /**
     * 活动编号
     */
    private String activityName;

    /**
     * 活动类型
     */
    private String activityType;
    
    /**
     * 标签
     */
    private String tagCode;
    
    /**
     * 是否查询过期活动
     */
    private Boolean isExpiration;
    
    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityStartTime;
    
    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityEndTime;
    
    /**
     * 活动状态
     */
    private String activityStatus;

    /**
     * 判決提案是否自動審批
     */
    private Boolean isAutoApprove;

    /**
     * 提案人
     */
    private String proposer;

    /**
     * 提案時間
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date proposalDate;

    /**
     * 案狀態
     */
    private String proposalStatus;

    /**
     * 執行人/審批人
     */
    private String approver;

    /**
     * 執行時間/審批時間
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName="approve_date",propName="approveDate",order="desc")
    private Date approveDate;

    /**
     * 取消人
     */
    private String canceller;

    /**
     * 取消時間
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelDate;

    /**
     * 取消原因
     */
    private String cancelReason;
    
    /**
     * 标签名称
     */
    private String tagName;
    
    
    /**
     * 活动标签
     */
    private List<String> tagCodes;
    
    /**
     * 过期时间
     */
    private String expirationTime;
    
    /**
     * 排序
     */
    private String orderStr;
    
    /***
     * 活动有效期,天数
     */
    private Integer finishDays;

    
    
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
     * 判決提案是否自動審批
     * 
     * @return is_auto_approve 判決提案是否自動審批
     */
    public Boolean getIsAutoApprove() {
        return isAutoApprove;
    }

    /**
     * 判決提案是否自動審批
     * 
     * @param isAutoApprove 判決提案是否自動審批
     */
    public void setIsAutoApprove(Boolean isAutoApprove) {
        this.isAutoApprove = isAutoApprove;
    }

    /**
     * 提案人
     * 
     * @return proposer 提案人
     */
    public String getProposer() {
        return proposer;
    }

    /**
     * 提案人
     * 
     * @param proposer 提案人
     */
    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    /**
     * 提案時間
     * 
     * @return proposal_date 提案時間
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getProposalDate() {
        return proposalDate;
    }

    /**
     * 提案時間
     * 
     * @param proposalDate 提案時間
     */
    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    /**
     * 案狀態
     * 
     * @return proposal_status 案狀態
     */
    public String getProposalStatus() {
        return proposalStatus;
    }

    /**
     * 案狀態
     * 
     * @param proposalStatus 案狀態
     */
    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus == null ? null : proposalStatus.trim();
    }

    /**
     * 執行人/審批人
     * 
     * @return approver 執行人/審批人
     */
    public String getApprover() {
        return approver;
    }

    /**
     * 執行人/審批人
     * 
     * @param approver 執行人/審批人
     */
    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    /**
     * 執行時間/審批時間
     * 
     * @return approve_date 執行時間/審批時間
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * 執行時間/審批時間
     * 
     * @param approveDate 執行時間/審批時間
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    /**
     * 取消人
     * 
     * @return canceller 取消人
     */
    public String getCanceller() {
        return canceller;
    }

    /**
     * 取消人
     * 
     * @param canceller 取消人
     */
    public void setCanceller(String canceller) {
        this.canceller = canceller == null ? null : canceller.trim();
    }

    /**
     * 取消時間
     * 
     * @return cancel_date 取消時間
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * 取消時間
     * 
     * @param cancelDate 取消時間
     */
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    /**
     * 取消原因
     * 
     * @return cancel_reason 取消原因
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * 取消原因
     * 
     * @param cancelReason 取消原因
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

	public List<String> getTagCodes() {
		return tagCodes;
	}

	public void setTagCodes(List<String> tagCodes) {
		this.tagCodes = tagCodes;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public Boolean getIsExpiration() {
		return isExpiration;
	}

	public void setIsExpiration(Boolean isExpiration) {
		this.isExpiration = isExpiration;
	}

	public Integer getFinishDays() {
		return finishDays;
	}

	public void setFinishDays(Integer finishDays) {
		this.finishDays = finishDays;
	}
	
	
}