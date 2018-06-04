package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2Group {
	private Long id;

	private Integer uuid;

	// private Date uutime;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date moditime;

	private Integer serverid;

	private String name;

	private String currency;

	private String platform;

	private String authentication;

	private Short minpwdlength;

	private Long companyid;

	private String company;

	private String companysite;

	private String companyemail;

	private String supportsite;

	private String supportemail;

	private String templatesfolder;

	private Short margincalllevel;

	private Short stopoutlevel;

	private Short weekendlevel;

	private Short ordermax;

	private Short enable;

	private Short clearnegative;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;

	private Integer createuserid;

	private Integer modiuserid;

	private Short status;

	private Double volumesmax;

	private String level;

	private Short balanceclearnegative;

	private Short marginlevel;

	private String env;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	// public Date getUutime() {
	// return uutime;
	// }
	//
	// public void setUutime(Date uutime) {
	// this.uutime = uutime;
	// }

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication == null ? null : authentication.trim();
	}

	public Short getMinpwdlength() {
		return minpwdlength;
	}

	public void setMinpwdlength(Short minpwdlength) {
		this.minpwdlength = minpwdlength;
	}

	public Long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getCompanysite() {
		return companysite;
	}

	public void setCompanysite(String companysite) {
		this.companysite = companysite == null ? null : companysite.trim();
	}

	public String getCompanyemail() {
		return companyemail;
	}

	public void setCompanyemail(String companyemail) {
		this.companyemail = companyemail == null ? null : companyemail.trim();
	}

	public String getSupportsite() {
		return supportsite;
	}

	public void setSupportsite(String supportsite) {
		this.supportsite = supportsite == null ? null : supportsite.trim();
	}

	public String getSupportemail() {
		return supportemail;
	}

	public void setSupportemail(String supportemail) {
		this.supportemail = supportemail == null ? null : supportemail.trim();
	}

	public String getTemplatesfolder() {
		return templatesfolder;
	}

	public void setTemplatesfolder(String templatesfolder) {
		this.templatesfolder = templatesfolder == null ? null : templatesfolder.trim();
	}

	public Short getMargincalllevel() {
		return margincalllevel;
	}

	public void setMargincalllevel(Short margincalllevel) {
		this.margincalllevel = margincalllevel;
	}

	public Short getStopoutlevel() {
		return stopoutlevel;
	}

	public void setStopoutlevel(Short stopoutlevel) {
		this.stopoutlevel = stopoutlevel;
	}

	public Short getWeekendlevel() {
		return weekendlevel;
	}

	public void setWeekendlevel(Short weekendlevel) {
		this.weekendlevel = weekendlevel;
	}

	public Short getOrdermax() {
		return ordermax;
	}

	public void setOrdermax(Short ordermax) {
		this.ordermax = ordermax;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public Short getClearnegative() {
		return clearnegative;
	}

	public void setClearnegative(Short clearnegative) {
		this.clearnegative = clearnegative;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Integer createuserid) {
		this.createuserid = createuserid;
	}

	public Date getModitime() {
		return moditime;
	}

	public void setModitime(Date moditime) {
		this.moditime = moditime;
	}

	public Integer getModiuserid() {
		return modiuserid;
	}

	public void setModiuserid(Integer modiuserid) {
		this.modiuserid = modiuserid;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Double getVolumesmax() {
		return volumesmax;
	}

	public void setVolumesmax(Double volumesmax) {
		this.volumesmax = volumesmax;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public Short getBalanceclearnegative() {
		return balanceclearnegative;
	}

	public void setBalanceclearnegative(Short balanceclearnegative) {
		this.balanceclearnegative = balanceclearnegative;
	}

	public Short getMarginlevel() {
		return marginlevel;
	}

	public void setMarginlevel(Short marginlevel) {
		this.marginlevel = marginlevel;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}