package com.gwghk.base.service.gateway;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.PrizeRecordRedoVO;
import com.gwghk.ims.mis.gateway.controller.ActPrizeRecordRedoController;
import com.gwghk.unify.framework.common.util.JsonUtil;

public class PrizeRecordRedoControllerTest extends BaseTest {
	
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	ActPrizeRecordRedoController prizeRecordRedoController;
	
	
	@Test
	public void testPageList(){
		PrizeRecordRedoVO reqDto=new PrizeRecordRedoVO();
		reqDto.setActivityPeriods("fx_rw_20180118135758");
		MisRespResult<List<PrizeRecordRedoVO>> pageList = prizeRecordRedoController.pageList(reqDto);
		logger.info(JsonUtil.obj2Str(">>>>>>>>>PrizeRecordRedoController pageList result:"+pageList));
	}
	
	@Test
	public void testBatchResend(){
		String idArray="3386,3387";
		 MisRespResult<Void> batchResend = prizeRecordRedoController.batchResend(idArray);
		logger.info(">>>>PrizeRecordRedoController batchResend result:{}",batchResend);
	}
	
	
	@Test
	public void testBatchDelete(){
		String idArray="3385,3384";
		MisRespResult<Void> batchDelete = prizeRecordRedoController.batchDelete(idArray);
		logger.info("PrizeRecordRedoController batchDelete result:{}",batchDelete);
		
	}
	

}
