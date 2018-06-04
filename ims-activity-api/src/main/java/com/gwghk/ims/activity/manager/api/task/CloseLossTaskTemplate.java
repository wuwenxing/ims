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

/**
 * 
 * 摘要：账号单笔平仓亏损(循环型)
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月8日
 */
@Component
@ActTaskType({ ActTaskTypeEnum.REAL_CLOSE_LOSS, ActTaskTypeEnum.DEMO_CLOSE_LOSS })
public class CloseLossTaskTemplate extends AbstractBaseTask {
	@Autowired
	private ActTradeRecordManager actTradeRecordManager;

	/**
	 * 任务描述 1、单笔平仓交易，亏损到达任务设定值，即可完成任务 2、可以完成多次，直到发放物品设定的上限
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform,
			Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode();
		if (ActTaskTypeEnum.DEMO_CLOSE_LOSS.getCode().equals(actTask.getTaskItemCode())) {
			accType = ActAccountTypeEnum.DEMO.getAliasCode();
		}
		List<ImsTaskRecord> record = new ArrayList<>();
		// 判断任务是否完成，奖励物品是否达到最高限制
		HashMap<String, Integer> map = findAvailableCount(actTask.getTaskItems(), actTask.getActivityPeriods(),
				actTask.getId().intValue(), accountNo, platform);
		if (null == map || map.size() == 0) {
			return null;
		}
		ActSetting actSetting=findActSettingByActNo(actTask.getActivityPeriods());
		// 取出上一次任务的完成时间点，从上次一次的时间开始查询
		Date[] taskDate = getTaskDate(actTask.getActivityPeriods(), actTask.getId(), accountNo, platform,
				actTask.getTaskGroup());
		// 任务参数——亏损金额
		BigDecimal amount = new BigDecimal(actTask.getTaskItemVal());
		ActTradeRecordVO vo = new ActTradeRecordVO();
		vo.setAccountNo(accountNo);
		vo.setPlatform(platform);
		vo.setEnv(accType);
		vo.setTradeStartTime(DateUtil.getDateSecondFormat(actSetting.getStartTime()));
		vo.setTradeEndTime(DateUtil.getDateSecondFormat(actSetting.getEndTime()));
		vo.setTradeType("out");
		vo.setOrder("asc");
		vo.setSort("tradeTime");
		List<ActTradeRecord> tradeList = actTradeRecordManager.findList(vo);
		if (null == tradeList)
			return null;
		for (ActTradeRecord trade : tradeList) {
			//活动禁用期间数据排除
			if(isDisabledActTradeData(actTask.getActivityPeriods(),companyId,trade.getTradeTime())) {
				continue ;
			}
			
			if (trade.getTradeTime().compareTo(taskDate[0]) > 0 && trade.getProfit().add(amount).doubleValue() <= 0) {
				List<ActTaskItemsVO> items = actTask.getTaskItems();
				for (ActTaskItemsVO item : items) {
					if (!map.containsKey(item.getItemNumber()))
						continue;
					Integer c = map.get(item.getItemNumber());
					if (c > 0) {
						ImsTaskRecord task = initTaskRecord(actTask, item, accountNo, platform, companyId, accType);
						task.setFinishAmount(trade.getProfit());
						task.setTaskFinishTime(trade.getTradeTime());
						task.setTaskParamAmount(amount);
						map.put(item.getItemNumber(), c - 1);
						if (!isHasItemCount(map)) {
							task.setTaskEnd(true);
						}
						record.add(task);
					}
				}

			}
		}

		return record;
	}

}