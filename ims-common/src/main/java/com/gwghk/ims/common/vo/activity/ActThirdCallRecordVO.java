package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.List;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table act_third_call_record_fx
 *
 * @mbggenerated
 */
public class ActThirdCallRecordVO extends BaseVO implements Serializable {
    
	private static final long serialVersionUID = 5769678456666328843L;

	/**
     *
     */
    private Long id;
    
    /**
     * 标签
     */
    private List<String> tagCodes;

   

	/**
     *活动编号
     */
    private String activityPeriods;

    /**
     *平台
     */
    private String platform;

    /**
     *账号
     */
    private String accountNo;

    /**
     *类型(bonus:贈金)
     */
    private String type;

    /**
     *操作(增加贈金:addBonus,releaseBonus:释放贈金未释放金额)（枚举：ActThirdCallEnum）
     */
    private String code;

    /**
     *订单号
     */
    private String recordNo;

    /**
     *第三方订单号
     */
    private String thirdRecordNo;

    /**
     *主从关系订单号
     */
    private String parentRecordNo;

    /**
     *第三方调用处理结果
     */
    private String thirdDealResult;

    /**
     *备注信息
     */
    private String remark;
    
    /**
	 * 调用api次数
	 */
    
    public List<String> getTagCodes() {
		return tagCodes;
	}

	public void setTagCodes(List<String> tagCodes) {
		this.tagCodes = tagCodes;
	}
	
	private Integer tryCount;

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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getThirdRecordNo() {
		return thirdRecordNo;
	}

	public void setThirdRecordNo(String thirdRecordNo) {
		this.thirdRecordNo = thirdRecordNo;
	}

	public String getParentRecordNo() {
		return parentRecordNo;
	}

	public void setParentRecordNo(String parentRecordNo) {
		this.parentRecordNo = parentRecordNo;
	}

	public String getThirdDealResult() {
		return thirdDealResult;
	}

	public void setThirdDealResult(String thirdDealResult) {
		this.thirdDealResult = thirdDealResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public void setCompanyCode(String codeByKey) {
		// TODO Auto-generated method stub
		
	}

}