package com.gwghk.ims.common.enums;


/**
 * 摘要：串码状态
 * 
 * @author eva
 * @version 1.0
 * @Date 2018年1月16日
 */
public enum ActStringCodeStatusEnum {
 
	/** 未使用 */
    NOTUSED(1, "未使用"),
    /** 已使用*/
    USED(2, "已使用"),
	;

	private final int value;
	private final String name;

	ActStringCodeStatusEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
 
	public int getValue() {
        return value;
    }
 
    public String getName() {
		return name;
	}
    
    public static String getNameByValue(Integer value) {
        if (value != null) {
            for (ActStringCodeStatusEnum ae : ActStringCodeStatusEnum.values()) {
                if (value.equals(ae.getValue())) {
                    return ae.getName();
                }
            }
        }
        return null;
    }

}
