package com.gwghk.ims.datacleaning.manager;

import java.util.Date;
import java.util.Map;

public interface HXMergeBusiness {
	/**
	 * 
	 * @MethodName: doMerge
	 * @Description: 处理合并数据逻辑
	 * @param data 返回最后一天数据更新时间
	 * @return
	 * @return Date
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Date doMerge(Map data) throws Exception;

	
}
