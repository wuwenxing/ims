package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.common.Kuak;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月21日  
* @version 1.0  
*/
public enum IsPrizeRecordEnum {

	/** 不可添加发放记录 */
	NO_RECORD(-1, "不可添加发放记录"),
	/** 等待添加发放记录*/
	WAIT_RECORD(0, "等待添加发放记录"),
	/** 等待发放时间后添加发放记录*/
	WAIT_TIME_RECORD(1, "等待发放时间后添加发放记录"),
	/** 完成添加发放记录*/
	FINISH_RECORD(2, "完成添加发放记录"),
	/** 添加等待中发放记录*/
	ADD_WAITING_RECORD(3, "添加等待中发放记录"),
  ;
 
	private final int key;
	private final String name;
 

	IsPrizeRecordEnum(int key, String name) {
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
