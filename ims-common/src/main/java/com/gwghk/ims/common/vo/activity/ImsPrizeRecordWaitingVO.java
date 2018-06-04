package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.enums.AuthColumnEnum;
import com.gwghk.ims.common.vo.BaseVO;

public class ImsPrizeRecordWaitingVO extends BaseVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 流水号
     */
    private String recordNo;

    /**
     * 第三方流水号
     */
    private String thirdRecordNo;

    /**
     * 活动编号
     */
    private String actNo;

    /**
     * 活动名称
     */
    private String actName;

    /**
     * 客户账号
     */
    private String accountNo;

    /**
     * 平台
     */
    private String platform;

    /**
     * 真实还是模拟
     */
    private String env;

    /**
     * 客户-姓名
     */
    @AuthColumn(columnName=AuthColumnEnum.CHINESENAME)
    private String custName;

    /**
     * 客户-手机号
     */
    @AuthColumn(columnName=AuthColumnEnum.MOBILEPHONE)
    private String custMobile;

    /**
     * 客户-邮箱
     */
    @AuthColumn(columnName=AuthColumnEnum.EMAIL)
    private String custEmail;

    /**
     * 物品-类型
     */
    private String itemType;
    private String itemTypeTxt;

    /**
     * 物品-种类
     */
    private String itemCategory;
    private String itemCategoryTxt;

    /**
     * 物品-编号
     */
    private String itemNo;

    /**
     * 物品-名称
     */
    private String itemName;

    /**
     * 物品-发放额度
     */
    private BigDecimal itemAmount;
    /**
     * 物品-发放额度Txt
     */
    private String itemAmountTxt;

    /**
     * 物品-发放额度单位
     */
    private String itemAmountUnit;

    /**
     * 物品-中奖概率
     */
    private BigDecimal itemProbability;

    /**
     * 物品-价格
     */
    private BigDecimal itemPrice;
    private String itemPriceTxt;

    /**
     * 物品-价格单位
     */
    private String itemPriceUnit;

    /**
     * 发放状态
     */
    private String givedStatus;
    private String givedStatusTxt ;
    /**
     * 发放时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date givedTime;

    /**
     * 审核状态
     */
    private String auditStatus;
    private String auditStatusTxt;

    /**
     * 审核时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime ;
    /**
     * 0：可发，1:数量不足 2：禁用 4：时间过期 8：时间未到
     */
    private Integer waitingStatus;
    private String waitingStatusTxt;

    /**
     * 物品-使用的开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useStartTime;

    /**
     * 物品-使用的结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useEndTime;

    /**
     * 任务-编号
     */
    private String taskCode;

    /**
     * 规则识别码
     */
    private String ruleCode;

    /**
     * 任务-名称
     */
    private String taskName;

    /**
     * 任务类型
     */
    private Integer taskType;

    /**
     * 任务-完成时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date taskFinishedTime;

    /**
     * 是否后台手动添加(0: 否 1:是)
     */
    private Integer addFromBos;
    private String addFromBosTxt;

    /**
     * 是否自动生成(0:系统生成 1：手动触发)
     */
    private Integer isAuto;

    /**
     * 敏感数据(例如：串码)
     */
    @AuthColumn(columnName=AuthColumnEnum.STRINGCODE)
    private String sensitiveData;

    /**
     * 其他信息(json 格式)
     */
    private String otherMsg;

    /**
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * 扩展字段3
     */
    private String ext3;

    /**
     * 扩展字段4
     */
    private String ext4;

    /**
     * 扩展字段5
     */
    private String ext5;
    /**开始创建时间*/
    private String startCreateDate ;
    /**结束创建时间*/
    private String endCreateDate ;
    
    private Boolean alreadySend ;
    /**任务物品ID*/
    private Long taskItemId ;
    
    private Long taskSettingId ;
    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 流水号
     * @return record_no 流水号
     */
    public String getRecordNo() {
        return recordNo;
    }

    /**
     * 流水号
     * @param recordNo 流水号
     */
    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo == null ? null : recordNo.trim();
    }

    /**
     * 第三方流水号
     * @return third_record_no 第三方流水号
     */
    public String getThirdRecordNo() {
        return thirdRecordNo;
    }

    /**
     * 第三方流水号
     * @param thirdRecordNo 第三方流水号
     */
    public void setThirdRecordNo(String thirdRecordNo) {
        this.thirdRecordNo = thirdRecordNo == null ? null : thirdRecordNo.trim();
    }

    /**
     * 活动编号
     * @return act_no 活动编号
     */
    public String getActNo() {
        return actNo;
    }

    /**
     * 活动编号
     * @param actNo 活动编号
     */
    public void setActNo(String actNo) {
        this.actNo = actNo == null ? null : actNo.trim();
    }

    /**
     * 活动名称
     * @return act_name 活动名称
     */
    public String getActName() {
        return actName;
    }

    /**
     * 活动名称
     * @param actName 活动名称
     */
    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    /**
     * 客户账号
     * @return account_no 客户账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 客户账号
     * @param accountNo 客户账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    /**
     * 平台
     * @return platform 平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 平台
     * @param platform 平台
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 真实还是模拟
     * @return env 真实还是模拟
     */
    public String getEnv() {
        return env;
    }

    /**
     * 真实还是模拟
     * @param env 真实还是模拟
     */
    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    /**
     * 客户-姓名
     * @return cust_name 客户-姓名
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 客户-姓名
     * @param custName 客户-姓名
     */
    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    /**
     * 客户-手机号
     * @return cust_mobile 客户-手机号
     */
    public String getCustMobile() {
        return custMobile;
    }

    /**
     * 客户-手机号
     * @param custMobile 客户-手机号
     */
    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile == null ? null : custMobile.trim();
    }

    /**
     * 客户-邮箱
     * @return cust_email 客户-邮箱
     */
    public String getCustEmail() {
        return custEmail;
    }

    /**
     * 客户-邮箱
     * @param custEmail 客户-邮箱
     */
    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail == null ? null : custEmail.trim();
    }

    /**
     * 物品-类型
     * @return item_type 物品-类型
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * 物品-类型
     * @param itemType 物品-类型
     */
    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    /**
     * 物品-种类
     * @return item_category 物品-种类
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * 物品-种类
     * @param itemCategory 物品-种类
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory == null ? null : itemCategory.trim();
    }

    /**
     * 物品-编号
     * @return item_no 物品-编号
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 物品-编号
     * @param itemNo 物品-编号
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

    /**
     * 物品-名称
     * @return item_name 物品-名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 物品-名称
     * @param itemName 物品-名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * 物品-发放额度
     * @return item_amount 物品-发放额度
     */
    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    /**
     * 物品-发放额度
     * @param itemAmount 物品-发放额度
     */
    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    /**
     * 物品-发放额度单位
     * @return item_amount_unit 物品-发放额度单位
     */
    public String getItemAmountUnit() {
        return itemAmountUnit;
    }

    /**
     * 物品-发放额度单位
     * @param itemAmountUnit 物品-发放额度单位
     */
    public void setItemAmountUnit(String itemAmountUnit) {
        this.itemAmountUnit = itemAmountUnit == null ? null : itemAmountUnit.trim();
    }

    /**
     * 物品-中奖概率
     * @return item_probability 物品-中奖概率
     */
    public BigDecimal getItemProbability() {
        return itemProbability;
    }

    /**
     * 物品-中奖概率
     * @param itemProbability 物品-中奖概率
     */
    public void setItemProbability(BigDecimal itemProbability) {
        this.itemProbability = itemProbability;
    }

    /**
     * 物品-价格
     * @return item_price 物品-价格
     */
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    /**
     * 物品-价格
     * @param itemPrice 物品-价格
     */
    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * 物品-价格单位
     * @return item_price_unit 物品-价格单位
     */
    public String getItemPriceUnit() {
        return itemPriceUnit;
    }

    /**
     * 物品-价格单位
     * @param itemPriceUnit 物品-价格单位
     */
    public void setItemPriceUnit(String itemPriceUnit) {
        this.itemPriceUnit = itemPriceUnit == null ? null : itemPriceUnit.trim();
    }

    /**
     * 发放状态
     * @return gived_status 发放状态
     */
    public String getGivedStatus() {
        return givedStatus;
    }

    /**
     * 发放状态
     * @param givedStatus 发放状态
     */
    public void setGivedStatus(String givedStatus) {
        this.givedStatus = givedStatus == null ? null : givedStatus.trim();
    }

    /**
     * 发放时间
     * @return gived_time 发放时间
     */
    public Date getGivedTime() {
        return givedTime;
    }

    /**
     * 发放时间
     * @param givedTime 发放时间
     */
    public void setGivedTime(Date givedTime) {
        this.givedTime = givedTime;
    }

    /**
     * 审核状态
     * @return audit_status 审核状态
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * 审核状态
     * @param auditStatus 审核状态
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    /**
     * 0：可发，1:数量不足 2：禁用 4：时间过期 8：时间未到
     * @return waiting_status 0：可发，1:数量不足 2：禁用 4：时间过期 8：时间未到
     */
    public Integer getWaitingStatus() {
        return waitingStatus;
    }

    /**
     * 0：可发，1:数量不足 2：禁用 4：时间过期 8：时间未到
     * @param waitingStatus 0：可发，1:数量不足 2：禁用 4：时间过期 8：时间未到
     */
    public void setWaitingStatus(Integer waitingStatus) {
        this.waitingStatus = waitingStatus;
    }

    /**
     * 物品-使用的开始时间
     * @return use_start_time 物品-使用的开始时间
     */
    public Date getUseStartTime() {
        return useStartTime;
    }

    /**
     * 物品-使用的开始时间
     * @param useStartTime 物品-使用的开始时间
     */
    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    /**
     * 物品-使用的结束时间
     * @return use_end_time 物品-使用的结束时间
     */
    public Date getUseEndTime() {
        return useEndTime;
    }

    /**
     * 物品-使用的结束时间
     * @param useEndTime 物品-使用的结束时间
     */
    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    /**
     * 任务-编号
     * @return task_code 任务-编号
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 任务-编号
     * @param taskCode 任务-编号
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode == null ? null : taskCode.trim();
    }

    /**
     * 规则识别码
     * @return rule_code 规则识别码
     */
    public String getRuleCode() {
        return ruleCode;
    }

    /**
     * 规则识别码
     * @param ruleCode 规则识别码
     */
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    /**
     * 任务-名称
     * @return task_name 任务-名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务-名称
     * @param taskName 任务-名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * 任务类型
     * @return task_type 任务类型
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * 任务类型
     * @param taskType 任务类型
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * 任务-完成时间
     * @return task_finished_time 任务-完成时间
     */
    public Date getTaskFinishedTime() {
        return taskFinishedTime;
    }

    /**
     * 任务-完成时间
     * @param taskFinishedTime 任务-完成时间
     */
    public void setTaskFinishedTime(Date taskFinishedTime) {
        this.taskFinishedTime = taskFinishedTime;
    }

    /**
     * 是否后台手动添加(0: 否 1:是)
     * @return add_from_bos 是否后台手动添加(0: 否 1:是)
     */
    public Integer getAddFromBos() {
        return addFromBos;
    }

    /**
     * 是否后台手动添加(0: 否 1:是)
     * @param addFromBos 是否后台手动添加(0: 否 1:是)
     */
    public void setAddFromBos(Integer addFromBos) {
        this.addFromBos = addFromBos;
    }

    /**
     * 是否自动生成(0:系统生成 1：手动触发)
     * @return is_auto 是否自动生成(0:系统生成 1：手动触发)
     */
    public Integer getIsAuto() {
        return isAuto;
    }

    /**
     * 是否自动生成(0:系统生成 1：手动触发)
     * @param isAuto 是否自动生成(0:系统生成 1：手动触发)
     */
    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    /**
     * 敏感数据(例如：串码)
     * @return sensitive_data 敏感数据(例如：串码)
     */
    public String getSensitiveData() {
        return sensitiveData;
    }

    /**
     * 敏感数据(例如：串码)
     * @param sensitiveData 敏感数据(例如：串码)
     */
    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData == null ? null : sensitiveData.trim();
    }

    /**
     * 其他信息(json 格式)
     * @return other_msg 其他信息(json 格式)
     */
    public String getOtherMsg() {
        return otherMsg;
    }

    /**
     * 其他信息(json 格式)
     * @param otherMsg 其他信息(json 格式)
     */
    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg == null ? null : otherMsg.trim();
    }

    /**
     * 扩展字段1
     * @return ext1 扩展字段1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 扩展字段1
     * @param ext1 扩展字段1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 扩展字段2
     * @return ext2 扩展字段2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 扩展字段2
     * @param ext2 扩展字段2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * 扩展字段3
     * @return ext3 扩展字段3
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 扩展字段3
     * @param ext3 扩展字段3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    /**
     * 扩展字段4
     * @return ext4 扩展字段4
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * 扩展字段4
     * @param ext4 扩展字段4
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * 扩展字段5
     * @return ext5 扩展字段5
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * 扩展字段5
     * @param ext5 扩展字段5
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Boolean getAlreadySend() {
		return alreadySend;
	}

	public void setAlreadySend(Boolean alreadySend) {
		this.alreadySend = alreadySend;
	}

	public Long getTaskItemId() {
		return taskItemId;
	}

	public void setTaskItemId(Long taskItemId) {
		this.taskItemId = taskItemId;
	}

	public String getItemAmountTxt() {
		return itemAmountTxt;
	}

	public void setItemAmountTxt(String itemAmountTxt) {
		this.itemAmountTxt = itemAmountTxt;
	}

	public String getItemPriceTxt() {
		return itemPriceTxt;
	}

	public void setItemPriceTxt(String itemPriceTxt) {
		this.itemPriceTxt = itemPriceTxt;
	}

	public String getItemTypeTxt() {
		return itemTypeTxt;
	}

	public void setItemTypeTxt(String itemTypeTxt) {
		this.itemTypeTxt = itemTypeTxt;
	}

	public String getItemCategoryTxt() {
		return itemCategoryTxt;
	}

	public void setItemCategoryTxt(String itemCategoryTxt) {
		this.itemCategoryTxt = itemCategoryTxt;
	}

	public String getGivedStatusTxt() {
		return givedStatusTxt;
	}

	public void setGivedStatusTxt(String givedStatusTxt) {
		this.givedStatusTxt = givedStatusTxt;
	}

	public String getAuditStatusTxt() {
		return auditStatusTxt;
	}

	public void setAuditStatusTxt(String auditStatusTxt) {
		this.auditStatusTxt = auditStatusTxt;
	}

	public String getWaitingStatusTxt() {
		return waitingStatusTxt;
	}

	public void setWaitingStatusTxt(String waitingStatusTxt) {
		this.waitingStatusTxt = waitingStatusTxt;
	}

	public String getAddFromBosTxt() {
		return addFromBosTxt;
	}

	public void setAddFromBosTxt(String addFromBosTxt) {
		this.addFromBosTxt = addFromBosTxt;
	}

	public Long getTaskSettingId() {
		return taskSettingId;
	}

	public void setTaskSettingId(Long taskSettingId) {
		this.taskSettingId = taskSettingId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}