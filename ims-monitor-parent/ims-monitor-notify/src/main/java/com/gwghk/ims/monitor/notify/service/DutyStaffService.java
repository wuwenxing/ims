package com.gwghk.ims.monitor.notify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.monitor.notify.dao.entity.DutyStaff;
import com.gwghk.ims.monitor.notify.dao.inf.DutyStaffDao;

import net.oschina.durcframework.easymybatis.query.Query;

@Service
public class DutyStaffService {
	@Autowired
	private DutyStaffDao dutyStaffDao;
	
	public List<DutyStaff> findDutyStaffByItemId(Long itemId) {
		
		//select s.* from duty_staff ds left join staff s on ds.staff_id=s.staff_id where ds.item_id=#{itemId};
		Query query=Query.build();
		query.addOtherColumns("t2.*").join("left join staff t2 on t.staff_id=t2.staff_id").eq("item_id", itemId);
		return dutyStaffDao.findAll(query);
	}
	
}
