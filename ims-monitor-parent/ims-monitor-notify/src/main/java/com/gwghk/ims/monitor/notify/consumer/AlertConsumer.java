package com.gwghk.ims.monitor.notify.consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.enums.AlertLevelEnum;
import com.gwghk.ims.common.enums.MonitorEnum;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.dao.entity.DutyStaff;
import com.gwghk.ims.monitor.notify.service.AlertNotifyRecordService;
import com.gwghk.ims.monitor.notify.service.DutyStaffService;
import com.gwghk.ims.monitor.notify.utils.StringUtils;

/**
 * 告警消费线程
 * @author jackson.tang
 *
 */
public class AlertConsumer extends MonitorConsumer{
	
	/**
	 * 最小通知时间间隔(单位天)
	 */
	private int minNotifyInterval;
	/**
	 * 致命通知方式定义
	 */
	private String fatalNotifyStrategy;
	/**
	 * 警告性质的通知方式定义
	 */
	private String warnNotifyStrategy;
	/**
	 * 历史记录
	 */
	private Map<Long,AlertRecord> history;
	/**
	 * 告警具体策略
	 */
	private DutyStaffService dutyStaffService;
	
	private AlertNotifyRecordService alertNotifyRecordService;
	
	private Map<Integer,List<IAlertNotify>> alertStrategy;
	
	public AlertConsumer() {
		//确定具体主题
		super(MonitorEnum.MQ_TOPIC_ALERT);
		//初始化历史记录
		history=new HashMap<Long,AlertRecord>();
		//定义通告策略
		alertStrategy=new LinkedHashMap<Integer,List<IAlertNotify>>();
	}

	public void initAlertStrategy(){
		final String SERVICE_PACKAGE_POSITION="com.gwghk.ims.monitor.notify.service.";
		try {
			//定义致命错误
			List<IAlertNotify> fatalList=new ArrayList<IAlertNotify>();
			for(String name:fatalNotifyStrategy.trim().split(",")) {
				String handle=SERVICE_PACKAGE_POSITION+StringUtils.getFirstUpperCaseStr(name)+"Notify";
				fatalList.add((IAlertNotify)Class.forName(handle).newInstance());
			}
			alertStrategy.put(AlertLevelEnum.FATAL.value(), fatalList);
			
			//定义警告错误
			List<IAlertNotify> warnList=new ArrayList<IAlertNotify>();
			for(String name:warnNotifyStrategy.split(",")) {
				String handle=SERVICE_PACKAGE_POSITION+StringUtils.getFirstUpperCaseStr(name)+"Notify";
				fatalList.add((IAlertNotify)Class.forName(handle).newInstance());
			}
			alertStrategy.put(AlertLevelEnum.WARN.value(), warnList);	
				
		}catch(Exception e) {
			logger.error(">>>初始化定义告警策略失败:{}",e.getMessage(),e);
		}
	}

	@Override
	protected void handleMessage(MonitorResultVo dto) throws Exception {
		Date curDate=new Date();
		
		//相同的警告在规定的时间内不能重复发送
		if(history.containsKey(dto.getItemId()) &&
				//在最小的时间间隔内不能重复发送告警， 所以丢掉本次告警数据
				history.get(dto.getItemId()).getNotifyDate().getTime()  +  86400000L*minNotifyInterval  >=  curDate.getTime()
				){
			return;
		}
		
		//寻找到相关的值班人员
		List<DutyStaff> list=dutyStaffService.findDutyStaffByItemId(dto.getItemId());
		if(list==null || list.size()<=0) {
			logger.warn(">>>无法找到【{}】对应的值班人员，所以本条消息忽略掉，本条消息详情:{}",dto.getItemId(),JSON.toJSONString(dto));
			return;
		}
			
		
		//告警
		for(IAlertNotify alertNotify:alertStrategy.get(dto.getAlertLevel())) 
			alertNotify.send(list, dto);
		
		if(!history.containsKey(dto.getItemId()))
			history.put(dto.getItemId(),new AlertRecord());	
		
		//保存历史记录
		AlertRecord record=history.get(dto.getItemId());
		record.setAlertLevel(dto.getAlertLevel());
		record.setAlertMsg(dto.getAlertMsg());
		record.setNotifyDate(curDate);
		
		//保存发送记录
		alertNotifyRecordService.batchSave(list,dto);
		logger.info(">>>监控条目ID【{}】:向指定的人员【{}】 发送告警:【{}】 ",dto.getItemId(),JSON.toJSONString(list),dto.getAlertMsg());
	}
	
	public void setMinNotifyInterval(int minNotifyInterval) {
		this.minNotifyInterval = minNotifyInterval;
	}

	public void setFatalNotifyStrategy(String fatalNotifyStrategy) {
		this.fatalNotifyStrategy = fatalNotifyStrategy;
	}

	public void setWarnNotifyStrategy(String warnNotifyStrategy) {
		this.warnNotifyStrategy = warnNotifyStrategy;
	}

	public void setDutyStaffService(DutyStaffService dutyStaffService) {
		this.dutyStaffService = dutyStaffService;
	}

	public void setAlertNotifyRecordService(AlertNotifyRecordService alertNotifyRecordService) {
		this.alertNotifyRecordService = alertNotifyRecordService;
	}

}
