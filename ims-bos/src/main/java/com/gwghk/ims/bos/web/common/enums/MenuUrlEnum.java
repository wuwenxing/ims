package com.gwghk.ims.bos.web.common.enums;

/**
 * 摘要：菜单url
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年1月11日
 */
public enum MenuUrlEnum{
	 
	/**活动提案*/
	ACTSETTINGPROPOSAL("activitySettingController/index","活动提案"),
	/**活动维护**/
	ACTSETTINGMODIFY("activitySettingMgrController/index","活动维护");
	
	private final String value;
	private final String labelKey;
	
	MenuUrlEnum(String value, String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
 
}
