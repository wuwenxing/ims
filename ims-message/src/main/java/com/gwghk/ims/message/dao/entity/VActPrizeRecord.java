package com.gwghk.ims.message.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: VActPrizeRecord
 * @Description: 活动物品记录对象视图对象
 * @author lawrence
 * @date 2017年7月25日
 *
 */
public class VActPrizeRecord extends BaseEntity {
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
	 * 发放流水号(程序产生)
	 */
	private String recordNumber;

	/**
	 * 关联的订单流水号(争对重发)
	 */
	private String refRecordNumber;

	/**
	 * 第三方流水号
	 */
	private String dealNumber;

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
	 * 发放额度
	 */
	private BigDecimal giftAmount;

	/**
	 * 发放额度单位
	 */
	private String giftAmountUnit;

	/**
	 * 物品价格
	 */
	private BigDecimal giftPrice;

	/**
	 * 物品价格
	 */
	private String giftPriceUnit;

	/**
	 * 物品名称
	 */
	private String giftName;

	/**
	 * 物品种类
	 */
	private String giftCategory;

	/**
	 * 物品类型
	 */
	private String giftType;

	/**
	 * 抽奖状态(0未完成，1已完成)
	 */
	private Boolean lotteryStatus;

	/**
	 * 抽奖时间
	 */
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
	 * 是否应发/待发 --true:应发(物品数量不够),false:数量足够
	 */
	private Integer sendStatus;

	/**
	 * 是否从后台添加(0:否,1:是)
	 */
	private Boolean addFromBos;

	/**
	 * 其他信息
	 */
	private String otherMsg;

	/**
	 * 任务名称
	 */
	private String taskTitle;

	/**
	 * 物品价格显示：价格+单位
	 */
	private String giftPriceTxt;

	/**
	 * 发放额度单位显示：价格+单位
	 */
	private String giftAmountTxt;

	/**
	 * 真实还是模拟账号
	 */
	private String env;

	/**
	 * 发放到BU结算中心开始时间
	 */
	private Date dealStartTime;

	/**
	 * BU结算中心返回结果信息结束时间
	 */
	private Date dealEndTime;

	/**
	 * 需要交易手数（对赠金的任务)需留
	 */
	private BigDecimal needTradeLots;

	/**
	 * 已完成的交易手数（对赠金的任务)需留
	 */
	private BigDecimal finishedTradeLots;

	/**
	 * 完成所有手数的交易id
	 */
	private Long finishedLotsTradeId;
	/**
	 * 完成所有手数的交易时间
	 */
	private Date finishedLotsTradeTime;

	/**
	 * 达到要求发放的交易记录入库id
	 */
	private Long receiveTradeId;

	/**
	 * 达到要求发放的完成时间点
	 */
	private Date receiveFinishTime;

	/**
	 * 开始记录完成手数的开始交易id
	 */
	private Long withdrawalStartTradeId;

	/**
	 * 开始记录完成手数的开始时间
	 */
	private Date withdrawalStartTime;

	/**
	 * 发放记录的所属任务id(act_task_setting表外健)
	 */
	private Long taskId;

	/**
	 * 外键,对应act_rw_task_items,act_wpdh_task_items表的id
	 */
	private Long taskItemsId;

	/**
	 * 发放记录的所属任务所属层级
	 */
	private Integer taskGroup;

	/**
	 * BU结算中心处理时间
	 */
	private Date finishTime;

	/**
	 * 结算时间
	 */
	private Date settlementTime;

	/**
	 * 是否释放该笔贈金(当完成手数小于要求完成手数:0,否则为1(结算完成时，也会设置为1))
	 */
	private Integer releaseFinish;

	/**
	 * 多出的订单号(一般是主从关系的订单使用)
	 */
	private String otherRecordNumber;

	/**
	 * 多出的订单号(一般是主从关系的订单使用,第三方返回的订单号)
	 */
	private String otherDealNumber;

	/**
	 * 释放类型：1：表示立即按已完成手数做释放,2:表示要达到完成手数后才能释放,3:表示只做新增不做释放，4：表示最后层级完成后，才将该层级的其他贈金做释放
	 */
	private Integer releaseType;
	/**
	 * 已释放的赠金
	 */
	private BigDecimal releasedBonus;

