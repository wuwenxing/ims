package com.gwghk.ims.marketingtool.manager.push;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.common.MisRespResult;

@Component
@Transactional
public class MsgPushSMSService extends MsgPushAbsService{

	private static final Logger logger = LoggerFactory.getLogger(MsgPushSMSService.class);
	
	@Override
	protected MisRespResult<Map<String, Object>> push(Object vo) {
		logger.info("推送短信消息start...");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// TODO
		
		
		
		return MisRespResult.success(resultMap);
	}
	
}
