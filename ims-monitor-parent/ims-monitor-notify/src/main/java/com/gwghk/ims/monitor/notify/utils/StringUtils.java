package com.gwghk.ims.monitor.notify.utils;

public class StringUtils {
	/**
	 * 首字母大写字符串
	 * @param str
	 * @return
	 */
	public static String getFirstUpperCaseStr(String str) {
		if(str==null || str.length()<=0)
			return str;
		
		String result=str.substring(0,1).toUpperCase();
		if(str.length()>1)
			result+=str.substring(1).toLowerCase();
		
		return result;
	}
}
