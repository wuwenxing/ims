package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：短信发送状态
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年1月31日
 */
public enum SmsStatusEnum {
    /**待发送:waiting*/
    waiting("waiting","待发送"),
    /**成功:success*/
    success("success","成功"),
    /**失败:fail*/
    fail("fail","失败");
	 
	private String value;
	private  String labelKey;
	
	SmsStatusEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}
	
	public static List<SmsStatusEnum> getList(){
		List<SmsStatusEnum> result = new ArrayList<SmsStatusEnum>();
		for(SmsStatusEnum ae : SmsStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String getLabelByValue(String value) {
		for(SmsStatusEnum  status: SmsStatusEnum.values()) {
			if(status.getValue().equalsIgnoreCase(value)){
				return status.getLabelKey() ;
			}
		}
		return null;
    }
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }
}