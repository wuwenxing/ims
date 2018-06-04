package com.gwghk.ims.common.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 摘要：币种-枚举类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年7月4日
 */
public enum CurrencyEnum {
	HKD("HKD", "港币"),
	CNY("CNY", "人民币"),
	USD("USD", "美元"),
	VND("OTHER", "其他");

	private final String code;
	private final String name;
	
	CurrencyEnum(String _code, String name) {
		this.code = _code;
		this.name = name;
	}

	public String getValue() {
        return this.code;
    }
	public String getLabelKey() {
        return this.name;
    }
	
	/**
	 * 
	 * 将枚举转换为map
	 * @return
	 */
	public static Map<String, Object> toMap(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		CurrencyEnum[] currencys = CurrencyEnum.values();
    	for(CurrencyEnum currency : currencys){
    		map.put(currency.getValue(), currency.getLabelKey());
    	}
    	return map;
	}
}