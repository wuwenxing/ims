package com.gwghk.ims.activity.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.BaseTest;
import com.gwghk.ims.activity.manager.api.ActivityApiManager;

/**
 * 
 * 摘要：活动api发放入口
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月9日
 */
public class ActivityApiManagerTest extends BaseTest{

	@Autowired
	private ActivityApiManager activityApiManager;
	
	@Test
	public void testAutoPrizeRecord(){
		System.out.println("------->testAutoPrizeRecord");
		String accountNo = "86140592" ;
		String platform = "GTS2" ;
		String accType = "real" ;
		Long companyId = 1L ;
		activityApiManager.autoPrizeRecord(accountNo, platform, accType, companyId); 
	}
	
	public void testAuto(){
		//http://localhost:9066/ims-mis-gateway/api/activity/prizerecord?accountNo=86140592&platform=GTS2&accType=real&companyId=1&test=1
	}
	
 
}
