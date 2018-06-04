package com.gwghk.ims.common.enums;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：通用状态枚举
 * @author eva
 * @version 1.0
 * @Date 2017年7月17日
 */
public enum ActCommonStatusEnum {
    /** 启用 */
    ENABLE("Y", "启用","enable"),
	/**禁用 */
    DISABLE ("N", "禁用","disabled"),
	/** 启用 */
	INVALID("invalid", "无效","invalid"),
	/** 否 */
	NO("0", "否","no"),
	/** 是 */
	YES("1", "是","yes"),;
	
	private String code;
	private  String name_cn;
	private  String name_en;
	private String name;
	
	ActCommonStatusEnum(String code,String name_cn,String name_en){
		this.code = code;
		this.name_cn = name_cn;
		this.name_en = name_en;
		this.name = name_cn;
	}

	public static String formatCode(String code){
		if(StringUtil.isNotEmpty(code)){
			for(ActCommonStatusEnum ae : ActCommonStatusEnum.values()){
				if(ae.getCode().equals(code)){
					return ae.getNameCn();
				}
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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