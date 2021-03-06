package com.gwghk.ims.bos.web.common.enums;


/**
 * 摘要：活动规则类型
 * 
 * @author eva
 * @version 1.0
 * @Date 2017年5月24日
 */
public enum ActRuleType {
	 
	/** 客户信息 */
	CUSTINFO("act_custInfo", "客户信息"),
	/** 交易信息 */
	TRADE("act_trade", "交易信息"),
	/** 出入金信息 */
	GOLDS("act_golds", "出入金信息");

	private final String code;
	private final String name;

	ActRuleType(String _code, String name) {
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
