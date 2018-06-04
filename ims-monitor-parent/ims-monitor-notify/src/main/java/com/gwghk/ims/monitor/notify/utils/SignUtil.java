package com.gwghk.ims.monitor.notify.utils;


import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;


/**
 * 摘要：签名工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年2月9日
 */
public class SignUtil {

	/**
	 * 功能：接口加入相关权限验证参数
	 */
	public static Map<String,String> getHeaderMap(){
		Map<String,String> headerMap = new HashMap<String,String>();
		String sign = ResourcesUtil.application.getString("salt") + ":" 
					+ ResourcesUtil.application.getString("appKey")+":"
					+ ResourcesUtil.application.getString("masterSecret");
  	    String authorization = "Basic "+ Base64.encodeBase64String(sign.getBytes());

    	headerMap.put(SignConfig.AUTH_HEADE_RNAME,authorization);
    	headerMap.put(SignConfig.CLIENT_TYPE_HEADE_RNAME, ResourcesUtil.application.getString("clientType"));
    	return headerMap;
	} 
}
