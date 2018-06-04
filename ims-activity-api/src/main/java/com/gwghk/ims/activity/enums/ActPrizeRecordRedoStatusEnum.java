package com.gwghk.ims.activity.enums;

/**
 * 
 * @ClassName: ActPrizeRecordRedoStatus
 * @Description: 摘要：重发状态
 * @author eva
 * @date 2017年12月1日
 *
 */
public enum ActPrizeRecordRedoStatusEnum {
	/** 发放失败*/
	FAIL("redo_fail", "失败中"),
	/** 待发放 */
	WAITING("redo_waiting", "待处理"),
	/** 发放成功 */
	SUCCESS("redo_success", "已成功");

	private final String code;
	private final String name;
 

	ActPrizeRecordRedoStatusEnum(String _code, String name) {
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