package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;

public interface MisActSettingDubboService {
 
	MisRespResult<ActSettingDetailsVO> findByActivityPeriods(String activityPeriods,@Company Long companyId);
	
	MisRespResult<PageR<ActSettingVO>> findPageList(ActSettingVO vo,@Company Long companyId);
	
	/**
	 * 加载活动
	 * @param vo
	 * @param format 是否需要封装国际化显示字段
	 * @param companyId
	 * @return
	 */
	MisRespResult<List<ActSettingVO>> findList(ActSettingVO vo,boolean format,@Company Long companyId);
	 
	
	MisRespResult<Void> saveOrUpdate(ActSettingDetailsVO vo,@Company Long companyId);
  
	
	MisRespResult<String> getNewActivityPeriods(@Company Long companyId);
	
	/**
	 * 加载活动及活动模板信息
	 * @param activityType
	 * @param activityPeriods
	 * @param companyId
	 * @return
	 */
	MisRespResult<Map<String,Object>> findActDetailInfoAndTemplateConfig(String activityType,String activityPeriods,@Company Long companyId);
	
	/**
	 * 根据活动类型加载活动模板配置
	 * @param activityType
	 * @param companyId
	 * @return
	 */
	MisRespResult<Map<String,Object>> findActConfigByActivityType(String activityType,@Company Long companyId);
	
	/**
	 * 批量审批通过活动
	 * @param ids
	 * @param approver -审核者
	 * @param companyId  
	 * @return
	 */
	MisRespResult<Void> approve(String ids,SystemUserVO approver,@Company Long companyId);
	
	/**
	 * 取消活动提案
	 * @param ids
	 * @param Canceler -取消者
	 * @param companyId  
	 * @return
	 */
	MisRespResult<Void> cancel(String ids,SystemUserVO canceler,@Company Long companyId);
 
}
