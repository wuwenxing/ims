package com.gwghk.ims.activity.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

public class ActProduct extends BaseEntity {
    private Long id;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 产品编码
     * @return product_code 产品编码
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 产品编码
     * @param productCode 产品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    /**
     * 产品名称
     * @return product_name 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 产品名称
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

}