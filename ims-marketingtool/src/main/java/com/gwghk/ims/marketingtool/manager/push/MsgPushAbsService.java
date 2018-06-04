package com.gwghk.ims.marketingtool.manager.push;

import java.util.Map;

import com.gwghk.ims.common.common.MisRespResult;

public abstract class MsgPushAbsService {
	
	protected abstract MisRespResult<Map<String, Object>> push(Object vo);
	
}
