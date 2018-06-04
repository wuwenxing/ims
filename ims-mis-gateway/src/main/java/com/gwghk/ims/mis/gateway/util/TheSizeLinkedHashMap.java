package com.gwghk.ims.mis.gateway.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现固定大小的map，每次添加新条目时删除最旧的条目，始终维持 100 个条目的稳定状态
 * @author wayne
 *
 */
public class TheSizeLinkedHashMap<K, V> extends LinkedHashMap<K, V>{

	private static final long serialVersionUID = 4884204360964525489L;
	
	// 保存最大大小
	private static final int MAX_ENTRIES = 10000;

	/**
	 * 每次添加新条目时删除最旧的条目
	 * @param eldest
	 * @return
	 */
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
	    return size() > MAX_ENTRIES;   
	}
	
	public static void main(String[] args) {
		// 缓存已校验的签名,签名只能用一次
		Map<String, String> signMap = new TheSizeLinkedHashMap<String, String>();
		for(int i=0; i<MAX_ENTRIES; i++){
			signMap.put(i+"key", i+"val");
		}
		System.out.println("旧大小：" + signMap.size());
		for(Map.Entry<String, String> entry:signMap.entrySet()){
			System.out.println("旧：" + entry.getKey() + entry.getValue());
		}
		
		signMap.put("200", "200");
		signMap.put("201", "201");
		signMap.put("202", "203");
		signMap.put("204", "204");
		
		System.out.println("插入新值后大小：" + signMap.size());
		for(Map.Entry<String, String> entry:signMap.entrySet()){
			System.out.println("新：" + entry.getKey() + entry.getValue());
		}
	}
}
