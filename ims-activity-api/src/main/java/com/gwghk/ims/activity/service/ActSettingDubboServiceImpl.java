package com.gwghk.ims.activity.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.manager.ActConditionSettingManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.activity.service.mis.CommonDubboService;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.dto.activity.ActConditionSettingDTO;
import com.gwghk.ims.common.dto.activity.ActSettingDTO;
import com.gwghk.ims.common.dto.activity.ActSettingDetailsDTO;
import com.gwghk.ims.common.dto.activity.ActTaskSettingDTO;
import com.gwghk.ims.common.enums.ActCommonStatusEnum;
import com.gwghk.ims.common.inf.external.activity.ActSettingDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 摘要：活动提案实现
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月3日
 */
@Service
public class ActSettingDubboServiceImpl  implements ActSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(ActSettingDubboServiceImpl.class);
	@Autowired
	private ActSettingManager actSettingManager;
	@Autowired
	private ActTaskSettingManager actTaskSettingManager;
	@Autowired
	private ActConditionSettingManager actConditionSettingManager;
	@Autowired
	private CommonDubboService commonDubboService;
	
	@Override
	public MisRespResult<ActSettingDetailsDTO> findByActivityPeriods(String activityPeriods, Long companyId) {
		logger.info("findByactivityPeriods-->【activityPeriods:{}】", activityPeriods);
		try {
			ActSettingDetailsDTO detailsDto = getActSettingDetailsDto(activityPeriods,companyId);
			return MisRespResult.success(detailsDto);
		} catch (Exception e) {
			logger.error("<--系统异常,【findByactivityPeriods】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 获得活动的所有详情信息
	 * @param activityPeriods
	 * @return
	 */
	private ActSettingDetailsDTO getActSettingDetailsDto(String activityPeriods,Long companyId){
		ActSettingDetailsDTO detailsDto = new ActSettingDetailsDTO();
		//基础信息
		ActSetting actSetting = actSettingManager.findByactivityPeriods(activityPeriods);
		if(actSetting!=null){
			ActSettingDTO actBaseInfo = ImsBeanUtil.copyNotNull(new ActSettingDTO(), actSetting);
			wrapActBaseInfo(actBaseInfo,companyId);
			detailsDto.setActBaseInfo(actBaseInfo);
			
			//参与条件
			ActConditionSettingDTO actCond = ImsBeanUtil.copyNotNull(new ActConditionSettingDTO(),actConditionSettingManager.findCustCondtionSettingVO(activityPeriods));
			detailsDto.setActCondSetting(actCond);
			
			//加载任务
			ActTaskSettingDTO taskReqDto = new ActTaskSettingDTO();
			taskReqDto.setActivityPeriods(activityPeriods);
			List<ActTaskSettingVO> actTaskSettingsVo=actTaskSettingManager.findTaskTreeList(activityPeriods);
			commonDubboService.wrapTaskFullName(actTaskSettingsVo); 
			
			List<ActTaskSettingDTO> actTaskSettings = ImsBeanUtil.copyList(actTaskSettingsVo,ActTaskSettingDTO.class);
			detailsDto.setActTaskSettings(actTaskSettings);
		}
		
		return detailsDto;
	}
	 
	/**
	 * 活动基本信息封装格式化显示
	 * @param actBaseInfo
	 * @return
	 */
	private void wrapActBaseInfo(ActSettingDTO actBaseInfo,Long companyId){
		//活动状态:启用、禁用、无效
		String activityStatus = actBaseInfo.getEnableFlag();
        Date endDate = actBaseInfo.getEndTime();
        Date curTime = new Date();
        if(actBaseInfo.getFinishDays() > 0){//取活动结束时间+n天
            Calendar actEndDate = Calendar.getInstance();
            actEndDate.setTime(endDate);
            actEndDate.add(Calendar.DATE, actBaseInfo.getFinishDays());
            endDate = actEndDate.getTime();
        }
        if (endDate != null && curTime.after(endDate)) { // 活动结束时间已过期,标记为无效
            activityStatus = ActCommonStatusEnum.INVALID.getCode();
        }
        actBaseInfo.setEnableFlagName(ActCommonStatusEnum.formatCode(activityStatus));
        //加载所属标签 名
        actBaseInfo.setTagName(commonDubboService.getTagName(actBaseInfo.getTagCode(),companyId));
	}
	 
}