package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.util.CustomDateSerializer;

/**
 * 摘要：活动物品记录对象
 * @author eva
 * @version 1.0
 * @Date 2017年7月20日
 */
public class ActPrizeRecord extends BaseEntity {
    private Long id;

    /**
     * 活动期数
     */
    private String activityPeriods;
 
    /**
     * 发放流水号(程序产生)
     */
    private String recordNumber;
 
    /**
     * 关联的订单流水号(争对重发)
     */
    private String refRecordNumber;

    /**
     * 平台账号
     */
    private String accountNo;
    
    /**
     * 真实还是模拟账号
     */
    private String env;


    /**
     * 客人姓名
     */
    private String guestName;

    /**
     * 客人电话
     */
    private String guestPhone;
    
    /**
     * 客人邮箱
     */
    private String guestEmail;

    /**
     * 客人访问来源
     */
    private String guestFrom;
    
    /**
     * 客户所属平台
     */
    private String guestPlatForm;

    /**
     * 客人访问IP
     */
    private String guestIp;

    /**
     * 物品编号 
     */
    private String giftNumber;
    
    /**
     * 领取额度
     */
    private BigDecimal giftAmount;
    
    /**
     * 发放额度
     */
    private BigDecimal issueAmount;
    
    /**
     * 发放额度单位
     */
    private String giftAmountUnit;
    
    
    /**
     * 物品价格
     */
    private BigDecimal giftPrice;

    /**
     * 物品价格单位
     */
    private String giftPriceUnit;
    /**
     *物品名称
     */
    private String giftName;

    /**
     * 赠送的奖品概率
     */
    private BigDecimal giftProbability;

    /**
     * 抽奖状态(0未完成，1已完成)
     */
    private Boolean lotteryStatus;

    /**
     * 排序(越大越靠前)
     */
    private Integer lotterySort;
    
    

    /**
     * 抽奖时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName="lottery_time",propName="lotteryTime",order="asc")
    private Date lotteryTime;

    /**
     * 发放状态，见IssuingStatusEnum
     */
    private String issuingStatus;
    
    /**
     * 审核状态，见AuditStatusEnum
     */
    private String auditStatus;
    
    
    /**
     *0可发，>1应发，参考WaitSendReasonUtil
     */
    private Integer sendStatus;
    
    /**
     * 物品可使用-开始时间(用于发放后奖品的开始使用时间)
     */
    @OrderBy(columnName="use_start_time",propName="useStartTime",order="asc")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useStartTime;

    /**
     * 物品可使用-结束时间(用于发放奖品后的结束使用时间)
     */
    @OrderBy(columnName="use_end_time",propName="useEndTime",order="asc")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useEndTime;
    
    /**
     * 串码
     */
    @OrderBy(columnName="ext1",propName="ext1",order="asc")
    private String ext1;
 
    /**
     * 
     */
    @OrderBy(columnName = "ext2", propName = "ext2", order = "asc")
    private String ext2;
 
    /**
     * 模拟币对等值
     */
    @OrderBy(columnName = "ext3", propName = "ext3", order = "asc")
    private String ext3;
    
    
    @OrderBy(columnName = "ext4", propName = "ext4", order = "asc")
    private String ext4;
 
    @OrderBy(columnName = "ext5", propName = "ext5", order = "asc")
    private String ext5;

    /**
     * 物品是否使用(0:未使用 1:使用)
     */
    private Boolean isUsed;

    /**
     * 是否从后台添加(0:否,1:是)
     */
    private Boolean addFromBos;
    
    /**
     * 用于批量设置
     */
    private List<Long> ids;
    
    /**
     * 其他信息
     */
    private String otherMsg;
    
    /**
     * 任务名称
     */
    private String taskTitle;
    
    private List<String> recordNumbers;
    
    public List<String> getRecordNumbers() {
		return recordNumbers;
	}

	public void setRecordNumbers(List<String> recordNumbers) {
		this.recordNumbers = recordNumbers;
	}

	public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
     * 活动期数
     * @return activity_periods 活动期数
     */
    public String getActivityPeriods() {
        return activityPeriods;
    }

    /**
     * 活动期数
     * @param activityPeriods 活动期数
     */
    public void setActivityPeriods(String activityPeriods) {
        this.activityPeriods = activityPeriods == null ? null : activityPeriods.trim();
    }

     
    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getRefRecordNumber() {
        return refRecordNumber;
    }

    public void setRefRecordNumber(String refRecordNumber) {
        this.refRecordNumber = refRecordNumber;
    }

    /**
     * 平台账号
     * @return account_no 平台账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 平台账号
     * @param accountNo 平台账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    /**
     * 客人姓名
     * @return guest_name 客人姓名
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * 客人姓名
     * @param guestName 客人姓名
     */
    public void setGuestName(String guestName) {
        this.guestName = guestName == null ? null : guestName.trim();
    }

    /**
     * 客人电话
     * @return guest_phone 客人电话
     */
    public String getGuestPhone() {
        return guestPhone;
    }

