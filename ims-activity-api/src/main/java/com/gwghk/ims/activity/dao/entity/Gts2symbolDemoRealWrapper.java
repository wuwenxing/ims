package com.gwghk.ims.activity.dao.entity;


/**
 * 摘要：交易产品视图对象
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月14日
 */
public class Gts2symbolDemoRealWrapper  {
    private Long id;
    
    /**
     * 交易产品编号
     */
    private String code;

    /**
     * 交易产品名称
     */
    private String name;

    /**
     * 是否为模拟账户的
     */
    private Boolean isDemo;
    
    private Integer type;
    
    private String typeName;
    
    /**
     * 公司id
     */
    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Boolean isDemo) {
        this.isDemo = isDemo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
 
}