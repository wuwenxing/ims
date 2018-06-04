package com.gwghk.ims.common.enums;

/**
 * 
 * @ClassName: ActTradeType
 * @Description: 交易方向
 * @author lawrence
 * @date 2017年7月19日
 *
 */
public enum ActTradeTypeEnum {
	/**开仓*/
	IN("in", "开仓"),
	/**平仓*/
	OUT("out", "平仓");

	private final String value;
	private final String labelKey;

	ActTradeTypeEnum(String value, String labelKey) {
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