package com.gwghk.ims.scheduling;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-task-context.xml", 
	"classpath:spring-task-mybatis.xml","classpath:spring-task-mvc.xml",
	"classpath:spring-task-mybatis-hx.xml","classpath:spring-task-mybatis-hxrealtrade.xml",
	"classpath:spring-task-mybatis-hxdemotrade.xml",
	"classpath:spring-task-consumer.xml",
	"classpath:spring-task-provider.xml","classpath:spring-kafka-producer.xml"
	,"classpath:mybatis.xml","classpath:mybatis-hx.xml","classpath:mybatis-hxrealtrade.xml"
	,"classpath:mybatis-hxdemotrade.xml"})
public abstract class BaseTest {

}
