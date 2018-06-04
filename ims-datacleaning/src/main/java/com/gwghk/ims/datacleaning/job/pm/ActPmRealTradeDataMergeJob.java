package com.gwghk.ims.datacleaning.job.pm;

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
* @ClassName: ActPmRealTradeDataMergeJob
* @Description: 清洗客户交易数据【PM-Gts2-真实->活动中心】
* @author lawrence
* @date 2018年3月23日
*
 */
//@Component
public class ActPmRealTradeDataMergeJob implements SimpleJob {
	private static Logger logger = LoggerFactory.getLogger(ActPmRealTradeDataMergeJob.class);

	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;
	
	@Override
	public void execute(ShardingContext context) {
		logger.info("--->开始清洗pm真实交易数据【PM-Gts2-真实->活动中心】");
		try{
			long startTime=System.currentTimeMillis();
			actGts2DataMergeManager.mergeRealActTradeRecord(Long.valueOf(CompanyEnum.pm.getId()),CompanyEnum.pm.getCode(),
				SyncDataUpdateTypeEnum.GTS2_PM_RREAL_TRADE);
			logger.info("<---结束清洗pm真实交易数据【PM-Gts2-真实->活动中心】,耗时:{}s",(System.currentTimeMillis()-startTime)/1000f);
		}catch(Exception e){
			logger.error("<---结束清洗pm真实交易数据【PM-Gts2-真实->活动中心】,系统异常!!!",e);
		}
	}
}