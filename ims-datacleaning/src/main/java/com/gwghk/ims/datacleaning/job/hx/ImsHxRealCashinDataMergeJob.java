package com.gwghk.ims.datacleaning.job.hx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.SyncDataUpdateTypeEnum;
import com.gwghk.ims.datacleaning.manager.GTS2DataCleanManager;
import com.gwghk.ims.datacleaning.manager.HXMt4DataCleanManager;

/**
 * 
 * @ClassName: ActHxRealCashinDataMergeJob
 * @Description: 清洗客户入金数据【HX-MT4-模拟->活动中心】,清洗客户入金数据【HX-Gts2-模拟->活动中心】
 * @author lawrence
 * @date 2017年8月10日
 *
 */
//@Component
public class ImsHxRealCashinDataMergeJob implements SimpleJob {
	private static Logger logger = LoggerFactory.getLogger(ImsHxRealCashinDataMergeJob.class);

	@Autowired
	private GTS2DataCleanManager gts2DataCleanManager;

	@Autowired
	private HXMt4DataCleanManager actHxDataMergeManager;
	
	@Override
	public void execute(ShardingContext context) {
		logger.info("--->开始清洗HX-Gts2真实入金数据【HX-Gts2-真实->活动中心】");
		try{
			long startTime = System.currentTimeMillis();
			gts2DataCleanManager.mergeRealActCashin(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
					SyncDataUpdateTypeEnum.GTS2_HX_RREAL_CASHIN);
			logger.info("<---结束清洗HX-Gts2真实入金数据【HX-Gts2-真实->活动中心】,耗时:{}s",(System.currentTimeMillis()-startTime)/1000f);
		}catch(Exception e){
			logger.error("<---结束清洗HX-Gts2真实入金数据【HX-Gts2-真实->活动中心】,系统异常!!!",e);
		}
		logger.info("--->开始清洗HX-MT4真实入金数据【HX-MT4-真实->活动中心】");
		try{
			long startTime2 = System.currentTimeMillis();
			actHxDataMergeManager.mergeRealActCashin();
			logger.info("<---结束清洗HX-MT4真实入金数据【HX-MT4-真实->活动中心】,耗时:{}s",(System.currentTimeMillis()-startTime2)/1000f);
		}catch(Exception e){
			logger.error("<---结束清洗HX-MT4真实入金数据【HX-MT4-真实->活动中心】,系统异常!!!",e);
		}
	}
}