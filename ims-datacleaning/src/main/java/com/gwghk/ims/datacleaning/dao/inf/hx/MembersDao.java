package com.gwghk.ims.datacleaning.dao.inf.hx;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.datacleaning.dao.entity.Members;

public interface MembersDao {
	
	@SuppressWarnings("rawtypes")
	List<Members> select(Map data);
}