package com.gwghk.ims.common.inf.external.prize;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.dto.job.ActAccountActivityReqDTO;
import com.gwghk.ims.common.dto.job.ActAccountActivityRespDTO;

/**
 * @ClassName: ActAccountActivityDubboService
 * @Description: 活动资格记录汇总
 * @author lawrence
 * @date 2017年6月19日
 *
 */
public interface ActAccountActivityDubboService {
	/**
	 * 功能：查询用户-活动列表
	 */
	List<ActAccountActivityRespDTO> getAccountActivityList(ActAccountActivityReqDTO actAccountActivityReqDto);
	
	/**
	 * 功能：同步用户-活动关系数据
	 */
	void syncAccountActivity(@Company Long companyId);
	
	/**
	 * 功能：根据查询条件->同步用户-活动关系数据
	 */
	void syncAccountActivityRecordWithParams(Map<String,Object> actParams,@Company Long companyId);
}
