package com.gwghk.ims.monitor.notify.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.dao.entity.AlertNotifyRecord;
import com.gwghk.ims.monitor.notify.dao.entity.DutyStaff;
import com.gwghk.ims.monitor.notify.dao.inf.AlertNotifyRecordDao;

@Service
public class AlertNotifyRecordService {
	@Autowired
	private AlertNotifyRecordDao notifyRecordDao;
	
	public boolean batchSave(List<DutyStaff> staffList, MonitorResultVo dto) {
		List<AlertNotifyRecord> list=new ArrayList<AlertNotifyRecord>();
		Date curDate=new Date();
		for(DutyStaff staff:staffList) {
			AlertNotifyRecord record=new AlertNotifyRecord();
			record.setNotifyStatusJson(JSON.toJSONString(staff.getNotifyStatusMap()));
			record.setStaffId(staff.getStaffId());
			record.setAlertContent(JSON.toJSONString(dto));
			record.setAlertDate(curDate);
			list.add(record);
		}
		
		return notifyRecordDao.saveBatch(list)>0;
	}
	
}
