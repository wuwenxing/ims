package com.gwghk.ims.marketingtool.enums;

/**  
* 摘要:   推送设备
* @author George.li  
* @date 2018年4月24日  
* @version 1.0  
*/
public enum AppPushDevicesEnum {

	ios("ios","ios"),
	android("android","android"),
	pcui("pcui","pcui"),
	webui("webui","webui");
	
	private String value;
	private  String labelKey;
	
	AppPushDevicesEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
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
