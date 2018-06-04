package com.gwghk.ims.activity.manager.api.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;
import com.gwghk.ims.common.vo.activity.ImsTaskRecordVO;

/**
 * 摘要:账号开仓手数(循环型)
 * 
 * @author George.li
 * @date 2018年5月17日
 * @version 1.0
 */
@Component
@ActTaskType({ ActTaskTypeEnum.REAL_OPEN_LOT, ActTaskTypeEnum.DEMO_OPEN_LOT })
public class OpenLotTaskItemTemplate extends AbstractBaseTask {

	@Autowired
	private ActTradeRecordManager actTradeRecordManager;

	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform,
			Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode();
		if (ActTaskTypeEnum.DEMO_OPEN_LOT.getCode().equals(actTask.getTaskItemCode())) {
			accType = ActAccountTypeEnum.DEMO.getAliasCode();
		}
		// 判断任务是否完成，奖励物品是否达到最高限制
		HashMap<String, Integer> map = findAvailableCount(actTask.getTaskItems(), actTask.getActivityPeriods(),
				actTask.getId().intValue(), accountNo, platform);
		if (null == map || map.size() == 0) {
			return null;
		}
		List<ImsTaskRecord> taskRecord = new ArrayList<ImsTaskRecord>();
		ActSetting actSetting = findActSettingByActNo(actTask.getActivityPeriods());
		// 取出上一次任务的完成时间点，从上次一次的时间开始查询
		Date[] taskDate = getTaskDate(actTask.getActivityPeriods(), actTask.getId(), accountNo, platform,
				actTask.getTaskGroup());
		// 任务要求的开仓手数
		BigDecimal taskCloseLot = actTask.getTaskItemVal() == null ? BigDecimal.ZERO
				: new BigDecimal(actTask.getTaskItemVal());
		ActTradeRecordVO tradeRecordVO = new ActTradeRecordVO();
		tradeRecordVO.setAccountNo(accountNo);
		tradeRecordVO.setPlatform(platform);
		tradeRecordVO.setEnv(accType);
		tradeRecordVO.setTradeStartTime(DateUtil.getDateSecondFormat(actSetting.getStartTime()));
		tradeRecordVO.setTradeEndTime(DateUtil.getDateSecondFormat(actSetting.getEndTime()));
		tradeRecordVO.setTradeType("in");
		tradeRecordVO.setOrder("asc");
		tradeRecordVO.setSort("tradeTime");
		List<ActTradeRecord> tradeRecordList = actTradeRecordManager.findList(tradeRecordVO);
		if (tradeRecordList == null || tradeRecordList.isEmpty())
			return null;

		// 查询任务完成任务列表
		ImsTaskRecordVO imsTaskRecordVO = new ImsTaskRecordVO();
		imsTaskRecordVO.setActNo(actSetting.getActivityPeriods());
		imsTaskRecordVO.setAccountNo(accountNo);
		imsTaskRecordVO.setTaskGroup(actTask.getTaskGroup());// 是否是层级性任务
		imsTaskRecordVO.setStartTimeStr(DateUtil.getDateSecondFormat(actSetting.getStartTime()));
		imsTaskRecordVO.setEndTimeStr(DateUtil.getDateSecondFormat(actSetting.getEndTime()));
		List<ImsTaskRecord> taskRecordList = findTaskRecord(actTask.getActivityPeriods(), imsTaskRecordVO);
		// 该账号的在活动期间的交易统计数据
		BigDecimal finishLot = BigDecimal.ZERO;
		for (ImsTaskRecord ims : taskRecordList) {
			if (null != ims.getFinishLot()) {
				finishLot = finishLot.add(ims.getFinishLot());
			}
		}
		BigDecimal tradeLot = BigDecimal.ZERO;
		List<ActTaskItemsVO> items = actTask.getTaskItems();

		boolean finish = false;
		for (ActTradeRecord trade : tradeRecordList) {
			if (finish)
				break;
			//活动禁用期间数据排除
			if(isDisabledActTradeData(actTask.getActivityPeriods(),companyId,trade.getTradeTime())) {
				continue ;
			}
			
			tradeLot = tradeLot.add(trade.getTradeLot());
			while (tradeLot.subtract(finishLot).compareTo(taskCloseLot) >= 0) {
				if (finish)
					break;
				boolean addLot = true;
				// 如果交易时间在任务开始时间之后，且满足任务要求手数，则任务完成
				if (trade.getTradeTime().compareTo(taskDate[0]) > 0
						&& tradeLot.compareTo(finishLot.add(taskCloseLot)) >= 0) {
					for (ActTaskItemsVO item : items) {
						Integer receiveMaxNum = map.get(item.getItemNumber());
						if (receiveMaxNum != null && receiveMaxNum > 0) {
							ImsTaskRecord task = initTaskRecord(actTask, item, accountNo, platform, companyId, accType);
							task.setTaskParamLot(taskCloseLot);
							task.setTaskFinishTime(trade.getTradeTime());
							task.setNeedTradeLot(taskCloseLot);
							if (addLot) {
								task.setFinishLot(taskCloseLot);
								finishLot = finishLot.add(taskCloseLot);
								addLot = false;
							}
							taskRecord.add(task);
							map.put(item.getItemNumber(), receiveMaxNum - 1);
							if (!isHasItemCount(map)) {
								task.setTaskEnd(true);
								finish = true;
							}
						}

					}
				}
			}
		}
		return taskRecord;
	}

}
