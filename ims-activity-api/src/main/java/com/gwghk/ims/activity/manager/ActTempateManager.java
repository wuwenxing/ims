package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gwghk.ims.activity.context.ActTaskBean;
import com.gwghk.ims.activity.context.ActTaskItemBean;
import com.gwghk.ims.activity.context.ActTemplateContextFactory;
import com.gwghk.ims.activity.context.ParamBean;
import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import com.gwghk.ims.activity.enums.ActTaskEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActTaskConfigVO;
import com.gwghk.ims.common.vo.activity.ActTaskCustomSettingVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemConfigVO;
import com.gwghk.ims.common.vo.system.ParamUnitVO;
import com.gwghk.ims.common.vo.system.ParamVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动模板业务处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月10日
 */
@Component
@Transactional
public class ActTempateManager {
 
	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager;
	
	 /**
     * 根据活动类型获得基本信息参数配置
     * @param activityType
     * @return
     */
    public List<ParamBean> getBaseParams(String activityType){
    	return ActTemplateContextFactory.getActBaseParams(activityType);
    }
    
    /**
     * 根据活动类型获得参与条件参数配置
     * @param activityType
     * @returnActRuleType
     */
    public Map<String,List<ParamBean>> getCondtionParams(String activityType){
    	return ActTemplateContextFactory.getActCondtionParamsMap(activityType);
    }
    
    /**
     * 根据活动类型获得任务与奖励参数配置(模板原始配置)
     * @param activityType
     * @return
     */
    public Map<String,ActTaskBean> getTaskConfigs(String activityType){
    	return ActTemplateContextFactory.getActTaskConfigs(activityType);
    }
 
    
    /**
     * 根据活动类型获得基本参数配置(VO)
     * @param activityType
     * @return
     */
    public List<ParamVO> getBaseParamVOs(String activityType){
    	List<ParamBean> baseParams = getBaseParams(activityType);
    	return wrapToParamVOList(baseParams,activityType);
    }
	
 
   /**
    *  根据活动类型参与条件配置(VO)
    * @param activityType
    * @return
    */
    public Map<String,List<ParamVO>> getCondtionParamVOs(String activityType){
    	Map<String,List<ParamBean>> condtionParams = getCondtionParams(activityType);
  		if(condtionParams!=null){
  			Map<String,List<ParamVO>> condtionParamsVO = new HashMap<String,List<ParamVO>>();
  			for(String cType : condtionParams.keySet()){
  				List<ParamBean> condParams = condtionParams.get(cType);
  				condtionParamsVO.put(cType, wrapToParamVOList(condParams,activityType));
  			}
  			return condtionParamsVO;
  		}
  		return null;
    }
  		
	
  
