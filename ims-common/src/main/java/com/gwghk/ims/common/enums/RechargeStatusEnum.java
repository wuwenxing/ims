package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：充值状态类型定义
 */
public enum RechargeStatusEnum {

	sendFail("充值失败", "sendFail"),
	sendIn("充值中", "sendIn"),
	sendSuccess("充值成功", "sendSuccess");
	
	private final String value;
	private final String labelKey;
	RechargeStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<RechargeStatusEnum> getList(){
		List<RechargeStatusEnum> result = new ArrayList<RechargeStatusEnum>();
		for(RechargeStatusEnum ae : RechargeStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(RechargeStatusEnum ae : RechargeStatusEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
}
