package com.gwghk.ims.common.enums;

public enum ActWaitingPrizeStatusEnum {
	ready_send(0,"准备发送"),
	inventory_reason(1,"数量不足"),
	invalid_reason(2,"禁用"),
	not_start_reason(8,"时间未到");
	
	private int value;
	private String desc;
	ActWaitingPrizeStatusEnum(int value,String desc) {
		this.value=value;
		this.desc=desc;
	}
	
	public int getValue() {return this.value;}
	public String getDesc() {return this.desc;}
}
