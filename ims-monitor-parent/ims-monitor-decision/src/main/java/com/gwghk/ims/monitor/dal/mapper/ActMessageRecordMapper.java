package com.gwghk.ims.monitor.dal.mapper;

import java.util.List;

import com.gwghk.ims.monitor.dal.entity.ActMessageRecord;

public interface ActMessageRecordMapper {
	List<ActMessageRecord> queryList();

	int update();
}