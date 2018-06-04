package com.gwghk.ims.monitor;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-monitor-context.xml","spring-monitor-mvc.xml","classpath:spring-monitor-mybatis.xml"})
public abstract class BaseTest {

}
