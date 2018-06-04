package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.enums.ActAllowStatusEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.ActSendTypeEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.util.CustomDateSerializer;
import com.gwghk.ims.common.util.ImsBigDecimalUtil;
import com.gwghk.ims.common.vo.BaseVO;

/**
 * 活动详情信息 -活动的所有信息
 * @author eva
 *
 */
public class ActSettingVO extends BaseVO  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2320823678001431616L;

	/**
     * id
     */
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
     * 活动类型
     */
    private String activityType;
    
    
    /**
     * 是否允许转组 (0:不允许,1：允许)
     */
    private Integer turnGroup;
 
    /**
     * 是否允许转账 (0:不允许,1：允许)
     */
    private Integer transfer;
    
    
    
    /**
     * 需要过滤的活动类型
     */
    private String filterActType;
    
    /**
     * 活动类型集合
     */
    private List<String> activityTypeList;

    /**
     * 活动-开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 活动-结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    
    /**
     * 所属标签 
     */
   
    private String tagCode;
    
    /**
     * 格式化显示 标签
     */
    private String tagName;

    /**
     * 活动完成时间
     */
    private Integer finishDays;
    
    /**
	 * 活动完成天数单位
	 */
    private String finishDaysUnit;

    /**
     * 赠金有效期
     */
    private Integer withGoldDays;
    
	/**
	 * 赠金有效期单位
	 */
    private String withGoldDaysUnit;
 

    /**
     * 代币有效期
     */
    private Integer coinDays;
    
	/**
	 * 代币有效期单位
	 */
    private String coinDaysUnit;

    /**
     * demo账号保留余额
     */
    private BigDecimal demoKeepAmount;
    

	/**
	 * demo账号保留余额单位
	 */
    private String demoKeepAmountUnit;

    /**
     * 最高兑换次数
     */
    private Integer maxExchangeCount;
    
    /**
	 * 最高兑换次数单位
	 */
    private String maxExchangeCountUnit;
 
    /**
     * 活动url
     */
    private String activityUrl;

    /**
	 * 活动图片
	 */
	private String activityImg;

    /**
     * 其他信息或备注
     */
    private String otherMsg;

   

    /**
     * 提案號/單號
     */
    private String pno;

    /**
     * 提案人
     */
    private String proposer;

    /**
     * 提案时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date proposalDate;

    /**
     * 提案状态
     */
    private String proposalStatus;

    /**
     * 审批人
     */
    private String approver;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveDate;

    /**
     * 取消人
     */
    private String canceller;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelDate;
    
 
    /**
     * 活动真正结算完的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settlementDate;

    /**
     * 活动是否已结算 0:未结算 1:已结算
     */
    private Integer settlement;
    
    /**
     * 取消原因
     */
    private String cancelReason;
    
    
  
    /**
     * 活动标签 
     */
    private List<String> tagCodes;

   
    /**
     * 是否查询过期活动
     */
    
    private Boolean isExpiration;
    
 
    /**
     * 是否自动分发物品(0:不自动，人工审核,1:自动发放)
     */
    private Integer autoHandOut;
 
    /**
     * 发放奖励优先平台
     */
    private String priorityPlatform;
 
    
    /**
     * 排序字段
     */
    private String orderStr;
    
 
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
     * 活动名称
     * 
     * @return activity_name 活动名称
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * 活动名称
     * 
     * @param activityName 活动名称
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    /**
     * 活动类型(来源于数据字典)
     * 
     * @return activity_type 活动类型(来源于数据字典)
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * 活动类型(来源于数据字典)
     * 
     * @param activityType 活动类型(来源于数据字典)
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType == null ? null : activityType.trim();
    }
 

	/**
     * 活动-开始时间
     * 
     * @return start_time 活动-开始时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 活动-开始时间
     * 
     * @param startTime 活动-开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 活动-结束时间
     * 
     * @return end_time 活动-结束时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 活动-结束时间
     * 
     * @param endTime 活动-结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 活动完成时间
     * 
     * @return finish_days 活动完成时间
     */
    public Integer getFinishDays() {
        return finishDays;
    }

    /**
     * 活动完成时间
     * 
     * @param finishDays 活动完成时间
     */
    public void setFinishDays(Integer finishDays) {
        this.finishDays = finishDays;
    }

    /**
     * 赠金有效期
     * 
     * @return with_gold_days 赠金有效期
     */
    public Integer getWithGoldDays() {
        return withGoldDays;
    }

    /**
     * 赠金有效期
     * 
     * @param withGoldDays 赠金有效期
     */
    public void setWithGoldDays(Integer withGoldDays) {
        this.withGoldDays = withGoldDays;
    }

    /**
     * 代币有效期
     * 
     * @return coin_days 代币有效期
     */
    public Integer getCoinDays() {
        return coinDays;
    }

    /**
     * 代币有效期
     * 
     * @param coinDays 代币有效期
     */
    public void setCoinDays(Integer coinDays) {
        this.coinDays = coinDays;
    }

     
    /**
     * 活动url
     * 
     * @return activity_url 活动url
     */
    public String getActivityUrl() {
        return activityUrl;
    }

    /**
     * 活动url
     * 
     * @param activityUrl 活动url
     */
    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl == null ? null : activityUrl.trim();
    }

         
   
    /**
     * 其他信息或备注
     * 
     * @param otherMsg 其他信息或备注
     */
    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg == null ? null : otherMsg.trim();
    }

     
    /**
     * 提案號/單號
     * 
     * @return pno 提案號/單號
     */
    public String getPno() {
        return pno;
    }

    /**
     * 提案號/單號
     * 
     * @param pno 提案號/單號
     */
    public void setPno(String pno) {
        this.pno = pno == null ? null : pno.trim();
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
     * 提案时间
     * 
     * @return proposal_date 提案时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getProposalDate() {
        return proposalDate;
    }

    /**
     * 提案时间
     * 
     * @param proposalDate 提案时间
     */
    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus == null ? null : proposalStatus.trim();
    }

    public String getProposalStatusName(){
    	return ActProposalStatusEnum.formatByCode(this.getProposalStatus());
    }
    
    /**
     * 审批人
     * 
     * @return approver 审批人
     */
    public String getApprover() {
        return approver;
    }

    /**
     * 审批人
     * 
     * @param approver 审批人
     */
    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    /**
     * 审批时间
     * 
     * @return approve_date 审批时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * 审批时间
     * 
     * @param approveDate 审批时间
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
     * 取消时间
     * 
     * @return cancel_date 取消时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * 取消时间
     * 
     * @param cancelDate 取消时间
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
 

    public BigDecimal getDemoKeepAmount() {
    	if(demoKeepAmount!=null){
			return ImsBigDecimalUtil.formatComma2BigDecimal(demoKeepAmount);
		}
        return demoKeepAmount;
    }

    public void setDemoKeepAmount(BigDecimal demoKeepAmount) {
        this.demoKeepAmount = demoKeepAmount;
    }

    public Integer getMaxExchangeCount() {
        return maxExchangeCount;
    }

    public void setMaxExchangeCount(Integer maxExchangeCount) {
        this.maxExchangeCount = maxExchangeCount;
    }

  
 
    public Boolean getIsExpiration() {
        return isExpiration;
    }

    public void setIsExpiration(Boolean isExpiration) {
        this.isExpiration = isExpiration;
    }

    public Integer getAutoHandOut() {
        return autoHandOut;
    }

    public void setAutoHandOut(Integer autoHandOut) {
        this.autoHandOut = autoHandOut;
    }
 
    public String getFilterActType() {
		return filterActType;
	}

	public void setFilterActType(String filterActType) {
		this.filterActType = filterActType;
	}

	public List<String> getActivityTypeList() {
		return activityTypeList;
	}

	public void setActivityTypeList(List<String> activityTypeList) {
		this.activityTypeList = activityTypeList;
	}

	public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    

    public Integer getSettlement() {
		return settlement;
	}

	public void setSettlement(Integer settlement) {
		this.settlement = settlement;
	}

	public String getPriorityPlatform() {
        return priorityPlatform;
    }

    public void setPriorityPlatform(String priorityPlatform) {
        this.priorityPlatform = priorityPlatform;
    }

	 
    public Integer getTurnGroup() {
        return turnGroup;
    }

    public void setTurnGroup(Integer turnGroup) {
        this.turnGroup = turnGroup;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public List<String> getTagCodes() {
        return tagCodes;
    }

    public void setTagCodes(List<String> tagCodes) {
        this.tagCodes = tagCodes;
    }

	public String getOtherMsg() {
		return otherMsg;
	}
 
	/**
	 * 活动类型格式化
	 * 
	 */
	public String getActivityTypeName() {
		return  ActTypeEnum.formatByCode(this.getActivityType());
	}
 

	/**
	 * 是否转组 格式化
	 * @return
	 */
	public String getTurnGroupName() {
		return ActAllowStatusEnum.formatByKey(this.getTurnGroup());
	}

	/**
	 * 是否转账 格式化
	 * @return
	 */
	public String getTransferName() {
		return ActAllowStatusEnum.formatByKey(this.getTransfer());
	}
 
    /**
     * 发放方式格式化
     *
     */
	public String getAutoHandOutName() {
		return ActSendTypeEnum.formatCode(this.getAutoHandOut());
	}

	public String getActivityImg() {
		return activityImg;
	}

	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}


	public String getFinishDaysUnit() {
		return finishDaysUnit;
	}

	public void setFinishDaysUnit(String finishDaysUnit) {
		this.finishDaysUnit = finishDaysUnit;
	}

	public String getWithGoldDaysUnit() {
		return withGoldDaysUnit;
	}

	public void setWithGoldDaysUnit(String withGoldDaysUnit) {
		this.withGoldDaysUnit = withGoldDaysUnit;
	}

	public String getCoinDaysUnit() {
		return coinDaysUnit;
	}

	public void setCoinDaysUnit(String coinDaysUnit) {
		this.coinDaysUnit = coinDaysUnit;
	}

	public String getDemoKeepAmountUnit() {
		return demoKeepAmountUnit;
	}

	public void setDemoKeepAmountUnit(String demoKeepAmountUnit) {
		this.demoKeepAmountUnit = demoKeepAmountUnit;
	}

	public String getMaxExchangeCountUnit() {
		return maxExchangeCountUnit;
	}

	public void setMaxExchangeCountUnit(String maxExchangeCountUnit) {
		this.maxExchangeCountUnit = maxExchangeCountUnit;
	}
}