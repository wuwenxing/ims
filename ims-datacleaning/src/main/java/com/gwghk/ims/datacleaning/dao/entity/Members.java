package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

public class Members {
    /**
     * 唯一ID
     */
    private Integer id;

    /**
     * 交易账号
     */
    private String username;

    /**
     * 中文姓名
     */
    private String chineseName;

    /**
     * 英文名
     */
    private String englishName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 证件类别
     */
    private String idName;

    /**
     * 地址
     */
    private String address;

    /**
     * 国家代码（电话）
     */
    private String countryCode;

    /**
     * 腾讯QQ
     */
    private String qq;

    /**
     * Skype
     */
    private String skype;

    /**
     * MSN
     */
    private String msn;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 其他联系方式
     */
    private String otherContact;

    /**
     * 代理账号
     */
    private String agent;

    /**
     * 邮编
     */
    private String zipcode;

    private String bankswift;

    /**
     * 银行名称
     */
    private String bankname;

    /**
     * 银行地址
     */
    private String bankaddr;

    /**
     * 银行开户省
     */
    private String province;

    /**
     * 银行开户城市
     */
    private String city;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 会员类型
     */
    private String memberType;

    /**
     * 推广ID
     */
    private String adFrom;

    /**
     * 状态
     */
    private String status;

    private String userStatus;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    /**
     * 金银贸易场编码
     */
    private String cgseCoding;

    private String area;

    /**
     * 国家
     */
    private String country;

    private String ip;

    private String iecookie;

    private String mobileSec;

    private String idNumberHidden;

    private String modifier;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;
    
    private String idNumber;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;
    
    /**
     * 入金时间
     */
    private Date depositTime;
    
    /**
     * 标准客户 =20美元 专业客户=26美元 尊贵客户=28美元
     */
    private String group;
    
    /**
     * 修改时间
     */
    private Date platformMtime;

    /**
     * 唯一ID
     * @return id 唯一ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 唯一ID
     * @param id 唯一ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 交易账号
     * @return username 交易账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 交易账号
     * @param username 交易账号
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 中文姓名
     * @return chinese_name 中文姓名
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     * 中文姓名
     * @param chineseName 中文姓名
     */
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    /**
     * 英文名
     * @return english_name 英文名
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 英文名
     * @param englishName 英文名
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    /**
     * 性别
     * @return sex 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 生日
     * @return birthday 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 生日
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 证件类别
     * @return id_name 证件类别
     */
    public String getIdName() {
        return idName;
    }

    /**
     * 证件类别
     * @param idName 证件类别
     */
    public void setIdName(String idName) {
        this.idName = idName == null ? null : idName.trim();
    }

    /**
     * 地址
     * @return address 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 国家代码（电话）
     * @return country_code 国家代码（电话）
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 国家代码（电话）
     * @param countryCode 国家代码（电话）
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    /**
     * 腾讯QQ
     * @return qq 腾讯QQ
     */
    public String getQq() {
        return qq;
    }

    /**
     * 腾讯QQ
     * @param qq 腾讯QQ
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * Skype
     * @return skype Skype
     */
    public String getSkype() {
        return skype;
    }

    /**
     * Skype
     * @param skype Skype
     */
    public void setSkype(String skype) {
        this.skype = skype == null ? null : skype.trim();
    }

    /**
     * MSN
     * @return msn MSN
     */
    public String getMsn() {
        return msn;
    }

    /**
     * MSN
     * @param msn MSN
     */
    public void setMsn(String msn) {
        this.msn = msn == null ? null : msn.trim();
    }

    /**
     * 国籍
     * @return nationality 国籍
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * 国籍
     * @param nationality 国籍
     */
    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    /**
     * 其他联系方式
     * @return other_contact 其他联系方式
     */
    public String getOtherContact() {
        return otherContact;
    }

    /**
     * 其他联系方式
     * @param otherContact 其他联系方式
     */
    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact == null ? null : otherContact.trim();
    }

    /**
     * 代理账号
     * @return agent 代理账号
     */
    public String getAgent() {
        return agent;
    }

    /**
     * 代理账号
     * @param agent 代理账号
     */
    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    /**
     * 邮编
     * @return zipcode 邮编
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 邮编
     * @param zipcode 邮编
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getBankswift() {
        return bankswift;
    }

    public void setBankswift(String bankswift) {
        this.bankswift = bankswift == null ? null : bankswift.trim();
    }

    /**
     * 银行名称
     * @return bankname 银行名称
     */
    public String getBankname() {
        return bankname;
    }

    /**
     * 银行名称
     * @param bankname 银行名称
     */
    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    /**
     * 银行地址
     * @return bankaddr 银行地址
     */
    public String getBankaddr() {
        return bankaddr;
    }

    /**
     * 银行地址
     * @param bankaddr 银行地址
     */
    public void setBankaddr(String bankaddr) {
        this.bankaddr = bankaddr == null ? null : bankaddr.trim();
    }

    /**
     * 银行开户省
     * @return province 银行开户省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 银行开户省
     * @param province 银行开户省
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 银行开户城市
     * @return city 银行开户城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 银行开户城市
     * @param city 银行开户城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 银行账号
     * @return bank_account 银行账号
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 银行账号
     * @param bankAccount 银行账号
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    /**
     * 会员类型
     * @return member_type 会员类型
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * 会员类型
     * @param memberType 会员类型
     */
    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    /**
     * 推广ID
     * @return ad_from 推广ID
     */
    public String getAdFrom() {
        return adFrom;
    }

    /**
     * 推广ID
     * @param adFrom 推广ID
     */
    public void setAdFrom(String adFrom) {
        this.adFrom = adFrom == null ? null : adFrom.trim();
    }

    /**
     * 状态
     * @return status 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    /**
     * 问题
     * @return question 问题
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 问题
     * @param question 问题
     */
    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    /**
     * 答案
     * @return answer 答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 答案
     * @param answer 答案
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * 金银贸易场编码
     * @return cgse_coding 金银贸易场编码
     */
    public String getCgseCoding() {
        return cgseCoding;
    }

    /**
     * 金银贸易场编码
     * @param cgseCoding 金银贸易场编码
     */
    public void setCgseCoding(String cgseCoding) {
        this.cgseCoding = cgseCoding == null ? null : cgseCoding.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 国家
     * @return country 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 国家
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getIecookie() {
        return iecookie;
    }

    public void setIecookie(String iecookie) {
        this.iecookie = iecookie == null ? null : iecookie.trim();
    }

    public String getMobileSec() {
        return mobileSec;
    }

    public void setMobileSec(String mobileSec) {
        this.mobileSec = mobileSec == null ? null : mobileSec.trim();
    }

    public String getIdNumberHidden() {
        return idNumberHidden;
    }

    public void setIdNumberHidden(String idNumberHidden) {
        this.idNumberHidden = idNumberHidden == null ? null : idNumberHidden.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 创建时间
     * @return ctime 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 创建时间
     * @param ctime 创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 修改时间
     * @return mtime 修改时间
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * 修改时间
     * @param mtime 修改时间
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDepositTime() {
		return depositTime;
	}

	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getPlatformMtime() {
		return platformMtime;
	}

	public void setPlatformMtime(Date platformMtime) {
		this.platformMtime = platformMtime;
	}
    
    
}