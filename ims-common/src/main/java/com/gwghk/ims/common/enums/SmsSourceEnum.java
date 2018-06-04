package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：短信来源
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月8日
 */
public enum SmsSourceEnum {
	/**发放记录 */
	PRIZE_RECORD("prize_record", "发放记录"),
	/**人工补发*/
	ARTIFICIAL_REISSUE("artificial_reissue", "人工补发");

	private final String value;
	private final String labelKey;

	SmsSourceEnum(String value, String labelKey) {
		this.value = value;
		this.labelKey = labelKey;
	}

	public static String getLabelByValue(String value) {
		for (SmsSourceEnum source : SmsSourceEnum.values()) {
			if (source.getValue().equalsIgnoreCase(value)) {
				return source.getLabelKey();
			}
		}
		return null;
	}

	public static List<SmsSourceEnum> getList() {
		List<SmsSourceEnum> result = new ArrayList<SmsSourceEnum>();
		for (SmsSourceEnum ae : SmsSourceEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}