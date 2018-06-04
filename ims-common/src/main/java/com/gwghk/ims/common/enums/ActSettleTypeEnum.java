package com.gwghk.ims.common.enums;

/**
 * 
 * 摘要：清算方式枚举
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月29日
 */

public enum ActSettleTypeEnum {
	
	/**手动清算*/
	MANUAL(1,"手动清算"),
	/**活动结束清算*/
	ACTEND(2,"活动结束清算"),
	/**黑名单清算*/
	BLACKLIST(3,"黑名单清算"),
	/**取款清算*/
	WITHDRAW(4,"取款清算"),
	/**转账清算*/
	TRANSFERACCOUNTS(5,"转账清算"),
	/**转组清算*/
	TRANSFERGROUP(6,"转组清算"),
	
	;
	
	private Integer code;
	private  String name;
	
	ActSettleTypeEnum(Integer code,String name){
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
	
	/**
	 * 查找清算类型
	 * @param code
	 * @return
	 */
	public static ActSettleTypeEnum findByCode(Integer code) {
		for(ActSettleTypeEnum type: ActSettleTypeEnum.values()) {
			if(type.getCode()==code)
				return type;
		}
		
		return null;
	}
}
