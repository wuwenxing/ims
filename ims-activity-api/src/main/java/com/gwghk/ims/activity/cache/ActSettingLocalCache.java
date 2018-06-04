package com.gwghk.ims.activity.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActConditionSetting;
import com.gwghk.ims.activity.dao.entity.ActConditionSettingWrapper;
import com.gwghk.ims.activity.dao.entity.ActConditionValJson;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActSettingParamsValJson;
import com.gwghk.ims.activity.dao.inf.ActConditionSettingDao;
import com.gwghk.ims.activity.dao.inf.ActSettingDao;
import com.gwghk.ims.activity.dao.inf.ActTaskSettingDao;
import com.gwghk.ims.common.enums.ActRuleTypeEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.unify.framework.cache.local.AbstractLocalCache;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动信息缓存
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月4日
 */
@Component
public class ActSettingLocalCache extends AbstractLocalCache {
    private static Logger logger = LoggerFactory.getLogger(ActSettingLocalCache.class);

     
    @Autowired
    public ActSettingDao actSettingDao;
    
    @Autowired
    private ActConditionSettingDao actConditionSettingDao;
    @Autowired
    private ActTaskSettingDao actTaskSettingDao;
 
    /**
     * 所有活动
     */
    public static Map<String,ActSetting> activityMap = new ConcurrentHashMap<String,ActSetting>();
 
    public static Map<String,ActConditionSettingWrapper> actCodnitionCustomInfoWrapMap = new ConcurrentHashMap<String,ActConditionSettingWrapper>();
 
    public static Map<String,ActConditionSetting> actCodnitionCustomInfoMap = new ConcurrentHashMap<String,ActConditionSetting>();
    /**
     * 活动延迟时间
     */
    public static  int lazySecond =  600;//10分钟 10*60
 

    @Override
    public void reloadData() throws Exception {
        logger.debug("reloadData->start, 活动信息数据全量同步.");
        long start = System.currentTimeMillis();
        loadAllActSettingMap();
        actCodnitionCustomInfoMap.clear();
        actCodnitionCustomInfoWrapMap.clear();
        logger.debug("reloadData->end,完成活动信息数据全量同步.当前共有活动:{}个,总耗时：{}ms",activityMap.size(),(System.currentTimeMillis() - start));
    }
 
    
    /**
     * 加载系统中所有活动
     * @return
     */
    private void loadAllActSettingMap(){
    	  List<ActSetting> tempActivityList = actSettingDao.findListByMap(new HashMap<>());
          if (CollectionUtils.isNotEmpty(tempActivityList)) {
        	  for(ActSetting actSetting : tempActivityList){
        		  if(StringUtil.isNotEmpty(actSetting.getActivityPeriods())){
        			  activityMap.put(actSetting.getActivityPeriods(), warpActSetting(actSetting));
        		  }
              }
          }
    }
    
    private ActSetting warpActSetting(ActSetting actSetting){
    	   //把json中的属性值赋值给actSetting对象
  	      ActSettingParamsValJson jsonVO = JsonUtil.json2Obj(actSetting.getParamsVal(), ActSettingParamsValJson.class);
          ImsBeanUtil.copyNotNull(actSetting, jsonVO);
          return actSetting;
    }
	
    /**
     * 重新加载活动缓存
     * @param activityPeriods
     */
    public void reloadActCacheByActivityPeriods(String activityPeriods){
    	activityMap.remove(activityPeriods);
    	actCodnitionCustomInfoWrapMap.remove(activityPeriods);
    	actCodnitionCustomInfoMap.remove(activityPeriods);
    	Map<String,Object> reqMap = new HashMap<String,Object>();
    	reqMap.put("activityPeriods", activityPeriods);
    	ActSetting actSetting = actSettingDao.findObjectByMap(reqMap);
    	if(actSetting!=null){
    		activityMap.put(activityPeriods, warpActSetting(actSetting));
    	}
    }
  
    /**
     * 功能：查询进行中的活动列表
     */
    public List<ActSetting> getActiveActSettingList() {
        if (CollectionUtils.isNotEmpty(activityMap.values())) {
            List<ActSetting> activeActSettingList = new ArrayList<>();
            Date curDate = new Date();
            for (ActSetting as : activityMap.values()) {
                Date actEndTime = as.getEndTime();
                if (as.getFinishDays() != null && as.getFinishDays() > 0) {
                    Calendar actEndDate = Calendar.getInstance();
                    actEndDate.setTime(actEndTime);
                    actEndDate.add(Calendar.DATE, as.getFinishDays());
                    actEndTime = actEndDate.getTime();
                }
                if (curDate.compareTo(as.getStartTime()) > 0 && curDate.compareTo(actEndTime) < 0) {
                    activeActSettingList.add(as);
                }
            }
            return activeActSettingList;
        }
        return null;
    }

