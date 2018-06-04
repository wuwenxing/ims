package com.gwghk.ims.common.enums;

/**
 * 
 * @ClassName: ActAccountLevel
 * @Description: 账户类型
 * @author eva.liu
 * @date 2018年4月4日
 *
 */
public enum ActAccountLevelEnum {
	/**迷你*/
	MINI("MINI", "迷你"),
	/**标准*/
	STANDARD("standard", "标准"),
	/**VIP账户*/
	VIP("vip", "vip账户");

	private final String code;
	private final String name;

	ActAccountLevelEnum(String _code, String name) {
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