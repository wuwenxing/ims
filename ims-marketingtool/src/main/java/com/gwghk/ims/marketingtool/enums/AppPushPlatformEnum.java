package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：推送消息到哪个app id
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月15日
 */
public enum AppPushPlatformEnum {
	/**fx gts2 app*/
	GWFX_GTS2_APP("FX GTS2 APP","GWFX_GTS2_APP"),
    /**hx gts2 app*/
	HX_GTS2_APP("HX GTS2 APP","HX_GTS2_APP"),
	/**FXDEMO GTS2 APP*/
	FXDEMO_GTS2_APP("FXDEMO GTS2 APP","FXDEMO_GTS2_APP"),
    /**pm gts2 app*/
	PM_GTS_APP("PM GTS APP","PM_GTS_APP");

	private  String labelKey;
	private String value;
	
	
	AppPushPlatformEnum(String labelKey,String value){
		this.labelKey = labelKey;
		this.value = value;
	}

	public static List<AppPushPlatformEnum> getList(){
		List<AppPushPlatformEnum> result = new ArrayList<AppPushPlatformEnum>();
		for(AppPushPlatformEnum ae : AppPushPlatformEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String getLabelKeyByValue(String value){
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		}
		for(AppPushPlatformEnum ae : AppPushPlatformEnum.values()){
			if(ae.getValue().equals(value.toString())){
				return ae.getLabelKey();
			}
		}
		return "";
	}
	
	public static String getLabelKeyByValues(String values){
		if(StringUtil.isNullOrEmpty(values)){
			return "";
		}
		String labels = "";
		for(AppPushPlatformEnum ae : AppPushPlatformEnum.values()){
			if(values.contains(ae.getValue())){
				labels += ae.getLabelKey()+",";
			}
		}
		if(labels.length() > 1){
			return labels.substring(0,labels.length()-1);
		}
		return "" ;
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
