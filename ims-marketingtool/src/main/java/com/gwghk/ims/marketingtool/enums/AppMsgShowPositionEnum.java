package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：App消息展示位置
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月6日
 */
public enum AppMsgShowPositionEnum {
    /**弹出窗:1*/
    alert("弹出窗","1"),
    /**跑马灯:2*/
    horse("跑马灯","2"),
    /**工具栏:3*/
    toolbar("工具栏","3");
	
	private  String labelKey;
	private String value;
	
	AppMsgShowPositionEnum(String labelKey,String value){
		this.labelKey = labelKey;
		this.value = value;
	}

	public static List<AppMsgShowPositionEnum> getList(){
		List<AppMsgShowPositionEnum> result = new ArrayList<AppMsgShowPositionEnum>();
		for(AppMsgShowPositionEnum ae : AppMsgShowPositionEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String getLabelKeyByValues(String values){
		if(StringUtil.isNullOrEmpty(values)){
			return "";
		}
		String labels = "";
		for(AppMsgShowPositionEnum ae : AppMsgShowPositionEnum.values()){
			if(values.contains(ae.getValue())){
				labels += ae.getLabelKey()+",";
			}
		}
		return labels.substring(0,labels.length()-1);
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