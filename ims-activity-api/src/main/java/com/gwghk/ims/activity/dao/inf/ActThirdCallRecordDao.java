package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.ActThirdCallRecord;
import com.gwghk.ims.common.common.BaseDao;


public interface ActThirdCallRecordDao extends BaseDao<ActThirdCallRecord>{

	List<ActThirdCallRecord> findFailureBonus(ActThirdCallRecord actThirdCallRecord);

	List<ActThirdCallRecord> findFailureBonusRecordNumber(ActThirdCallRecord params);

	Integer selectCount(ActThirdCallRecord params);

}