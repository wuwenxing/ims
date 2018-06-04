package com.gwghk.ims.datacleaning.dao.inf.base;

import java.util.List;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ActSyncDataUpdate;

/**
 * 
 * @ClassName: ActivitySyncDataUpdateMapper
 * @Description: 外部表同步记录mapper
 * @author lawrence
 * @date 2017年5月19日
 *
 */
public interface ActSyncDataUpdateDao extends BaseDao<ActSyncDataUpdate> {
	List<ActSyncDataUpdate> findBySyncType(String syncType);
}
