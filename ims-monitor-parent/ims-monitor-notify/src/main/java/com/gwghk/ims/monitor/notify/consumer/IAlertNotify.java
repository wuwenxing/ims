package com.gwghk.ims.monitor.notify.consumer;

import java.util.List;

import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.dao.entity.DutyStaff;
/**
 * 告警发送，它的实现类必须使用例如： EmailNotify 方式命名(假如email方式),只能在com.gwghk.ims.monitor.notify.service包下
 * @author jackson.tang
 *
 */
public interface IAlertNotify {
	/**
	 * 向指定的人员发送警告
	 * @param staffList 会更新里面的发送状态
	 * @param monitorResultVo
	 * @return
	 */
	public boolean send(List<DutyStaff> staffList, MonitorResultVo monitorResultDto);
}
