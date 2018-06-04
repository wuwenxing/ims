package com.gwghk.ims.monitor.service.inf;

import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;

public interface IProcessMonitor {
	/**
	 * 获取慢spring bean
	 * @param item
	 * @return
	 */
	public MonitorResultVo getSlowSpring(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取慢 SQL
	 * @param item
	 * @return
	 */
	public MonitorResultVo getSlowSQL(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取请求响应时间
	 * @param item
	 * @return
	 */
	public MonitorResultVo getRequestTime(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取错误日志 ,可以使用这个命令获取指定范围的日志
	 * sed -n '/2014-12-17 16:17:20/,/2014-12-17 16:17:36/p'  test.log
	 * @param item
	 * @return
	 */
	public MonitorResultVo getErrorLog(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取java虚拟使用比例
	 * @param machine
	 * @param item
	 * @return
	 */
	public MonitorResultVo getJvmMem(MonitorMachine machine,MonitorItem item);
}
