package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：短信渠道
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年1月23日
 */
public enum SmsChannelEnum {
	/**ucweb*/
	UCWEB("ucweb","ucweb");
	
	private final String code;
	private final String label;
	
	SmsChannelEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public static List<SmsChannelEnum> getList(){
        List<SmsChannelEnum> result = new ArrayList<SmsChannelEnum>();
        for(SmsChannelEnum ae : SmsChannelEnum.values()){
            result.add(ae);
        }
        return result;
    }
	
	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
	
    public static String getLabelByCode(String code) {
		for(SmsChannelEnum  channel: SmsChannelEnum.values()) {
			if(channel.getCode().equalsIgnoreCase(code)){
				return channel.getLabel();
			}
		}
		return null;
    }
	
	public static boolean isExsit(String code){
		if(null == code || "".equals(code)){
			return false;
		}
		for(SmsChannelEnum obj : SmsChannelEnum.values()){
			if(obj.getCode().equalsIgnoreCase(code)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotExsit(String code){
		return !isExsit(code);
	}
}
