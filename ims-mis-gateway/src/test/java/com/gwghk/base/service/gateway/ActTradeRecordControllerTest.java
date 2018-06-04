package com.gwghk.base.service.gateway;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;
import com.gwghk.ims.mis.gateway.controller.ActTradeRecordController;
import com.gwghk.unify.framework.common.util.JsonUtil;

public class ActTradeRecordControllerTest extends BaseTest {
	
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	ActTradeRecordController actTradeRecordController;
	
	
	@Test
	public void testPageList(){
		ActTradeRecordVO reqDto=new ActTradeRecordVO();
		reqDto.setEnv("real");
		reqDto.setAccountNo("258011818");
		reqDto.setTradeStartTime("2018-01-21 00:00:00");
		reqDto.setTradeEndTime("2018-03-30 00:00:00");
		reqDto.setSort("closeTime");
		MisRespResult<List<ActTradeRecordVO>> pageList = actTradeRecordController.pageList(reqDto);
		logger.info(JsonUtil.obj2Str(">>>>>>>>>:"+pageList));
	}
	
	

}
