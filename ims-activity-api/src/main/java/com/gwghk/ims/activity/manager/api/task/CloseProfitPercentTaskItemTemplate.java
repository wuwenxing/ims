package com.gwghk.ims.activity.manager.api.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActTradeRecord;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActTradeRecordManager;
import com.gwghk.ims.activity.manager.api.AbstractBaseTask;
import com.gwghk.ims.common.annotation.ActTaskType;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;
import com.gwghk.ims.common.vo.activity.ImsTaskRecordVO;

/**  
* 摘要: 账号单笔平仓盈利%
* @author George.li  
* @date 2018年5月16日  
* @version 1.0  
*/
@Component
@ActTaskType({ ActTaskTypeEnum.REAL_CLOSE_PROFIT_PERCENT, ActTaskTypeEnum.DEMO_CLOSE_PROFIT_PERCENT })
public class CloseProfitPercentTaskItemTemplate extends AbstractBaseTask {

	 
	@Autowired
	private ActTradeRecordManager  actTradeRecordManager;
	 
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform, Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		if(ActTaskTypeEnum.DEMO_CLOSE_PROFIT_PERCENT.getCode().equals(actTask.getTaskItemCode())){
			accType = ActAccountTypeEnum.DEMO.getAliasCode() ;
		}
		//判断任务是否完成，奖励物品是否达到最高限制
		HashMap<String,BigDecimal> map = findAvailableMoney(actTask.getTaskItems(), actTask.getActivityPeriods(), actTask.getId().intValue(), accountNo, platform) ;
		if(null == map || map.size() == 0){
			return null ;
		}
		ActSetting actSetting=findActSettingByActNo(actTask.getActivityPeriods());
		
		List<ImsTaskRecord> imsTaskRecordList=new ArrayList<ImsTaskRecord>();;
		 
		// 任务要求的盈利金额
		BigDecimal taskLoss=BigDecimalUtil.roundDown4(new BigDecimal(actTask.getTaskItemVal()));
	 
		//循环型任务: 取出上一次任务的完成时间点，从上次一次的时间开始查询,非循环性取活动的开始时间和结束时间
		Date[] taskDate = getTaskDate( actTask.getActivityPeriods(), actTask.getId(), accountNo, platform,actTask.getTaskGroup()) ;
	
		//指定时间范围内的交易记录数 
		ActTradeRecordVO tradeRecordVO= new ActTradeRecordVO();
		tradeRecordVO.setAccountNo(accountNo);
		tradeRecordVO.setPlatform(platform);
		tradeRecordVO.setEnv(accType);
		tradeRecordVO.setTradeStartTime(DateUtil.getDateSecondFormat(actSetting.getStartTime()));
		tradeRecordVO.setTradeEndTime(DateUtil.getDateSecondFormat(actSetting.getEndTime()));
		tradeRecordVO.setTradeType("out");
		tradeRecordVO.setOrder("asc"); 
		tradeRecordVO.setSort("tradeTime");
		List<ActTradeRecord> tradeRecordList = actTradeRecordManager.findList(tradeRecordVO);
		boolean finishTask=false;//已经达到最大领取，完成任务
		BigDecimal totalFinishAmount=new BigDecimal(0);
		for(ActTradeRecord tradeRecord:tradeRecordList) {
			if(finishTask) {
				break;
			}
			//活动禁用期间数据排除
			if(isDisabledActTradeData(actTask.getActivityPeriods(),companyId,tradeRecord.getTradeTime())) {
				continue ;
			}
			//是否盈利条件
			if(tradeRecord.getTradeTime().compareTo(taskDate[0])>0 && tradeRecord.getProfit().doubleValue()>0 && tradeRecord.getProfit().subtract(taskLoss).doubleValue()>= 0) {
				List<ActTaskItemsVO> items = actTask.getTaskItems() ;
                BigDecimal itemGiftAmount = BigDecimal.ZERO; 
                BigDecimal issueAmount = BigDecimal.ZERO;//实际发放的额度 
				for(ActTaskItemsVO item : items) {
					if(finishTask) {
						break;
					} 
					ImsTaskRecordVO imsTaskRecordVO=new ImsTaskRecordVO();
					imsTaskRecordVO.setActNo(actTask.getActivityPeriods());
					imsTaskRecordVO.setAccountNo(accountNo);	
					imsTaskRecordVO.setActTaskSettingId(actTask.getId().intValue());
					imsTaskRecordVO.setTaskGroup(actTask.getTaskGroup());//层级型任务参数
					imsTaskRecordVO.setStartTimeStr(DateUtil.getDateSecondFormat(actSetting.getStartTime()));
					imsTaskRecordVO.setEndTimeStr(DateUtil.getDateSecondFormat(actSetting.getEndTime()));
					List<ImsTaskRecord> taskRecordList=findTaskRecord(actTask.getActivityPeriods(),imsTaskRecordVO);
					
					//该账号在指定期间的任务完成统计数据
					DoubleSummaryStatistics taskRecordStatistics=taskRecordList.stream().filter(e->e.getFinishAmount()!=null).mapToDouble(e->e.getFinishAmount().doubleValue()).summaryStatistics();
					
					//该活动、任务、条件下已经领取的金额
					BigDecimal finishAmount = new BigDecimal(taskRecordStatistics.getSum());
					totalFinishAmount=totalFinishAmount.add(finishAmount);
					
					//物品最高可领取金额
					BigDecimal receiveMaxMoney=item.getReceiveMaxMoney();
					
					BigDecimal itemParam = StringUtils.isNotBlank(item.getItemParamVal())?BigDecimalUtil.convertDownFromDouble4(item.getItemParamVal()):BigDecimal.ZERO;// 物品奖励比例
					
					//本次需要发放额度
					itemGiftAmount = BigDecimalUtil.roundDown4(tradeRecord.getProfit().multiply(itemParam).divide(new BigDecimal(100)));
					 
					// 最高可领取金额-实际已经领取金额 比较 本次需领取金额
					if(totalFinishAmount.compareTo(receiveMaxMoney)>=0 || totalFinishAmount.compareTo(receiveMaxMoney)>=0) {
						finishTask=true;
						break;
					}
					if(receiveMaxMoney.subtract(totalFinishAmount).compareTo(itemGiftAmount)>=0) {
                    	issueAmount=itemGiftAmount;
                    }else {
                    	issueAmount=receiveMaxMoney.subtract(totalFinishAmount);
                    	finishTask=true;//已经达到最大领取额
                    }
					totalFinishAmount=totalFinishAmount.add(issueAmount);
					//生成任务完成记录
					ImsTaskRecord task = initTaskRecord(actTask, item, accountNo, platform, companyId,accType) ;
					task.setFinishAmount(tradeRecordVO.getProfit());
					task.setTaskFinishTime(tradeRecord.getTradeTime());
					task.setFinishAmount(issueAmount);//已领取金额
					task.setItemAmount(issueAmount);//发放金额
					task.setItemAmountUnit(actTask.getTaskItemValUnit());//发放单位
					task.setTaskParamAmount(taskLoss);
					task.setTaskEnd(finishTask);
					imsTaskRecordList.add(task);
				}
			}	
		} 
		return imsTaskRecordList;
	}

}
