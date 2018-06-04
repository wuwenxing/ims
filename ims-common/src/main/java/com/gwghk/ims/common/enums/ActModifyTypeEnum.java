package com.gwghk.ims.common.enums;

/**
 * 摘要：活动修改的类型
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月14日
 */

public enum ActModifyTypeEnum {
	
	BAESETTING("baseSetting","基础设置"),
	CUSTCONDITIONS("custConditions","账户参与条件"),
	TASK("task","任务"),
	ITEM("item","奖励物品");
	 
	
	private String value;
	private  String labelKey;
	ActModifyTypeEnum(String value,String labelKey){
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
