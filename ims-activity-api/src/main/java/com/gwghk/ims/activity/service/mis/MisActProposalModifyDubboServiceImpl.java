package com.gwghk.ims.activity.service.mis;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gwghk.ims.activity.manager.ActProposalModifyManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActCommonStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActProposalModifyDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActProposalModifyVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;

@Service
public class MisActProposalModifyDubboServiceImpl implements MisActProposalModifyDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisActProposalModifyDubboServiceImpl.class);
	@Autowired
	private ActProposalModifyManager actProposalModifyManager;
	@Autowired
	private MisActSettingDubboService misActSettingDubboService;
	
	@Autowired
	private CommonDubboService commonDubboService;
	
	@Override
	public MisRespResult<PageR<ActProposalModifyVO>> findPageList(ActProposalModifyVO vo,Long companyId) {
		vo.setCompanyId(companyId);
		
		logger.info("findPageList-->【params:{}】", vo.toString());
		try {
			PageR<ActProposalModifyVO> pageR = actProposalModifyManager.findPageList(vo);
			 if (pageR != null) {
	                List<ActProposalModifyVO> actSettingList = pageR.getRows();
	                if (CollectionUtils.isNotEmpty(actSettingList)) {
	                	for(ActProposalModifyVO act :actSettingList){
	                		wrapActBaseInfo(act,companyId);
	                	}
	                }
			 }
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}

	}
	
	/**
	 * 活动基本信息封装格式化显示
	 * @param actBaseInfo
	 * @return
	 */
	private void wrapActBaseInfo(ActProposalModifyVO actBaseInfo,Long companyId){
		//活动状态:启用、禁用、无效
		String activityStatus = actBaseInfo.getEnableFlag();
        Date endDate = actBaseInfo.getActivityEndTime();
        Date curTime = new Date();
        if(actBaseInfo.getFinishDays()!=null && actBaseInfo.getFinishDays() > 0){//取活动结束时间+n天
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
	
	/**
     * 根据提案号，查询该提案的活动的修改详情
     * 
     * @param actSetting
     * @param actProposalModify
     * @return
     */
	@Override
	public MisRespResult<Map<String,Object>> getActProposalModifyByPno(String pno,Long companyId){
		try {
			Map<String,Object> resultMap= actProposalModifyManager.getActProposalModifyByPno(pno);
			if(resultMap!=null && resultMap.get("actSettingDetails")!=null){
				String activityType = ((ActSettingDetailsVO)resultMap.get("actSettingDetails")).getActBaseInfo().getActivityType();
				//加载活动模板
				MisRespResult<Map<String,Object>> actConfigResult = misActSettingDubboService.findActConfigByActivityType(activityType, companyId);
				if(actConfigResult.isOk()){
					resultMap.putAll(actConfigResult.getData());
				}
			}
			return MisRespResult.success(resultMap);
		} catch (Exception e) {
			logger.error("<--系统异常,【getActProposalModifyByPno【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
     * 功能：批核活动修改提案
     */
	@Override
	public MisRespResult<Void> updateApproveModifyProposal(String pno, boolean isAutoApprove,SystemUserVO approver,Long companyId){
		try {
			return actProposalModifyManager.updateApproveModifyProposal(pno,isAutoApprove,approver!=null?approver.getUpdateUser():null);
		} catch (Exception e) {
			logger.error("<--系统异常,【updateApproveModifyProposal【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
    }
	
	 /**
     * 功能：取消活动修改提案
     */
	@Override
	public MisRespResult<Void> updateCancelModifyProposal(String pno,String cancelReason,SystemUserVO approver,Long companyId){
		try {
			return actProposalModifyManager.updateCancelModifyProposal(pno,cancelReason,approver!=null?approver.getUpdateUser():null);
		} catch (Exception e) {
			logger.error("<--系统异常,【updateCancelModifyProposal【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
    }
	

}
