package com.gwghk.ims.message;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-message-context.xml","spring-message-mvc.xml","classpath:spring-message-mybatis.xml"})
public abstract class BaseTest {
	
}
