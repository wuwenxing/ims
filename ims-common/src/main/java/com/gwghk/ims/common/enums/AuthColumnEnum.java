package com.gwghk.ims.common.enums;

public enum AuthColumnEnum {

    STRINGCODE("stringCode","串码"),
    CHINESENAME("chineseName","客户姓名"),
    MOBILEPHONE("mobilePhone","客户电话"),
    EMAIL("email","客户邮箱"),
    
    
    ;
	
	private String code;
	private  String name;
	
	AuthColumnEnum(String code,String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}
