package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

public class Demousers {
    /**
     * 唯一ID
     */
    private Integer id;

    /**
     * 模拟账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 平台
     */
    private String platform;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 区域
     */
    private String area;

    /**
     * 区号
     */
    private String countryCode;

    /**
     * QQ
     */
    private String qq;

    /**
     * 类型
     */
    private String type;

    /**
     * 统计代码
     */
    private String adFrom;

    /**
     * cookie
     */
    private String cookie;

    /**
     * 转换为会员
     */
    private String becomeMember;

    /**
     * 手机隐藏
     */
    private String mobileSec;

    /**
     * ip
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date ctime;

    private Date mtime;
    /**
     * 手机号码
     */
    private String mobile;
    
    /**
     * 电子邮箱
     */
    private String email;
    
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
     * 模拟账号
     * @return username 模拟账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 模拟账号
     * @param username 模拟账号
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 平台
     * @return platform 平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 平台
     * @param platform 平台
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 区域
     * @return area 区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 区域
     * @param area 区域
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 区号
     * @return country_code 区号
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 区号
     * @param countryCode 区号
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    /**
     * QQ
     * @return qq QQ
     */
    public String getQq() {
        return qq;
    }

    /**
     * QQ
     * @param qq QQ
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 统计代码
     * @return ad_from 统计代码
     */
    public String getAdFrom() {
        return adFrom;
    }

    /**
     * 统计代码
     * @param adFrom 统计代码
     */
    public void setAdFrom(String adFrom) {
        this.adFrom = adFrom == null ? null : adFrom.trim();
    }

    /**
     * cookie
     * @return cookie cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * cookie
     * @param cookie cookie
     */
    public void setCookie(String cookie) {
        this.cookie = cookie == null ? null : cookie.trim();
    }

    /**
     * 转换为会员
     * @return become_member 转换为会员
     */
    public String getBecomeMember() {
        return becomeMember;
    }

    /**
     * 转换为会员
     * @param becomeMember 转换为会员
     */
    public void setBecomeMember(String becomeMember) {
        this.becomeMember = becomeMember == null ? null : becomeMember.trim();
    }

    /**
     * 手机隐藏
     * @return mobile_sec 手机隐藏
     */
    public String getMobileSec() {
        return mobileSec;
    }

    /**
     * 手机隐藏
     * @param mobileSec 手机隐藏
     */
    public void setMobileSec(String mobileSec) {
        this.mobileSec = mobileSec == null ? null : mobileSec.trim();
    }

    /**
     * ip
     * @return ip ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
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
    
    
}