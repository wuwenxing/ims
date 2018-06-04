package com.gwghk.ims.datacleaning.dao.inf.hx;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.datacleaning.dao.entity.Demousers;

public interface DemousersDao {
	
	@SuppressWarnings("rawtypes")
	List<Demousers> select(Map data);
}