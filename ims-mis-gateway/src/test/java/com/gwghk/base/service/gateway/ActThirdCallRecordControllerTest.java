package com.gwghk.base.service.gateway;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActThirdCallRecordVO;
import com.gwghk.ims.mis.gateway.controller.ActThirdCallRecordController;
import com.gwghk.unify.framework.common.util.JsonUtil;

public class ActThirdCallRecordControllerTest extends BaseTest {
	
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	ActThirdCallRecordController actThirdCallRecordController;
	
	
	@Test
	public void testPageList(){
		
		ActThirdCallRecordVO reqDto=new ActThirdCallRecordVO();
		reqDto.setAccountNo("258013139");
		reqDto.setActivityPeriods("hx_rw_20171211100148");
		MisRespResult<List<ActThirdCallRecordVO>> pageList = actThirdCallRecordController.pageList(reqDto);
		logger.info(JsonUtil.obj2Str(">>>>>>>>>:"+pageList));
	}
	
	@Test
	public void testFindById(){
		Long id=390786l;
		MisRespResult<ActThirdCallRecordVO> findById = actThirdCallRecordController.findById(id);
		logger.info(">>>>ActThirdCallRecordController findById :{}",findById);
	}
	
	
	@Test
	public void testRecall(){
		String ids="390788,390789";
		MisRespResult<Void> recall = actThirdCallRecordController.recall(ids);
		logger.info(">>>>>ActThirdCallRecordController testRecll result:{}",recall);
	}
	
	

}
