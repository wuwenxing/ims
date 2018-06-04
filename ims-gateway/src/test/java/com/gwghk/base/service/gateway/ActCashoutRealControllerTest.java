package com.gwghk.base.service.gateway;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.ActCashoutRealDTO;
import com.gwghk.ims.gateway.controller.ActCashoutRealController;

public class ActCashoutRealControllerTest extends BaseTest{
	@Autowired
	private ActCashoutRealController controller;
	
	@Test
	public void testPageList() throws Exception {
		ActCashoutRealDTO dto=new ActCashoutRealDTO();
		dto.setRows(30);
		dto.setPage(1);
		req.setParameter("companyId", "3");
		controller.initHandle(req, resp);
		MisRespResult<List<ActCashoutRealDTO>> result= controller.pageList(dto);
		
		logger.info(">>>测试pageList结果:{}",JSON.toJSONString(result));
	}
}
