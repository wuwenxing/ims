package com.gwghk.ims.mis.gateway.util;

import java.util.List;

/**
 * 摘要：集合工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月2日
 */
public class CollectionsUtil extends org.apache.commons.collections.CollectionUtils{

	/**
	 * 集合中是否包含某元素(支持模糊)
	 */
	public static boolean contains(List<String> srcList,String var){
		if(null == srcList || srcList.size() == 0 || "".equals(var) || null == var){
			return false;
		}
		for(String item : srcList){
			if(var.contains(item)){
				return true;
			}
		}
		return false;
	}
}