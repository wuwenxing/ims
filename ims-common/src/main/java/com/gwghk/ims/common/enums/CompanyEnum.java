package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum CompanyEnum implements EnumIntf {

	fx("外汇", "fx", "1"),
	hx("恒信", "hx", "2"),
	pm("贵金属", "pm", "3"),
	cf("创富", "cf", "8"),
	hxfx("hxfx", "hxfx", "9")
	;
	
	private final String name;
	private final String code;
	private final String id;
	
	CompanyEnum(String name, String code, String id) {
		this.name = name;
		this.code = code;
		this.id = id;
	}
	
	public static List<CompanyEnum> getList(){
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		for(CompanyEnum ae : CompanyEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static List<CompanyEnum> getList(String companyIds){
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		if(StringUtils.isNotBlank(companyIds)){
			String[] companyIdAry = companyIds.split(",");
			for(String companyIdStr: companyIdAry){
				CompanyEnum ce = CompanyEnum.findById(companyIdStr);
				if(null != ce){
					result.add(ce);
				}
			}
		}
		return result;
	}
	
	public static String getAllCompanyIds(){
		String companyIds = "";
		for(CompanyEnum ae : CompanyEnum.values()){
			companyIds += ae.getId() + ",";
		}
		if(StringUtils.isNotBlank(companyIds)){
			companyIds = companyIds.substring(0, companyIds.length()-1);
		}
		return companyIds;
	}
	
	public static CompanyEnum findById(Object id){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getId().equals(id+"")){
				return ae;
			}
		}
		return null;
	}
	
	public static CompanyEnum findByName(Object name){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getName().equals(name+"")){
				return ae;
			}
		}
		return null;
	}
	
	public static String getCodeById(Object id){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getId().equals(id+"")){
				return ae.getCode();
			}
		}
		return id+"";
	}

	public static String getCodeByName(Object name){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getName().equals(name+"")){
				return ae.getCode();
			}
		}
		return name+"";
	}
	
	public static String getIdByCode(String code){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getCode().equals(code)){
				return ae.getId();
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getId() {
		return id;
	}

	public Long getLongId() {
		return Long.parseLong(id);
	}
}