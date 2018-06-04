package com.gwghk.ims.common.enums;

/**
 * 摘要：活动发放方式
 * @author eva
 * @version 1.0
 * @Date 2018年4月3日
 */
public enum ActSendTypeEnum {
	/**自动发放*/
	auto(0,"自动发放","allow"),
    /**人工审核*/
	manually(1,"人工审核","manual review");
	
	private Integer code;
	private  String name_cn;
	private  String name_en;
	
	ActSendTypeEnum(Integer code,String name_cn,String name_en){
		this.code = code;
		this.name_cn = name_cn;
		this.name_en = name_en;
	}
	
	
	public static String formatCode(Integer code){
		if(code!=null){
			for(ActSendTypeEnum ae : ActSendTypeEnum.values()){
				if(ae.getCode().equals(code)){
					return ae.getNameCn();
				}
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getNameCn() {
		return name_cn;
	}

	public void setNameCn(String name_cn) {
		this.name_cn = name_cn;
	}

	public String getNameEn() {
		return name_en;
	}

	public void setNameEn(String name_en) {
		this.name_en = name_en;
	}

	  
}