package com.gwghk.ims.activity.manager.api.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActCustomerInfoManager;
import com.gwghk.ims.activity.manager.api.AbstractBaseTask;
import com.gwghk.ims.common.annotation.ActTaskType;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 
 * 摘要：注册账号
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月15日
 */
@Component
@ActTaskType({ActTaskTypeEnum.ADD_DEMO_ACCOUNT,ActTaskTypeEnum.ADD_REAL_ACCOUNT})
public class AddAccountTaskTemplate extends AbstractBaseTask{

	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager ;
	
	/**
	 * 任务描述
	 * 1、活动期间，完成账号注册即可完成任务
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform,Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		if(ActTaskTypeEnum.ADD_DEMO_ACCOUNT.getCode().equals(actTask.getTaskItemCode())){
			accType = ActAccountTypeEnum.DEMO.getAliasCode() ;
		}
		List<ImsTaskRecord> record = new ArrayList<>() ;
		//取出活动的时间
		ActSetting act = findActSettingByActNo(actTask.getActivityPeriods()) ;
		ActCustomerInfo actCust = actCustomerInfoManager.findByAccountNo(accountNo, platform, accType) ;
		if(null == actCust) return null ;
		//判断是否在活动期间内注册账号
		if(actCust.getRegisterTime().compareTo(act.getStartTime()) > 0 && actCust.getRegisterTime().compareTo(act.getEndTime()) < 0){
			List<ActTaskItemsVO> items = actTask.getTaskItems() ;
			for(ActTaskItemsVO item : items){
				ImsTaskRecord task = initTaskRecord(actTask, item, accountNo, platform, companyId,accType) ;
				task.setTaskEnd(true);
				task.setTaskFinishTime(actCust.getRegisterTime());
				record.add(task) ;
			}
		}
		return record;
	}
}
