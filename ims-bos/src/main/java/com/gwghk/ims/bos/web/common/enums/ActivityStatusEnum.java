package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：物品类型
 * 
 * @author 
 * @version 1.0
 * @Date 2017年7月24日
 */
public enum ActivityStatusEnum implements EnumIntf {

	/** 启用 */
    ENABLE("启用", "Y"),
	/**禁用 */
    DISABLE ("禁用", "N"),
	/** 启用 */
	INVALID("无效", "invalid");

	private final String value;
	private final String labelKey;
	ActivityStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<ActivityStatusEnum> getList() {
		List<ActivityStatusEnum> result = new ArrayList<ActivityStatusEnum>();
		for (ActivityStatusEnum ae : ActivityStatusEnum.values()) {
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(ActivityStatusEnum ae : ActivityStatusEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

}
