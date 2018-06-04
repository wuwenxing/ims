package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.common.Kuak;
import com.gwghk.unify.framework.common.util.StringUtil;
 
/**
 * 摘要：是否允许枚举
 * @author eva
 * @version 1.0
 * @Date 2017年7月17日
 */
public enum ActAllowStatusEnum implements EnumIntf{
	/**允许*/
	ALLOW("allow","允许","allow",1),
    /**不允许*/
    NOT_ALLOWED("not_allowed","不允许","not allowed",0),;
	
	private String code;
	private  String name_cn;
	private  String name_en;
	 private String name;
	 private Integer key;
	
	ActAllowStatusEnum(String code,String name_cn,String name_en,Integer key){
		this.code = code;
		this.name_cn = name_cn;
		this.name_en = name_en;
		this.name = name_cn;
		this.key = key;
	}

	public static String formatCode(String code){
		if(StringUtil.isNotEmpty(code)){
			for(ActAllowStatusEnum ae : ActAllowStatusEnum.values()){
				if(ae.getCode().equals(code)){
					return ae.getName();
				}
			}
		}
		return null;
	}
	
	public static String formatByKey(Integer key){
		if(key!=null){
			for(ActAllowStatusEnum ae : ActAllowStatusEnum.values()){
				if(ae.getKey().equals(key)){
					return ae.getName();
				}
			}
		}
		return null;
	}
	
	
	public static List<Kuak> getOptions(){
		List<Kuak> options = new ArrayList<Kuak>();
		for(ActAllowStatusEnum ae : ActAllowStatusEnum.values()){
			options.add(new Kuak(ae.getKey().toString(), ae.getName()));
        }
		 return options;
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

	public String getName_cn() {
		return name_cn;
	}

	public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

 
	  
}