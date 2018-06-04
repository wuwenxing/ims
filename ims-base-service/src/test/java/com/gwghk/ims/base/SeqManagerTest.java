package com.gwghk.ims.base;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.base.manager.SeqManager;
import com.gwghk.ims.common.enums.SeqEnum;

/**
 * 摘要：统一序列号处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月31日
 */
public class SeqManagerTest extends BaseTest{
	
	@Autowired
	private SeqManager seqManager;
	
	@Test
	public void testUpdateSeq(){
		for(int i=0;i<5;i++){
			seqManager.updateSeq(SeqEnum.ActivityBonusRecordNumber.getSeqCode(),1L);
		}
		System.err.println(">>>>>>>>>>testUpdateSeq");
	}
}
