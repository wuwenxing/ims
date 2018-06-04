package com.gwghk.ims.activity.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 摘要：活动公共处理类util
 * 
 * @author eva
 * @date 2017-08-9
 */
public class ActCommonDealUtil {

	/**
	 * 截取平台 
	 * platformsCcy GTS2#CYN,MT4#USD
	 * @param platformsCcy
	 * @return GTS2
	 */
	public static String getPlatforms(String platformsCcy){
		// 处理产品平台
		String platforms=null;
		if (StringUtils.isNotBlank(platformsCcy)) {
			String[] pCcys = platformsCcy.split(",");// GTS2#CYN,GTS2#USD
			if (pCcys.length > 0) {
				Set<String> platformsSet = new HashSet<String>();
				for (String pCcy : pCcys) {
					if (StringUtils.isNotBlank(pCcy)) {
						String[] objs = pCcy.split("#");
						if (objs.length > 0) {
							platformsSet.add(objs[0]);
						}
					}
				}
				StringBuffer pStr = null;
				for (String p : platformsSet) {
					if (pStr == null) {
						pStr = new StringBuffer(p);
					} else {
						pStr.append(",").append(p);
					}
				}
				platforms = pStr.toString();
			}
		}
		return platforms;
	}
	
	/**
	 * 截取币种
	 * platformsCcy GTS2#CNY,MT4#CNY
	 * @param platformsCcy
	 * @return CNY
	 */
	public static String getCcy(String platformsCcy){
		// 处理币种
		String ccy=null;
		if (StringUtils.isNotBlank(platformsCcy)) {
			String[] pCcys = platformsCcy.split(",");// GTS2#CYN,GTS2#USD
			if (pCcys.length > 0) {
				Set<String> ccySet = new HashSet<String>();
				for (String pCcy : pCcys) {
					if (StringUtils.isNotBlank(pCcy)) {
						String[] objs = pCcy.split("#");
						if (objs.length > 1) {
							ccySet.add(objs[1]);
						}
					}
				}
				StringBuffer cStr = null;
				for (String c: ccySet) {
					if (cStr == null) {
						cStr = new StringBuffer(c);
					} else {
						cStr.append(",").append(c);
					}
				}
				ccy = cStr!=null?cStr.toString():null;
			}
		}
		return ccy;
	}
}
