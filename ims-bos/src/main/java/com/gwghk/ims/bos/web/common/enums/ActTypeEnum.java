package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：活动类型
 * @author eva
 * @version 1.0
 * @Date 2017年6月14日
 */

public enum ActTypeEnum {
	
	RW("任务型", "rw"),
	WPDH("物品兑换型", "wpdh"),
	QT("其他", "qt");
	 
	
	private String value;
	private  String labelKey;
	ActTypeEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}

	public static List<ActTypeEnum> getList() {
		List<ActTypeEnum> result = new ArrayList<ActTypeEnum>();
		for (ActTypeEnum ae : ActTypeEnum.values()) {
			result.add(ae);
		}
		return result;
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
