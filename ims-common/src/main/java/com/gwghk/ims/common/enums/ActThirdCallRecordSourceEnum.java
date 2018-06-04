package com.gwghk.ims.common.enums;

/**
 * 
* @ClassName: ActThirdCallRecordSourceEnum
* @Description: 第三方发放记录来源
* @author lawrence
* @date 2018年1月8日
*
 */
public enum ActThirdCallRecordSourceEnum {
	ACT_PRIZE("act_prize", "模板发放记录来源"),
	ACT_DEMO_PRIZE("act_demo_prize", "模拟发放记录来源"),
	ACT_TEMP_PRIZE_RECORD("act_temp_prize", "定制发放记录来源");
	
	private final String code;
	private final String name;

	ActThirdCallRecordSourceEnum(String _code, String name) {
		this.code = _code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}