package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.common.Kuak;
import com.gwghk.unify.framework.common.util.StringUtil;
 
/**
 * 入金型任务释放类型
 * @author jackson.tang
 *
 */
public enum ActDiscountTypeEnum implements EnumIntf{
	
	/**允许*/
	CJ_DISCOUNT("cj_discount","按层级扣回","cj_discount",1),
    /**不允许*/
    ALL_DISCOUNT("all_discount","全部扣回","all_discount",2),;
	
	private String code;
	private  String name_cn;
	private  String name_en;
	 private String name;
	 private Integer key;
	
	 ActDiscountTypeEnum(String code,String name_cn,String name_en,Integer key){
		this.code = code;
		this.name_cn = name_cn;
		this.name_en = name_en;
		this.name = name_cn;
		this.key = key;
	}

	public static String formatCode(String code){
		if(StringUtil.isNotEmpty(code)){
			for(ActDiscountTypeEnum ae : ActDiscountTypeEnum.values()){
				if(ae.getCode().equals(code)){
					return ae.getName();
				}
			}
		}
		return null;
	}
	
	public static String formatByKey(Integer key){
		if(key!=null){
			for(ActDiscountTypeEnum ae : ActDiscountTypeEnum.values()){
				if(ae.getKey().equals(key)){
					return ae.getName();
				}
			}
		}
		return null;
	}
	
	
	public static List<Kuak> getOptions(){
		List<Kuak> options = new ArrayList<Kuak>();
		for(ActDiscountTypeEnum ae : ActDiscountTypeEnum.values()){
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