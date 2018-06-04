package com.gwghk.ims.common.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
* @ClassName: ActThirdCallEnum
* @Description: 第三方调用结果类型
* @author lawrence
* @date 2017年10月30日
*
 */
public enum ActThirdCallEnum {
	BONUS_ADD_BONUS("bonus", "addBonus", "新增奖励金额"),
	BONUS_RELEASE_BONUS("bonus", "releaseBonus", "释放奖励金额"),
	BONUS_CANCEL_BONUS("bonus", "cancelBonus", "扣回奖励金额");

	private String type;
	private String code;
	private String labelKey;

	ActThirdCallEnum(String type, String code, String labelKey) {
		this.type = type;
		this.code = code;
		this.labelKey = labelKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}
	/**
	 * 
	 * 将枚举转换为map
	 * @return
	 */
	public static Map<String, Object> toMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		ActThirdCallEnum[] actThirdCallEnum = ActThirdCallEnum.values();
    	for(ActThirdCallEnum actThirdCall : actThirdCallEnum){
    		map.put(actThirdCall.getCode(), actThirdCall.getLabelKey());
    	}
    	return map;
	}
}