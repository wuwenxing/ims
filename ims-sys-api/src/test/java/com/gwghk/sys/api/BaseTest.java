package com.gwghk.sys.api;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-sys-context.xml"
	, "classpath:spring-sys-mybatis.xml"
	, "classpath:spring-sys-redis.xml"
	, "classpath:spring-sys-ehcache.xml"})
public abstract class BaseTest {
	
}
