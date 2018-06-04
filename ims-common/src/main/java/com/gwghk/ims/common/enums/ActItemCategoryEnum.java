package com.gwghk.ims.common.enums;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：物品种类
 * @author eva
 * @version 1.0
 * @Date 2017年7月17日
 */
public enum ActItemCategoryEnum {
	/**流量*/
    MOBILEDATA("mobile_data","流量"),
    /**话费*/
    MOBILECHARGE("mobile_charge","话费"),
    /**会员*/
    MEMBERVIP("member_vip","会员"),
    /**串码物品*/
    STRINGCODE("string_code","串码物品");
	 
	
 
	private String value;
	private  String labelKey;
	ActItemCategoryEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}
	
	public static String format(String value){
		if(StringUtil.isNotEmpty(value)){
			for(ActItemCategoryEnum ae : ActItemCategoryEnum.values()){
				if(ae.getValue().equals(value)){
					return ae.getLabelKey();
				}
			}
			return value;
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
