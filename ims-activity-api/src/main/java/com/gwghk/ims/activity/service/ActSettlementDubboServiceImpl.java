package com.gwghk.ims.activity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.manager.settle.ActSettlementManager;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.inf.external.activity.ActSettlementDubboService;
import com.gwghk.ims.common.vo.BaseVO;
/**
 * 
 * 摘要：活动清算接口
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月30日
 */

@Service
public class ActSettlementDubboServiceImpl implements ActSettlementDubboService {

	private static final Logger logger = LoggerFactory.getLogger(ActSettlementDubboServiceImpl.class);
	
	@Autowired 
	private ActSettlementManager actSettlementManager ;
	
	@Override
	public ApiRespResult<Void> beginUserSettlement(ActSettleTypeEnum type,String accountNo,String actNo,String platform,BaseVO base,Long companyId) {
		try{
			actSettlementManager.beginUserSettlement(type, actNo, accountNo, platform,base);
		        
			return ApiRespResult.success();
		}catch(Exception e){
			e.printStackTrace(); 
			logger.error("<--系统异常,【autoPrizeRecord,accountNo:{},platform:{},actNo:{},companyId:{},Exception:{}", new Object[]{accountNo,platform,actNo,companyId,e});
			return ApiRespResult.error(ApiResultCode.EXCEPTION);
		}
	}

}
