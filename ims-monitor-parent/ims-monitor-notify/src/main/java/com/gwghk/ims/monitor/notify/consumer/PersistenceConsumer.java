package com.gwghk.ims.monitor.notify.consumer;

import com.gwghk.ims.common.enums.MonitorEnum;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.service.MonitorContentService;

/**
 * 持久化消费者线程:保存出现的监控数据
 * @author jackson.tang
 *
 */
public class PersistenceConsumer extends MonitorConsumer{
	/**
	 * 过期数据
	 */
	private int dataExpiredDay;
	
	private MonitorContentService monitorContentService;
	
	public PersistenceConsumer() {
		super(MonitorEnum.MQ_TOPIC_PERSISTENCE);
	}
	
	public void setDataExpiredDay(int day) {
		dataExpiredDay=day;
	}

	@Override
	protected void handleMessage(MonitorResultVo dto) throws Exception {
		//保存告警数据
		monitorContentService.save(dto);
		
		//删除过期的数据,数据的有效期至少也得有7天
		if(dataExpiredDay>7)
			monitorContentService.delExpiredData(dataExpiredDay);
			
	}

	public void setMonitorContentService(MonitorContentService monitorContentService) {
		this.monitorContentService = monitorContentService;
	}
}
