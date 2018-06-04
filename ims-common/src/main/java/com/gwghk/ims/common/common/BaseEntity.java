package com.gwghk.ims.common.common;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.util.CustomDateSerializer;

/**
 * 摘要：基础实体类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
public class BaseEntity {

    /**
     * 创建用户
     */
    @OrderBy(columnName = "create_user", propName = "createUser", order = "asc")
    public String createUser;

    /**
     * 创建用户IP
     */
    public String createIp;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName = "create_date", propName = "createDate", order = "desc")
    public Date createDate;

    /**
     * 更新人
     */
    @OrderBy(columnName = "update_user", propName = "updateUser", order = "asc")
    public String updateUser;

    /**
     * 更新IP
     */
    public String updateIp;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName = "update_date", propName = "updateDate", order = "desc")
    public Date updateDate;

    /**
     * 版本号
     */
    public Integer versionNo;

    /** 有效标示 */
    @OrderBy(columnName = "enable_flag", propName = "enableFlag", order = "asc")
    public String enableFlag;

    /** 删除标示 */
    @OrderBy(columnName = "delete_flag", propName = "deleteFlag", order = "asc")
    public String deleteFlag;

    /** 公司Id */
    @OrderBy(columnName = "company_id", propName = "companyId", order = "asc")
    public Long companyId;
    
    /**
	 * 备注
	 */
	private String remark;

    public void beforeSave() {
        Date now = new Date();
        if (null == this.createDate) {
            this.setCreateDate(now);
        }
        if (null == this.updateDate) {
            this.setUpdateDate(now);
        }
        if (null == this.enableFlag) {
            this.setEnableFlag("Y");
        }
        if (null == this.deleteFlag) {
            this.setDeleteFlag("N");
        }
    }
    
    public void beforeUpdate(){
    	 if (null == this.updateDate) {
             this.setUpdateDate(new Date());
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

    @JsonSerialize(using = CustomDateSerializer.class)
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

    @JsonSerialize(using = CustomDateSerializer.class)
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}