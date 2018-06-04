package com.gwghk.ims.activity.manager.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.cache.ActSettingLocalCache;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSettingWrapper;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActMaintenanceSettingManager;
import com.gwghk.ims.activity.manager.ImsTaskRecordManager;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.vo.activity.ActMaintenanceSettingVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ImsTaskRecordVO;

public abstract class AbstractBaseTask {
	
	@Autowired
	private ImsTaskRecordManager imsTaskRecordManager ;
	
	@Autowired
	private ActMaintenanceSettingManager actMaintenanceManager ;
	
	
	@Autowired
	private ActSettingLocalCache actSettingLocalCache ;
	
	protected List<ImsTaskRecord> findTaskRecord(String actNo,Integer taskSettingId,String accNo,String platform,String itemNo){
		return imsTaskRecordManager.findListByAccount(actNo, accNo, platform, taskSettingId,itemNo,null) ;
	}
	
	/**
	* 
	* 摘要: 查询制定条件内的任务记录   
	* @author George.li  
	* @date 2018年5月15日  
	* @version 1.0   
	* @param actNo 活动编号
	* @param vo 任务记录查询条件VO
	* @return
	 */
	protected List<ImsTaskRecord> findTaskRecord(String actNo,ImsTaskRecordVO vo){
		return imsTaskRecordManager.findList(actNo,vo);
	}
	
	/**
	 * 根据任务ID和子任务ID查询记录，用于层级型任务
	 */
	protected List<ImsTaskRecord> findSubTaskRecord(String actNo,Integer taskSettingId,Integer subTaskSettingId,String accNo,String platform){
		ImsTaskRecordVO record = new ImsTaskRecordVO() ;
		record.setAccountNo(accNo);
		record.setPlatform(platform);
		record.setActTaskSettingId(taskSettingId);
		record.setSubTaskSettingId(subTaskSettingId);
		return imsTaskRecordManager.findList(actNo, record) ;
	}
	
	/**
	 * 根据活动编号获取活动信息
	 * @param actNo
	 * @return
	 */
	protected ActSetting findActSettingByActNo(String actNo) {
		return actSettingLocalCache.getByActivityPeriods(actNo) ;
	}
	
	/**
	 * 获取任务的开始时间和结束时间
	 * @param actNo
	 * @param taskSettingId
	 * @param accNo
	 * @param platform
	 * @return
	 */
	
	protected Date[] getTaskDate(String actNo,Long taskSettingId,String accNo,String platform,Integer taskGroup) {
		ActSetting act = findActSettingByActNo(actNo) ;
		Date t = act.getStartTime() ;
		if(null != taskGroup) {
			//层级型
			t = imsTaskRecordManager.findMaxTaskFinishTime(actNo, accNo, platform, null,taskGroup) ;
		}else {
			t = imsTaskRecordManager.findMaxTaskFinishTime(actNo, accNo, platform, taskSettingId.intValue(),taskGroup) ;
			
		}
		if(null == t){
			return new Date[]{act.getStartTime(),act.getEndTime()};
		}else{
			return new Date[]{t,act.getEndTime()} ;
		}
	}
	
	/**
	 * 更新任务记录的发放状态
	 * @param isPrizeRecord
	 * @param actNo
	 * @param taskSettingId
	 * @param accNo
	 * @param platform
	 */
	public void updateTaskRecord(int id,int isPrizeRecord){
		ImsTaskRecord task = new ImsTaskRecord() ;
		task.setId(id);
		task.setIsPrizeRecord(isPrizeRecord);
		imsTaskRecordManager.saveOrUpdate(task) ;
	}
	 
	/**
	 * 查询每个物品的可完成次数
	 * @param items
	 * @param actNo
	 * @param taskSettingId
	 * @param accNo
	 * @param platform
	 * @return null表示不能在进行了
	 */
	protected HashMap<String, Integer> findAvailableCount(List<ActTaskItemsVO> items,String actNo,Integer taskSettingId,String accNo,String platform){
		//如果该任务已经结束，则不能在进行
		if(isTaskEnd(actNo, taskSettingId, accNo, platform)){
			return null ;
		}
		HashMap<String,Integer> map = new HashMap<>() ;
		for(ActTaskItemsVO vo : items){
			List<ImsTaskRecord> list = findTaskRecord(actNo, taskSettingId, accNo, platform, vo.getItemNumber()) ;
			//如果没有设置最高可完成次数，则只能完成1次
			if(vo.getReceiveMaxNum() == null){
				if(list.size() > 0){
					continue ;
				}else{
					map.put(vo.getItemNumber(), 1) ;
				}
			}else{
				int c = vo.getReceiveMaxNum()-list.size() ;
				if(c > 0) map.put(vo.getItemNumber(), c) ;
			}
			
		}
		
		return map ;
	}
	/**
	 * 查询每个物品的可完成次数
	 * @param items
	 * @param actNo
	 * @param taskSettingId
	 * @param accNo
	 * @param platform
	 * @return null表示不能在进行了
	 */
	protected HashMap<String, BigDecimal> findAvailableMoney(List<ActTaskItemsVO> items,String actNo,Integer taskSettingId,String accNo,String platform){
		//如果该任务已经结束，则不能在进行
		if(isTaskEnd(actNo, taskSettingId, accNo, platform)){
			return null ;
		}
		HashMap<String,BigDecimal> map = new HashMap<>() ;
		for(ActTaskItemsVO vo : items){
			List<ImsTaskRecord> list = findTaskRecord(actNo, taskSettingId, accNo, platform, vo.getItemNumber()) ;
		
			//最高金额
			if(vo.getReceiveMaxMoney()==null) {
				map.put(vo.getItemNumber(), new BigDecimal(0)) ;
			}else {
				BigDecimal amount=new BigDecimal(0);
				//合计金额
				for(ImsTaskRecord record:list) {
					amount.add(record.getItemAmount());
				}
				BigDecimal remainingIssueMaxAmount = vo.getReceiveMaxMoney().subtract(amount) ;
				if(remainingIssueMaxAmount.doubleValue() > 0) map.put(vo.getItemNumber(), remainingIssueMaxAmount) ;
			}
			
		}
		
		return map ;
	}
	
