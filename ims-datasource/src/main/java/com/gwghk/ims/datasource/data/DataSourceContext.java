package com.gwghk.ims.datasource.data;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;

public class DataSourceContext {
	public static void setCompany(DataSourceTypeEnum type,Object company) throws Exception {
		switch(type) {
		case BASE_DATA:
			DynamicDataSource.setDataSourceHolder("baseDS");
			break;
		case BUSINESS_DATA:
			String companyCode=CompanyEnum.findById(company).getCode();
			if(companyCode==null)
				throw new Exception("无法获取company!");
			
			DynamicDataSource.setDataSourceHolder(CompanyEnum.findById(company).getCode()+"DS");
			break;
		default:
			throw new Exception("不正确的数据源类型!");
		}
	}
}
