package com.gwghk.ims.activity.service.prize;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.inf.external.prize.ActAccountActStatDubboService;

/**
 * 
 * @ClassName: ActAccountActStatDubboServiceImpl
 * @Description: 用户参与活动统计(提供第三方数据源)
 * @author lawrence
 * @date 2018年4月23日
 *
 */
@Service
public class ActAccountActStatDubboServiceImpl implements ActAccountActStatDubboService {

	//@Autowired
	//private ActAccountActStatManager actAccountActStatManager; 

	@Override
	public void doStatRecord(Long companyId) {
		//actAccountActStatManager.doStatRecord(companyCode);
	}

}