	private String accountNoBegin;
	private String accountNoEnd;
	private List<String> pExcludeList;

	/**
	 * 组内是否释放的标识
	 */
	private Integer groupRelease;

	/**
	 * 0:新增，1:释放
	 */
	private Integer op;
	
	private Integer settleFlag;
	/**
	 * 绑定关联账号
	 */
	private String refAccountNo;
	/**
	 * 绑定时间
	 */
	private Date realRefUpdateDate;
	
	/**
	 * 绑定关联账号
	 */
	private String newRefAccountNo;
	/**
	 * 绑定时间
	 */
	private Date newRealRefUpdateDate;
	
	/**
	 * 判断是否绑定关系标识
	 */
	private boolean needSaveRef;
	
	private String tmpAccountNo;
	
	/**
	 * 物品可使用-结束时间(用于发放奖品后的结束使用时间)
	 */
	private Date useEndTime;
	
	/**
     * 是否允许转组 (0:不允许,1：允许)
     */
    private Integer turnGroup;
    /**
     * 是否允许转账 (0:不允许,1：允许)
     */
    private Integer transfer;
    
    /**
     * 完成手数累计达到的交易id，用来判断结算前该笔记录是否已经释放完
     */
    private Long cumulativeTradeId;
    /**
     * demo给真实cny账号发贈金时汇率
     */
    private BigDecimal exchangeRate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 活动期数
	 * 
	 * @return activity_periods 活动期数
	 */
	public String getActivityPeriods() {
		return activityPeriods;
	}

	/**
	 * 活动期数
	 * 
	 * @param activityPeriods
	 *            活动期数
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
	 * 
	 * @return account_no 平台账号
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 平台账号
	 * 
	 * @param accountNo
	 *            平台账号
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	/**
	 * 客人姓名
	 * 
	 * @return guest_name 客人姓名
	 */
	public String getGuestName() {
		return guestName;
	}

	/**
	 * 客人姓名
	 * 
	 * @param guestName
	 *            客人姓名
	 */
	public void setGuestName(String guestName) {
		this.guestName = guestName == null ? null : guestName.trim();
	}

	/**
	 * 客人电话
	 * 
	 * @return guest_phone 客人电话
	 */
	public String getGuestPhone() {
		return guestPhone;
	}

