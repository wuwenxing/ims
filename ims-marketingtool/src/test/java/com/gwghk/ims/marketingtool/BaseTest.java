package com.gwghk.ims.marketingtool;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-marketingtool-context.xml", "classpath:spring-marketingtool-mybatis.xml",
		"classpath:spring-marketingtool-consumer.xml",
		"classpath:spring-marketingtool-provider.xml",
		"classpath:spring-marketingtool-producer.xml"})
public abstract class BaseTest {
}
