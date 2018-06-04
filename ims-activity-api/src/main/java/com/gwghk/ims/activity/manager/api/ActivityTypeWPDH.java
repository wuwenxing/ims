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
 * 摘要：任务型活动处理逻辑
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月10日
 */
@Component
@ActType(ActTypeEnum.WPDH)
public class ActivityTypeWPDH extends AbstractActivityType{

	Logger logger = LoggerFactory.getLogger(ActivityTypeWPDH.class) ;
	
	@Autowired
	private ActTaskSettingManager actTaskSettingManager ;
	
	@Override
	public List<ImsTaskRecord> beginActivity(String actNo,String accNo,String platform,String accType,Long companyId) {
		List<ImsTaskRecord> records = new ArrayList<>() ;
		//查询出该活动下的所有任务
		List<ActTaskSettingVO> list = actTaskSettingManager.findTaskTreeList(actNo) ;
		logger.info("beginTask:当前可判断任务数量：{}",new Object[]{list.size()});
		for(ActTaskSettingVO actTask : list){
			//分别判断每个任务是否完成
			if(isTaskEnd(actTask, accNo, platform)){
				continue ;
			}
			List<ImsTaskRecord> taskRecord = isFinishTask(actTask, accNo, platform, companyId) ;
			if(null != taskRecord)
			records.addAll(taskRecord) ;
		}
		return records ;
	}

}
