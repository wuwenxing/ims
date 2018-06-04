package com.gwghk.base.service.gateway;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.dto.activity.ActCustomerInfoDTO;
import com.gwghk.ims.gateway.controller.ActCustomerInfoController;

public class ActCustomerInfoControllerTest extends BaseTest {
	@Autowired
	private ActCustomerInfoController controller;
	@Test
	public void testPageList() throws Exception {
		ActCustomerInfoDTO reqDto=new ActCustomerInfoDTO();
		
		reqDto.setAccountType("real");
		reqDto.setAccountEnv("real");
		reqDto.setPage(1);
		reqDto.setRows(30);
		req.setParameter("companyId", "3");
		controller.initHandle(req, resp);
		MisRespResult<List<ActCustomerInfoDTO>> result=controller.pageList(reqDto);
		
		logger.info(">>>输出pageList结果：{}",JSON.toJSONString(result));
	}
	
	@Test
	public void testGetAccountInfo() throws Exception {
		req.setParameter("companyId", "3");
		controller.initHandle(req, resp);
		MisRespResult<ActCustomerInfoDTO> result=controller.getAccountPlatformInfo("86000021", "GTS2");
		logger.info(">>>输出getAccountInfo结果:{}",JSON.toJSONString(result));
	}
}
