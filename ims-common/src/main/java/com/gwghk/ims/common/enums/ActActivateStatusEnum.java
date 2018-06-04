package com.gwghk.ims.common.enums;

/**
 * 
 * @ClassName: ActActivateStatusEnum
 * @Description: 客户激活状态
 * @author eva.liu
 * @date 2018年4月11日
 *
 */
public enum ActActivateStatusEnum {
	/**未激活*/
	INACTIVATED("inactivated", "未激活"),
	/**已激活*/
	ACTIVATED("activated", "已激活");
	

	private final String code;
	private final String name;

	ActActivateStatusEnum(String _code, String name) {
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