package com.gwghk.ims.mis.gateway;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * 摘要：键值对Controller测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月23日
 */
public class KeyValControllerTest extends BaseTest{
	//基本请求的路径
	public final String basePath = "/mis/base/keyval";
	
	@Test
	public void testPageList() throws Exception{
		String responseString = mockMvc.perform(get(basePath+"/page")
									   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
									   .param("companyId", "1"))
									   .andExpect(status().isOk())
									   .andReturn().getResponse().getContentAsString();
		System.err.println("<---testPageList返回的结果：" + responseString);
	}
	
	@Test
	public void testFindById() throws Exception{
		String responseString = mockMvc.perform(get("/mis/base/keyval/320")
									   .contentType(MediaType.APPLICATION_FORM_URLENCODED))
									   .andExpect(status().isOk())
									   .andReturn().getResponse().getContentAsString();
		System.err.println("<---testFindById返回的结果：" + responseString);
	}
}
