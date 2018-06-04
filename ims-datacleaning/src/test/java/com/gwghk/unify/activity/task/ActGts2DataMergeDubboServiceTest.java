package com.gwghk.unify.activity.task;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.SyncDataUpdateTypeEnum;
import com.gwghk.ims.datacleaning.manager.GTS2DataCleanManager;

public class ActGts2DataMergeDubboServiceTest extends BaseTest {

	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;

	/**
	 * 测试：合并Gts2真实客户资料数据
	 */
	@Test
	public void testMergeGts2RealCustomerInfo() {
//		actGts2DataMergeManager.mergeRealActCustomerInfo();
	}

	/**
	 * 测试：合并Gts2数据，防止丢失
	 */
	@Test
	public void testMergeMissData() {
		// actGts2DataMergeDubboService.mergeMissGts2RealCustomerInfo();
//		actGts2DataMergeManager.mergeMissRealActCashout(null);
		// actGts2DataMergeDubboService.mergeMissGts2DemoCustomerInfo();
		// actGts2DataMergeDubboService.mergeMissGts2RealTrade();
		// actGts2DataMergeDubboService.mergeMissGts2DemoTrade();
	}

	/**
	 * 测试：合并Gts2模拟客户资料数据
	 */
	@Test
	public void testMergeGts2DemoCustomerInfo() {
//		actGts2DataMergeManager.mergeDemoActCustomerInfo();
	}

	/**
	 * 测试：合并Gts2模拟客户交易数据
	 */
	@Test
	public void testMergeGts2DemoTrade() {
//		actGts2DataMergeManager.mergeDemoActTradeRecord();
	}

	/**
	 * 测试：合并Gts2模拟客户交易数据
	 */
	@Test
	public void testMergeGts2RealTrade() {
//		actGts2DataMergeManager.mergeRealActTradeRecord();
	}

	/**
	 * 测试：合并Gts2真实账号入金数据
	 */
	@Test
	public void testMergeGts2RealCashin() {
		actGts2DataMergeManager.mergeRealActCashin(Long.valueOf(CompanyEnum.fx.getId()),
				CompanyEnum.fx.getCode(),SyncDataUpdateTypeEnum.GTS2_FX_RREAL_CASHIN);
	}

	/**
	 * 测试：合并Gts2真实账号出金数据
	 */
	@Test
	public void testMergeGts2RealCashout() {
//		actGts2DataMergeManager.mergeRealActCashout();
	}
}
