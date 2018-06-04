package com.gwghk.ims.scheduling.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.dto.job.ActAccountActivityStatRespDTO;
import com.gwghk.ims.common.enums.ActSettleStatusEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.external.activity.ActAccountActivityStatDubboService;
import com.gwghk.ims.common.inf.external.activity.ActSettlementDubboService;

/**
 * 
 * 摘要：活动到清算时间时执行清算
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月31日
 */
@Component
public class ActEntSettleJob extends AbstractActivityJob {
	@Autowired
	private ActAccountActivityStatDubboService actAccountActivityStatDubboService;
	@Autowired
	private ActSettlementDubboService actSettlementDubboService ;

	@Override
	protected void doJob(CompanyEnum company) {
		logger.info("--->开始处理{}【用户参与活动统计数据-ActEntSettleJob】", company.getCode());
		try {
			long startTime = System.currentTimeMillis();
			 ApiRespResult<List<ActAccountActivityStatRespDTO>> res = actAccountActivityStatDubboService.findListBySettleStatus(ActSettleStatusEnum.UNSETTLE.getCode()) ;
			 List<ActAccountActivityStatRespDTO> list = res.getData() ;
			 if(null != list) {
				 for(ActAccountActivityStatRespDTO act : list) {
						try{
							//判断活动是否到清算时间，如果到了则进入待清算状态
							if(act.getAboutSettleTime().compareTo(new Date()) > 0){
								logger.info("<--开始清算执行清算,活动：{},账号：{}，平台：{}",new Object[]{act.getActNo(),act.getAccountNo(),act.getPlatform()});
								actSettlementDubboService.beginUserSettlement(ActSettleTypeEnum.ACTEND, act.getAccountNo(), act.getActNo(), act.getPlatform(), null, act.getCompanyId()) ;
							}
						}catch(Exception e){
							logger.error("初始化任务记录异常：{}",e);
						}
					}
			 }
			logger.info("<---结束处理{}【用户参与活动统计数据-ActEntSettleJob】,耗时:{} s", company.getCode(),
					(System.currentTimeMillis() - startTime) / 1000f);
		} catch (Exception e) {
			logger.error("<---结束处理{}【用户参与活动统计数据-ActEntSettleJob】,系统异常!!!{}", company.getCode(), e);
		}
	}
	
}
