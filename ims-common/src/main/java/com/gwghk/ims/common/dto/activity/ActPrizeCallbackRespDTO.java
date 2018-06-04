package com.gwghk.ims.common.dto.activity;

public class ActPrizeCallbackRespDTO extends ActPrizeBaseReqDTO{
	/** 流水号 **/
	private String returnRecordNo;
	/** 返回结果信息 **/
	private String returnResult;
	/** 物品价格 **/
	private Double giftPrice;
	/** 物品价格单位 **/
	private String giftPriceUnit;
	/** BU处理结束时间 yyyy-MM-dd HH:mm:ss **/
	private String finishTime;

	public String getReturnRecordNo() {
		return returnRecordNo;
	}
	public void setReturnRecordNo(String returnRecordNo) {
		this.returnRecordNo = returnRecordNo;
	}
	public String getReturnResult() {
		return returnResult;
	}
	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}
	public Double getGiftPrice() {
		return giftPrice;
	}
	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}
	public String getGiftPriceUnit() {
		return giftPriceUnit;
	}
	public void setGiftPriceUnit(String giftPriceUnit) {
		this.giftPriceUnit = giftPriceUnit;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
}
