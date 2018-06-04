package com.gwghk.ims.marketingtool.manager.market;

import java.util.Map;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;

/**
 * 营销工具接口
 * @author wayne
 */
public interface ImsMarkingService {
	
	/**
	 * 流量及话费充值接口
	 * @param detail
	 * @return
	 */
	MisRespResult<Map<String, Object>> send(ImsRechargeLogDetailVO vo);
	
}
