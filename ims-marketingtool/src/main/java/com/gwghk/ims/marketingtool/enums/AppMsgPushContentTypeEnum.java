package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：App消息推送内容类型
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月6日
 */
public enum AppMsgPushContentTypeEnum {
    /**图片:1*/
    img("图片","1"),
    /**图文:2*/
    imgtext("图文","2"),
    /**other:3*/
    other("其他","3");
	
	private  String labelKey;
	private String value;
	
	AppMsgPushContentTypeEnum(String labelKey,String value){
		this.labelKey = labelKey;
		this.value = value;
	}

	public static List<AppMsgPushContentTypeEnum> getList(){
		List<AppMsgPushContentTypeEnum> result = new ArrayList<AppMsgPushContentTypeEnum>();
		for(AppMsgPushContentTypeEnum ae : AppMsgPushContentTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String getLabelKeyByValue(Integer value){
		if(null == value){
			return "";
		}
		for(AppMsgPushContentTypeEnum ae : AppMsgPushContentTypeEnum.values()){
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