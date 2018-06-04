package com.gwghk.ims.datacleaning.dao.inf.hxdemotrade;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.datacleaning.dao.entity.Mt4Trades;

public interface DemoMt4TradesDao {
	
	@SuppressWarnings("rawtypes")
	List<Mt4Trades> selectTrade(Map data);
}