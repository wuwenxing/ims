package com.gwghk.ims.common.inf.external.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.dto.activity.ActTaskItemsDTO;
/***
 * 
* 摘要:任务奖励实现
* @author George.li  
* @date 2018年5月4日  
* @version 1.0
 */
public interface ActTaskItemsDubboService {
	
	/**
	 * 根据规则识别码获取任务的物品
	 * @param ruleCode
	 * @param companyId
	 * @return
	 */
	ApiRespResult<List<ActTaskItemsDTO>> findListByTaskCode(String actNo,String ruleCode,@Company Long companyId);
 
 
}
