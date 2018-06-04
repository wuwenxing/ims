package com.gwghk.ims.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-base-service-context.xml"
	, "classpath:spring-base-service-mybatis.xml"
	, "spring-base-service-ehcache.xml"})
public abstract class BaseTest {
}
