package com.gwghk.ims.common.vo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.enums.ActCommonStatusEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：基本对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
public class BaseVO extends PageVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 是否启用
	 */
	public String enableFlag;

	/**
	 * 是否删除
	 */
	public String deleteFlag;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@OrderBy(columnName="create_date",propName="createDate",order="desc")
	public Date createDate;

	/**
	 * 创建人
	 */
	public String createUser;

	/**
	 * 创建用户IP
	 */
	public String createIp;

	/**
	 * 更新人
	 */
	public String updateUser;

	/**
	 * 更新IP
	 */
	public String updateIp;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@OrderBy(columnName="update_date",propName="updateDate",order="desc")
	public Date updateDate;

	/**
	 * 公司ID
	 */
	private Long companyId;

	/**
	 * 版本号
	 */
	public Integer versionNo;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 格式化显示 
	 */
	private String enableFlagName;
	

	/**
	 * 格式化显示 
	 */
	private String deleteFlagName;


	/**
	 * 是否超级管理员
	 */
	private boolean superAdmin;

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
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

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		if(null != companyId){
			return CompanyEnum.getCodeById(companyId);
		}
		return null;
	}

	public String getCompanyName() {
		if(null != companyId){
			CompanyEnum temp = CompanyEnum.findById(companyId);
			if(null != temp){
				return temp.getName();
			}
		}
		return null;
	}
	
	 

	public String getEnableFlagName() {
		if(StringUtil.isEmpty(this.enableFlagName)){
			return ActCommonStatusEnum.formatCode(this.enableFlag);
		}
		return enableFlagName;
	}

	public void setEnableFlagName(String enableFlagName) {
		this.enableFlagName = enableFlagName;
	}

	 
	public String getDeleteFlagName() {
		if(StringUtil.isEmpty(this.deleteFlagName)){
			return ActCommonStatusEnum.formatCode(this.deleteFlag);
		}
		return deleteFlagName;
	}

	public void setDeleteFlagName(String deleteFlagName) {
		this.deleteFlagName = deleteFlagName;
	}

	/**
	 * 输出VO对象为String
	 * @return
	 */
	public String toStr() {
		StringBuffer str=new StringBuffer();
		Method[] methods=this.getClass().getMethods();
		try {
			for(Method m:methods) {
				if(!m.isAccessible() || !m.getName().startsWith("get"))
					continue;
				
				str.append(m.getName().replaceFirst("get","")).append(":").append(m.invoke(this)).append(",");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return str.toString();
	}
}
