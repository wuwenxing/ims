package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：App绑定消息类型
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月21日
 */
public enum AppMsgBindTypeEnum {
	/**物品*/
	bind_item("bind_item","物品"),
    /**活动*/
	bind_activity("bind_activity","活动");
	 
	private String value;
	private  String labelKey;
	
	AppMsgBindTypeEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}

	public static List<AppMsgBindTypeEnum> getList(){
		List<AppMsgBindTypeEnum> result = new ArrayList<AppMsgBindTypeEnum>();
		for(AppMsgBindTypeEnum ae : AppMsgBindTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String getLabelKeyByValue(String value){
		if(null == value){
			return "";
		}
		for(AppMsgBindTypeEnum ae : AppMsgBindTypeEnum.values()){
			if(ae.getValue().equals(value.toString())){
				return ae.getLabelKey();
			}
		}
		return "";
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
