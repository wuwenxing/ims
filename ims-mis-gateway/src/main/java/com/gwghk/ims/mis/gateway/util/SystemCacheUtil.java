package com.gwghk.ims.mis.gateway.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * 摘要：系统缓存数据工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月2日
 */
public class SystemCacheUtil {
	/**
	 * 缓存servletContext
	 */
	public static ServletContext servletContext;

	public final static String MENU_COMPANY_ID_CACHE_KEY = "menu_company_id_cache_"; 
	
	/**
	 * 缓存查询需要权限控制的即需要拦截的菜单Url
	 */
	public static Map<String,List<String>> menuUrlList = new HashMap<String,List<String>>();
}