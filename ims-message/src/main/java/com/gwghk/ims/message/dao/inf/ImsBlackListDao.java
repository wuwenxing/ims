package com.gwghk.ims.message.dao.inf;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.ImsBlackList;

public interface ImsBlackListDao  extends BaseDao<ImsBlackList>{
	ImsBlackList select(@Param("accountNo") String accountNo);
}