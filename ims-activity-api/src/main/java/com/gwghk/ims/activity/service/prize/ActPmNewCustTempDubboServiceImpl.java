package com.gwghk.ims.activity.service.prize;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.inf.external.prize.ActPmNewCustTempDubboService;

/**
 * 摘要：PM-GTS2新客活动 后赠处理service
 * @author warren.wu
 * @version 1.0
 * @Date 2018年04月20日
 */
@Service
public class ActPmNewCustTempDubboServiceImpl implements ActPmNewCustTempDubboService {
	private static Logger logger = LoggerFactory.getLogger(ActPmNewCustTempDubboServiceImpl.class);
	
	//@Autowired
	//private ActPmNewCustTempManager actPmNewCustTempManager;
	
	final Lock lock = new ReentrantLock();
	
	public ApiRespResult<Void> statAward(String activityPeriodsKey, String ruleCode, Long companyId, Date curDate) {
		try {
			lock.lock();
			logger.info(">>>开始处理PM新客活动--生成发放记录  开始，activityPeriodsKey={} , ruleCode={}, companyId={}, curDate={}",
					new Object[]{activityPeriodsKey, ruleCode, companyId, curDate});
			ApiRespResult<Void> result = new ApiRespResult<Void>();
			Date curTime = new Date();
			if (null != curDate){
				curTime = curDate;
			}
			//result = actPmNewCustTempManager.statAward(activityPeriodsKey, companyId, curTime, ruleCode);
			logger.info(">>>开始处理PM新客活动--生成发放记录  结束，activityPeriodsKey={} , ruleCode={}, companyId={}, curDate={}",
					new Object[]{activityPeriodsKey, ruleCode, companyId, curDate});
			return result;
		} finally {
            lock.unlock();
        }
	}

	@Override
	public ApiRespResult<Void> statqualify(String activityPeriodsKey, String ruleCode, Long companyId, Date curDate) {
		try {
			lock.lock();
			logger.info(">>>开始处理FX新客活动-统计后赠资格 开始，activityPeriodsKey={} , ruleCode={}, companyId={}, curDate={}",
					new Object[]{activityPeriodsKey, ruleCode, companyId, curDate});
			ApiRespResult<Void> result = new ApiRespResult<Void>();
			Date curTime = new Date();
			if (null != curDate){
				curTime = curDate;
			}
			//result = actPmNewCustTempManager.statQualify(activityPeriodsKey, companyId, curTime, ruleCode);
			logger.info(">>>开始处理FX新客活动-统计后赠资格 结束，activityPeriodsKey={} , ruleCode={}, companyId={}, curDate={}",
					new Object[]{activityPeriodsKey, ruleCode, companyId, curDate});
			return result;
		} finally {
            lock.unlock();
        }
	}
}