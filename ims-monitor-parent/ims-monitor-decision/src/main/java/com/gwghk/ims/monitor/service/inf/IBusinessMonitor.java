package com.gwghk.ims.monitor.service.inf;

import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;

public interface IBusinessMonitor {
	/**
	 * 获取业务相关监控信息 ,其中interfaceName 决定了是什么样的业务
	 * @param item
	 * @return
	 */
	public MonitorResultVo getMonitorInfo(MonitorMachine machine,MonitorItem item);
}
