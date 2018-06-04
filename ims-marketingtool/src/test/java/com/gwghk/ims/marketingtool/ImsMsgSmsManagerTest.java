package com.gwghk.ims.marketingtool;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgDataVO;
import com.gwghk.ims.marketingtool.manager.ImsMsgSmsManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 
 * 摘要：短信发送Manager Test
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月4日
 */
public class ImsMsgSmsManagerTest extends BaseTest{

	@Autowired
	private ImsMsgSmsManager imsMsgSmsManager ;
	
	
	@Test
	public void testSendSmsMsgByTpl(){
		String mobile = "13800138000" ;
		String source = "test" ;
		String businessNo = "10000001" ;
		String sendTime = "" ;
		ImsMsgBindVO imsMsgBindVO = new ImsMsgBindVO() ;
		imsMsgBindVO.setSmsCode("A0001");
		Long companyId = 1L ;
		imsMsgSmsManager.sendSmsMsgByTpl(mobile, source, businessNo, sendTime, imsMsgBindVO, null, companyId) ;
	}
	
	@Test
	public void testWaitingSendList(){
		imsMsgSmsManager.waitingSendList(1L);
	}
	
	@Test
	public void testFindList(){
		String mobile = "13800138000" ;
		String content = "测试短信自定义发送内容" ;
		String source = "测试" ;
		String businessNo = "100000" ;
		Long companyId = 1L ;
		MisRespResult<Void> list = imsMsgSmsManager.sendSmsMsgByCustom(mobile, content, businessNo, "test", companyId) ;
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		String mobile = "13800138000" ;
		String content = "测试短信自定义发送内容" ;
		String source = "测试" ;
		String businessNo = "100000" ;
		Long companyId = 1L ;
		ImsMsgBindVO msgBind = new ImsMsgBindVO() ;
		ImsMsgDataVO msgData = new ImsMsgDataVO() ;
		MisRespResult<Void> task = imsMsgSmsManager.sendSmsMsgByTpl(mobile, source, businessNo, "", msgBind, msgData, companyId) ;
		System.out.println(JsonUtil.obj2Str(task));
	}
	
	

}
