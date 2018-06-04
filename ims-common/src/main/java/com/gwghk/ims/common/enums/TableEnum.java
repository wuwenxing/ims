package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 回调表枚举
 * @author wayne
 *
 */
public enum TableEnum implements EnumIntf {

	act_prize_record_("发放记录表", "ims_prize_record_")
	;
	
	private final String name;
	private final String code;
	TableEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public static List<TableEnum> getList(){
		List<TableEnum> result = new ArrayList<TableEnum>();
		for(TableEnum ae : TableEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	/**
	 * 动态表获取方法
	 * @param companyId
	 * @return
	 */
	public String getCode(Object companyId) {
		if(null != companyId){
			return code + CompanyEnum.getCodeById(companyId);
		}else{
			return code;
		}
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	
}
