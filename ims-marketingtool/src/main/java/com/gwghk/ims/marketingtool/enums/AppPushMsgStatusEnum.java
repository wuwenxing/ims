package com.gwghk.ims.marketingtool.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：APP消息推送状态
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月4日
 */
public enum AppPushMsgStatusEnum {
    /**待发送:waiting*/
    waiting("待发送","waiting"),
    /**发送中:sending*/
    sending("发送中","sending"),
    /**成功:success*/
    success("成功","success"),
    /**失败:fail*/
    fail("失败","fail");
	
	private  String labelKey;
	private String value;
	
	AppPushMsgStatusEnum(String labelKey,String value){
		this.labelKey = labelKey;
		this.value = value;
	}

	public static List<AppPushMsgStatusEnum> getList(){
		List<AppPushMsgStatusEnum> result = new ArrayList<AppPushMsgStatusEnum>();
		for(AppPushMsgStatusEnum ae : AppPushMsgStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String getLabelKeyByValue(String value){
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		}
		for(AppPushMsgStatusEnum ae : AppPushMsgStatusEnum.values()){
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