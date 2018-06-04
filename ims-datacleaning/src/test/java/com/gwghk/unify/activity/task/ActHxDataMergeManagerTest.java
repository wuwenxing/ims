package com.gwghk.unify.activity.task;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.datacleaning.manager.HXMt4DataCleanManager;

public class ActHxDataMergeManagerTest extends BaseTest {

	@Autowired
	private HXMt4DataCleanManager actHxDataMergeManager;
	
	@Test
	public void testMergeRealActCustomerInfo(){
		actHxDataMergeManager.mergeRealActCustomerInfo();
	}
	
	@Test
	public void testMergeRealActTradeRecord(){
		actHxDataMergeManager.mergeRealActTradeRecord();
	}
	
	@Test
	@Ignore
	public void testMergeRealActCashin(){
		actHxDataMergeManager.mergeRealActCashin();
	}
	
	@Test
	@Ignore
	public void testMergeRealActCashout(){
		actHxDataMergeManager.mergeRealActCashout();
	}
}
	

	
