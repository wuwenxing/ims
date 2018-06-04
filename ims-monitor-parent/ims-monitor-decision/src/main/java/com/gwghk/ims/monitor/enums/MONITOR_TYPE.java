package com.gwghk.ims.monitor.enums;

import net.oschina.durcframework.easymybatis.handler.BaseEnum;

/**
 * 监控类型
 * @author jackson.tang
 *
 */
public enum MONITOR_TYPE implements BaseEnum<MONITOR_TYPE> {
	SYSTEM(1,"system"),PROCESS(2,"process"),BUSINESS(3,"business");
	private int val;
	private String name;
	MONITOR_TYPE(int val,String name){
		this.val=val;
		this.name=name;
	}
	
	public int getVal() {return this.val;}
	public String getName() {return this.name;}
	/**
	 * 通过值查找具体的枚举
	 * @param val
	 * @return
	 */
	public static MONITOR_TYPE findByVal(int val) {
		for(MONITOR_TYPE item : MONITOR_TYPE.values()){
			if(item.getVal()==val)
				return item;
		}
		
		return null;
	}

	@Override
	public MONITOR_TYPE getCode() {
		return this;
	}
}
