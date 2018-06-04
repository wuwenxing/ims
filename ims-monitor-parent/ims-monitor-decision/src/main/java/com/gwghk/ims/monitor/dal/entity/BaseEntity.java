package com.gwghk.ims.monitor.dal.entity;

import java.util.Date;

public class BaseEntity {

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建用户IP
     */
    private String createIp;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新IP
     */
    private String updateIp;

    /**
     * 更新使劲
     */
    private Date updateDate;

    /**
     * BB TODO DEL 1001
     */
    private Integer versionNo;

    /** 有效标示 */
    private String enableFlag = "Y";

    /** 删除标示 */
    private String deleteFlag = "N";

    /** 公司Id */
    private Long companyId;
    
    private String companyCode;

    public void beforeSave() {
        Date now = new Date();
        if (null == this.createDate) {
            this.setCreateDate(now);
        }
        if (null == this.updateDate) {
            this.setUpdateDate(now);
        }
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
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
        this.updateUser = updateUser;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
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

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
    
    
}
