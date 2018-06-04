package com.gwghk.ims.activity.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.gwghk.ims.common.annotation.OrderBy;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.util.CustomDateSerializer;
 
public class ActItemsSetting  extends BaseEntity{
    /**
     * 主键id
     */
    private Long id;

    /**
     * 物品编号
     */
    @OrderBy(columnName = "item_number", propName = "itemNumber", order = "asc")
    private String itemNumber;

    /**
     * 物品名称
     */
    @OrderBy(columnName = "item_name", propName = "itemName", order = "asc")
    private String itemName;

    /**
     * 物品库存数量
     */
    @OrderBy(columnName = "item_stock_amount", propName = "itemStockAmount", order = "asc")
    private Integer itemStockAmount;

    /**
     * 物品实际可用数量
     */
    @OrderBy(columnName = "item_usable_amount", propName = "itemUsableAmount", order = "asc")
    private Integer itemUsableAmount;

    /**
     * 物品类型(来源于数据字典)
     */
    @OrderBy(columnName = "item_type", propName = "itemType", order = "asc")
    private String itemType;

    /**
     * 物品种类
     */
    @OrderBy(columnName = "item_category", propName = "itemCategory", order = "asc")
    private String itemCategory;

    /**
     *  物品(虚拟物品)该种类的价值数量（eg:话费20元)
     */
    @OrderBy(columnName = "item_category_amount", propName = "itemCategoryAmount", order = "asc")
    private Integer itemCategoryAmount;

    /**
     *  物品价格
     */
    @OrderBy(columnName = "item_price", propName = "itemPrice", order = "asc")
    private BigDecimal itemPrice;

    /**
     *  物品单位
     */
    @OrderBy(columnName = "item_unit", propName = "itemUnit", order = "asc")
    private String itemUnit;

    /**
     * 中奖概率(小数,如：0.2,即20%)
     */
    @OrderBy(columnName = "item_rate", propName = "itemRate", order = "asc")
    private BigDecimal itemRate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName = "end_date", propName = "endDate", order = "asc")
    private Date endDate;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @OrderBy(columnName = "start_date", propName = "startDate", order = "asc")
    private Date startDate;
 
    /**
     * 主键id
     * @return id 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键id
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 物品编号
     * @return item_number 物品编号
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * 物品编号
     * @param itemNumber 物品编号
     */
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    /**
     * 物品名称
     * @return item_name 物品名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 物品名称
     * @param itemName 物品名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * 物品库存数量
     * @return item_stock_amount 物品库存数量
     */
    public Integer getItemStockAmount() {
        return itemStockAmount;
    }

    /**
     * 物品库存数量
     * @param itemStockAmount 物品库存数量
     */
    public void setItemStockAmount(Integer itemStockAmount) {
        this.itemStockAmount = itemStockAmount;
    }

    /**
     * 物品实际可用数量
     * @return item_usable_amount 物品实际可用数量
     */
    public Integer getItemUsableAmount() {
        return itemUsableAmount;
    }

    /**
     * 物品实际可用数量
     * @param itemUsableAmount 物品实际可用数量
     */
    public void setItemUsableAmount(Integer itemUsableAmount) {
        this.itemUsableAmount = itemUsableAmount;
    }

    /**
     * 物品类型(来源于数据字典)
     * @return item_type 物品类型(来源于数据字典)
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * 物品类型(来源于数据字典)
     * @param itemType 物品类型(来源于数据字典)
     */
    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    /**
     * 物品种类
     * @return item_category 物品种类
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * 物品种类
     * @param itemCategory 物品种类
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory == null ? null : itemCategory.trim();
    }

    /**
     *  物品(虚拟物品)该种类的价值数量（eg:话费20元)
     * @return item_category_amount  物品(虚拟物品)该种类的价值数量（eg:话费20元)
     */
    public Integer getItemCategoryAmount() {
        return itemCategoryAmount;
    }

    /**
     *  物品(虚拟物品)该种类的价值数量（eg:话费20元)
     * @param itemCategoryAmount  物品(虚拟物品)该种类的价值数量（eg:话费20元)
     */
    public void setItemCategoryAmount(Integer itemCategoryAmount) {
        this.itemCategoryAmount = itemCategoryAmount;
    }

    /**
     *  物品价格
     * @return item_price  物品价格
     */
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    /**
     *  物品价格
     * @param itemPrice  物品价格
     */
    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     *  物品单位
     * @return item_unit  物品单位
     */
    public String getItemUnit() {
        return itemUnit;
    }

    /**
     *  物品单位
     * @param itemUnit  物品单位
     */
    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit == null ? null : itemUnit.trim();
    }

    /**
     * 中奖概率(小数,如：0.2,即20%)
     * @return item_rate 中奖概率(小数,如：0.2,即20%)
     */
    public BigDecimal getItemRate() {
        return itemRate;
    }

    /**
     * 中奖概率(小数,如：0.2,即20%)
     * @param itemRate 中奖概率(小数,如：0.2,即20%)
     */
    public void setItemRate(BigDecimal itemRate) {
        this.itemRate = itemRate;
    }

    /**
     * 结束时间
     * @return end_date 结束时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 开始时间
     * @return start_date 开始时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}