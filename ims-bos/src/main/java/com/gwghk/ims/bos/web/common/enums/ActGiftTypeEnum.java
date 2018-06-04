package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：物品类型
 * 
 * @author 
 * @version 1.0
 * @Date 2017年7月24日
 */
public enum ActGiftTypeEnum implements EnumIntf {

	/** 实物物品 */
	REAL("实物物品", "real"),
	/** 虚拟物品 */
	VIRTUAL("虚拟物品", "virtual"),
	/** 虚拟物品 */
	INTERFACE("接口物品", "interface"),
	/** 模拟币 */
	ANALOGCOIN("模拟币", "analogCoin"),
	/** 代币 */
	TOKENCOIN("代币", "tokenCoin"),
	/** 赠金 */
	WITHGOLD("赠金", "withGold");

	private final String value;
	private final String labelKey;
	ActGiftTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<ActGiftTypeEnum> getList() {
		List<ActGiftTypeEnum> result = new ArrayList<ActGiftTypeEnum>();
		for (ActGiftTypeEnum ae : ActGiftTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(ActGiftTypeEnum ae : ActGiftTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

}
