package com.gwghk.ims.datacleaning.dao.inf.base;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ImsBlackList;

public interface ImsBlackListDao  extends BaseDao<ImsBlackList>{
	ImsBlackList select(@Param("accountNo") String accountNo);
}