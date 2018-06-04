package com.gwghk.base.service.gateway;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.ActSettingDetailsDTO;
import com.gwghk.ims.gateway.controller.ActSettingController;

public class ActSettingControllerTest extends BaseTest {
	@Autowired
	private ActSettingController controller;
	
	@Test
	public void testFindActByNo() throws Exception {
		req.setParameter("companyId", "1");
		controller.initHandle(req, resp);
		MisRespResult<ActSettingDetailsDTO> result=controller.findByActivityPeriods("FX_1524535588810");
		
		logger.info(">>>测试FindActByNo结果:{}",JSON.toJSONString(result));
	}
}
