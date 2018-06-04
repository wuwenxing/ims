package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.common.common.BaseDao;

public interface ActAccountActiviStatDao extends BaseDao<ActAccountActiviStat>{
	List<ActAccountActiviStat> findByAccountNo(String accountNo, Long companyId);
	
	ActAccountActiviStat findByBean(ActAccountActiviStat accActStat);
}