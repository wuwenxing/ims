package com.gwghk.ims.common.inf.external.marketingtool;

import java.util.Map;

import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 
 * @ClassName: CamiDubboService
 * @Description: 卡密接口实现
 * @author lawrence
 * @date 2018年5月7日
 *
 */
public interface CamiDubboService {

	ApiRespResult<Map<String, Object>> send(String mobile, String orderNo,String giftName,
			String camiChannel,String ip, Map<String, String> extMap,Long companyId);

}
