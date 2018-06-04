package com.gwghk.ims.activity.manager.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.common.annotation.ActType;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 
 * 摘要：入金型活动逻辑
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月10日
 */
@Component
@ActType(ActTypeEnum.RJZJ)
public class ActivityTypeRJ extends AbstractActivityType{

	Logger logger = LoggerFactory.getLogger(ActivityTypeRJ.class) ;
	
	@Autowired
	private ActTaskSettingManager actTaskSettingManager ;
	
	@Override
	public List<ImsTaskRecord> beginActivity(String actNo,String accNo,String platform,String accType,Long companyId) {
		List<ImsTaskRecord> records = new ArrayList<>() ;
		//查询出该活动下的所有任务
		List<ActTaskSettingVO> list = actTaskSettingManager.findTaskTreeList(actNo) ;
		
		logger.info("beginTask:当前可判断任务数量：{}",new Object[]{list.size()});
		for(int i = 0 ;i< list.size() ;i++){
			ActTaskSettingVO actTask = list.get(i) ;
			if(i > 0){
				//入金型任务要求有序的，如果前置任务没有完成，后面的不能进行
				if(isTaskEnd(list.get(i-1), accNo, platform)){
					//TODO 前置任务如果有要求手数，手数也必须要完成，才能进行下一个任务
					
					
					List<ImsTaskRecord> taskRecord = isFinishTask(actTask, accNo, platform, companyId) ;
					if(null != taskRecord) records.addAll(taskRecord) ;
				}
			}else{
				if(isTaskEnd(actTask, accNo, platform)){
					continue ;
				}
				List<ImsTaskRecord> taskRecord = isFinishTask(actTask, accNo, platform, companyId) ;
				if(null != taskRecord) records.addAll(taskRecord) ;
			}
		}
		return records ;
	}

}
