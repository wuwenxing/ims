package com.gwghk.ims.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.inf.external.activity.ActivityApiDubboService;

@RestController
@RequestMapping("/api/activity")
public class ActivityApiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActivityApiController.class);
	

	@Autowired
	private ActivityApiDubboService activityApiDubboService ;
	
	/**
	 * 执行任务自动发放记录
	 * @param accountNo
	 * @param platform
	 * @param accType
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/prizerecord", method = { RequestMethod.POST,RequestMethod.GET })
	public ApiRespResult<Void> autoPrizeRecord(@RequestParam("accountNo")String accountNo,@RequestParam("platform")String platform,@RequestParam("accType")String accType,@RequestParam("companyId")Long companyId) {
		logger.info("autoPrizeRecord,accountNo:{},platform:{},acctType:{},companyId:{}", new Object[]{accountNo,platform,accType,companyId});
		return activityApiDubboService.autoPrizeRecord(accountNo, platform, accType, companyId) ;
	}
	
	/**
	 * 物品兑换入口
	 * @param taskItemId
	 * @param platform
	 * @param accountNo
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/goods/exchange", method = { RequestMethod.POST,RequestMethod.GET })
	public ApiRespResult<Void> goodsExchange(@RequestParam("taskItemId")Integer taskItemId,@RequestParam("platform")String platform,@RequestParam("accountNo")String accountNo,@RequestParam("companyId")Long companyId) {
		logger.info("autoPrizeRecord,accountNo:{},platform:{},taskItemId:{},companyId:{}", new Object[]{accountNo,platform,taskItemId,companyId});
		return activityApiDubboService.handPrizeRecord(taskItemId, accountNo, platform, companyId) ;
	}
}
