package com.gwghk.ims.activity.manager.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.util.BeanFactoryUtil;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

public abstract class AbstractActivityType {
	Logger logger = LoggerFactory.getLogger(AbstractActivityType.class) ;
	
	/**
	 * 判断当前任务是否有完成
	 * @param actTask
	 * @param accountNo
	 * @param platform
	 * @param companyId
	 * @return
	 */
	List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask,String accountNo,String platform,Long companyId){
		AbstractBaseTask baseTaskBean = BeanFactoryUtil.getActTaskTypeBean(actTask.getTaskItemCode()) ;
		if(baseTaskBean == null){
			logger.error("不支持该任务类型：{}",new Object[]{actTask.getTaskItemCode()});
			return null ;
		}
		return baseTaskBean.isFinishTask(actTask, accountNo, platform, companyId) ;
	}
	
	/**
	 * 判断任务是否结束了
	 * @param actTask
	 * @param accNo
	 * @param platform
	 * @return
	 */
	protected boolean isTaskEnd(ActTaskSettingVO actTask,String accNo,String platform){
		AbstractBaseTask baseTaskBean = BeanFactoryUtil.getActTaskTypeBean(actTask.getTaskItemCode()) ;
		if(baseTaskBean == null){
			logger.error("不支持该任务类型：{}",new Object[]{actTask.getTaskItemCode()});
			return false ;
		}
		return baseTaskBean.isTaskEnd(actTask.getActivityPeriods(), actTask.getId().intValue(), accNo, platform) ;
	}
	
	/**
	 * 查询出当前活动类型，当前可执行的任务
	 * @param actNo 活动编号
	 * @return
	 */
	public abstract List<ImsTaskRecord> beginActivity(String actNo,String accNo,String platform,String accType,Long companyId) ;
}
