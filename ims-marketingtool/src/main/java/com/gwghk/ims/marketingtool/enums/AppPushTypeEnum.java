package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：App 推送类型 (all/tag/alias/device)
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月17日
 */
public enum AppPushTypeEnum {
	/**推送所有人*/
	all("all","推送所有人指定设备(all)"),
    /**推送指定分组的人*/
	tag("tag","指定分组的人(tag)"),
	/**推送指定人*/
	alias("alias","指定人(alias)"),
    /**推送指定设备*/
	device("device","指定设备(device)");
	 
	private String value;
	private  String labelKey;
	
	AppPushTypeEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}

	public static List<AppPushTypeEnum> getList(){
		List<AppPushTypeEnum> result = new ArrayList<AppPushTypeEnum>();
		for(AppPushTypeEnum ae : AppPushTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String getLabelKeyByValue(String value){
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		}
		for(AppPushTypeEnum ae : AppPushTypeEnum.values()){
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
