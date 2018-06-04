package com.gwghk.ims.activity.dao.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.enums.AuthColumnEnum;
import com.gwghk.ims.common.util.CustomDateSerializer;

/**
 * 摘要：活动管理-活动参与资格实体对象
 * 
 * @author misa.liu
 * @version 1.0
 * @Date 2017年6月20日
 */
public class ActJoinQualify extends BaseEntity {

    private Long id;
 
    /**
     * 手机号
     */
    @AuthColumn(columnName = AuthColumnEnum.MOBILEPHONE)
    private String mobile;
    
    /**
     * 邮箱
     */
    @AuthColumn(columnName = AuthColumnEnum.EMAIL)
    private String email;

    /**
     * 账号
     */
    private String accountNo;
    /**
     * 平台
     */
    private String platform;
    /**
     * 活动编号
     */
    private String activityPeriods;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 中文名
     */
    @AuthColumn(columnName = AuthColumnEnum.CHINESENAME)
    private String chineseName;

    /**
     * 账号创建时间
     */
    @OrderBy(columnName = "account_create_date", propName = "accountCreateDate", order = "asc")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accountCreateDate;
    
    /**
     * 真实还是模拟账号
     */
    private String env;
    
    /**
     * 预先报名的栏位  0:否，1:是
     */
    private Integer signUp;
    
    /**
     * 参与状态
     */
    private Integer participateStatus;
    
    
    /**
     * 参与时间
     */
    private Date joinTime;
    
    
    
    /**
     * 是否结算
     */
    private Boolean settlement;
    
    /**
     * 结算时间
     */
    private Date settlementTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getActivityPeriods() {
        return activityPeriods;
    }

    public void setActivityPeriods(String activityPeriods) {
        this.activityPeriods = activityPeriods;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getAccountCreateDate() {
        return accountCreateDate;
    }

    public void setAccountCreateDate(Date accountCreateDate) {
        this.accountCreateDate = accountCreateDate;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Integer getSignUp() {
        return signUp;
    }

    public void setSignUp(Integer signUp) {
        this.signUp = signUp;
    }

    public Integer getParticipateStatus() {
        return participateStatus;
    }

    public void setParticipateStatus(Integer participateStatus) {
        this.participateStatus = participateStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
    public Boolean getSettlement() {
        return settlement;
    }

    public void setSettlement(Boolean settlement) {
        this.settlement = settlement;
    }

    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
 
}
