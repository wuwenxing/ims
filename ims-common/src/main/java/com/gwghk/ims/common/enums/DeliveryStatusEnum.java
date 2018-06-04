package com.gwghk.ims.common.enums;

public enum DeliveryStatusEnum {
// 0 待发放 1 发放中 2 已取消 3 已出库
	/** 待发放 */
	issuing(0, "待发放"),
	/** 发放中 */
	in_distributed(1, "发放中"),
	/** 已取消 */
	cancel(2, "已取消"),
	/** 已出库*/
	out_library(3, "已出库");
	
	private final String name;
	private final Integer code;

	DeliveryStatusEnum(Integer code, String name) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
	
	public static String format(String code){
		for(DeliveryStatusEnum ae : DeliveryStatusEnum.values()){
			if(ae.getCode().equals(code)){
				return ae.getName() ;
			}
		}
		return code;
	}
	
}
