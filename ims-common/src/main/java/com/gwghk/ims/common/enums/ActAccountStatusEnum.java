package com.gwghk.ims.common.enums;

/**
 * 
 * @ClassName: ActAccountStatus
 * @Description: 客户账号状态
 * @author eva.liu
 * @date 2018年4月4日
 *
 */
public enum ActAccountStatusEnum {
	/**启用*/
	A("A", "启用"),
	/**禁用*/
	S("S", "禁用"),
	/**注销*/
	D("D", "注销");
	

	private final String code;
	private final String name;

	ActAccountStatusEnum(String _code, String name) {
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