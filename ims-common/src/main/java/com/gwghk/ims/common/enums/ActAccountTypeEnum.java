package com.gwghk.ims.common.enums;

/**
 * 
 * @ClassName: ActAccountType
 * @Description: 摘要：活动账户类型
 * @author lawrence
 * @date 2017年5月24日
 *
 */
public enum ActAccountTypeEnum {
	/** 无限制 */
	NOLIMIT("type_noLimit", "无限制", ""),
	/** 模拟账户 */
	DEMO("type_demo", "模拟账户", "demo"),
	/** 真实账户 */
	REAL("type_real", "真实账户", "real");

	private final String code;
	private final String name;
	private final String aliasCode;

	ActAccountTypeEnum(String _code, String name, String aliasCode) {
		this.code = _code;
		this.name = name;
		this.aliasCode = aliasCode;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getAliasCode() {
		return aliasCode;
	}
}