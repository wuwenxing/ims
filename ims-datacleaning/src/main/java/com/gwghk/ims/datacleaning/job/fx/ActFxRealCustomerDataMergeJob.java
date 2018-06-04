package com.gwghk.ims.datacleaning.job.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.SyncDataUpdateTypeEnum;
import com.gwghk.ims.datacleaning.manager.GTS2DataCleanManager;

/**
 * 
 * @ClassName: ActFxRealCustomerDataMergeJob
 * @Description: 清洗客户资料数据【FX-Gts2-真实->活动中心】
 * @author lawrence
 * @date 2017年5月22日
 *
 */
@Component
public class ActFxRealCustomerDataMergeJob implements SimpleJob {
	private static Logger logger = LoggerFactory.getLogger(ActFxRealCustomerDataMergeJob.class);

	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;
	

	@Override
	public void execute(ShardingContext context) {
		logger.info("--->开始清洗fx真实客户资料数据【FX-Gts2-真实->活动中心】");
		try{
			long startTime=System.currentTimeMillis();
			actGts2DataMergeManager.mergeRealActCustomerInfo(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_FX_RREAL_CUSTOMER);
			logger.info("<---结束清洗fx真实客户资料数据【FX-Gts2-真实->活动中心】,耗时:{}s",(System.currentTimeMillis()-startTime)/1000f);
		}catch(Exception e){
			logger.error("<---结束清洗fx真实客户资料数据【FX-Gts2-真实->活动中心】,系统异常!!!",e);
		}
	}
}