package com.gwghk.ims.message;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class DisruptorKafkaGts2DealDataMainTest  extends BaseTest{

	/*@Autowired
	private DisruptorKafkaGts2DealDataMain disruptorKafkaGts2DealDataMain;*/
	
	@Test
	public void testProduct() throws Exception{
		String message = "{\"C\":\"2017-08-22 03:26:51.722+00\",\"A\":\"U\",\"S\":\"trade_gts2\",\"T\":\"gts2group\",\"O\":{\"uuid\": 2230, \"uutime\": \"2017-08-22 02:58:56+00\", \"moditime\": \"2017-08-22 02:58:56+00\", \"marginlevel\": 0},\"N\":{\"id\":57,\"uuid\":2232,\"uutime\":\"2017-08-22 03:26:52+00\",\"serverid\":2,\"name\":\"NZ/CNY/GTS/MM/STD/0/A\",\"currency\":\"CNY \",\"platform\":\"GTS2    \",\"authentication\":\"STD \",\"minpwdlength\":8,\"companyid\":1,\"company\":\"gwfx\",\"companysite\":\"\",\"companyemail\":\"\",\"supportsite\":\"\",\"supportemail\":\"\",\"templatesfolder\":\"\",\"margincalllevel\":50,\"stopoutlevel\":20,\"weekendlevel\":40,\"ordermax\":20,\"enable\":1,\"clearnegative\":1,\"createtime\":\"2015-06-25 08:34:46+00\",\"createuserid\":1,\"moditime\":\"2017-08-22 03:26:52+00\",\"modiuserid\":2,\"status\":0,\"volumesmax\":100,\"level\":\"STD      \",\"balanceclearnegative\":0,\"marginlevel\":1},\"U\":\"4d1c311d-3e40-41bc-9966-c687b2d25ebb\"}";
//		KafkaGTS2CommonData commonData = JsonUtil.json2Obj(value, KafkaGTS2CommonData.class);
		//disruptorKafkaGts2DealDataMain.product(message, ActEnv.REAL.getValue());
		TimeUnit.MINUTES.sleep(10);
	}
}
