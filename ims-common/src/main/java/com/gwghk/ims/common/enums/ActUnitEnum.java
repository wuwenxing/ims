package com.gwghk.ims.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 摘要：任务参数单位
 * @author eva
 * @version 1.0
 * @Date 2017年10月26日
 */
public enum ActUnitEnum {
	/**次*/
    TIMES("TIMES","次"),
    /**手*/
    LOT("LOT","手"),
    /**小时*/
    HOUR("HOUR","小时"),
    /**小时*/
    MONTH("MONTH","月"),
    /**天*/
    DAY("DAY","天"),
    /**人民币*/
    CNY("CNY","元"),
    /**人民币*/
    RMB("RMB","元"),
    /**美元*/
    USD("USD","美元"),
    /**%*/
    PERCENTAGE("%","%"),
    /**个*/
    NUM("NUM","个");
	
	private String code;
	private  String name;
	
	ActUnitEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
 
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public static String getFormatCode(String code){
        if(StringUtils.isNotEmpty(code)){
            for(ActUnitEnum ae : ActUnitEnum.values()){
                if(code.equals(ae.getCode())){
                    return ae.getName();
                }
            }
        }
        return null;
    }
}