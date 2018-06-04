package com.gwghk.ims.activity.service.mis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.manager.ActConditionSettingManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.manager.ActTagManager;
import com.gwghk.ims.activity.manager.ActTaskCustomSettingManager;
import com.gwghk.ims.activity.manager.ActTaskItemsManager;
import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.activity.manager.ActTempateManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.Kuak;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActAllowStatusEnum;
import com.gwghk.ims.common.enums.ActCommonStatusEnum;
import com.gwghk.ims.common.enums.ActDiscountTypeEnum;
import com.gwghk.ims.common.enums.ActIssuingTypeEnum;
import com.gwghk.ims.common.enums.ActUnitEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DictCodeEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.activity.ActTaskConfigVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ActTemplateConfigVO;
import com.gwghk.ims.common.vo.base.ActTagVO;
import com.gwghk.ims.common.vo.system.ParamVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动提案实现
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月3日
 */
@Service
public class MisActSettingDubboServiceImpl  implements MisActSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActSettingDubboServiceImpl.class);
 
	

	@Override
	public MisRespResult<PageR<ActSettingVO>> findPageList(ActSettingVO vo,Long companyId) {
		logger.info("findPageList-->【params:{}】", vo.toString());
		try {
			PageR<ActSettingVO> pageR = actSettingManager.findPageList(vo);
			 if (pageR != null) {
	                List<ActSettingVO> actSettingList = pageR.getRows();
	                if (CollectionUtils.isNotEmpty(actSettingList)) {
	                	for(ActSettingVO act :actSettingList){
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

	@Override
	public MisRespResult<List<ActSettingVO>> findList(ActSettingVO vo,boolean format,Long companyId) {
		logger.info("findList-->【params:{}】", vo.toString());
		try {
			List<ActSettingVO> actSettingList = actSettingManager.findList(vo);
			if(format){
				if (CollectionUtils.isNotEmpty(actSettingList)) {
	            	for(ActSettingVO act :actSettingList){
	            		wrapActBaseInfo(act,companyId);
	            	}
	            }
			}
			return MisRespResult.success(actSettingList);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	

	@Override
	public MisRespResult<Void> saveOrUpdate(ActSettingDetailsVO vo,Long companyId) {
		logger.info("saveOrUpdate-->【params:{}】",vo.toString());
		try {
			vo.setCompanyId(companyId);
			return actSettingManager.saveOrUpdate(vo);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	@Override
	public MisRespResult<ActSettingDetailsVO> findByActivityPeriods(String activityPeriods, Long companyId) {
		logger.info("findByactivityPeriods-->【activityPeriods:{}】", activityPeriods);
		try {
			ActSettingDetailsVO detailsVO = getActSettingDetailsVO(activityPeriods,companyId);
			return MisRespResult.success(detailsVO);
		} catch (Exception e) {
			logger.error("<--系统异常,【findByactivityPeriods】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public  MisRespResult<Map<String,Object>> findActDetailInfoAndTemplateConfig(String activityType,String activityPeriods,Long companyId){
		logger.info("findActDetailInfoAndTemplateConfig-->【activityType:{},activityPeriods:{},companyId:{}】", new Object[]{activityType,activityPeriods,companyId});
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			ActSettingDetailsVO actDetails = new ActSettingDetailsVO();
			//加载活动详情信息
			if(StringUtil.isNotEmpty(activityPeriods)){
			    actDetails = getActSettingDetailsVO(activityPeriods,companyId);
				if(actDetails!=null && actDetails.getActBaseInfo()!=null && StringUtils.isBlank(activityType)){
					activityType = actDetails.getActBaseInfo().getActivityType();
				}
			}
			map.put("actDetails", actDetails);
			//加载活动模板
			ActTemplateConfigVO actTemplateConfig = new ActTemplateConfigVO();
			if(StringUtils.isNotBlank(activityType)){
				actTemplateConfig = getActTemplateConfigVO(activityType,companyId);
			}
			map.put("actTemplateConfig", actTemplateConfig);
			
			//加载各下拉集合等相关动态数据
			map.put("dynamicList", getDynamicList(companyId));
			return MisRespResult.success(map);
		} catch (Exception e) {
			logger.error("<--系统异常,【findActDetailInfoAndTemplateConfig】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	private Map<String,Object> getDynamicList(Long companyId){
		Map<String,Object> dynamicList = new HashMap<String,Object>();
		DictCodeEnum.itemsUnit.getValue();
		//公用 ：单位列表
		dynamicList.put("itemsUnitMap", commonDubboService.getSubDictMapByParentCode(DictCodeEnum.itemsUnit.getLabelKey(),companyId));
 
		//step1:活动基本信息
		//是否允许转组
		dynamicList.put("turnGroup_options", ActAllowStatusEnum.getOptions());
		//是否允许转账
		dynamicList.put("transfer_options", ActAllowStatusEnum.getOptions());
		//释放类型
		dynamicList.put("releaseType_options", ActDiscountTypeEnum.getOptions());
		//任务发放方式 
		dynamicList.put("issuingType_options", ActIssuingTypeEnum.getOptions());
		//标签列表，获得已启用的标签
		ActTagVO reqUserTagVO = new ActTagVO();
		reqUserTagVO.setCompanyId(companyId);
		actTagManager.findTagOptions(reqUserTagVO, companyId);
		dynamicList.put("tagCode_options",actTagManager.findTagOptions(reqUserTagVO, companyId));
		
		//step2:参与条件
		//账号类型
		addSubDictOptions(dynamicList,"accountType",DictCodeEnum.actAccountType.getLabelKey(),companyId);
		//账户状态
		addSubDictOptions(dynamicList,"accountStatus",DictCodeEnum.actAccountStatus.getLabelKey(),companyId);
		//账号级别
		addSubDictOptions(dynamicList,"accountLevel",DictCodeEnum.actAccountLevel.getLabelKey(),companyId);
		//账号唯一性
		List<Kuak> yesNoOptions = new ArrayList<Kuak>();
		yesNoOptions.add(new Kuak(ActCommonStatusEnum.NO.getCode(),ActCommonStatusEnum.NO.getName()));
		yesNoOptions.add(new Kuak(ActCommonStatusEnum.YES.getCode(),ActCommonStatusEnum.YES.getName()));
		dynamicList.put("accountOnly_options",yesNoOptions);
		//注销过账号参加活动
		dynamicList.put("allowCancelAccount_options",yesNoOptions);
		//只允许白名单用户参加活动
        dynamicList.put("allowWhiteUsers_options",yesNoOptions);
        //是否允许取款
        dynamicList.put("allowWithdrawals_options",yesNoOptions);
        
		//根据不同的公司获取对应的平台
		List<Kuak> platformsCcyOptions = new ArrayList<Kuak>();
        if(companyId!=null){
            if(CompanyEnum.fx.getId().equals(String.valueOf(companyId))){
            	platformsCcyOptions.add(new Kuak("GTS2#USD","GTS2#USD"));
            	platformsCcyOptions.add(new Kuak("MT4#USD","MT4#USD"));
            	platformsCcyOptions.add(new Kuak("GTS2#CNY","GTS2#CNY"));
            	platformsCcyOptions.add(new Kuak("MT4#CNY","MT4#CNY"));
            }else  if(CompanyEnum.hx.getId().equals(String.valueOf(companyId))){
            	platformsCcyOptions.add(new Kuak("GTS2#USD","GTS2#USD"));
            	platformsCcyOptions.add(new Kuak("MT4#USD","MT4#USD"));
            }else if(CompanyEnum.hxfx.getId().equals(String.valueOf(companyId))){
            	platformsCcyOptions.add(new Kuak("GTS2#USD","GTS2#USD"));
            	platformsCcyOptions.add(new Kuak("MT4#USD","MT4#USD"));
            }else if(CompanyEnum.pm.getId().equals(String.valueOf(companyId))){
            	platformsCcyOptions.add(new Kuak("GTS2#USD","GTS2#USD"));
            	platformsCcyOptions.add(new Kuak("MT4#USD","MT4#USD"));
            }
        }
        dynamicList.put("platformsCcy_options",platformsCcyOptions);
         
        
        
        //任务与物品
        //物品接口公式
        addSubDictOptions(dynamicList,"itemParamVal",DictCodeEnum.actInfItemRule.getLabelKey(),companyId);
        //交易手数单位
  		List<Kuak> tradeLotUnitOptions = new ArrayList<Kuak>();
  		tradeLotUnitOptions.add(new Kuak(ActUnitEnum.LOT.getCode(),ActUnitEnum.LOT.getName()));
  		tradeLotUnitOptions.add(new Kuak(ActUnitEnum.PERCENTAGE.getCode(),ActUnitEnum.PERCENTAGE.getName()));
  		dynamicList.put("tradeLotUnit_options",tradeLotUnitOptions);
		return dynamicList;
	}
	
	private void addSubDictOptions(Map<String,Object> dynamicList,String proName,String parentCode,Long companyId){
		dynamicList.put(proName+"_options", commonDubboService.getOptionsByParentCode(parentCode,companyId));//账号类型
	}

	/**
	 * 获得模板配置VO对象
	 * @param activityType
	 * @param companyId
	 * @return
	 */
	private ActTemplateConfigVO getActTemplateConfigVO(String activityType, Long companyId){
		ActTemplateConfigVO actTemplateConfig = new ActTemplateConfigVO();
		//基本信息参与配置
		List<ParamVO> baseParamVOs = actTempateManager.getBaseParamVOs(activityType);
		actTemplateConfig.setBaseParams(baseParamVOs);
		
		//基本信息参与配置
		Map<String,List<ParamVO>> condtionParamVOs = actTempateManager.getCondtionParamVOs(activityType);
		actTemplateConfig.setCondParams(condtionParamVOs);
		
		//任务与物品配置
		Map<String,ActTaskConfigVO> taskConfigVOs = actTempateManager.getTaskConfigVOs(activityType);
		actTemplateConfig.setTaskConfigs(taskConfigVOs);
		
		return actTemplateConfig;
	}
	
	
	/**
	 * 获得活动的所有详情信息
	 * @param activityPeriods
	 * @return
	 */
	private ActSettingDetailsVO getActSettingDetailsVO(String activityPeriods,Long companyId){
		ActSettingDetailsVO detailsVO = new ActSettingDetailsVO();
		//基础信息
		ActSetting actSetting = actSettingManager.findByactivityPeriods(activityPeriods);
		if(actSetting!=null){
			ActSettingVO actBaseInfo = ImsBeanUtil.copyNotNull(new ActSettingVO(), actSetting);
			wrapActBaseInfo(actBaseInfo,companyId);
			detailsVO.setActBaseInfo(actBaseInfo);
			
			//参与条件
			ActConditionSettingVO actCond = actConditionSettingManager.findCustCondtionSettingVO(activityPeriods);
			detailsVO.setActCondSetting(actCond);
			 
			//加载任务
			ActTaskSettingVO taskReqVo = new ActTaskSettingVO();
			taskReqVo.setActivityPeriods(activityPeriods);
			List<ActTaskSettingVO> actTaskSettings = actTaskSettingManager.findTaskTreeList(activityPeriods);
			commonDubboService.wrapTaskFullName(actTaskSettings); 
			detailsVO.setActTaskSettings(actTaskSettings);
		}
		
		return detailsVO;
	}
	 
	
	/**
	 * 活动基本信息封装格式化显示
	 * @param actBaseInfo
	 * @return
	 */
	private void wrapActBaseInfo(ActSettingVO actBaseInfo,Long companyId){
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
	
	
 
	
	 
	
	/**
	 * 根据活动类型获得活动模板配置
	 * @param activityType
	 * @param companyId
	 * @return
	 */
	@Override
	public MisRespResult<Map<String,Object>> findActConfigByActivityType(String activityType, Long companyId) {
		logger.info("findActConfigByActivityType-->【params:{}】", activityType.toString());
		try {
			//加载活动模板
			Map<String,Object> resultMap = new HashMap<String,Object>();
			ActTemplateConfigVO	actTemplateConfig = getActTemplateConfigVO(activityType,companyId);
			resultMap.put("actTemplateConfig", actTemplateConfig);
			//加载各下拉集合等相关动态数据
			resultMap.put("dynamicList", getDynamicList(companyId));
			return MisRespResult.success(resultMap);
		} catch (Exception e) {
			logger.error("<--系统异常,【findActConfigByActivityType】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	 
	}
	
	/**
	 * 批量审批活动
	 * @param ids
	 * @param approver
	 * @param updateIp
	 * @return
	 */
	@Override
	public  MisRespResult<Void> approve(String ids,SystemUserVO approver,Long companyId) {
        try {
            String[] idArr = ids.split(",");
            if (idArr != null && idArr.length > 0) {
                for (String id : idArr) {
                	MisRespResult<Void> result = actSettingManager.updateApproveById(Long.valueOf(id),approver.getUserName(),approver.getUpdateIp());
                    if (result.isNotOk()) {
                        return result;
                    } 
                }
            } 
            return MisRespResult.success();
        } catch (Exception e) {
            logger.error("<----canclel->审批活动提案失败!{}", ids, e);
            return MisRespResult.error(MisResultCode.EXCEPTION);
        }
    }
	
	/**
	 * 取消活动提案
	 * @param ids
	 * @param canceler -取消者
	 * @param companyId  
	 * @return
	 */
	@Override
	public MisRespResult<Void> cancel(String ids,SystemUserVO canceler,@Company Long companyId){
		 try {
	            String[] idArr = ids.split(",");
	            if (idArr != null && idArr.length > 0) {
	                for (String id : idArr) {
	                	MisRespResult<Void> result = actSettingManager.updateCancelById(Long.valueOf(id),canceler.getUserName(),canceler.getUpdateIp());
	                    if (result.isNotOk()) {
	                        return result;
	                    } 
	                }
	            } 
	            return MisRespResult.success();
	        } catch (Exception e) {
	            logger.error("<----canclel->取消活动提案失败!{}", ids, e);
	            return MisRespResult.error(MisResultCode.EXCEPTION);
	        }
	}
	
	/**
	 * 获得系统默认的活动编号
	 * @param idArray
	 * @param companyId
	 * @return
	 */
	@Override
	public MisRespResult<String> getNewActivityPeriods(Long companyId){
		String companyCode = CompanyEnum.getCodeById(companyId) ;
		String newItemNumber = (StringUtil.isNotEmpty(companyCode)?companyCode:"") + "_" + ImsDateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
		return MisRespResult.success(newItemNumber);
	}
	
	
	@Autowired
	private ActSettingManager actSettingManager;
	
	@Autowired
	private ActTempateManager actTempateManager;
	
	@Autowired
	private ActTaskSettingManager actTaskSettingManager;
	
	@Autowired
	private ActConditionSettingManager actConditionSettingManager;
	
	@Autowired
	private ActTaskItemsManager actTaskItemsManager;
	
	@Autowired
	private CommonDubboService commonDubboService;
	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager;
	@Autowired
	private ActTagManager actTagManager;

 
	 
}