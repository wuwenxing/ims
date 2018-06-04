package com.gwghk.ims.activity.manager.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.ImsTaskRecordManager;
import com.gwghk.ims.common.annotation.ActType;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.IsPrizeRecordEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
/***
 * 
* 摘要:层级型任务   
* @author George.li  
* @date 2018年5月10日  
* @version 1.0
 */
@Component
@ActType(ActTypeEnum.CJ)
public class ActivityTypeCJ extends AbstractActivityType {

	Logger logger = LoggerFactory.getLogger(ActivityTypeCJ.class);

	@Autowired
	private ActTaskSettingManager actTaskSettingManager;

	@Autowired
	private ImsTaskRecordManager imsTaskRecordManager;
	
	@Autowired
	private ActivityApiManager activityApiManager;
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager ;

	/***
	 * 1.层级任务表示，用户完成一个层级内的所有任务时，才能领取这个层级的所有奖励；
	 * 2.层级型任务有先后顺序，用户完成层级任务的第一个子任务才能参加第二个子任务；
	 */
	@Override
	public List<ImsTaskRecord> beginActivity(String actNo, String accNo, String platform, String accType,
			Long companyId) {
		List<ImsTaskRecord> records = new ArrayList<>();

		// 查询出该活动下的所有任务
		List<ActTaskSettingVO> list = actTaskSettingManager.findTaskTreeList(actNo);
		logger.info("层级型:当前可判断任务数量：{}", new Object[] { list.size() });

		 
		Map<Integer, List<ActTaskSettingVO>> groupList = list.stream().collect(Collectors.groupingBy(ActTaskSettingVO::getTaskGroup,Collectors.toList()));
		
		for (Map.Entry<Integer, List<ActTaskSettingVO>> entry : groupList.entrySet()) {  
			logger.info("层级型:当前可判断子任务数量：{}", new Object[] { entry.getValue().size() });
			List<ActTaskSettingVO> subList =entry.getValue().stream().sorted(Comparator.comparing(ActTaskSettingVO::getTaskOrder)).collect(Collectors.toList());
			for (int i = 0; i < subList.size(); i++) {
				ActTaskSettingVO subTask = subList.get(i);
				// 层级任务要求有序的，如果上一层任务没有完成，后面的不能进行
				if (i > 0 && !isTaskEnd(subList.get(i - 1), accNo, platform)) {
					break;
				}
				//TODO 存在活动进行中插入任务可能，目前界面没有地方可以进行活动任务的插入
				//下一级任务如果进行中 (已经产生的任务完成记录=进行中说明该任务是修改后插入的任务，则直接跳过该任务)
				if(i>0 && i!=subList.size()-1) {
					boolean isNewTask=false;
					for(int index=i;index<subList.size()-1;index++) {
						ActTaskSettingVO nextSubTask=subList.get(index);
						List<ImsTaskRecord> finishTaskRecordList = imsTaskRecordManager.findListByAccount(actNo, accNo,
								platform, nextSubTask.getId().intValue(), null,nextSubTask.getTaskGroup());
						if(finishTaskRecordList!=null && !finishTaskRecordList.isEmpty()) {
							isNewTask=true;
							break;
						}
					}
					if(isNewTask) {
						continue ;
					}
				}
				List<ImsTaskRecord> taskRecord = isFinishTask(subTask, accNo, platform, companyId);
				if (null == taskRecord || taskRecord.isEmpty())
					continue;
 			
				records.addAll(taskRecord);

				// 一个层级 最后一个任务完成设置该层级下任务所有任务为可发放状态
				if (i == subList.size() - 1 && checkTaskEnd(taskRecord)) {
					taskRecord.stream().forEach(record -> {
						record.setLastSubTask(true);// 最后一个子任务
						record.setIsPrizeRecord(IsPrizeRecordEnum.WAIT_RECORD.getKey());// 可产生发放记录
					});

					// 设置子任务集 为已经完成
					List<ImsTaskRecord> finishTaskRecordList = imsTaskRecordManager.findListByAccount(actNo, accNo,
							platform, null, null,subTask.getTaskGroup());
					updatePrizeRecord(finishTaskRecordList);
					records.addAll(finishTaskRecordList);
				} else {
					
					for(ImsTaskRecord record:taskRecord) {
						record.setLastSubTask(false);
						record.setIsPrizeRecord(IsPrizeRecordEnum.ADD_WAITING_RECORD.getKey()); // -1不可发放
					} 
				}
				
				activityApiManager.saveTaskRecord(records);
				records.clear();
				
			}
		}
//		);
		return records;
	}
	
	/**
	* 摘要:判断当前任务是否结束
	* @author George.li  
	* @date 2018年5月21日  
	* @version 1.0   
	* @param list
	* @return
	 */
	private boolean checkTaskEnd(List<ImsTaskRecord> list){
		for(ImsTaskRecord ims : list) {
			if(ims.isTaskEnd()) return true ;
		}
		return false ;
	}
	
	/**
	 * 更新发放记录等待中为待发放
	 * @param list
	 */
	private void updatePrizeRecord(List<ImsTaskRecord> list){
		for(ImsTaskRecord ims : list){
			 ImsPrizeRecord rec = imsPrizeRecordManager.findObjectByTaskRecordId(ims.getActNo(),ims.getId()) ;
			 if(null != rec){
				if(IssuingStatusEnum.waiting.getValue().equals(rec.getGivedStatus())){
					if (ActItemTypeEnum.REAL.getValue().equals(rec.getItemType())) {// 实物
						 rec.setGivedStatus(IssuingStatusEnum.in_library.getValue());
					} else {
						rec.setGivedStatus(IssuingStatusEnum.issuing.getValue());
					}
					imsPrizeRecordManager.saveOrUpdate(rec);
				}
			 }
		}
	}
	
}
