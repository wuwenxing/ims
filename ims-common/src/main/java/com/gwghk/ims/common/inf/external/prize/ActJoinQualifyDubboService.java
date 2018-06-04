package com.gwghk.ims.common.inf.external.prize;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.enums.ActTypeEnum;

/**
 * 
 * @ClassName: ActJoinQualifyDubboService
 * @Description: 处理符合活动资格条件的客户
 * @author lawrence
 * @date 2017年5月24日
 *
 */
public interface ActJoinQualifyDubboService {
	
	/**
	 * 
	 * @MethodName: processActCustomerQuality
	 * @Description: 处理活动资格以及用户完成的参数任务
	 * @param actSettingType 活动类型
	 * @param isSending
	 * @param companyId
	 * @return void
	 */
	void processActCustomerQuality(ActTypeEnum actSettingType,boolean isSending,@Company Long companyId);
		
	/**
	 * @MethodName: processActCustomerQuality
	 * @Description: 处理活动资格以及用户完成的参数任务
	 * @param actSettingType 活动类型
	 * @param actParams  相关参数
	 * @param companyId
	 * @return void
	 */
	void processActCustomerQuality(ActTypeEnum actSettingType,Map<String,Object> actParams,@Company Long companyId);
}
