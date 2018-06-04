package com.gwghk.ims.activity.manager.api.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

/**
 * 
 * 摘要：真实账号累计亏损总额、模拟账号累计亏损总额
 *
 * @author greet.guo
 * @version 1.0
 * @Date 2018年05月15日
 */
@Component
@ActTaskType({ActTaskTypeEnum.REAL_TOTAL_CLOSE_LOSS,ActTaskTypeEnum.DEMO_TOTAL_CLOSE_LOSS})
public class TotalCloseLossTaskItemTemplate extends AbstractBaseTask {

	@Autowired
	private ActTradeRecordManager actTradeRecordManager;
	
	/**
	 * 任务描述
	 * 1、活动期间，达到账号累计平仓亏损总额的要求
	 * 2、如果达到要求则生成相应的任务完成记录
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform, Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		if(ActTaskTypeEnum.DEMO_TOTAL_CLOSE_LOSS.getCode().equals(actTask.getTaskItemCode())){
			accType = ActAccountTypeEnum.DEMO.getAliasCode() ;
		}
		//判断任务是否完成，奖励物品是否达到最高限制
		HashMap<String,Integer> map = findAvailableCount(actTask.getTaskItems(), actTask.getActivityPeriods(), actTask.getId().intValue(), accountNo, platform) ;
		if(null == map || map.size() == 0){
			return null ;
		}
		//取活动期间内的交易时间
		ActSetting taskDate = findActSettingByActNo(actTask.getActivityPeriods()) ;
		//总亏损金额
		BigDecimal totalLoss = new BigDecimal(0);
		//任务参数亏损金额
		BigDecimal taskLoss = new BigDecimal(actTask.getTaskItemVal()) ;
		ActTradeRecordVO vo = new ActTradeRecordVO() ;
		vo.setAccountNo(accountNo);
		vo.setPlatform(platform);
		vo.setEnv(accType);
		vo.setTradeStartTime(DateUtil.getDateSecondFormat(taskDate.getStartTime()));
		vo.setTradeEndTime(DateUtil.getDateSecondFormat(taskDate.getEndTime()));
		vo.setTradeType("out") ; 
		vo.setProfit(BigDecimal.valueOf(-0.000000001));//亏损查询
		vo.setOrder("asc") ; 
		vo.setSort("tradeTime") ; 
		
		//查询活动期间亏损列表
		List<ActTradeRecord> tradeList = actTradeRecordManager.findList(vo);
		//生成发放记录
		List<ImsTaskRecord> imsTaskRecordList = null;
		//交易完成时间
		Date tradeDate = null;
		if(CollectionUtils.isNotEmpty(tradeList)){
            for(int i=0;i<tradeList.size();i++){
            	//活动禁用期间数据排除
    			if(isDisabledActTradeData(actTask.getActivityPeriods(),companyId,tradeList.get(i).getTradeTime())) {
    				continue ;
    			}
            	
            	totalLoss = BigDecimalUtil.add(totalLoss, BigDecimalUtil.multiply(tradeList.get(i).getProfit(), BigDecimal.valueOf(-1)));
            	tradeDate = tradeList.get(i).getTradeTime();
            	if(totalLoss.compareTo(taskLoss)>=0){
                	//生成任务完成记录
    				imsTaskRecordList = builderTaskRecords(actTask, accountNo, platform, companyId, tradeDate, totalLoss, taskLoss,accType);
    				break;
                }
            }
        }
		
		return imsTaskRecordList;
	}
	
	/**
	 * 说明： 任务记录列表构建方法
	 * 
	 * @param actTask    活动
	 * @param accountNo  账号
	 * @param platform   平台
	 * @param companyId  公司
	 * @param tradeDate  最后交易时间
	 * @param totelLoss  累计亏损金额
	 * @param taskLoss   参数亏损金额
	 * @return List<ImsTaskRecord> 返回任务记录列表
	 */
	private List<ImsTaskRecord> builderTaskRecords(ActTaskSettingVO actTask,String accountNo, String platform,Long companyId,Date tradeDate,BigDecimal totelLoss,BigDecimal taskLoss,String accType){
		List<ImsTaskRecord> imsTaskRecordList=new ArrayList<ImsTaskRecord>();
		List<ActTaskItemsVO> items = actTask.getTaskItems() ;
		for(ActTaskItemsVO item : items) {
			ImsTaskRecord imsTaskRecord =initTaskRecord(actTask, item, accountNo, platform, companyId,accType) ;
			imsTaskRecord.setFinishAmount(totelLoss);
			imsTaskRecord.setTaskParamAmount(totelLoss);
			imsTaskRecord.setItemAmount(item.getItemPrice());
			imsTaskRecord.setItemAmountUnit(item.getItemParamValUnit());
			imsTaskRecord.setEqualValue(item.getItemPrice());
			imsTaskRecord.setTaskFinishTime(tradeDate);
			imsTaskRecord.setTaskEnd(true);
			imsTaskRecordList.add(imsTaskRecord) ;
		}
		return imsTaskRecordList;
	}

}
