package com.gwghk.ims.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.issue.item.WithGoldApiManager;
import com.gwghk.ims.activity.manager.prize.ActPrizeRecordApiManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.inf.external.activity.ActPrizeRecordApiDubboService;

@Service
public class ActPrizeRecordApiDubboServiceImpl  implements ActPrizeRecordApiDubboService{
	private final Logger logger=LoggerFactory.getLogger(ActPrizeRecordApiDubboServiceImpl.class);

	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	@Autowired
	private ActAccountActiviStatManager accountActStatManager;
	@Autowired
	private ActSettingManager actSettingManager;
	@Autowired 
	private WithGoldApiManager withGoldApiManager;
	
	@Autowired
	private ActPrizeRecordApiManager actPrizeRecordApiManager;
	
	@Override
	public MisRespResult<Void> batchRelease(String accountNo,String env,Long companyId) {
		try {
			logger.info(">>>开始释放账号{} 的赠金-----------------",accountNo);
			//找出用户参与的所有的活动
			List<ActAccountActiviStat> actList=accountActStatManager.findByAccountNo(accountNo, companyId);
			
			for(ActAccountActiviStat act:actList) {
				ActSetting actSetting=actSettingManager.findByactivityPeriods(act.getActNo());
				if(actSetting==null) {
					logger.warn(">>>活动编号{}不存在",act.getActNo());
					continue;
				}
				
				//找出用户参加本活动的发放记录
				List<VActPrizeRecord> recordlist=imsPrizeRecordManager.findVByAccount(accountNo, ActItemTypeEnum.WITHGOLD.getValue(),act.getActNo());
				for(VActPrizeRecord record:recordlist) {
					logger.info(">>>开始处理发放记录{} 的释放-----------------",record.getRecordNo());
					
					withGoldApiManager.releaseBonus(record, record.getActName());
					
					logger.info(">>>完成处理发放记录{} 的释放-----------------",record.getRecordNo());
				}
			}
		
			return MisRespResult.success();
		}catch(Exception e) {
			e.printStackTrace(); 
			logger.error("<--系统异常,【batchSend,accountNo:{},companyId:{},Exception:{}", new Object[]{accountNo,companyId,e});
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<Void> send(String prizeNo,Integer releaseType,String actNo,String env,Long companyId) {
		try {
			boolean isSuccess=actPrizeRecordApiManager.send(prizeNo,releaseType, actNo, env, companyId);
			
			if(!isSuccess)
				logger.error(">>>执行发放记录{}的发放,结果失败-----------------",prizeNo);
				
			return isSuccess ? MisRespResult.success() : MisRespResult.error("发放失败");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("<--系统异常,【Send,prizeNo:{},actNo:{},companyId:{},Exception:{}", prizeNo,actNo,companyId,e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
