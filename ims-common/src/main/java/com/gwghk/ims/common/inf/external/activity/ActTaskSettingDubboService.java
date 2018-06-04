package com.gwghk.ims.common.inf.external.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/***
 * 
* 摘要: 活动任务实现
* @author George.li  
* @date 2018年5月4日  
* @version 1.0
 */
public interface ActTaskSettingDubboService {
	
	/**
	 * 加载任务
	 * @param vo
	 * @param companyId
	 * @return
	 */
	MisRespResult<List<ActTaskSettingVO>> findList(ActTaskSettingVO vo,@Company Long companyId);
 
 
}
