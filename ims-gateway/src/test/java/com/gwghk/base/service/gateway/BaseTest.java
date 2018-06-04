package com.gwghk.base.service.gateway;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-context.xml", "classpath:spring-dubbo-consumer.xml" })
public abstract class BaseTest {
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	// 模拟request,response
	protected MockHttpServletRequest req;
	protected MockHttpServletResponse resp;
	
	// 执行测试方法之前初始化模拟request,response
	@Before
	public void setUp() {
		req = new MockHttpServletRequest();
		req.setCharacterEncoding("UTF-8");
		resp = new MockHttpServletResponse();
	}
}
