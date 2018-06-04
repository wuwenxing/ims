package com.gwghk.ims.marketingtool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class SmsUtil {

	/**
	 * 
	 * 方法用途: 判断是否为手机号码<br>
	 * 实现步骤: <br>
	 * 
	 * @param mobileInput
	 * @return
	 */
	public static boolean checkMobileNum(String mobile) {
		if(StringUtils.isNotBlank(mobile)){
			int i = mobile.lastIndexOf("-");
			if(i >= 0){
				mobile = mobile.substring(i+1, mobile.length());
			}
		}
		Pattern p = Pattern.compile("^[1][0-9][0-9]{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static void main(String[] args) {
		String mobile = "86--10000000000";
		int i = mobile.lastIndexOf("-");
		if(i > 0){
			mobile = mobile.substring(i+1, mobile.length());
		}
		System.out.println(mobile);
		System.out.println(SmsUtil.checkMobileNum("86--10000000000")+"");
	}
}
