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
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.util.ImsBigDecimalUtil;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;

/**
 * 
 * 摘要：真实账号累计开仓手数、模拟账号累计开仓手数
 *
 * @author greet.guo
 * @version 1.0
 * @Date 2018年05月18日
 */
@Component
@ActTaskType({ActTaskTypeEnum.REAL_TOTAL_OPEN_LOT,ActTaskTypeEnum.DEMO_TOTAL_OPEN_LOT})
public class TotalOpenLotTaskItemTemplate extends AbstractBaseTask {

	@Autowired
	private ActTradeRecordManager actTradeRecordManager;
	
	/**
	 * 任务描述
	 * 1、活动期间，达到账号累计开仓手数的要求
	 * 2、如果达到要求则生成相应的任务完成记录
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform, 
			Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		if(ActTaskTypeEnum.DEMO_TOTAL_OPEN_LOT.getCode().equals(actTask.getTaskItemCode())){
			accType = ActAccountTypeEnum.DEMO.getAliasCode() ;
		}
		//判断任务是否完成，奖励物品是否达到最高限制
		HashMap<String,Integer> map = findAvailableCount(actTask.getTaskItems(), actTask.getActivityPeriods(), actTask.getId().intValue(), accountNo, platform) ;
		if(null == map || map.size() == 0){
			return null ;
		}
		//取活动期间内的交易时间
		ActSetting taskDate = findActSettingByActNo(actTask.getActivityPeriods()) ;
		//平仓累计手数
		BigDecimal totalOpenLots = BigDecimal.ZERO; //统计的总平仓次数
		//任务参数平仓手数
        BigDecimal taskOpenLots = BigDecimal.valueOf(Long.valueOf(actTask.getTaskItemVal())); 
		ActTradeRecordVO vo = new ActTradeRecordVO() ;
		vo.setAccountNo(accountNo);
		vo.setPlatform(platform);
		vo.setEnv(accType);
		vo.setTradeStartTime(DateUtil.getDateSecondFormat(taskDate.getStartTime()));
		vo.setTradeEndTime(DateUtil.getDateSecondFormat(taskDate.getEndTime()));
		vo.setTradeType("in") ; 
		vo.setOrder("asc") ; 
		vo.setSort("tradeTime") ; 
		
		//查询开仓次数
		List<ActTradeRecord> tradeList = actTradeRecordManager.findList(vo);
		//生成发放记录
		List<ImsTaskRecord> imsTaskRecordList = null;
		//交易完成时间
		Date tradeDate = null;
		if(CollectionUtils.isNotEmpty(tradeList)){
			for(int i=0;i<tradeList.size();i++){
				//排除活动禁用期间数据
				if(isDisabledActTradeData(actTask.getActivityPeriods(), companyId, tradeDate)) {
					continue;
				}
				totalOpenLots = totalOpenLots.add(ImsBigDecimalUtil.roundDown4(tradeList.get(i).getTradeLot()));
            	if(totalOpenLots.compareTo(taskOpenLots) >= 0){
                	//生成任务完成记录
    				imsTaskRecordList = builderTaskRecords(actTask, accountNo, platform, companyId, tradeDate,accType,taskOpenLots,totalOpenLots);
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
	 * @param taskOpenLots  任务交易手数
	 * @param totalOpenLots  总交易手数
	 * @return List<ImsTaskRecord> 返回任务记录列表
	 */
	private List<ImsTaskRecord> builderTaskRecords(ActTaskSettingVO actTask,String accountNo, String platform,Long companyId,Date tradeDate,String accType,BigDecimal taskOpenLots,BigDecimal totalOpenLots){
		List<ImsTaskRecord> imsTaskRecordList=new ArrayList<ImsTaskRecord>();
		List<ActTaskItemsVO> items = actTask.getTaskItems() ;
		for(ActTaskItemsVO item : items) {
			ImsTaskRecord imsTaskRecord =initTaskRecord(actTask, item, accountNo, platform, companyId,accType) ;
			
			imsTaskRecord.setTaskParamLot(taskOpenLots);
			//imsTaskRecord.setFinishLot(totalOpenLots);
			imsTaskRecord.setItemAmount(item.getItemPrice());
			imsTaskRecord.setItemAmountUnit(item.getItemParamValUnit());
			imsTaskRecord.setNeedTradeLot(taskOpenLots);
			imsTaskRecord.setEqualValue(item.getItemPrice());
			imsTaskRecord.setTaskFinishTime(tradeDate);
			imsTaskRecord.setTaskEnd(true);
			imsTaskRecordList.add(imsTaskRecord) ;
		}
		return imsTaskRecordList;
	}

}
