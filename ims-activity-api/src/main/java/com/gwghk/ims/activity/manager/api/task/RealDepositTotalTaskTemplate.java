package com.gwghk.ims.activity.manager.api.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActCashinReal;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActCashinRealManager;
import com.gwghk.ims.activity.manager.ActCustomerInfoManager;
import com.gwghk.ims.activity.manager.api.AbstractBaseTask;
import com.gwghk.ims.common.annotation.ActTaskType;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.vo.activity.ActCashinRealVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 
 * 摘要：真实账号累计入金
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月10日
 */
@Component
@ActTaskType({ActTaskTypeEnum.REAL_DEPOSIT_TOTAL})
public  class RealDepositTotalTaskTemplate extends AbstractBaseTask {
	@Autowired
	private ActCashinRealManager actCashinRealManager ;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager ;
	
	
	/**
	 * 任务描述
	 * 1、活动期间，从首次入金开始，在指定要求时间段内，累计入金金额达到对应层级的要求
	 * 2、当累计入金金额满足某一层级时，自动发放该层级物品，如果是虚拟币（模拟币，代币，赠金），则可以多层级发放，其他则只能发放第一个达到的层级
	 * 3、当发放虚拟币时，下一个层级要减去之前层级已经发放的金额，要求手数减去之前发放的要求手数
	 * 4、第一个层级完成后，后面的层级完成时间都是为首次的完成时间
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform,Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		List<ImsTaskRecord> record = new ArrayList<>() ;
		if(isTaskEnd(actTask.getActivityPeriods(), actTask.getId().intValue(), accountNo, platform)){
			return null ;
		}
		//取活动期间内的交易时间
		ActSetting taskDate = findActSettingByActNo(actTask.getActivityPeriods()) ;
		// 转换任务需要多少秒内完成
		long second = actTask.getTaskItemTime().multiply(new BigDecimal(3600)).longValue() ;
		ActCashinRealVO vo = new ActCashinRealVO() ;
		vo.setAccountNo(accountNo);
		vo.setPlatform(platform);
		vo.setStartApproveDate(DateUtil.getDateSecondFormat(taskDate.getStartTime()));
		vo.setEndApproveDate(DateUtil.getDateSecondFormat(taskDate.getEndTime()));
		vo.setOrder("asc") ; 
		vo.setSort("approveDate") ; 
		List<ActCashinReal> tradeList = actCashinRealManager.findList(vo) ;
		if(null == tradeList || tradeList.isEmpty()) return null ;
		BigDecimal total = new BigDecimal(0) ;
		//首次入金到目前最大过去的时间
		long currentTime = 0 ;
		Date tradeDate = null ;
		BigDecimal sendAmount = BigDecimal.ZERO ;
		BigDecimal sendLot = BigDecimal.ZERO ;
		List<ImsTaskRecord> list = findTaskRecord(actTask.getActivityPeriods(), actTask.getId().intValue(), accountNo, platform, null) ;
		if(null != list && list.size()>0){
			//如果已经完成过任务了，取第一个完成任务时间为后续的完成时间
			tradeDate = list.get(0).getTaskFinishTime() ;
		}
		Map<Integer,List<ImsTaskRecord>> records = list.stream().collect(Collectors.groupingBy(ImsTaskRecord::getSubTaskSettingId)) ;
		boolean taskFinish = false ;
		for(ActCashinReal trade : tradeList) {
			if(taskFinish){
				break ;
			}
			long t = (System.currentTimeMillis()-trade.getApproveDate().getTime()) / 1000 ;
			if(currentTime == 0){
				currentTime = t ;
			}
			//从第一笔入金时间开始算起
			if(currentTime - t <= second){
				total = total.add(trade.getTransAmount()) ;
				if(null == tradeDate)
				tradeDate = trade.getApproveDate(); 
				List<ActTaskSettingVO> subTask = checkSubTask(actTask.getSubTaskSettings(),total) ;
				for(int i = 0 ;i < subTask.size() ;i++) {
					ActTaskSettingVO sub = subTask.get(i) ;
					if(checkExitRecord(record,sub)){
						continue ;
					}
					//查询出当前层级任务是否完成
					List<ImsTaskRecord> rec = records.get(sub.getId().intValue()) ;
					if(null != rec && rec.size() > 0){
						for(ImsTaskRecord ims : rec) {
							//如果是虚拟币，则需要把已经发放的金额累计，后续发放虚拟币，需要扣除已经发放的
							if(isVirtualCoin(ims.getItemType())){
								if(null != ims.getItemAmount())
									sendAmount = sendAmount.add(ims.getItemAmount()) ;
								if(null != ims.getNeedTradeLot())
									sendLot = sendLot.add(ims.getNeedTradeLot()); 
							}
						}
						continue ;
					}
					List<ActTaskItemsVO> items = sub.getTaskItems() ;
					for(ActTaskItemsVO item : items) {
						ImsTaskRecord task =initTaskRecord(actTask, item, accountNo, platform, companyId,accType) ;
						task.setFinishAmount(total);
						task.setTaskParamAmount(new BigDecimal(sub.getTaskItemVal()));
						task.setSubTaskSettingId(sub.getId().intValue()); 
						task.setTaskFinishTime(tradeDate);
						//如果是最后一个层级已经完成，则任务结束
						if(i == actTask.getSubTaskSettings().size() -1 ){
							task.setLastSubTask(true);
							taskFinish = true ;
						}
						if(isVirtualCoin(item.getItemType())){
							//虚拟币每个层级都进行发放，发放时要减去之前发放的
							if(null != item.getItemPrice()){
								if("%".equals(item.getItemParamValUnit())){
									task.setItemAmount(total.multiply(item.getItemPrice().divide(new BigDecimal(100))).subtract(sendAmount)) ;
									ActCustomerInfo cust = actCustomerInfoManager.findByAccountNo(accountNo, platform, accType) ;
									task.setItemAmountUnit(cust.getAccountCurrency());
								}else{
									task.setItemAmount(item.getItemPrice().subtract(sendAmount));
								}
								sendAmount = sendAmount.add(task.getItemAmount());
							}
							if(null != item.getTradeNum()){
								task.setNeedTradeLot(item.getTradeNum().subtract(sendLot));
								sendLot = sendLot.add(task.getNeedTradeLot()) ;
							}
						}else{
							//如果不是模拟币，则发放后任务结束，后续不在进行发放了
							taskFinish = true ;
						}
						task.setTaskEnd(taskFinish);
						record.add(task) ;
					}
				}
			}
		}
		return record;
	}
	
	private List<ActTaskSettingVO> checkSubTask(List<ActTaskSettingVO> subList,BigDecimal total){
		List<ActTaskSettingVO> list = new ArrayList<>() ;
		subList.forEach(sub ->{
			if(new BigDecimal(sub.getTaskItemVal()).compareTo(total) <= 0)
				list.add(sub) ;
			
		});
		return list ;
	}
	
	private boolean checkExitRecord(List<ImsTaskRecord> record,ActTaskSettingVO sub){
		for(ImsTaskRecord ims : record){
			if(ims.getSubTaskSettingId() == sub.getId().intValue()){
				return true ;
			}
		}
		return false ;
	}
	
}