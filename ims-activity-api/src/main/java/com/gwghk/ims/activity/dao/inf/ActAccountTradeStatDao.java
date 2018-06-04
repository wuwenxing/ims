package com.gwghk.ims.activity.dao.inf;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ActAccountTradeStat;

public interface ActAccountTradeStatDao {

	int insert(ActAccountTradeStat record);

	ActAccountTradeStat findActAccountActivityStat(@Param("activityPeriods") String activityPeriods,
			@Param("accountNo") String accountNo, @Param("platform") String platform);
	
	int updateActAccountActivityStat(ActAccountTradeStat record);

	void saveOrUpdateNormal(ActAccountTradeStat record);

	void saveOrUpdate(ActAccountTradeStat record);
}