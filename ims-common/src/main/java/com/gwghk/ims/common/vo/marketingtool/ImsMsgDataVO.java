package com.gwghk.ims.common.vo.marketingtool;

import java.math.BigDecimal;

/**
 * 
 * 摘要：封装消息模板关键词对应的数据 
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月3日
 */

public class ImsMsgDataVO {

	/**活动名称*/
	private String activityName ;
	
	/**
     * 物品价格
     */
    private BigDecimal giftPrice;

    /**
     * 物品价格单位
     */
    private String giftPriceUnit;
    /**
     *物品名称
     */
    private String giftName;
    /**串码*/
    private String stringCode ;
    
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public BigDecimal getGiftPrice() {
		return giftPrice;
	}
	public void setGiftPrice(BigDecimal giftPrice) {
		this.giftPrice = giftPrice;
	}
	public String getGiftPriceUnit() {
		return giftPriceUnit;
	}
	public void setGiftPriceUnit(String giftPriceUnit) {
		this.giftPriceUnit = giftPriceUnit;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getStringCode() {
		return stringCode;
	}
	public void setStringCode(String stringCode) {
		this.stringCode = stringCode;
	}
	@Override
	public String toString() {
		return "ImsMsgDataVO [activityName=" + activityName + ", giftPrice=" + giftPrice + ", giftPriceUnit="
				+ giftPriceUnit + ", giftName=" + giftName + ", stringCode=" + stringCode + "]";
	}
	
}