	/**
	 * 判断该任务是否已经结束了
	 * @param actNo
	 * @param taskSettingId
	 * @param accNo
	 * @param platform
	 * @return
	 */
	public boolean isTaskEnd(String actNo,Integer taskSettingId,String accNo,String platform){
		List<ImsTaskRecord> list = findTaskRecord(actNo, taskSettingId, accNo, platform, null) ;
		if(null != list){
			for(ImsTaskRecord task : list) {
				if(task.isTaskEnd() && task.getIsPrizeRecord() != -1 && task.getIsPrizeRecord() != 1){
					return true ;
				}
			}
		}
		return false ;
	}
	/**
	 * 判断是否虚拟币
	 * @param type 物品类型
	 * @return
	 */
	public boolean isVirtualCoin(String type){
		if(ActItemTypeEnum.ANALOGCOIN.getValue().equals(type) || ActItemTypeEnum.TOKENCOIN.getValue().equals(type) || ActItemTypeEnum.WITHGOLD.getValue().equals(type))
		return true ;
		return false ;
	}
	
	/**
	 * 初始化taskRecord对象
	 * @param actTask
	 * @param item
	 * @param accountNo
	 * @param platform
	 * @param companyId
	 * @return
	 */
	public ImsTaskRecord initTaskRecord(ActTaskSettingVO actTask,ActTaskItemsVO item,String accountNo,String platform,Long companyId,String accType){
		ImsTaskRecord task = new ImsTaskRecord() ;
		task.setAccountNo(accountNo);
		task.setActTaskSettingId(actTask.getId().intValue());
		task.setCompanyId(companyId);
		task.setCreateDate(new Date());
		task.setTaskCode(actTask.getTaskItemCode());
		task.setTaskItemId(item.getId());
		task.setItemAmount(item.getItemPrice());
		task.setItemAmountUnit(item.getItemParamValUnit());
		task.setItemNo(item.getItemNumber());
		task.setPlatform(platform);
		task.setNeedTradeLot(item.getTradeNum());
		task.setEqualValue(item.getEqualValue());
		task.setActNo(actTask.getActivityPeriods());
		task.setItemType(item.getItemType());
		task.setTaskRecordTime(new Date());
		task.setTaskGroup(actTask.getTaskGroup());
		task.setIsPrizeRecord(0);
		task.setAccountType(accType);
		return task;
	}
	

	/**
	 * 
	 * 摘要: 判断任务下的物品集合是否还可以领取
	 * 
	 * @author George.li
	 * @date 2018年5月18日
	 * @version 1.0
	 * @param map
	 * @return
	 */
	public boolean isHasItemCount(HashMap<String, Integer> map) {
		boolean valadate = false;
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() != 0) {
					valadate = true;
					break;
				}
			}
		}
		return valadate;
	}
	
	/***
	* 摘要: 是否是活动禁用期间交易数据   
	* @author George.li  
	* @date 2018年5月23日  
	* @version 1.0   
	* @param tradeDate
	* @return true 是 ; false 否
	 */
	public boolean isDisabledActTradeData(String activityPeriods,Long companyId,Date tradeDate) {
		boolean status=false;
		ActMaintenanceSettingVO vo=new ActMaintenanceSettingVO();
		vo.setActivityPeriods(activityPeriods);
		vo.setEffectiveFlag("Y");
		vo.setCompanyId(companyId);
		vo.setDeleteFlag("N");
		//vo.setEnableFlag("Y");
		List<ActMaintenanceSettingWrapper> list=actMaintenanceManager.findList(vo);
		for(ActMaintenanceSettingWrapper wrapper:list) {
			if(wrapper.getStartTime().compareTo(tradeDate)<=0 && wrapper.getEndTime().compareTo(tradeDate)>=0) {
				status=true;
				break;
			}
		}
		return status;
	}
	
	/**
	 * 判断客户是否完成任务，如果没有完成返回null
	 * @param taskCode 任务编号
	 * @param accountNo 账号
	 * @param platform 平台
	 * @param accType 账号类型 demo|real
	 * @param companyId 公司ID
	 * @return
	 */
	public abstract List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask,String accountNo,String platform,Long companyId) ;
	
	
}