    /**
     * 功能：查询活动列表(包括非进行中的)
     */
    public List<ActSetting> getActSettingList(Long companyId) {
        if (CollectionUtils.isNotEmpty(activityMap.values())) {
            List<ActSetting> activeActSettingList = new ArrayList<>();
            for (ActSetting as : activityMap.values()) {
                if (as.getCompanyId().equals(companyId)) {
                    activeActSettingList.add(as);
                }
            }
            return activeActSettingList;
        }
        return null;
    }

    /**
     * 功能：查询活动列表(包括非进行中的)
     */
    public List<ActSetting> getActSettingList() {
        return (List<ActSetting>)activityMap.values();
    }

    /**
     * 功能：根据活动类型查询活动列表(进行中的)
     */
    public List<ActSetting> getActSettingList(String type, Long companyId) {
        if (CollectionUtils.isNotEmpty(activityMap.values()) && StringUtils.isNotBlank(type)) {
            List<ActSetting> activeActSettingList = new ArrayList<>();
            Date curDate = new Date();
            for (ActSetting as : activityMap.values()) {
                if(type.equals(as.getActivityType()) && (companyId==null || companyId.equals(as.getCompanyId()))){
                    Date actEndTime = as.getEndTime();
                    if (as.getFinishDays() != null && as.getFinishDays() > 0) {
                        Calendar actEndDate = Calendar.getInstance();
                        actEndDate.setTime(actEndTime);
                        actEndDate.add(Calendar.DATE, as.getFinishDays());
                        actEndTime = actEndDate.getTime();
                    }
                    if (curDate.compareTo(as.getStartTime()) > 0 && curDate.compareTo(actEndTime) < 0) {
                        activeActSettingList.add(as);
                    }
                }
            }
            return activeActSettingList;
        }
        return null;
    }

    /**
     * 功能：根据活动类型查询活动列表(进行中的)
     */
    public List<ActSetting> getActSettingList(Set<String> types, Long companyId) {
        if (CollectionUtils.isNotEmpty(activityMap.values()) && types!=null && !types.isEmpty() && companyId!=null) {
            List<ActSetting> activeActSettingList = new ArrayList<>();
            Date curDate = new Date();
            for (ActSetting as : activityMap.values()) {
                if(types.contains(as.getActivityType()) && companyId.equals(as.getCompanyId())){
                    Date actEndTime = as.getEndTime();
                    if (as.getFinishDays() != null && as.getFinishDays() > 0) {
                        Calendar actEndDate = Calendar.getInstance();
                        actEndDate.setTime(actEndTime);
                        actEndDate.add(Calendar.DATE, as.getFinishDays());
                        actEndTime = actEndDate.getTime();
                    }
                    if (curDate.compareTo(as.getStartTime()) > 0 && curDate.compareTo(actEndTime) < 0) {
                        activeActSettingList.add(as);
                    }
                }
            }
            return activeActSettingList;
        }
        return null;
    }
    
    
    /**
     * 功能：根据活动类型查询活动列表(进行中的),并在活动结束时间基本上增加指定秒数，已防止活动刚刚结束还未存在未发放完的
     */
    public List<ActSetting> getActSettingList(Set<String> types, Long companyId,int second) {
        if (CollectionUtils.isNotEmpty(activityMap.values()) && types!=null && !types.isEmpty() && companyId!=null) {
            List<ActSetting> activeActSettingList = new ArrayList<>();
            Date curDate = new Date();
            for (ActSetting as : activityMap.values()) {
                if(types.contains(as.getActivityType()) && companyId.equals(as.getCompanyId())){
                    Date actEndTime = as.getEndTime();
                    if (as.getFinishDays() != null && as.getFinishDays() > 0) {
                        Calendar actEndDate = Calendar.getInstance();
                        actEndDate.setTime(actEndTime);
                        actEndDate.add(Calendar.DATE, as.getFinishDays());
                        actEndTime = actEndDate.getTime();
                    }
                    if(second>0){//延长时间
                    	actEndTime =ImsDateUtil.addSecond(actEndTime, second);//过期时间                    
                    }
                    
                    if (curDate.compareTo(as.getStartTime()) > 0 && curDate.compareTo(actEndTime) < 0) {
                        activeActSettingList.add(as);
                    }
                }
            }
            return activeActSettingList;
        }
        return null;
    }


 
    
    /**
     * 功能：根据活动类型查询活动列表(进行中的)
     */
    public List<ActSetting> getActSettingList(Set<String> types) {
        return getActSettingList(types,null,0);
    }
    
