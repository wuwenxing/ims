package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：App消息展示位置
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月6日
 */
public enum AppMsgTypeEnum {
    /**营销消息1*/
    marketing(1,"营销消息"),
    /**预警消息2*/
    warning(2,"预警消息"),
    ;
	
	private Integer code ;
	private String value;
	
	AppMsgTypeEnum(Integer code,String value){
		this.code = code;
		this.value = value;
	}

	
	public static String getValueByCode(Integer code){
		if(null == code){
			return "";
		}
		for(AppMsgTypeEnum ae : AppMsgTypeEnum.values()){
			if(ae.getCode().equals(code)){
				return ae.getValue() ;
			}
		}
		return "" ;
	}
	
	public String getValue() {
		return value;
	}

	public Integer getCode() {
		return code;
	}

	
}