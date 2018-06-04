package com.gwghk.ims.activity.enums;

/**
 * @ClassName: ActWaitSendReasonEnum
 * @Description: 应发记录原因
 * @author eva
 * @date 2017年8月9日
 */
public enum ActWaitSendReasonEnum {
	/**禁用*/
    DISABLED("Disabled",1, "禁用"),
    /**数量不足*/
    NOTENOUGH("notEnough", 2,"数量不足"),
    /**时间过期*/
    TIMEEXPIRED("timeExpired",4, "时间过期"),
    /**时间未到*/
    TIMENOTREACHED("timeNotReached", 8,"时间未到");

	private final String code;//代号
	private final int codeNum;//代号数
	private final String name;//代号描述

	ActWaitSendReasonEnum(String _code,int _codeNum, String name) {
		this.code = _code;
		this.codeNum = _codeNum;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

    public int getCodeNum() {
        return codeNum;
    }
}