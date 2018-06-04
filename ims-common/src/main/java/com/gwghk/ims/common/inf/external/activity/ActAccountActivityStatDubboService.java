package com.gwghk.ims.common.inf.external.activity;

import java.util.List;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.dto.job.ActAccountActivityStatRespDTO;	

/**
 * 
 * 摘要：活动用户查询
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月31日
 */
public interface ActAccountActivityStatDubboService {
	
	/**
	 * 根据清算状态查询列表
	 * @param settleStatus
	 * @return
	 */
	ApiRespResult<List<ActAccountActivityStatRespDTO>> findListBySettleStatus(int settleStatus); 
	
}
