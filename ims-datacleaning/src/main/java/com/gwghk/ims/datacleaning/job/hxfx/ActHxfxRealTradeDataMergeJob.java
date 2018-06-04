package com.gwghk.ims.datacleaning.job.hxfx;

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
 * @ClassName: ActHxfxRealTradeDataMergeJob
 * @Description: 清洗客户交易数据【HXFX-Gts2-真实->活动中心】
 * @author lawrence
 * @date 2017年8月11日
 *
 */
//@Component
public class ActHxfxRealTradeDataMergeJob implements SimpleJob {
	private static Logger logger = LoggerFactory
			.getLogger(ActHxfxRealTradeDataMergeJob.class);

	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;
	
	@Override
	public void execute(ShardingContext context) {
		logger.info("--->开始清洗Hxfx真实交易数据【HXFX-Gts2-真实->活动中心】");
		try{
			long startTime=System.currentTimeMillis();
			actGts2DataMergeManager.mergeRealActTradeRecord(Long.valueOf(CompanyEnum.hxfx.getId()),CompanyEnum.hxfx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HXFX_RREAL_TRADE);
			logger.info("<---结束清洗Hxfx真实交易数据【HXFX-Gts2-真实->活动中心】,耗时:{}s",(System.currentTimeMillis()-startTime)/1000f);
		}catch(Exception e){
			logger.error("<---结束清洗Hxfx真实交易数据【HXFX-Gts2-真实->活动中心】,系统异常!!!",e);
		}
	}
}