	/**
	 * 客人电话
	 * 
	 * @param guestPhone
	 *            客人电话
	 */
	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone == null ? null : guestPhone.trim();
	}

	/**
	 * 客人邮箱
	 * 
	 * @return guest_email 客人邮箱
	 */
	public String getGuestEmail() {
		return guestEmail;
	}

	/**
	 * 客人邮箱
	 * 
	 * @param guestEmail
	 *            客人邮箱
	 */
	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail == null ? null : guestEmail.trim();
	}

	/**
	 * 客人访问来源
	 * 
	 * @return guest_from 客人访问来源
	 */
	public String getGuestFrom() {
		return guestFrom;
	}

	/**
	 * 客人访问来源
	 * 
	 * @param guestFrom
	 *            客人访问来源
	 */
	public void setGuestFrom(String guestFrom) {
		this.guestFrom = guestFrom == null ? null : guestFrom.trim();
	}

	/**
	 * 客人访问IP
	 * 
	 * @return guest_ip 客人访问IP
	 */
	public String getGuestIp() {
		return guestIp;
	}

	/**
	 * 客人访问IP
	 * 
	 * @param guestIp
	 *            客人访问IP
	 */
	public void setGuestIp(String guestIp) {
		this.guestIp = guestIp == null ? null : guestIp.trim();
	}

	/**
	 * 中奖的奖品编号
	 * 
	 * @return gift_number 中奖的奖品编号
	 */
	public String getGiftNumber() {
		return giftNumber;
	}

	/**
	 * 中奖的奖品编号
	 * 
	 * @param giftNumber
	 *            中奖的奖品编号
	 */
	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber == null ? null : giftNumber.trim();
	}

	/**
	 * 中奖的奖品名称
	 * 
	 * @return gift_name 中奖的奖品名称
	 */
	public String getGiftName() {
		return giftName;
	}

	/**
	 * 中奖的奖品名称
	 * 
	 * @param giftName
	 *            中奖的奖品名称
	 */
	public void setGiftName(String giftName) {
		this.giftName = giftName == null ? null : giftName.trim();
	}

	/**
	 * 抽奖状态(0未完成，1已完成)
	 * 
	 * @return lottery_status 抽奖状态(0未完成，1已完成)
	 */
	public Boolean getLotteryStatus() {
		return lotteryStatus;
	}

	/**
	 * 抽奖状态(0未完成，1已完成)
	 * 
	 * @param lotteryStatus
	 *            抽奖状态(0未完成，1已完成)
	 */
	public void setLotteryStatus(Boolean lotteryStatus) {
		this.lotteryStatus = lotteryStatus;
	}

	/**
	 * 抽奖时间
	 * 
	 * @return lottery_time 抽奖时间
	 */
	public Date getLotteryTime() {
		return lotteryTime;
	}

	/**
	 * 抽奖时间
	 * 
	 * @param lotteryTime
	 *            抽奖时间
	 */
	public void setLotteryTime(Date lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	/**
	 * 是否从后台添加(0:否,1:是)
	 * 
	 * @return add_from_bos 是否从后台添加(0:否,1:是)
	 */
	public Boolean getAddFromBos() {
		return addFromBos;
	}

	/**
	 * 是否从后台添加(0:否,1:是)
	 * 
	 * @param addFromBos
	 *            是否从后台添加(0:否,1:是)
	 */
	public void setAddFromBos(Boolean addFromBos) {
		this.addFromBos = addFromBos;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * 其他信息
	 * 
	 * @return ohterMsg 其他信息
	 */
	public String getOtherMsg() {
		return otherMsg;
	}

	/**
	 * 其他信息
	 * 
	 * @param ohterMsg
	 *            其他信息
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

	public String getGiftAmountUnit() {
		return giftAmountUnit;
	}

	public void setGiftAmountUnit(String giftAmountUnit) {
		this.giftAmountUnit = giftAmountUnit;
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

	public String getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}

	public String getGiftPriceUnit() {
		return giftPriceUnit;
	}

	public void setGiftPriceUnit(String giftPriceUnit) {
		this.giftPriceUnit = giftPriceUnit;
	}

	public String getGiftCategory() {
		return giftCategory;
	}

	public void setGiftCategory(String giftCategory) {
		this.giftCategory = giftCategory;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getGiftPriceTxt() {
		return giftPriceTxt;
	}

	public void setGiftPriceTxt(String giftPriceTxt) {
		this.giftPriceTxt = giftPriceTxt;
	}

	public String getGiftAmountTxt() {
		return giftAmountTxt;
	}

	public void setGiftAmountTxt(String giftAmountTxt) {
		this.giftAmountTxt = giftAmountTxt;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public Date getDealStartTime() {
		return dealStartTime;
	}

	public void setDealStartTime(Date dealStartTime) {
		this.dealStartTime = dealStartTime;
	}

	public Date getDealEndTime() {
		return dealEndTime;
	}

	public void setDealEndTime(Date dealEndTime) {
		this.dealEndTime = dealEndTime;
	}

	public Long getTaskItemsId() {
		return taskItemsId;
	}

	public void setTaskItemsId(Long taskItemsId) {
		this.taskItemsId = taskItemsId;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public BigDecimal getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(BigDecimal giftPrice) {
		this.giftPrice = giftPrice;
	}

	public BigDecimal getNeedTradeLots() {
		return needTradeLots;
	}

	public void setNeedTradeLots(BigDecimal needTradeLots) {
		this.needTradeLots = needTradeLots;
	}

	public BigDecimal getFinishedTradeLots() {
		return finishedTradeLots;
	}

	public void setFinishedTradeLots(BigDecimal finishedTradeLots) {
		this.finishedTradeLots = finishedTradeLots;
	}

	public Long getReceiveTradeId() {
		return receiveTradeId;
	}

	public void setReceiveTradeId(Long receiveTradeId) {
		this.receiveTradeId = receiveTradeId;
	}

	public Date getReceiveFinishTime() {
		return receiveFinishTime;
	}

	public void setReceiveFinishTime(Date receiveFinishTime) {
		this.receiveFinishTime = receiveFinishTime;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(Integer taskGroup) {
		this.taskGroup = taskGroup;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Integer getReleaseFinish() {
		return releaseFinish;
	}

	public void setReleaseFinish(Integer releaseFinish) {
		this.releaseFinish = releaseFinish;
	}

	public Integer getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(Integer releaseType) {
		this.releaseType = releaseType;
	}

	public String getOtherRecordNumber() {
		return otherRecordNumber;
	}

	public void setOtherRecordNumber(String otherRecordNumber) {
		this.otherRecordNumber = otherRecordNumber;
	}

	public String getOtherDealNumber() {
		return otherDealNumber;
	}

	public void setOtherDealNumber(String otherDealNumber) {
		this.otherDealNumber = otherDealNumber;
	}

	public Long getFinishedLotsTradeId() {
		return finishedLotsTradeId;
	}

	public void setFinishedLotsTradeId(Long finishedLotsTradeId) {
		this.finishedLotsTradeId = finishedLotsTradeId;
	}

	public Date getFinishedLotsTradeTime() {
		return finishedLotsTradeTime;
	}

	public void setFinishedLotsTradeTime(Date finishedLotsTradeTime) {
		this.finishedLotsTradeTime = finishedLotsTradeTime;
	}

	public Long getWithdrawalStartTradeId() {
		return withdrawalStartTradeId;
	}

	public void setWithdrawalStartTradeId(Long withdrawalStartTradeId) {
		this.withdrawalStartTradeId = withdrawalStartTradeId;
	}

	public Date getWithdrawalStartTime() {
		return withdrawalStartTime;
	}

	public void setWithdrawalStartTime(Date withdrawalStartTime) {
		this.withdrawalStartTime = withdrawalStartTime;
	}

	public BigDecimal getReleasedBonus() {
		return releasedBonus;
	}

	public void setReleasedBonus(BigDecimal releasedBonus) {
		this.releasedBonus = releasedBonus;
	}

	public String getAccountNoBegin() {
		return accountNoBegin;
	}

	public void setAccountNoBegin(String accountNoBegin) {
		this.accountNoBegin = accountNoBegin;
	}

	public String getAccountNoEnd() {
		return accountNoEnd;
	}

	public void setAccountNoEnd(String accountNoEnd) {
		this.accountNoEnd = accountNoEnd;
	}

	public List<String> getPExcludeList() {
		return pExcludeList;
	}

	public void setPExcludeList(List<String> pExcludeList) {
		this.pExcludeList = pExcludeList;
	}

	public Integer getGroupRelease() {
		return groupRelease;
	}

	public void setGroupRelease(Integer groupRelease) {
		this.groupRelease = groupRelease;
	}

	public Integer getOp() {
		return op;
	}

	public void setOp(Integer op) {
		this.op = op;
	}

	public Integer getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(Integer settleFlag) {
		this.settleFlag = settleFlag;
	}

	public Date getRealRefUpdateDate() {
		return realRefUpdateDate;
	}

	public void setRealRefUpdateDate(Date realRefUpdateDate) {
		this.realRefUpdateDate = realRefUpdateDate;
	}

	public String getRefAccountNo() {
		return refAccountNo;
	}

	public void setRefAccountNo(String refAccountNo) {
		this.refAccountNo = refAccountNo;
	}

	public boolean isNeedSaveRef() {
		return needSaveRef;
	}

	public void setNeedSaveRef(boolean needSaveRef) {
		this.needSaveRef = needSaveRef;
	}

	public String getTmpAccountNo() {
		return tmpAccountNo;
	}

	public void setTmpAccountNo(String tmpAccountNo) {
		this.tmpAccountNo = tmpAccountNo;
	}

	public Date getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(Date useEndTime) {
		this.useEndTime = useEndTime;
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

	public String getNewRefAccountNo() {
		return newRefAccountNo;
	}

	public void setNewRefAccountNo(String newRefAccountNo) {
		this.newRefAccountNo = newRefAccountNo;
	}

	public Date getNewRealRefUpdateDate() {
		return newRealRefUpdateDate;
	}

	public void setNewRealRefUpdateDate(Date newRealRefUpdateDate) {
		this.newRealRefUpdateDate = newRealRefUpdateDate;
	}

	public Long getCumulativeTradeId() {
		return cumulativeTradeId;
	}

	public void setCumulativeTradeId(Long cumulativeTradeId) {
		this.cumulativeTradeId = cumulativeTradeId;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

}