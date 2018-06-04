package com.gwghk.ims.activity.service.prize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.inf.external.prize.ActProgressDubboService;

/**
 * 摘要：发放处理服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月26日
 */
@Service
public class ActProgressDubboServiceImpl implements ActProgressDubboService {
	private static Logger logger = LoggerFactory.getLogger(ActProgressDubboServiceImpl.class);

//	@Autowired
//	private ActProgressManager actProgressManager;

	/**
	 * 功能：活动进度自动统计
	 */
	public ApiRespResult<Void> doAutoActProgressStat(Long companyId) {
		logger.info("--->开始活动进度自动统计处理:【companyId:{}】", companyId);
		ApiRespResult<Void> result = new ApiRespResult<Void>();
		try {
			/*result = actProgressManager.doAutoActProgressStat(companyId);
			if (result.isOk()) {
				logger.info("<---完成活动进度自动统计处理,谢谢参与！");
			} else {
				logger.error("<---活动进度自动统计失败！code:{},msg；{}", new Object[] { result.getCode(), result.getMsg() });
			}*/
			return result;
		} catch (Exception e) {
			logger.error("<---活动进度自动统计失败！系统出现异常！", e);
			return result.setRespMsg(ApiResultCode.EXCEPTION);
		}
	}
}