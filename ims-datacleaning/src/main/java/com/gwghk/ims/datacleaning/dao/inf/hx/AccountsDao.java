package com.gwghk.ims.datacleaning.dao.inf.hx;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.datacleaning.dao.entity.Accounts;

public interface AccountsDao {
	
	@SuppressWarnings("rawtypes")
	List<Accounts> select(Map data);
}