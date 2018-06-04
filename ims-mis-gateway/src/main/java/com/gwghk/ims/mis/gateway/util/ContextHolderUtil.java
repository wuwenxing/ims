package com.gwghk.ims.mis.gateway.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 摘要：获取request、session对象 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年8月25日
 */
public class ContextHolderUtil {
	
	/**
	 * 功能：获取request
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 功能：获取session
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
}
