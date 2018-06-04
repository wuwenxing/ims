package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.common.Kuak;

/**
 * 摘要：发放方式 
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年5月11日
 */
public enum ActIssuingTypeEnum {
	/** 到点时间发放 */
	TO_TIME(1, "到点时间发放"),
	/** 增量发放*/
	INCREMENT(2, "增量发放"),
  ;
 
	private final int key;
	private final String name;
 

	ActIssuingTypeEnum(int key, String name) {
		this.key = key;
		this.name = name;
	}
 
	public static List<ActIssuingTypeEnum> getList() {
		List<ActIssuingTypeEnum> result = new ArrayList<ActIssuingTypeEnum>();
		for (ActIssuingTypeEnum ae : ActIssuingTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static List<Kuak> getOptions(){
		List<Kuak> options = new ArrayList<Kuak>();
		for(ActIssuingTypeEnum ae : ActIssuingTypeEnum.values()){
			options.add(new Kuak(String.valueOf(ae.getKey()), ae.getName()));
        }
		 return options;
	}
	 
	public static String getNameByKey(Integer key) {
		if (key != null) {
			for (ActIssuingTypeEnum ae : ActIssuingTypeEnum.values()) {
				if (key.equals(ae.getKey())) {
					return ae.getName();
				}
			}
		}
		return null;
	}
 
    public int getKey() {
        return key;
    }
 
    public String getName() {
        return name;
    }
 
}