    /**
     *  根据活动类型获得任务与奖励参数配置(VO)
     */
    public Map<String,ActTaskConfigVO> getTaskConfigVOs(String activityType){
    	Map<String,ActTaskBean> taskConfigs = getTaskConfigs(activityType);
    	if(taskConfigs!=null){
    		//加载自定义任务(包括删除和启用的)
    		ActTaskCustomSettingVO reqTask = new ActTaskCustomSettingVO();
    		reqTask.setEnableFlag(null);
    		reqTask.setDeleteFlag(null);
			List<ActTaskCustomSetting> cTaskList = actTaskCustomSettingManager.findList(reqTask);
			Map<String,ActTaskCustomSetting> sysList = new HashMap<String,ActTaskCustomSetting>();
			Map<String,ActTaskCustomSetting> custList = new HashMap<String,ActTaskCustomSetting>();
			if(CollectionUtils.isNotEmpty(cTaskList)){
				for(ActTaskCustomSetting t : cTaskList){
				      if(ActTaskEnum.taskCustom.getCode().equals(t.getTaskType())){
				    	  custList.put(t.getTaskCode(), t);
				      }else  if(ActTaskEnum.taskSystem.getCode().equals(t.getTaskType())){
				    	  sysList.put(t.getTaskCode(), t);
				      }
				}
			}    		
    		Map<String,ActTaskConfigVO> taskConfigsVO = new LinkedHashMap<String,ActTaskConfigVO>();
    		for(String taskCode : taskConfigs.keySet()){
    			ActTaskBean actTaskBean = taskConfigs.get(taskCode);
    			if(taskCode.equals(ActTaskTypeEnum.ZDY_TASK.getCode())){//自定义任务
    				if(!custList.isEmpty()){
    					for(ActTaskCustomSetting custTask:custList.values()){
    						ActTaskConfigVO taskVO =wrapToTaskConfigVO(actTaskBean,activityType);
    						taskVO.setTaskType(custTask.getTaskType());
    						taskVO.setTaskName(custTask.getTaskName());
    						taskVO.setCode(custTask.getTaskCode());
    						taskVO.setDesc(custTask.getTaskDesc());
    						taskVO.setEnableFlag(custTask.getEnableFlag());
    						taskVO.setDeleteFlag(custTask.getDeleteFlag());
    						taskConfigsVO.put(taskVO.getCode(),taskVO);
    					}
    				}
    			}else{
    				ActTaskCustomSetting sysTask = sysList.get(taskCode);
    				if(sysTask!=null){
    					ActTaskConfigVO taskVO =wrapToTaskConfigVO(actTaskBean,activityType);
        				taskVO.setTaskType(sysTask.getTaskType());
        				taskVO.setTaskName(sysTask.getTaskName());
        				taskVO.setDesc(sysTask.getTaskDesc());
        				taskVO.setEnableFlag(sysTask.getEnableFlag());
						taskVO.setDeleteFlag(sysTask.getDeleteFlag());
        				taskConfigsVO.put(taskCode, taskVO);
    				}
    			}
    		}
    		  return taskConfigsVO;
    	}
    	return null;
    }
	
   
    /**
	 * 转换为VO
	 * @param taskConfigWrapper
	 * @return
	 */
	private ActTaskConfigVO wrapToTaskConfigVO(ActTaskBean actTaskBean,String activityType){
		//任务信息及参数封装VO对象
		ActTaskConfigVO taskConfigVO = new ActTaskConfigVO();
		ImsBeanUtil.copyNotNull(taskConfigVO, actTaskBean);
		taskConfigVO.setParams(wrapToParamVOList(actTaskBean.getParams(),activityType));
		//奖励物品信息及参数为VO对象
		Map<String, ActTaskItemBean> taskItemsMap = actTaskBean.getTaskItemsMap();
		if(taskItemsMap!=null){
			String itemTypeStr = actTaskBean.getTaskItemTypes();//支持的奖励物品类型开关控制,  "real,..."
			if(StringUtils.isNotBlank(itemTypeStr)){
				Map<String, ActTaskItemConfigVO> taskItemsMapVO = new HashMap<String, ActTaskItemConfigVO>();
				List<String> itemTypes = Arrays.asList(itemTypeStr.split(","));
				for(String itemType : taskItemsMap.keySet()){
					if(itemTypes.contains(itemType)){//只加载TaskItemTypes配置了的物品类型
						ActTaskItemBean taskItem = taskItemsMap.get(itemType);
						ActTaskItemConfigVO taskItemVO = new ActTaskItemConfigVO();
						ImsBeanUtil.copyNotNull(taskItemVO, taskItem);
						//奖励物品参数
						taskItemVO.setParams(wrapToParamVOList(taskItem.getParams(),activityType));
						taskItemsMapVO.put(itemType, taskItemVO);
					}
				}
				taskConfigVO.setTaskItemsMap(taskItemsMapVO);
			}
		}
		//子任务
		if(actTaskBean.getSubTaskConfig()!=null){
			ActTaskConfigVO subTaskConfigVO = wrapToTaskConfigVO(actTaskBean.getSubTaskConfig(),activityType);
			taskConfigVO.setSubTaskConfig(subTaskConfigVO);
		}
		return taskConfigVO;
	}
	
	/**
	 * 转换为VO
	 * @param params
	 * @return
	 */
	private List<ParamVO> wrapToParamVOList(List<ParamBean> params,String activityType){
		if(CollectionUtils.isNotEmpty(params)){
			List<ParamVO> paramVOList = new ArrayList<ParamVO>();
			for(ParamBean param :params){
				if(StringUtil.isNotEmpty(param.getActTypes())){//为空表示支持所有活动类型
					 //支持的活动类型
					 List<String> itemActTypes = Arrays.asList(param.getActTypes().split(","));
					 if(!itemActTypes.isEmpty() && !itemActTypes.contains(activityType)){
						 continue;
					 }
				}
				ParamVO paramVO = new ParamVO();
				ImsBeanUtil.copyNotNull(paramVO, param);
				paramVO.setParamUnit(ImsBeanUtil.copyNotNull(new ParamUnitVO(), param.getParamUnit()));
				//下拉单位
				if(param.getParamUnitMap()!=null){
				    Map<String,ParamUnitVO> paramUnitVOMap = new HashMap<String,ParamUnitVO>();
					for(String unitKey :param.getParamUnitMap().keySet()){
						ParamUnitVO paramUnitVO = new ParamUnitVO();
						ImsBeanUtil.copyNotNull(paramUnitVO, param.getParamUnitMap().get(unitKey));
						if(param.getParamUnitMap().get(unitKey)!=null && param.getParamUnitMap().get(unitKey).getOptions()!=null){
							paramUnitVO.setOptions(param.getParamUnitMap().get(unitKey).getOptions());
						}
						paramUnitVOMap.put(unitKey,paramUnitVO);
					}
					paramVO.setParamUnitMap(paramUnitVOMap);
				}
				paramVOList.add(paramVO);
			}
			return paramVOList;
		}
		return null;
	}
  		
}