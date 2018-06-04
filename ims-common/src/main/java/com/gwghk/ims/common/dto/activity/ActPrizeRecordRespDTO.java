package com.gwghk.ims.common.dto.activity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：奖品记录响应对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月23日
 */
public class ActPrizeRecordRespDTO extends BaseDTO{
	private Long id;

    /**
     * 活动期数
     */
    private String activityPeriods;

    /**
     * 抽奖编号(程序产生)
     */
    private Integer lotteryNumber;

    /**
     * 平台账号
     */
    private String accountNo;

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
     * 客人访问IP
     */
    private String guestIp;

    /**
     * 赠送的奖品编号
     */
    private String giftNumber;

    /**
     * 赠送的奖品名称
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
    private Date lotteryTime;

    /**
     * 是否发放奖品(0:未发放，1：已发放)
     */
    private Boolean isGived;

    /**
     * 物品可使用-开始时间(用于发放后奖品的开始使用时间)
     */
    private Date useStartTime;

    /**
     * 物品可使用-结束时间(用于发放奖品后的结束使用时间)
     */
    private Date useEndTime;

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

	public Long getId() {
		return id;
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

	public Integer getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(Integer lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getGuestFrom() {
		return guestFrom;
	}

	public void setGuestFrom(String guestFrom) {
		this.guestFrom = guestFrom;
	}

	public String getGuestIp() {
		return guestIp;
	}

	public void setGuestIp(String guestIp) {
		this.guestIp = guestIp;
	}

	public String getGiftNumber() {
		return giftNumber;
	}

	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public BigDecimal getGiftProbability() {
		return giftProbability;
	}

	public void setGiftProbability(BigDecimal giftProbability) {
		this.giftProbability = giftProbability;
	}

	public Boolean getLotteryStatus() {
		return lotteryStatus;
	}

	public void setLotteryStatus(Boolean lotteryStatus) {
		this.lotteryStatus = lotteryStatus;
	}

	public Integer getLotterySort() {
		return lotterySort;
	}

	public void setLotterySort(Integer lotterySort) {
		this.lotterySort = lotterySort;
	}

	public Date getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(Date lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public Boolean getIsGived() {
		return isGived;
	}

	public void setIsGived(Boolean isGived) {
		this.isGived = isGived;
	}

	public Date getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(Date useStartTime) {
		this.useStartTime = useStartTime;
	}

	public Date getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(Date useEndTime) {
		this.useEndTime = useEndTime;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Boolean getAddFromBos() {
		return addFromBos;
	}

	public void setAddFromBos(Boolean addFromBos) {
		this.addFromBos = addFromBos;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}