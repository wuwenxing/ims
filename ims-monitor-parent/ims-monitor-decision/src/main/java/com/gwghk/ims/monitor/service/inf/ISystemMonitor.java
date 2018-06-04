package com.gwghk.ims.monitor.service.inf;

import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;

public interface ISystemMonitor {
	/**
	 * 获取cpu使用率
	 * @param item
	 * @return
	 */
	public MonitorResultVo getCPU(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取硬盘容量
	 * @param item
	 * @return
	 */
	public MonitorResultVo getHardDisk(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取内存状况
	 * @param item
	 * @return
	 */
	public MonitorResultVo getMemory(MonitorMachine machine,MonitorItem item);
	/**
	 * 获取上传速度
	 * @param machine
	 * @param item
	 * @return
	 */
	public MonitorResultVo getUploadSpeed(MonitorMachine machine, MonitorItem item);
	/**
	 * 获取下载速度
	 * @param machine
	 * @param item
	 * @return
	 */
	public MonitorResultVo getDownloadSpeed(MonitorMachine machine, MonitorItem item);
}
