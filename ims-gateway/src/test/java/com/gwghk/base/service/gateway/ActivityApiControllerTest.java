package com.gwghk.base.service.gateway;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gwghk.unify.framework.common.util.HttpClientUtil;

public class ActivityApiControllerTest {

	public String activityUrl = "http://localhost:9099/ims-gateway";
	
	public String companyId = "1";
	
	/**
	 * 发放记录
	 */
	@Test
	public void testPrizeRecord(){
		activityUrl = activityUrl + "/api/activity/prizerecord";
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "1");
		map.put("accountNo", "86140689");
		map.put("platform", "GTS2");
		map.put("accType", "real");
		map.put("companyId", companyId);
		Map<String,String> header = new HashMap<String,String>();
		String apiResult = HttpClientUtil.doPostWithMap(activityUrl, map, header);
        System.out.println(":"+apiResult);
	}
	
	/**
	 * 手动进行物品兑换
	 */
	@Test
	public void testGoodExchange(){
		activityUrl = activityUrl + "/api/activity/goods/exchange";
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "1");
		map.put("taskItemId", "2560");
		map.put("accountNo", "86107793");
		map.put("platform", "GTS2");
		map.put("companyId", companyId);
		Map<String,String> header = new HashMap<String,String>();
		String apiResult = HttpClientUtil.doPostWithMap(activityUrl, map, header);
		System.out.println(":"+apiResult);
	}
	
	/**
	 * 查询任务的物品列表
	 */
	@Test
	public void testTaskItems(){
		activityUrl = activityUrl + "/act/task/items?actNo=fx_wpdh_20180523185247&taskCode=free_exchange&companyId=1";
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "1");
		map.put("actNo", "fx_wpdh_20180523185247");
		map.put("taskCode", "free_exchange");
		map.put("companyId", companyId);
		Map<String,String> header = new HashMap<String,String>();
		String apiResult = HttpClientUtil.doPostWithMap(activityUrl, map, header);
		System.out.println(":"+apiResult);
	}
	
}
