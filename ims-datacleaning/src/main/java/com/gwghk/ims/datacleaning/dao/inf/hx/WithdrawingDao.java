package com.gwghk.ims.datacleaning.dao.inf.hx;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.datacleaning.dao.entity.Withdrawing;

public interface WithdrawingDao {
	
	@SuppressWarnings("rawtypes")
	List<Withdrawing> select(Map data);
}