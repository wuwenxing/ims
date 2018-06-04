package com.gwghk.ims.marketingtool;

import org.junit.Test;

import com.gwghk.ims.marketingtool.util.AppConfigUtil;

public class AppConfigTest extends BaseTest{

	@Test
	public void testAppConf(){
		System.out.println(AppConfigUtil.getProperty("sms.ucweb.fx.url"));
	}
}
