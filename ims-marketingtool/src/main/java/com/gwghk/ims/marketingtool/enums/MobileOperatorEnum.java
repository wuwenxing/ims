package com.gwghk.ims.marketingtool.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 摘要：手机运营商枚举类型
 * @author eva.liu
 * @version 1.0
 * @Date 2017年4月6日
 */
public enum MobileOperatorEnum {
   /**
    * 中国移动
    */
    CMCC("中国移动", "CMCC"),
    /**
     * 中国联通
     */
    CUCC("中国联通", "CUCC"),
    /**
     * 中国电信
     */
	CTCC("中国电信", "CTCC"),
	/**
     * 未知运营商
     */
	UNKNOWN("未知运营商", "unknown"),
	;
	
	private final String value;
	private final String labelKey;
	
	MobileOperatorEnum(String labelKey,String value) {
		this.labelKey = labelKey;
		this.value = value;
	}
	
	/**
	 * 验证手机号为哪个运营商
	 * 移动：134|135|136|137|138|139|147|150|151|152|157|158|159|178|182|183|184|187|188|1705
	 * 联通：130|131|132|145|155|156|176|185|186|1709|1708|1707
	 * 电信：180|181|189|133|153|1700|177|173|1701|1702
	 * @return
	 */
	public static MobileOperatorEnum checkOpeator(String mobile){
		String cmcc = "(134|135|136|137|138|139|147|150|151|152|157|158|159|178|182|183|184|187|188|1705).*";
		String cucc = "(130|131|132|145|155|156|176|185|186|1709|1708|1707).*";
		String ctcc = "(180|181|189|133|153|1700|177|173|1701|1702).*";
		
		Pattern pCmcc = Pattern.compile(cmcc);
		Matcher mCmcc = pCmcc.matcher(mobile);
		
		Pattern pCucc = Pattern.compile(cucc);
		Matcher mCucc = pCucc.matcher(mobile);
		
		Pattern pCtcc = Pattern.compile(ctcc);
		Matcher mCtcc = pCtcc.matcher(mobile);

		if(mCmcc.matches()){
			return MobileOperatorEnum.CMCC;
		}
		if(mCucc.matches()){
			return MobileOperatorEnum.CUCC;
		}
		if(mCtcc.matches()){
			return MobileOperatorEnum.CTCC;
		}
		return MobileOperatorEnum.UNKNOWN;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

}
