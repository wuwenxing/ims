package com.gwghk.ims.activity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.manager.api.ActivityApiManager;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.inf.external.activity.ActivityApiDubboService;

@Service
public class ActivityApiDubboServiceImpl implements ActivityApiDubboService {

	private static final Logger logger = LoggerFactory.getLogger(ActivityApiDubboServiceImpl.class);
	
	@Autowired 
	private ActivityApiManager activityApiManager ;
	
	@Override
	public ApiRespResult<Void> autoPrizeRecord(String accountNo,String platform,String accType,Long companyId) {
		try{
			activityApiManager.autoPrizeRecord(accountNo, platform, accType, companyId);
		        
			return ApiRespResult.success();
		}catch(Exception e){
			e.printStackTrace(); 
			logger.error("<--系统异常,【autoPrizeRecord,accountNo:{},platform:{},acctType:{},companyId:{},Exception:{}", new Object[]{accountNo,platform,accType,companyId,e});
			return ApiRespResult.error(ApiResultCode.EXCEPTION);
		}
	}
	
	@Override
	public ApiRespResult<Void> handPrizeRecord(Integer taskItemId,String accountNo,String platform,Long companyId) {
		try{
			return activityApiManager.handPrizeRecord(taskItemId, accountNo, platform, companyId) ;
		}catch(Exception e){
			e.printStackTrace(); 
			logger.error("<--系统异常,【handPrizeRecord,taskItemId:{},accountNo:{},platform:{},companyId:{},Exception:{}", new Object[]{taskItemId,accountNo,platform,companyId,e});
			return ApiRespResult.error(ApiResultCode.EXCEPTION);
		}
	}

}
