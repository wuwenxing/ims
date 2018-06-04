package com.gwghk.ims.activity.enums;

/**
 * 摘要：任务类型
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月24日
 */
public enum ActTaskEnum {
	/**系统任务*/
	taskSystem("系统任务", 2),
	/**自定义任务*/
	taskCustom("自定义任务", 1),
	;
	
	private String name;
	private Integer code;
	
	ActTaskEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
}