    /**
     * 功能：根据活动编号->获取进行中的活动
     */
    public ActSetting getActiveByActivityPeriods(String activityPeriods) {
    	ActSetting as = activityMap.get(activityPeriods);
        if(as!=null){
        	Date curDate = new Date();
            Date actEndTime = as.getEndTime();
            if (as.getFinishDays() != null && as.getFinishDays() > 0) {
                Calendar actEndDate = Calendar.getInstance();
                actEndDate.setTime(actEndTime);
                actEndDate.add(Calendar.DATE, as.getFinishDays());
                actEndTime = actEndDate.getTime();
            }
            if (curDate.compareTo(as.getStartTime()) > 0 && curDate.compareTo(actEndTime) < 0) {
                 return as;
            }
        }
        return as;               
    }

    /**
     * 功能：根据活动编号->获取活动(包括非进行中的)
     */
    public ActSetting getByActivityPeriods(String activityPeriods) {
    	return activityMap.get(activityPeriods);
    }
    
   
    
    /**
     * 根据活动获得参与条件信息(封装了额外业务属性)
     * @return
     */
    public ActConditionSettingWrapper getActCondCustomInfoWrapByActivityPeriods(String activityPeriods){
    	ActConditionSettingWrapper condSettingWrapper = actCodnitionCustomInfoWrapMap.get(activityPeriods);
        if (condSettingWrapper==null) {
        	ActConditionSetting  actConditionSetting = getActCondCustomInfoByActivityPeriods(activityPeriods);
        	if(actConditionSetting!=null){
        		condSettingWrapper = new ActConditionSettingWrapper();
        		ImsBeanUtil.copyNotNull(condSettingWrapper, actConditionSetting);
        		 //封装平台集合
                if(StringUtil.isNotEmpty(condSettingWrapper.getPlatforms())){
                	String[] platformsArr = condSettingWrapper.getPlatforms().split(",");
                	if(platformsArr.length>0){
                		Set<String> platformsSet = new HashSet<String>();
                		for(String p :platformsArr){
                			if(StringUtil.isNotEmpty(p)){
                				platformsSet.add(p);
                			}
                		}
                		condSettingWrapper.setPlatformsSet(platformsSet);
                	}
                }
               // 当活动没有设置参与资格的交易平台时，所有平台不允许参加
       		   // 产品封装
               if (condSettingWrapper.getPlatformsSet()!=null && StringUtil.isNotEmpty(condSettingWrapper.getItems())) {
                   String[] items = condSettingWrapper.getItems().split(",");
                   if (items != null && items.length > 0) {
                       List<String> demoProducts = new ArrayList<String>();
                       List<String> realProducts = new ArrayList<String>();
                       for (String product : items) {
                           if (StringUtils.isNotBlank(product)) {
                               String[] ps = product.split("#");// code#0或code#1
                               if (ps.length > 1 && StringUtils.isNotBlank(ps[0])) {
                                   if ("1".equals(ps[1])) {
                                       demoProducts.add(ps[0]);
                                   } else if ("0".equals(ps[1])) {
                                       realProducts.add(ps[0]);
                                   }
                               }
                           }
                       }
                       condSettingWrapper.setDemoProducts(demoProducts);
                       condSettingWrapper.setRealProducts(realProducts);
                   }
               }
        	}
        	actCodnitionCustomInfoWrapMap.put(activityPeriods, condSettingWrapper);
        }
        return condSettingWrapper;
    }
    
    /**
     * 根据活动获得参与条件信息(原生的参与条件属性)
     * @return
     */
    public ActConditionSetting getActCondCustomInfoByActivityPeriods(String activityPeriods){
    	ActConditionSetting actCodnitionCustomInfo = actCodnitionCustomInfoMap.get(activityPeriods);
        if (actCodnitionCustomInfo==null) {
            return loadActCodnitionCustomInfo(activityPeriods);
        }
        return actCodnitionCustomInfo;
    }
    
    
    private ActConditionSetting loadActCodnitionCustomInfo(String activityPeriods){
    	ActConditionSetting conditionSetting = new ActConditionSetting();
        if(StringUtils.isNotBlank(activityPeriods)){
            // 获取活动用户参与条件
            Map<String, Object> actConParams = new HashMap<String, Object>();
            actConParams.put("activityPeriods", activityPeriods);
            actConParams.put("conditionType", ActRuleTypeEnum.CUSTINFO.getCode());// 客户信息
            List<ActConditionSetting> listActCondSetting = actConditionSettingDao.findListByMap(actConParams);
            if (listActCondSetting != null && !listActCondSetting.isEmpty()) {
            	conditionSetting = listActCondSetting.get(0);
                if (StringUtil.isNotEmpty(conditionSetting.getConditionVal())) {
	                	//把json中的属性值赋值给condSetting对象
	                	ActConditionValJson jsonVO = JsonUtil.json2Obj(conditionSetting.getConditionVal(), ActConditionValJson.class);
	        			ImsBeanUtil.copyNotNull(conditionSetting, jsonVO);
                    }
                }
            actCodnitionCustomInfoMap.put(activityPeriods, conditionSetting);
            return conditionSetting;
        }
        return null;
    }
   
}
