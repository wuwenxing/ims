package com.gwghk.ims.marketingtool.manager.push;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.enums.MsgPushChannelEnum;

/**
 * 推送代理对象
 * @author wayne
 *
 */
@Component
@Transactional
public class MsgPushProxyManager extends MsgPushAbsService{

	@Autowired
	private MsgPushSMSService msgPushSMSService;
	@Autowired
	private MsgPushEmailService msgPushEmailService;
	@Autowired
	private MsgPushPCUIService msgPushPCUIService;
	@Autowired
	private MsgPushIOSService msgPushIOSService;
	@Autowired
	private MsgPushAndroidService msgPushAndroidService;
	@Autowired
	private MsgPushWxChartService msgPushWxChartService;
	
	@Override
	protected MisRespResult<Map<String, Object>> push(Object vo) {
		if(MsgPushChannelEnum.SMS.getCode().equals(vo)){
			return msgPushSMSService.push(vo);
		}else if(MsgPushChannelEnum.Email.getCode().equals(vo)){
			return msgPushEmailService.push(vo);
		}else if(MsgPushChannelEnum.PCUI.getCode().equals(vo)){
			return msgPushPCUIService.push(vo);
		}else if(MsgPushChannelEnum.IOS.getCode().equals(vo)){
			return msgPushIOSService.push(vo);
		}else if(MsgPushChannelEnum.Android.getCode().equals(vo)){
			return msgPushAndroidService.push(vo);
		}else if(MsgPushChannelEnum.wxChart.getCode().equals(vo)){
			return msgPushWxChartService.push(vo);
		}else{
			return MisRespResult.error("类型错误");
		}
	}
	
}