    /**
     * 客人电话
     * @param guestPhone 客人电话
     */
    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone == null ? null : guestPhone.trim();
    }
    
    /**
     * 客人邮箱
     * @return guest_email 客人邮箱
     */
    public String getGuestEmail() {
        return guestEmail;
    }

    /**
     * 客人邮箱
     * @param guestEmail 客人邮箱
     */
    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail == null ? null : guestEmail.trim();
    }

    /**
     * 客人访问来源
     * @return guest_from 客人访问来源
     */
    public String getGuestFrom() {
        return guestFrom;
    }

    /**
     * 客人访问来源
     * @param guestFrom 客人访问来源
     */
    public void setGuestFrom(String guestFrom) {
        this.guestFrom = guestFrom == null ? null : guestFrom.trim();
    }

    /**
     * 客人访问IP
     * @return guest_ip 客人访问IP
     */
    public String getGuestIp() {
        return guestIp;
    }

    /**
     * 客人访问IP
     * @param guestIp 客人访问IP
     */
    public void setGuestIp(String guestIp) {
        this.guestIp = guestIp == null ? null : guestIp.trim();
    }

    /**
     * 中奖的奖品编号
     * @return gift_number 中奖的奖品编号
     */
    public String getGiftNumber() {
        return giftNumber;
    }

    /**
     * 中奖的奖品编号
     * @param giftNumber 中奖的奖品编号
     */
    public void setGiftNumber(String giftNumber) {
        this.giftNumber = giftNumber == null ? null : giftNumber.trim();
    }

    /**
     * 中奖的奖品名称
     * @return gift_name 中奖的奖品名称
     */
    public String getGiftName() {
        return giftName;
    }

    /**
     * 中奖的奖品名称
     * @param giftName 中奖的奖品名称
     */
    public void setGiftName(String giftName) {
        this.giftName = giftName == null ? null : giftName.trim();
    }

    /**
     * 中奖的奖品概率
     * @return gift_probability 中奖的奖品概率
     */
    public BigDecimal getGiftProbability() {
        return giftProbability;
    }

    /**
     * 中奖的奖品概率
     * @param giftProbability 中奖的奖品概率
     */
    public void setGiftProbability(BigDecimal giftProbability) {
        this.giftProbability = giftProbability;
    }

    /**
     * 抽奖状态(0未完成，1已完成)
     * @return lottery_status 抽奖状态(0未完成，1已完成)
     */
    public Boolean getLotteryStatus() {
        return lotteryStatus;
    }

    /**
     * 抽奖状态(0未完成，1已完成)
     * @param lotteryStatus 抽奖状态(0未完成，1已完成)
     */
    public void setLotteryStatus(Boolean lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    /**
     * 排序(越大越靠前)
     * @return lottery_sort 排序(越大越靠前)
     */
    public Integer getLotterySort() {
        return lotterySort;
    }

    /**
     * 排序(越大越靠前)
     * @param lotterySort 排序(越大越靠前)
     */
    public void setLotterySort(Integer lotterySort) {
        this.lotterySort = lotterySort;
    }

    /**
     * 抽奖时间
     * @return lottery_time 抽奖时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getLotteryTime() {
        return lotteryTime;
    }

    /**
     * 抽奖时间
     * @param lotteryTime 抽奖时间
     */
    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

   

    /**
     * 物品可使用-开始时间(用于发放后奖品的开始使用时间)
     * @return use_start_time 物品可使用-开始时间(用于发放后奖品的开始使用时间)
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getUseStartTime() {
        return useStartTime;
    }

    /**
     * 物品可使用-开始时间(用于发放后奖品的开始使用时间)
     * @param useStartTime 物品可使用-开始时间(用于发放后奖品的开始使用时间)
     */
    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    /**
     * 奖品可使用-结束时间(用于发放奖品后的结束使用时间)
     * @return use_end_time 奖品可使用-结束时间(用于发放奖品后的结束使用时间)
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getUseEndTime() {
        return useEndTime;
    }

    /**
     * 奖品可使用-结束时间(用于发放奖品后的结束使用时间)
     * @param useEndTime 奖品可使用-结束时间(用于发放奖品后的结束使用时间)
     */
    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    /**
     * 奖品是否使用(0:未使用 1:使用)
     * @return is_used 奖品是否使用(0:未使用 1:使用)
     */
    public Boolean getIsUsed() {
        return isUsed;
    }

    /**
     * 奖品是否使用(0:未使用 1:使用)
     * @param isUsed 奖品是否使用(0:未使用 1:使用)
     */
    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    /**
     * 是否从后台添加(0:否,1:是)
     * @return add_from_bos 是否从后台添加(0:否,1:是)
     */
    public Boolean getAddFromBos() {
        return addFromBos;
    }

    /**
     * 是否从后台添加(0:否,1:是)
     * @param addFromBos 是否从后台添加(0:否,1:是)
     */
    public void setAddFromBos(Boolean addFromBos) {
        this.addFromBos = addFromBos;
    }
 
	/**
     * 其他信息
     * @return ohterMsg 其他信息
     */
    public String getOtherMsg() {
        return otherMsg;
    }

    /**
     * 其他信息
     * @param ohterMsg 其他信息
     */
    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg;
    }

    public String getIssuingStatus() {
        return issuingStatus;
    }

    public void setIssuingStatus(String issuingStatus) {
        this.issuingStatus = issuingStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public BigDecimal getIssueAmount() {
        return issueAmount;
    }

    public void setIssueAmount(BigDecimal issueAmount) {
        this.issueAmount = issueAmount;
    }

    public String getGiftAmountUnit() {
        return giftAmountUnit;
    }

    public void setGiftAmountUnit(String giftAmountUnit) {
        this.giftAmountUnit = giftAmountUnit;
    }

    public BigDecimal getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(BigDecimal giftPrice) {
        this.giftPrice = giftPrice;
    }
 
    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getGuestPlatForm() {
        return guestPlatForm;
    }

    public void setGuestPlatForm(String guestPlatForm) {
        this.guestPlatForm = guestPlatForm;
    }
 
    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
 
    public String getGiftPriceUnit() {
        return giftPriceUnit;
    }

    public void setGiftPriceUnit(String giftPriceUnit) {
        this.giftPriceUnit = giftPriceUnit;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

}