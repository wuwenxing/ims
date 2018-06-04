package com.gwghk.unify.activity.task;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-datacleaning-context.xml", 
	"classpath:spring-datacleaning-mybatis.xml","classpath:spring-datacleaning-mvc.xml",
	"classpath:spring-datacleaning-mybatis-hx.xml","classpath:spring-datacleaning-mybatis-hxrealtrade.xml",
	"classpath:spring-datacleaning-mybatis-hxdemotrade.xml",
	"classpath:spring-datacleaning-consumer.xml",
	"classpath:spring-datacleaning-provider.xml","classpath:spring-kafka-producer.xml"
	,"classpath:mybatis.xml","classpath:mybatis-hx.xml","classpath:mybatis-hxrealtrade.xml"
	,"classpath:mybatis-hxdemotrade.xml"})
public abstract class BaseTest {

}
