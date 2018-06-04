package com.gwghk.ims.common.enums;

/**
 * 摘要：频率时间枚举
 * @author eva
 * @version 1.0
 * @Date 2018年4月4日
 */

public enum FrequencyTimeUnitEnum {
    /**
     * 天
     */
    HOUR("hour","小时"),
	/**
	 * 天
	 */
    DAY("day","天"),
    /**
     * 周
     */
    WEEK("week","周");
	 
	
	private String value;
	private  String labelKey;
	FrequencyTimeUnitEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
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
