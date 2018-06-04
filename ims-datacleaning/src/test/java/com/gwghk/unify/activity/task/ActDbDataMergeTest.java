package com.gwghk.unify.activity.task;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.SyncDataUpdateTypeEnum;
import com.gwghk.ims.datacleaning.manager.GTS2DataCleanManager;
import com.gwghk.ims.datacleaning.manager.HXMt4DataCleanManager;
import com.gwghk.ims.datacleaning.utils.CacheKeyConstant;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;

public class ActDbDataMergeTest extends BaseTest {
	private static Logger logger = LoggerFactory.getLogger(ActDbDataMergeTest.class);
	
	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;
	
	@Autowired
	private HXMt4DataCleanManager actHxDataMergeManager;
	
	
	@Autowired
    private RedisCacheTemplate redisCacheTemplate;

	@Test
	public void testFxDemoCustomerDataMerge() {
		
		redisCacheTemplate.set(CacheKeyConstant.getDealKey("real", "fx"),null);
		long startTime = System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【FX(Gts2)-模拟->活动中心】begin....");
		try {
			actGts2DataMergeManager.mergeDemoActCustomerInfo(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
					SyncDataUpdateTypeEnum.GTS2_FX_DEMO_CUSTOMER);
		} catch (Exception ex) {
			logger.error("execute->job-清洗客户数据【FX(Gts2)-模拟->活动中心】出错,{}",
					ex.getMessage());
		}
		logger.info("execute->job-清洗客户数据【FX(Gts2)-模拟->活动中心】end....");
		long endTime = System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【FX(Gts2)-模拟->活动中心】耗时:{}",
				endTime - startTime);
	}
	
	@Test
	public void testFxRealCustomerDataMerge(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【FX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCustomerInfo(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_FX_RREAL_CUSTOMER);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户数据【FX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户数据【FX(Gts2)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【FX(Gts2)-真实->活动中心】耗时:{}",endTime-startTime);		
	}
	
	@Test
	@Ignore
	public void testActHxDemoCustomerDataMerge(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX(MT4)-模拟->活动中心】begin....");
		try{
			actHxDataMergeManager.mergeDemoActCustomerInfo();
		}catch(Exception ex){
			logger.error("execute->job-清洗客户数据【HX(MT4)-模拟->活动中心】出错,{}",ex.getMessage());
		}
		long tmpTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX(MT4)-模拟->活动中心】end....");
		logger.info("execute->job-清洗客户数据【HX(MT4)-模拟->活动中心】耗时:{}",tmpTime-startTime);

		logger.info("execute->job-清洗客户数据【HX(Gts2)-模拟->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeDemoActCustomerInfo(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HX_DEMO_CUSTOMER);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户数据【HX(Gts2)-模拟->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户数据【HX(Gts2)-模拟->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX(Gts2)-模拟->活动中心】耗时:{}",endTime-tmpTime);
		
	}
	
	
	@Test
	@Ignore
	public void testActHxRealCustomerDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX(MT4)-真实->活动中心】begin....");
		try{
			actHxDataMergeManager.mergeRealActCustomerInfo();
		}catch(Exception ex){
			logger.error("execute->job-清洗客户数据【HX(MT4)-真实->活动中心】出错,{}",ex.getMessage());
		}
		long tmpTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX(MT4)-真实->活动中心】end....");
		logger.info("execute->job-清洗客户数据【HX(MT4)-真实->活动中心】耗时:{}",tmpTime-startTime);

		logger.info("execute->job-清洗客户数据【HX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCustomerInfo(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HX_REAL_CUSTOMER);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户数据【HX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户数据【HX(Gts2)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户数据【HX(Gts2)-真实->活动中心】耗时:{}",endTime-tmpTime);
	}
	
	
	@Test
	@Ignore
	public void testActFxRealCashinDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗入金数据【FX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCashin(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_FX_RREAL_CASHIN);
		}catch(Exception ex){
			logger.error("execute->job-清洗入金数据【FX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗入金数据【FX(Gts2)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗交易数据【FX(Gts2)-真实->活动中心】耗时:{}",endTime-startTime);	
	}
	
	@Test
	@Ignore
	public void testActFxRealCashoutDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗出金数据【FX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCashout(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_FX_RREAL_CASHOUT);
		}catch(Exception ex){
			logger.error("execute->job-清洗出金数据【FX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗出金数据【FX(Gts2)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗出金数据【FX(Gts2)-真实->活动中心】耗时:{}",endTime-startTime);	
	}
	
	@Test
	@Ignore
	public void testActFxRealTradeDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【FX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActTradeRecord(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_FX_RREAL_TRADE);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("execute->job-清洗客户交易数据【FX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户交易数据【FX(Gts2)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【FX(Gts2)-真实->活动中心】耗时:{}",endTime-startTime);
	}
	
	@Test
	@Ignore
	public void testActFxDemoTradeDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【FX(Gts2)-模拟->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeDemoActTradeRecord(Long.valueOf(CompanyEnum.fx.getId()),CompanyEnum.fx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_FX_DEMO_TRADE);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户交易数据【FX(Gts2)-模拟->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户交易数据【FX(Gts2)-模拟->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【FX(Gts2)-模拟->活动中心】耗时:{}",endTime-startTime);	
	}
	
	@Test
	@Ignore
	public void testActHxRealCashinDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户入金数据【HX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCashin(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HX_RREAL_CASHIN);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户入金数据【HX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户入金数据【HX(Gts2)-真实->活动中心】end....");
		long tmpTime=System.currentTimeMillis();
		logger.info("execute->job-清洗交易数据【HX(Gts2)-真实->活动中心】耗时:{}",tmpTime-startTime);	
		
		logger.info("execute->job-清洗客户入金数据【HX(MT4)-真实->活动中心】begin....");
		try{
			actHxDataMergeManager.mergeRealActCashin();
		}catch(Exception ex){
			logger.error("execute->job-清洗客户入金数据【HX(MT4)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户入金数据【HX(MT4)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗交易数据【HX(MT4)-真实->活动中心】耗时:{}",endTime-tmpTime);	
	}
	
	@Test
	@Ignore
	public void testActHxDemoTradeDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【HX(Gts2)-模拟->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeDemoActTradeRecord(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HX_DEMO_TRADE);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户交易数据【HX(Gts2)-模拟->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户交易数据【HX(Gts2)-模拟->活动中心】end....");
		long tmpTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【HX(Gts2)-模拟->活动中心】耗时:{}",tmpTime-startTime);
		
		logger.info("execute->job-清洗客户交易数据【HX-MT4-模拟->活动中心】begin....");
		try{
			actHxDataMergeManager.mergeDemoActTradeRecord();
		}catch(Exception ex){
			logger.error("execute->job-清洗客户交易数据【HX-MT4-模拟->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户交易数据【HX-MT4-模拟->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【HX-MT4-模拟->活动中心】耗时:{}",endTime-tmpTime);	
	}
		
	@Test
	@Ignore
	public void testActHxRealTradeDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【HX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActTradeRecord(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HX_RREAL_TRADE);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户交易数据【HX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户交易数据【HX(Gts2)-真实->活动中心】end....");
		long tmpTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【HX(Gts2)-真实->活动中心】耗时:{}",tmpTime-startTime);
		
		logger.info("execute->job-清洗客户交易数据【HX-MT4-真实->活动中心】begin....");
		try{
			actHxDataMergeManager.mergeRealActTradeRecord();
		}catch(Exception ex){
			logger.error("execute->job-清洗客户交易数据【HX-MT4-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户交易数据【HX-MT4-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户交易数据【HX-MT4-真实->活动中心】耗时:{}",endTime-tmpTime);	
	}
	
	@Test
	@Ignore
	public void testActHxRealCashoutDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户出金数据【HX(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCashout(Long.valueOf(CompanyEnum.hx.getId()),CompanyEnum.hx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HX_RREAL_CASHOUT);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户出金数据【HX(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户出金数据【HX(Gts2)-真实->活动中心】end....");
		long tmpTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户出金数据【HX(Gts2)-真实->活动中心】耗时:{}",tmpTime-startTime);	
		
		logger.info("execute->job-清洗客户出金数据【HX(MT4)-真实->活动中心】begin....");
		try{
			actHxDataMergeManager.mergeRealActCashout();
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("execute->job-清洗客户出金数据【HX(MT4)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户出金数据【HX(MT4)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗交易数据【HX(MT4)-真实->活动中心】耗时:{}",endTime-tmpTime);	
	}
	
	@Test
	@Ignore
	public void testActHxFxRealCashinDataMergeJob(){
		long startTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户入金数据【LGTS2(Gts2)-真实->活动中心】begin....");
		try{
			actGts2DataMergeManager.mergeRealActCashin(Long.valueOf(CompanyEnum.hxfx.getId()),CompanyEnum.hxfx.getCode(),
				SyncDataUpdateTypeEnum.GTS2_HXFX_RREAL_CASHIN);
		}catch(Exception ex){
			logger.error("execute->job-清洗客户入金数据【hxfx(Gts2)-真实->活动中心】出错,{}",ex.getMessage());
		}
		logger.info("execute->job-清洗客户入金数据【hxfx(Gts2)-真实->活动中心】end....");
		long endTime=System.currentTimeMillis();
		logger.info("execute->job-清洗客户入金数据【hxfx(Gts2)-真实->活动中心】耗时:{}",endTime-startTime);	
	}
}