package com.gwghk.sys.api.dao.entity;

import java.util.Date;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 摘要：系统-用户实体
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年8月10日
 */
public class SystemUser extends BaseEntity {
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 角色ID
	 */
	private Long roleId;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 账号
	 */
	private String userNo;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 职位
	 */
	private String position;

	/**
	 * 手机
	 */
	private String telephone;
	
	/**
	 * 登录次数
	 */
	private Integer loginTimes;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginDate;
	
	private String remark;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
