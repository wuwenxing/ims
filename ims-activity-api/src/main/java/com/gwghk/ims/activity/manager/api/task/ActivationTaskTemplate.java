package com.gwghk.ims.activity.manager.api.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * 摘要：真实账号激活
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月15日
 */
@Component
@ActTaskType({ActTaskTypeEnum.REAL_ACTIVATION})
public class ActivationTaskTemplate extends AbstractBaseTask{

	@Autowired
	private ActCashinRealManager actCashinRealManager ;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager ;
	/**
	 * 任务条件：
	 * 1、账号要求入金在活动期间且金额大于0
	 * 2、账号激活时间在活动期间且时间在入金时间之后
	 * 3、如果有层级关系，首次入金要在前置任务之后
	 * 4、如果有时间要求，要在时间内达到入金金额并完成激活
	 */
	@Override
	public List<ImsTaskRecord> isFinishTask(ActTaskSettingVO actTask, String accountNo, String platform, Long companyId) {
		String accType = ActAccountTypeEnum.REAL.getAliasCode() ;
		List<ImsTaskRecord> record = new ArrayList<>() ;
		ActCustomerInfo actCust = actCustomerInfoManager.findByAccountNo(accountNo, platform, ActAccountTypeEnum.REAL.getAliasCode()) ;
		if(null != actCust && null != actCust.getActivatedTime() && "Y".equals(actCust.getActivatedStatus())){
			//取活动期间内的交易时间
			ActSetting act = findActSettingByActNo(actTask.getActivityPeriods()) ;
			if(actCust.getActivatedTime().compareTo(act.getStartTime()) < 0 || actCust.getActivatedTime().compareTo(act.getEndTime()) > 0) {
				return null ;
			}
			//如果有前置任务，取前置任务的最后完成时间
			Date[] date = getTaskDate(actTask.getActivityPeriods(), actTask.getId(), accountNo, platform, actTask.getTaskGroup()) ;
			ActCashinRealVO vo = new ActCashinRealVO() ;
			vo.setAccountNo(accountNo);
			vo.setPlatform(platform);
			vo.setStartApproveDate(DateUtil.getDateSecondFormat(date[0]));
			//入金时间要在激活之前
			vo.setEndApproveDate(DateUtil.getDateSecondFormat(actCust.getActivatedTime()));
			vo.setOrder("asc") ; 
			vo.setSort("approveDate") ; 
			List<ActCashinReal> tradeList = actCashinRealManager.findList(vo) ;
			if(null == tradeList) return null ;
			// 转换任务需要多少秒内完成
			long second = actTask.getTaskItemTime().multiply(new BigDecimal(3600)).longValue() ;
			BigDecimal amount = new BigDecimal(actTask.getTaskItemVal()) ;
			//如果要求入金金额大于0，则需要在规定时间内达到入金金额
			if(!amount.equals(BigDecimal.ZERO)){
				//首次入金到目前最大过去的时间
				long currentTime = 0 ;
				BigDecimal total = new BigDecimal(0) ;
				for(ActCashinReal trade : tradeList) {
					long t = (System.currentTimeMillis()-trade.getApproveDate().getTime()) / 1000 ;
					//设置第一笔入金时间
					if(currentTime == 0){
						currentTime = t ;
					}
					//从第一笔入金时间开始算起，如果时间等于0，则不算时间
					if(currentTime - t <= second || second == 0){
						total = total.add(trade.getTransAmount()) ;
					}
				}
				if(total.compareTo(amount) < 0){
					return null ;
				}
			}
			
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
