package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.util.CustomDateSerializer;
import com.gwghk.ims.common.vo.BaseVO;

public class ActItemsSettingVO  extends BaseVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4345943732044989604L;

	/**
     * 主键id
     */
    private Long id;

    /**
     * 物品编号
     */
    private String itemNumber;

    /**
     * 物品名称
     */
    private String itemName;

    /**
     * 物品库存数量
     */
    private Integer itemStockAmount;

    /**
     * 物品实际可用数量
     */
    private Integer itemUsableAmount;

    /**
     * 物品类型(来源于数据字典)
     */
    private String itemType;

    /**
     * 物品种类
     */
    private String itemCategory;
    
    

    /**
     *  物品(虚拟物品)该种类的价值数量（eg:话费20元)
     */
    private Integer itemCategoryAmount;

    /**
     *  物品价格
     */
    private BigDecimal itemPrice;

    /**
     *  物品单位
     */
    private String itemUnit;

    /**
     * 中奖概率(小数,如：0.2,即20%)
     */
    private BigDecimal itemRate;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    
    /**
     * 物品是否正在被使用 true:正在使用 ,false:未使用
     */
    private Boolean actUseFlag;
 
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
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
 
 
    public Boolean getActUseFlag() {
		return actUseFlag;
	}

	public void setActUseFlag(Boolean actUseFlag) {
		this.actUseFlag = actUseFlag;
	}

	/**
     * 当前物品是否是有效物品
     * @return
     */
    public boolean isEffectiveItem(){
        //1:是否启用的，未删除的
        if("Y".equals(this.getEnableFlag())&&"N".equals(this.getDeleteFlag())){
        	
            //2:数量是否足够(非代币模拟币贈金没有数量控制)
            boolean numFlag = false;
            if(ActItemTypeEnum.ANALOGCOIN.getValue().equals(this.getItemType())||ActItemTypeEnum.TOKENCOIN.getValue().equals(this.getItemType())||ActItemTypeEnum.WITHGOLD.getValue().equals(this.getItemType())){
                numFlag =true;
            }else if(this.getItemStockAmount()!=null&&this.getItemUsableAmount()!=null&&this.getItemStockAmount()>0&&this.getItemUsableAmount()>0){
                numFlag =true;
            }
        
            if(numFlag){
              //3时间是否有效(包括未来时间) ,时间未结束
                Date curTime = new Date();
                if(this.endDate!=null&&curTime.before(this.endDate)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String getItemTypeName(){
    	return ActItemTypeEnum.formatValue(this.getItemType());
    }
    public String getItemCategoryName(){
    	return ActItemCategoryEnum.format(this.getItemCategory());
    }
	@Override
	public String toString() {
		return "id=" + id + ", itemNumber=" + itemNumber
				+ ", itemName=" + itemName + ", itemStockAmount="
				+ itemStockAmount + ", itemUsableAmount=" + itemUsableAmount
				+ ", itemType=" + itemType + ", itemCategory=" + itemCategory
				+ ", itemCategoryAmount=" + itemCategoryAmount + ", itemPrice="
				+ itemPrice + ", itemUnit=" + itemUnit + ", itemRate="
				+ itemRate + ", endDate=" + endDate + ", startDate="
				+ startDate + ", actUseFlag=" + actUseFlag;
	}

	 
}