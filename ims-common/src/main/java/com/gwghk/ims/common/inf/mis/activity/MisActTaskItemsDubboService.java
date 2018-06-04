package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;

public interface MisActTaskItemsDubboService {
	
	/**
	 * 加载任务奖励物品
	 * @param vo
	 * @param companyId
	 * @return
	 */
	MisRespResult<List<ActTaskItemsVO>> findList(ActTaskItemsVO vo,@Company Long companyId);
 
 
}
