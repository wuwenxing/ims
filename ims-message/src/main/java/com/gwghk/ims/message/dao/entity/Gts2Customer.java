package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2Customer {
	private Long id;

	private Long gts2CustomerId;

	private Long companyId;

	private Long customerNumber;

	private String loginname;

	private String password;

	private String agentCode;

	private String openPlatform;

	private String customerCategory;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	private String lastLoginIp;

	private String title;

	private String chineseName;

	private String englishLastName;

	private String englishFirstName;

	private String idDocument;

	private String idDocumentOther;

	private String idDocumentNumber;

	private String idDocumentNumberMd5;

	private String idDocumentCountry;

	private String idDocumentCountryOther;

	private String nationality;

	private String nationalityOther;

	private String homePhonePrefix;

	private String homePhone;

	private String mobilePhonePrefix;

	private String mobilePhone;

	private Boolean addressConsistent;

	private String province;

	private String country;

	private String countryOther;

	private String postalCode;

	private String email;

	private String informationFrom;

	private Integer isAgreement;

	private Integer goldenComment;

	private String emailService;

	private Integer errorcount;

	private Double maxCreditCount;

	private String firstDepositPlatform;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstDepositDate;

	private String firstActiveAccountNo;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstActiveDate;

	private String verifyMethod;

	private String firstActivePlatform;

	private Boolean iBornInUs;

	private Boolean iAmNotAmerican;

	private Boolean iAmUsCitizen;

	private Boolean isDemo;

	private Boolean isMigrateData;

	private Integer successLoginCount;

	private Integer failLoginCount;

	private Long customerGroupId;

	private String createUser;

	private String createIp;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	private String updateUser;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private String updateIp;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	private Integer versionNo;

	private String openFrom;

	private Long firstWithdrawGts2AccountId;

	private String firstWithdrawPlatform;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstWithdrawDate;

	private Long firstDepositGts2AccountId;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date openCustomerDate;

	private String encodePasswordType;

	private String migrateType;

	private String openIp;


	private String address;

	private String remark;

	private String env;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGts2CustomerId() {
		return gts2CustomerId;
	}

	public void setGts2CustomerId(Long gts2CustomerId) {
		this.gts2CustomerId = gts2CustomerId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname == null ? null : loginname.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode == null ? null : agentCode.trim();
	}

	public String getOpenPlatform() {
		return openPlatform;
	}

	public void setOpenPlatform(String openPlatform) {
		this.openPlatform = openPlatform == null ? null : openPlatform.trim();
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory == null
				? null
				: customerCategory.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName == null ? null : chineseName.trim();
	}

	public String getEnglishLastName() {
		return englishLastName;
	}

	public void setEnglishLastName(String englishLastName) {
		this.englishLastName = englishLastName == null
				? null
				: englishLastName.trim();
	}

	public String getEnglishFirstName() {
		return englishFirstName;
	}

	public void setEnglishFirstName(String englishFirstName) {
		this.englishFirstName = englishFirstName == null
				? null
				: englishFirstName.trim();
	}

	public String getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(String idDocument) {
		this.idDocument = idDocument == null ? null : idDocument.trim();
	}

	public String getIdDocumentOther() {
		return idDocumentOther;
	}

	public void setIdDocumentOther(String idDocumentOther) {
		this.idDocumentOther = idDocumentOther == null
				? null
				: idDocumentOther.trim();
	}

	public String getIdDocumentNumber() {
		return idDocumentNumber;
	}

	public void setIdDocumentNumber(String idDocumentNumber) {
		this.idDocumentNumber = idDocumentNumber == null
				? null
				: idDocumentNumber.trim();
	}

	public String getIdDocumentNumberMd5() {
		return idDocumentNumberMd5;
	}

	public void setIdDocumentNumberMd5(String idDocumentNumberMd5) {
		this.idDocumentNumberMd5 = idDocumentNumberMd5 == null
				? null
				: idDocumentNumberMd5.trim();
	}

	public String getIdDocumentCountry() {
		return idDocumentCountry;
	}

	public void setIdDocumentCountry(String idDocumentCountry) {
		this.idDocumentCountry = idDocumentCountry == null
				? null
				: idDocumentCountry.trim();
	}

	public String getIdDocumentCountryOther() {
		return idDocumentCountryOther;
	}

	public void setIdDocumentCountryOther(String idDocumentCountryOther) {
		this.idDocumentCountryOther = idDocumentCountryOther == null
				? null
				: idDocumentCountryOther.trim();
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality == null ? null : nationality.trim();
	}

	public String getNationalityOther() {
		return nationalityOther;
	}

	public void setNationalityOther(String nationalityOther) {
		this.nationalityOther = nationalityOther == null
				? null
				: nationalityOther.trim();
	}

	public String getHomePhonePrefix() {
		return homePhonePrefix;
	}

	public void setHomePhonePrefix(String homePhonePrefix) {
		this.homePhonePrefix = homePhonePrefix == null
				? null
				: homePhonePrefix.trim();
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone == null ? null : homePhone.trim();
	}

	public String getMobilePhonePrefix() {
		return mobilePhonePrefix;
	}

	public void setMobilePhonePrefix(String mobilePhonePrefix) {
		this.mobilePhonePrefix = mobilePhonePrefix == null
				? null
				: mobilePhonePrefix.trim();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
	}

	public Boolean getAddressConsistent() {
		return addressConsistent;
	}

	public void setAddressConsistent(Boolean addressConsistent) {
		this.addressConsistent = addressConsistent;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getCountryOther() {
		return countryOther;
	}

	public void setCountryOther(String countryOther) {
		this.countryOther = countryOther == null ? null : countryOther.trim();
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode == null ? null : postalCode.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getInformationFrom() {
		return informationFrom;
	}

	public void setInformationFrom(String informationFrom) {
		this.informationFrom = informationFrom == null
				? null
				: informationFrom.trim();
	}

	public Integer getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}

	public Integer getGoldenComment() {
		return goldenComment;
	}

	public void setGoldenComment(Integer goldenComment) {
		this.goldenComment = goldenComment;
	}

	public String getEmailService() {
		return emailService;
	}

	public void setEmailService(String emailService) {
		this.emailService = emailService == null ? null : emailService.trim();
	}

	public Integer getErrorcount() {
		return errorcount;
	}

	public void setErrorcount(Integer errorcount) {
		this.errorcount = errorcount;
	}

	public Double getMaxCreditCount() {
		return maxCreditCount;
	}

	public String getFirstDepositPlatform() {
		return firstDepositPlatform;
	}

	public void setFirstDepositPlatform(String firstDepositPlatform) {
		this.firstDepositPlatform = firstDepositPlatform;
	}

	public Date getFirstDepositDate() {
		return firstDepositDate;
	}

	public void setFirstDepositDate(Date firstDepositDate) {
		this.firstDepositDate = firstDepositDate;
	}

	public String getFirstActiveAccountNo() {
		return firstActiveAccountNo;
	}

	public void setFirstActiveAccountNo(String firstActiveAccountNo) {
		this.firstActiveAccountNo = firstActiveAccountNo;
	}

	public Date getFirstActiveDate() {
		return firstActiveDate;
	}

	public void setFirstActiveDate(Date firstActiveDate) {
		this.firstActiveDate = firstActiveDate;
	}

	public String getVerifyMethod() {
		return verifyMethod;
	}

	public void setVerifyMethod(String verifyMethod) {
		this.verifyMethod = verifyMethod;
	}

	public String getFirstActivePlatform() {
		return firstActivePlatform;
	}

	public void setFirstActivePlatform(String firstActivePlatform) {
		this.firstActivePlatform = firstActivePlatform;
	}

	public Boolean getiBornInUs() {
		return iBornInUs;
	}

	public void setiBornInUs(Boolean iBornInUs) {
		this.iBornInUs = iBornInUs;
	}

	public Boolean getiAmNotAmerican() {
		return iAmNotAmerican;
	}

	public void setiAmNotAmerican(Boolean iAmNotAmerican) {
		this.iAmNotAmerican = iAmNotAmerican;
	}

	public Boolean getiAmUsCitizen() {
		return iAmUsCitizen;
	}

	public void setiAmUsCitizen(Boolean iAmUsCitizen) {
		this.iAmUsCitizen = iAmUsCitizen;
	}

	public Boolean getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(Boolean isDemo) {
		this.isDemo = isDemo;
	}

	public Boolean getIsMigrateData() {
		return isMigrateData;
	}

	public void setIsMigrateData(Boolean isMigrateData) {
		this.isMigrateData = isMigrateData;
	}

	public Integer getSuccessLoginCount() {
		return successLoginCount;
	}

	public void setSuccessLoginCount(Integer successLoginCount) {
		this.successLoginCount = successLoginCount;
	}

	public Integer getFailLoginCount() {
		return failLoginCount;
	}

	public void setFailLoginCount(Integer failLoginCount) {
		this.failLoginCount = failLoginCount;
	}

	public Long getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(Long customerGroupId) {
		this.customerGroupId = customerGroupId;
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

	public String getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(String openFrom) {
		this.openFrom = openFrom;
	}

	public Long getFirstWithdrawGts2AccountId() {
		return firstWithdrawGts2AccountId;
	}

	public void setFirstWithdrawGts2AccountId(Long firstWithdrawGts2AccountId) {
		this.firstWithdrawGts2AccountId = firstWithdrawGts2AccountId;
	}

	public String getFirstWithdrawPlatform() {
		return firstWithdrawPlatform;
	}

	public void setFirstWithdrawPlatform(String firstWithdrawPlatform) {
		this.firstWithdrawPlatform = firstWithdrawPlatform;
	}

	public Date getFirstWithdrawDate() {
		return firstWithdrawDate;
	}

	public void setFirstWithdrawDate(Date firstWithdrawDate) {
		this.firstWithdrawDate = firstWithdrawDate;
	}

	public Long getFirstDepositGts2AccountId() {
		return firstDepositGts2AccountId;
	}

	public void setFirstDepositGts2AccountId(Long firstDepositGts2AccountId) {
		this.firstDepositGts2AccountId = firstDepositGts2AccountId;
	}

	public Date getOpenCustomerDate() {
		return openCustomerDate;
	}

	public void setOpenCustomerDate(Date openCustomerDate) {
		this.openCustomerDate = openCustomerDate;
	}

	public String getEncodePasswordType() {
		return encodePasswordType;
	}

	public void setEncodePasswordType(String encodePasswordType) {
		this.encodePasswordType = encodePasswordType;
	}

	public String getMigrateType() {
		return migrateType;
	}

	public void setMigrateType(String migrateType) {
		this.migrateType = migrateType;
	}

	public String getOpenIp() {
		return openIp;
	}

	public void setOpenIp(String openIp) {
		this.openIp = openIp;
	}

	public void setMaxCreditCount(Double maxCreditCount) {
		this.maxCreditCount = maxCreditCount;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}