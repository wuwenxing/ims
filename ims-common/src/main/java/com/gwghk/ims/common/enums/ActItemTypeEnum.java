package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：物品类型
 * @author 
 * @version 1.0
 * @Date 2017年7月24日
 */
public enum ActItemTypeEnum {

	/** 实物物品 */
	REAL("real","实物物品"),
	/** 虚拟物品 */
	VIRTUAL("virtual","虚拟物品"),
	/** 接口物品 */
	INTERFACE("interface","接口物品"),
	/** 模拟币 */
	ANALOGCOIN("analogCoin","模拟币"),
	/** 代币 */
	TOKENCOIN("tokenCoin","代币"),
	/** 赠金 */
	WITHGOLD("withGold","赠金");

	private final String value;
	private final String code;
	ActItemTypeEnum(String value, String code) {
		this.value = value;
		this.code = code;
	}

	public static List<ActItemTypeEnum> getList() {
		List<ActItemTypeEnum> result = new ArrayList<ActItemTypeEnum>();
		for (ActItemTypeEnum ae : ActItemTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}
	
	public static String formatValue(String value){
		if(StringUtil.isNotEmpty(value)){
			for(ActItemTypeEnum ae : ActItemTypeEnum.values()){
				if(ae.getValue().equals(value)){
					return ae.getCode() ;
				}
			}
			return value;
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

}
