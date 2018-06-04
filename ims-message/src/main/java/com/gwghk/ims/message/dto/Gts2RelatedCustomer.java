package com.gwghk.ims.message.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2RelatedCustomer {
    /**
     * t_related_customer的ID
     */
    private Long id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 客戶的 GTS2 id
     */
    private Long gts2CustomerId;

    /**
     * 客戶的 GTS2 id
     */
    private Long gts2DemoCustomerId;

    /**
     * 客戶統一帳號
     */
    private Long customerNumber;

    /**
     * 模擬客戶統一帳號
     */
    private Long demoCustomerNumber;

    /**
     * 帳號號碼
     */
    private String loginname;

    /**
     * 密碼  公司可設置 ENCODE_PASSWORD_TYPE設定加密方法 
     */
    private String password;

    /**
     * 密碼來運的賬號 
     */
    private String passwordSeed;

    /**
     * 電郵
     */
    private String email;

    /**
     * 手提电话 - 國家區號
     */
    private String mobilePhonePrefix;

    /**
     * 手提电话
     */
    private String mobilePhone;

    /**
     * 已經綁定的關聯賬號
     */
    private String isBind;

    /**
     * 加密后的密碼 
     */
    private String encryptedPassword;

    /**
     * 行為類型，如開戶/激活/變更狀態
     */
    private String groupActionType;

    /**
     * Customer group ID
     */
    private Long customerGroupId;

    /**
     * 行為類型，如開戶/激活/變更狀態
     */
    private String demoGroupActionType;

    /**
     * Demo customer group ID
     */
    private Long demoCustomerGroupId;

    /**
     * 登入錯誤次數
     */
    private Integer errorcount;

    /**
     * 記錄操作日誌，更新時候自動寫入了t_related_customer_log
     */
    private String log;

    /**
     * t_company中的ID
     */
    private Long companyId;

    private String createUser;

    private String createIp;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String updateUser;

    private String updateIp;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private Integer versionNo;
    
    private String companyCode;

    private Date bindDate;
    /**
     * t_related_customer的ID
     * @return id t_related_customer的ID
     */
    public Long getId() {
        return id;
    }

    /**
     * t_related_customer的ID
     * @param id t_related_customer的ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * uuid
     * @return uuid uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * uuid
     * @param uuid uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 客戶的 GTS2 id
     * @return gts2_customer_id 客戶的 GTS2 id
     */
    public Long getGts2CustomerId() {
        return gts2CustomerId;
    }

    /**
     * 客戶的 GTS2 id
     * @param gts2CustomerId 客戶的 GTS2 id
     */
    public void setGts2CustomerId(Long gts2CustomerId) {
        this.gts2CustomerId = gts2CustomerId;
    }

    /**
     * 客戶的 GTS2 id
     * @return gts2_demo_customer_id 客戶的 GTS2 id
     */
    public Long getGts2DemoCustomerId() {
        return gts2DemoCustomerId;
    }

    /**
     * 客戶的 GTS2 id
     * @param gts2DemoCustomerId 客戶的 GTS2 id
     */
    public void setGts2DemoCustomerId(Long gts2DemoCustomerId) {
        this.gts2DemoCustomerId = gts2DemoCustomerId;
    }

    /**
     * 客戶統一帳號
     * @return customer_number 客戶統一帳號
     */
    public Long getCustomerNumber() {
        return customerNumber;
    }

    /**
     * 客戶統一帳號
     * @param customerNumber 客戶統一帳號
     */
    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    /**
     * 模擬客戶統一帳號
     * @return demo_customer_number 模擬客戶統一帳號
     */
    public Long getDemoCustomerNumber() {
        return demoCustomerNumber;
    }

    /**
     * 模擬客戶統一帳號
     * @param demoCustomerNumber 模擬客戶統一帳號
     */
    public void setDemoCustomerNumber(Long demoCustomerNumber) {
        this.demoCustomerNumber = demoCustomerNumber;
    }

    /**
     * 帳號號碼
     * @return loginname 帳號號碼
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * 帳號號碼
     * @param loginname 帳號號碼
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * 密碼  公司可設置 ENCODE_PASSWORD_TYPE設定加密方法 
     * @return password 密碼  公司可設置 ENCODE_PASSWORD_TYPE設定加密方法 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密碼  公司可設置 ENCODE_PASSWORD_TYPE設定加密方法 
     * @param password 密碼  公司可設置 ENCODE_PASSWORD_TYPE設定加密方法 
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 密碼來運的賬號 
     * @return password_seed 密碼來運的賬號 
     */
    public String getPasswordSeed() {
        return passwordSeed;
    }

    /**
     * 密碼來運的賬號 
     * @param passwordSeed 密碼來運的賬號 
     */
    public void setPasswordSeed(String passwordSeed) {
        this.passwordSeed = passwordSeed == null ? null : passwordSeed.trim();
    }

    /**
     * 電郵
     * @return email 電郵
     */
    public String getEmail() {
        return email;
    }

    /**
     * 電郵
     * @param email 電郵
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 手提电话 - 國家區號
     * @return mobile_phone_prefix 手提电话 - 國家區號
     */
    public String getMobilePhonePrefix() {
        return mobilePhonePrefix;
    }

    /**
     * 手提电话 - 國家區號
     * @param mobilePhonePrefix 手提电话 - 國家區號
     */
    public void setMobilePhonePrefix(String mobilePhonePrefix) {
        this.mobilePhonePrefix = mobilePhonePrefix == null ? null : mobilePhonePrefix.trim();
    }

    /**
     * 手提电话
     * @return mobile_phone 手提电话
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 手提电话
     * @param mobilePhone 手提电话
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    /**
     * 已經綁定的關聯賬號
     * @return is_bind 已經綁定的關聯賬號
     */
    public String getIsBind() {
        return isBind;
    }

    /**
     * 已經綁定的關聯賬號
     * @param isBind 已經綁定的關聯賬號
     */
    public void setIsBind(String isBind) {
        this.isBind = isBind == null ? null : isBind.trim();
    }

    /**
     * 加密后的密碼 
     * @return encrypted_password 加密后的密碼 
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * 加密后的密碼 
     * @param encryptedPassword 加密后的密碼 
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword == null ? null : encryptedPassword.trim();
    }

    /**
     * 行為類型，如開戶/激活/變更狀態
     * @return group_action_type 行為類型，如開戶/激活/變更狀態
     */
    public String getGroupActionType() {
        return groupActionType;
    }

    /**
     * 行為類型，如開戶/激活/變更狀態
     * @param groupActionType 行為類型，如開戶/激活/變更狀態
     */
    public void setGroupActionType(String groupActionType) {
        this.groupActionType = groupActionType == null ? null : groupActionType.trim();
    }

    /**
     * Customer group ID
     * @return customer_group_id Customer group ID
     */
    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    /**
     * Customer group ID
     * @param customerGroupId Customer group ID
     */
    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    /**
     * 行為類型，如開戶/激活/變更狀態
     * @return demo_group_action_type 行為類型，如開戶/激活/變更狀態
     */
    public String getDemoGroupActionType() {
        return demoGroupActionType;
    }

    /**
     * 行為類型，如開戶/激活/變更狀態
     * @param demoGroupActionType 行為類型，如開戶/激活/變更狀態
     */
    public void setDemoGroupActionType(String demoGroupActionType) {
        this.demoGroupActionType = demoGroupActionType == null ? null : demoGroupActionType.trim();
    }

    /**
     * Demo customer group ID
     * @return demo_customer_group_id Demo customer group ID
     */
    public Long getDemoCustomerGroupId() {
        return demoCustomerGroupId;
    }

    /**
     * Demo customer group ID
     * @param demoCustomerGroupId Demo customer group ID
     */
    public void setDemoCustomerGroupId(Long demoCustomerGroupId) {
        this.demoCustomerGroupId = demoCustomerGroupId;
    }

    /**
     * 登入錯誤次數
     * @return errorcount 登入錯誤次數
     */
    public Integer getErrorcount() {
        return errorcount;
    }

    /**
     * 登入錯誤次數
     * @param errorcount 登入錯誤次數
     */
    public void setErrorcount(Integer errorcount) {
        this.errorcount = errorcount;
    }

    /**
     * 記錄操作日誌，更新時候自動寫入了t_related_customer_log
     * @return log 記錄操作日誌，更新時候自動寫入了t_related_customer_log
     */
    public String getLog() {
        return log;
    }

    /**
     * 記錄操作日誌，更新時候自動寫入了t_related_customer_log
     * @param log 記錄操作日誌，更新時候自動寫入了t_related_customer_log
     */
    public void setLog(String log) {
        this.log = log == null ? null : log.trim();
    }

    /**
     * t_company中的ID
     * @return company_id t_company中的ID
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * t_company中的ID
     * @param companyId t_company中的ID
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
    
    
}