package com.gwghk.ims.common.enums;

/**
 * 
 * 摘要：清算状态枚举
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月29日
 */
public enum ActSettleStatusEnum {
	/**未清算*/
	UNSETTLE(0,"未清算"),
	/**待清算*/
	WAITTING(1,"待清算"),
	/**清算中*/
	SETTLING(2,"清算中"),
	/**清算成功*/
	SETTLESUCC(3,"清算成功"),
	/**清算失败*/
	SETTLEFAIL(4,"清算失败"),
	;
	
	private Integer code;
	private  String name;
	
	ActSettleStatusEnum(Integer code,String name){
		this.code = code;
		this.name = name;
	}
	

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
