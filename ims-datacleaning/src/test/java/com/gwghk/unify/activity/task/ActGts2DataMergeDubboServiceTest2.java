package com.gwghk.unify.activity.task;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.datacleaning.manager.GTS2DataCleanManager;
import com.gwghk.ims.datacleaning.manager.HXMt4DataCleanManager;

public class ActGts2DataMergeDubboServiceTest2 extends BaseTest {

	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;

	@Autowired
	private HXMt4DataCleanManager actHxDataMergeManager;

	private static Logger logger = LoggerFactory.getLogger(ActGts2DataMergeDubboServiceTest2.class);

	@Test
	public void test() {
		long startTime = System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX-MT4-真实->活动中心】begin....");
		try {
			actHxDataMergeManager.mergeRealActCustomerInfo();
		} catch (Exception ex) {
			logger.error("execute->job-清洗客户数据【HX-MT4-真实->活动中心】出错,{}", ex.getMessage());
		}
		logger.info("execute->job-清洗客户数据【HX-MT4-真实->活动中心】end....");
		long tmpTime = System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX-MT4-真实->活动中心】耗时:{}", tmpTime - startTime);

		logger.info("execute->job-清洗客户数据【Gts2-真实->活动中心】begin....");
		try {
			// actGts2DataMergeManager.mergeRealActCustomerInfo();
		} catch (Exception ex) {
			logger.error("execute->job-清洗客户数据【Gts2-真实->活动中心】出错,{}", ex.getMessage());
		}
		logger.info("execute->job-清洗客户数据【Gts2-真实->活动中心】end....");
		long endTime = System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【Gts2-真实->活动中心】耗时:{}", endTime - tmpTime);
	}
}
