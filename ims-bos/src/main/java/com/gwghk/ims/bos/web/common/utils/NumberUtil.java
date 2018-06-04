package com.gwghk.ims.bos.web.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

public class NumberUtil {

	/**
	 * double四舍五入取整
	 * 
	 * @param f
	 * @return
	 */
	public static Long doubleToLong(Double num) {
		// double四舍五入
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num.longValue();
	}

	/**
	 * double四舍五入，保留两位有效数字
	 * 
	 * @param f
	 * @return
	 */
	public static double m2(double num) {
		// double四舍五入
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}
	
	/**
	 * 保留小数位数
	 * 
	 * @param num      等处理的数据
	 * @param digit    处理的小数位数
	 * @return
	 */
	public static String NumberFormat(String num,int digit) {
		// double四舍五入
		if(StringUtils.isNotBlank(num)){
			NumberFormat df = NumberFormat.getNumberInstance();
	        df.setMaximumFractionDigits(digit);
	        String result = df.format(Double.valueOf(num));
	        return result.replace(",", "");
		}else{
			return "";
		}  
	}
	
	/**
	 * 字符串转double
	 * @param num
	 * @return
	 */
	public static Double StringToDouble(String num){
		if(StringUtils.isNotBlank(num) && !"null".equals(num)){
			return Double.valueOf(num);
		}else{
			return 0.0D;
		}
	}
	
	public static void main(String[] args) {
		Long s = NumberUtil.doubleToLong(304.99999999999545);
		System.out.println(s);
		
		System.out.println(NumberUtil.m2(42.74999999999999));
		
		
	}
	
}
