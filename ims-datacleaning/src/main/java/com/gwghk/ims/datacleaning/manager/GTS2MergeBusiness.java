package com.gwghk.ims.datacleaning.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public interface GTS2MergeBusiness {
	/**
	 * 
	 * @MethodName: doMerge
	 * @Description: 处理合并数据逻辑
	 * @param data
	 * @return void
	 */
	@SuppressWarnings("rawtypes")
	public int doMerge(Map data);

	/**
	 * 
	 * @MethodName: selectDemoLastUpdateTime
	 * @Description: 获取合并好的数据，最后一条数据的更新时间
	 * @return
	 * @return Date
	 */
	public Date selectLastUpdateTime();
}
