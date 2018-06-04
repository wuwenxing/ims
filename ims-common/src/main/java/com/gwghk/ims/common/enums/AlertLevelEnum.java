package com.gwghk.ims.common.enums;

/**
 * 监控报警级别定义
 * @author jackson.tang
 *
 */
public enum AlertLevelEnum {
	FATAL(50,"致命"),WARN(40,"警告");
	private int value;
	private String text;
	private AlertLevelEnum(int value,String text) {
		this.value=value;
		this.text=text;
	}
	
	public String text(){return text;}
	public int value(){return value;}
	
	public static AlertLevelEnum find(int value) {
		for(AlertLevelEnum item:AlertLevelEnum.values()) {
			if(item.value()==value)
				return item;
		}
		
		return null;
	}
}
