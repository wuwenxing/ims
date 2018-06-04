package com.gwghk.ims.activity.manager.api.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 摘要：真实账号入金（有时效性）
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月10日
 */
@Component
@ActTaskType({ActTaskTypeEnum.REAL_DEPOSIT})
public  class RealDepositTaskTemplate extends AbstractBaseTask {
	Logger logger = LoggerFactory.getLogger(RealDepositTaskTemplate.class) ;
	@Autowired
	private ActCashinRealManager actCashinRealManager ;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager ;

	/**
	 * 任务描述
	 * 1、活动期间，从首次入金开始，在指定要求时间段内，入金金额达到对应层级的要求
	 * 2、在时间段结束后，发放对应的层级物品
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform,Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		logger.info("real_deposit->开始任务：accountNo:{},accType:{}",accountNo,accType);
		List<ImsTaskRecord> record = new ArrayList<>() ;
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
		for(ActCashinReal trade : tradeList) {
			long t = (System.currentTimeMillis()-trade.getApproveDate().getTime()) / 1000 ;
			if(currentTime == 0){
				currentTime = t ;
				//首次入金已超过限定时间，不计算了
				if(currentTime > second){
					return null ;
				}
			}
			//从第一笔入金时间开始算起
			if(currentTime - t <= second){
				total = total.add(trade.getTransAmount()) ;
				tradeDate = trade.getApproveDate(); 
			}
		}
		List<ActTaskSettingVO> subTask = checkSubTask(actTask.getSubTaskSettings(),total) ;
		BigDecimal sendAmount = BigDecimal.ZERO ;
		BigDecimal sendLot = BigDecimal.ZERO ;
		for(int i = 0 ;i < subTask.size() ;i++) {
			ActTaskSettingVO sub = subTask.get(i) ;
				//查询出当前层级任务是否完成
				List<ImsTaskRecord> rec = findSubTaskRecord(actTask.getActivityPeriods(), actTask.getId().intValue(), sub.getId().intValue(), accountNo, platform) ;
				if(rec.size() > 0){
					for(ImsTaskRecord ims : rec) {
						//如果是虚拟币，则需要把已经发放的金额累计，后续发放虚拟币，需要扣除已经发放的
						if(isVirtualCoin(ims.getItemType())){
							if(null != ims.getItemAmount()){
								if("%".equals(ims.getItemAmountUnit())){
									sendAmount = sendAmount.add(ims.getItemAmount().multiply(ims.getFinishAmount().divide(new BigDecimal(100)))) ;
								}else{
									sendAmount = sendAmount.add(ims.getItemAmount()) ;
								}
							}
							if(null != ims.getNeedTradeLot())
								sendLot = sendLot.add(ims.getNeedTradeLot()); 
						}else{
							//如果当前非模拟币，非最后一个任务，则取消发放
							if(i < subTask.size() -1 ){
								updateTaskRecord(ims.getId(),-1);
							}
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
					task.setIsPrizeRecord(1); 
					//需要设置最后的发放时间
					task.setTaskRecordTime(DateUtil.addSeconds(tradeList.get(0).getApproveDate(), (int)second));
					//如果是最后一个层级已经完成，则任务结束
					if(i == actTask.getSubTaskSettings().size() -1 ){
						task.setLastSubTask(true);
						task.setTaskEnd(true);
					}
					//虚拟币每个层级都进行发放，发放时要减去之前发放的
					if(isVirtualCoin(item.getItemType())){
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
						if(i != subTask.size() -1) {
							//如果不是最后一个层级，非虚拟币不发
							continue ;
						}
					}
					record.add(task) ;
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
	
	
}