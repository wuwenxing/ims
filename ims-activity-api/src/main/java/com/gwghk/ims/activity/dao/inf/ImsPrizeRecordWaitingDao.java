package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.ImsPrizeRecordWaiting;
import com.gwghk.ims.common.common.BaseDao;

public interface ImsPrizeRecordWaitingDao extends BaseDao<ImsPrizeRecordWaiting> {
	/**
	 * 获取所有可以发送的发放记录
	 * @return
	 */
	List<ImsPrizeRecordWaiting> findReadySendRecord();
	
}