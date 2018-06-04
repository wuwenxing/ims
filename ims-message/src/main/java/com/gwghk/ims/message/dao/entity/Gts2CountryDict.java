package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2CountryDict {
    /**
     * table id, 這ID在這TABLE沒有任何用處
     */
    private Long id;

    /**
     * 國家ID，ISO開頭的就是國家，省份關聯國家是用到的
     */
    private String code;

    /**
     * 如果是-1，即是國家，如是正數，即是省份/城市，而當中的parent_country_id就是所屬的國家ID
     */
    private String parentCode;

    /**
     * 國家的2個英文字母簡稱
     */
    private String nationalCode;

    /**
     * 國家區號/電話區號, 86/852等等
     */
    private String countryCode;

    /**
     * 是否有效，默认有效
     */
    private Integer valid;

    /**
     * 简体名称
     */
    private String nameCn;

    /**
     * 繁体名称
     */
    private String nameTw;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 排序
     */
    private Integer sort;

    private String createUser;

    private String createIp;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String updateUser;

    private String updateIp;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private Integer versionNo;

    /**
     * table id, 這ID在這TABLE沒有任何用處
     * @return id table id, 這ID在這TABLE沒有任何用處
     */
    public Long getId() {
        return id;
    }

    /**
     * table id, 這ID在這TABLE沒有任何用處
     * @param id table id, 這ID在這TABLE沒有任何用處
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 國家ID，ISO開頭的就是國家，省份關聯國家是用到的
     * @return code 國家ID，ISO開頭的就是國家，省份關聯國家是用到的
     */
    public String getCode() {
        return code;
    }

    /**
     * 國家ID，ISO開頭的就是國家，省份關聯國家是用到的
     * @param code 國家ID，ISO開頭的就是國家，省份關聯國家是用到的
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 如果是-1，即是國家，如是正數，即是省份/城市，而當中的parent_country_id就是所屬的國家ID
     * @return parent_code 如果是-1，即是國家，如是正數，即是省份/城市，而當中的parent_country_id就是所屬的國家ID
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 如果是-1，即是國家，如是正數，即是省份/城市，而當中的parent_country_id就是所屬的國家ID
     * @param parentCode 如果是-1，即是國家，如是正數，即是省份/城市，而當中的parent_country_id就是所屬的國家ID
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /**
     * 國家的2個英文字母簡稱
     * @return national_code 國家的2個英文字母簡稱
     */
    public String getNationalCode() {
        return nationalCode;
    }

    /**
     * 國家的2個英文字母簡稱
     * @param nationalCode 國家的2個英文字母簡稱
     */
    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode == null ? null : nationalCode.trim();
    }

    /**
     * 國家區號/電話區號, 86/852等等
     * @return country_code 國家區號/電話區號, 86/852等等
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 國家區號/電話區號, 86/852等等
     * @param countryCode 國家區號/電話區號, 86/852等等
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    /**
     * 是否有效，默认有效
     * @return valid 是否有效，默认有效
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * 是否有效，默认有效
     * @param valid 是否有效，默认有效
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * 简体名称
     * @return name_cn 简体名称
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * 简体名称
     * @param nameCn 简体名称
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    /**
     * 繁体名称
     * @return name_tw 繁体名称
     */
    public String getNameTw() {
        return nameTw;
    }

    /**
     * 繁体名称
     * @param nameTw 繁体名称
     */
    public void setNameTw(String nameTw) {
        this.nameTw = nameTw == null ? null : nameTw.trim();
    }

    /**
     * 英文名称
     * @return name_en 英文名称
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * 英文名称
     * @param nameEn 英文名称
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    /**
     * 排序
     * @return sort 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
}