package com.gwghk.ims.activity.manager.prize;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.ImsTaskRecordManager;
import com.gwghk.ims.activity.manager.issue.IssueItemManager;
import com.gwghk.ims.activity.service.ActPrizeRecordApiDubboServiceImpl;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActSettleStatusEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;

@Component
public class ActPrizeRecordApiManager {
	private final Logger logger=LoggerFactory.getLogger(ActPrizeRecordApiDubboServiceImpl.class);

	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	@Autowired
	private ActAccountActiviStatManager accountActStatManager;
	@Autowired
	private ActSettingManager actSettingManager;
	/*@Autowired 
	private WithGoldApiManager withGoldApiManager;
	@Autowired
	private TokenCoinApiManager tokenCoinApiManager;
	@Autowired
	private AnalogCoinApiManager analogCoinApiManager;*/
	@Autowired
	private ImsTaskRecordManager imsTaskRecordManager;
	
	@Autowired
	private IssueItemManager issueItemManager;
	
	/**
	 * 执行发放记录具体的发放
	 * @param prizeNo 发放记录编号
	 * @param releaseType 释放类型，可以为空，这个主要提供给定制任务
	 * @param actNo
	 * @param env
	 * @param companyId
	 * @return
	 */
	public boolean send(String prizeNo,Integer releaseType,String actNo,String env,Long companyId) {
		try {
			logger.info(">>>开始执行发放记录{}的发放-----------------",prizeNo);
			
			if(releaseType==null) {
				releaseType=1;
			}
			
			ActPrizeRecordExt imsPrizeRecordExt=new ActPrizeRecordExt();
			imsPrizeRecordExt.setRecordNumber(prizeNo);
			imsPrizeRecordExt.setActNo(actNo);
			//目前所有的任务都是按照比例释放，除了入金任务以外另行考虑（是按照层级释放 还是 按照任务完成释放)
			imsPrizeRecordExt.setReleaseType(releaseType);
			imsPrizeRecordExt.setWithdrawalStartTime(new Date());
			imsPrizeRecordExt.setFinishedTradeLots(BigDecimal.ZERO);
			imsPrizeRecordExt.setCompanyId(companyId);
			
			//1.释放类型  2.需要手术  3.统计手数的开始时间
			imsPrizeRecordExt.beforeSave();
			logger.info(">>>【发放记录{}】更新发放记录扩展表，数据{}",prizeNo,JSON.toJSONString(imsPrizeRecordExt));
			imsPrizeRecordManager.saveOrUpdateExt(imsPrizeRecordExt);
			
			VActPrizeRecord vActPrizeRecord=imsPrizeRecordManager.findVByRecordNo(prizeNo,actNo);
			if(vActPrizeRecord==null)
				throw new Exception("发放记录【编号"+prizeNo+"】不存在");
			ActSetting actSetting=actSettingManager.findByactivityPeriods(vActPrizeRecord.getActNo());
			if(actSetting==null) 
				throw new Exception("活动【编号:"+vActPrizeRecord.getActNo()+"】不存在");
			
			//更新释放要用的关键数据
			if(ActTypeEnum.RJZJ.getCode().equals(actSetting.getActivityType()))//入金型任务先标记为禁止释放
				imsPrizeRecordExt.setReleaseType(3);
				
			updateReleaseData(vActPrizeRecord, imsPrizeRecordExt);
			
			//统计参与的活动
			ActAccountActiviStat accountActiviStat=new ActAccountActiviStat();;
			accountActiviStat.setAccountNo(vActPrizeRecord.getAccountNo());
			accountActiviStat.setPlatform(vActPrizeRecord.getPlatform());
			accountActiviStat.setEnv(env);
			accountActiviStat.setCustMobile(vActPrizeRecord.getCustMobile());
			accountActiviStat.setCustName(vActPrizeRecord.getCustName());
			accountActiviStat.setActStartTime(actSetting.getStartTime());
			accountActiviStat.setActEndTime(actSetting.getEndTime());
			accountActiviStat.setSettleStatus(ActSettleStatusEnum.UNSETTLE.getCode());
			long settleTime=actSetting.getEndTime().getTime()+3600l*24*1000*actSetting.getFinishDays();
			accountActiviStat.setAboutSettleTime(new Date(settleTime));
			accountActiviStat.setActName(actSetting.getActivityName());
			accountActiviStat.setActNo(actSetting.getActivityPeriods());
			accountActiviStat.setCompanyId(companyId);
			accountActiviStat.initBaseParam();
			accountActiviStat.beforeSave();
			
			
			logger.info(">>>发放记录{} ,更新用户参与活动统计表，数据{}",prizeNo,JSON.toJSONString(accountActiviStat));
			accountActStatManager.saveOrUpdate(accountActiviStat);
			
			//开始对发放记录进行发放处理
			String sendType=ActItemTypeEnum.VIRTUAL.getValue().equals(vActPrizeRecord.getItemType()) ? vActPrizeRecord.getItemCategory() : vActPrizeRecord.getItemType();
			
			return issueItemManager.send(prizeNo, sendType, actNo, vActPrizeRecord.getAccountNo(), companyId);
			
		}catch(Exception e) {
			logger.error("<--系统异常,【Send,prizeNo:{},actNo:{},companyId:{}", prizeNo,actNo,companyId,e);
			return false;
		}
	}
	
	/**
	 * 更新一些基本信息，为释放做准备
	 * @param vActPrizeRecord
	 * @param imsPrizeRecordExt
	 */
	private void updateReleaseData(VActPrizeRecord vActPrizeRecord,ActPrizeRecordExt imsPrizeRecordExt) {
		ImsTaskRecord taskRecord = imsTaskRecordManager.findById(vActPrizeRecord.getTaskRecordId().intValue(), vActPrizeRecord.getActNo()) ;

		if(taskRecord.getItemType().equals(ActItemTypeEnum.WITHGOLD.getValue()) || taskRecord.getItemType().equals(ActItemTypeEnum.TOKENCOIN.getValue())){
			imsPrizeRecordExt.setReleaseType(1);
			vActPrizeRecord.setNeedTradeLots(taskRecord.getNeedTradeLot());
			imsPrizeRecordExt.setNeedTradeLots(taskRecord.getNeedTradeLot());
			imsPrizeRecordExt.setTaskId(Long.parseLong(taskRecord.getActTaskSettingId().toString()));
			imsPrizeRecordExt.setTaskItemsId(taskRecord.getTaskItemId());
			if(imsPrizeRecordExt.getReleaseType()!=3)//等于3 先暂时不设置统计时间
				imsPrizeRecordExt.setWithdrawalStartTime(taskRecord.getTaskFinishTime());
			imsPrizeRecordExt.beforeUpdate();
			
			imsPrizeRecordManager.saveOrUpdateExt(imsPrizeRecordExt);
		}
	}
}
