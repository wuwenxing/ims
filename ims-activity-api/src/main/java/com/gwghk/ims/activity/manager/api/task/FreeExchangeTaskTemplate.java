package com.gwghk.ims.activity.manager.api.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.api.AbstractBaseTask;
import com.gwghk.ims.common.annotation.ActTaskType;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 
 * 摘要：摘要：自由兑换(循环型)
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月23日
 */
@Component
@ActTaskType({ ActTaskTypeEnum.FREE_EXCHANGE})
public class FreeExchangeTaskTemplate extends AbstractBaseTask {

	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform,
			Long companyId) {	
		List<ImsTaskRecord> taskRecord = new ArrayList<ImsTaskRecord>();
		String accType = ActAccountTypeEnum.DEMO.getAliasCode() ;
		for (ActTaskItemsVO item : actTask.getTaskItems()) {
			ImsTaskRecord task = initTaskRecord(actTask, item, accountNo, platform, companyId, accType);
			taskRecord.add(task);
		}
		return taskRecord;
	}
    
   
}