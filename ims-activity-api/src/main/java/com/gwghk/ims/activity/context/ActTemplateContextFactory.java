package com.gwghk.ims.activity.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 活动模板加载类
 * @author eva
 *
 */
public class ActTemplateContextFactory {
    private static ApplicationContext cxt;
    private final static Map<String, ActivityConfigBean> containerMap = new HashMap<String, ActivityConfigBean>(10);
    static {
        cxt = ContextLoader.getCurrentWebApplicationContext();
        if (cxt == null) {
            String[] configLocations = { "template/activity-config-list.xml" };
            cxt = new ClassPathXmlApplicationContext(configLocations);
        }
        // context.getParentBeanFactory().
        if (cxt != null) {
        	ActTemplateContextContainer con = (ActTemplateContextContainer) cxt.getBean("actTemplateContextContainer");
                if (con != null) {
                	Map<String, ActivityConfigBean> actTemplateMap = con.getActTemplateList();
                	System.out.println(JsonUtil.obj2Str(actTemplateMap));
                	containerMap.putAll(actTemplateMap);
                }
        }
    }
 
    
    /**
     * 根据活动类型获得基本信息参数配置
     * @param activityType
     * @return
     */
    public  static List<ParamBean> getActBaseParams(String activityType){
    	return containerMap.get(activityType)!=null?containerMap.get(activityType).getActBaseParams():null;
    }
    
    /**
     * 根据活动类型及参与条件类型参数配置map
     * @param activityType
     * @param conditionType
     * @return
     */
    public  static  Map<String,List<ParamBean>> getActCondtionParamsMap(String activityType){
    	ActivityConfigBean activityConfig = containerMap.get(activityType);
    	 if(activityConfig!=null){
    		  return  activityConfig.getActCondtionParamsMap();
    	 }else{
    		 return null;
    	 } 
    }
    
    /**
     * 根据活动类型及参与条件类型参数配置
     * @param activityType
     * @param conditionType
     * @return
     */
    public  static  List<ParamBean> getActCondtionParams(String activityType,String conditionType){
    	ActivityConfigBean activityConfig = containerMap.get(activityType);
    	 if(activityConfig!=null && activityConfig.getActCondtionParamsMap()!=null){
    		  return  activityConfig.getActCondtionParamsMap().get(conditionType);
    	 }else{
    		 return null;
    	 } 
    }
 
    
    /**
     * 根据活动类型获得任务配置
     * @param activityType
     * @return
     */
    public  static  Map<String,ActTaskBean> getActTaskConfigs(String activityType){
    	ActivityConfigBean activityConfig = containerMap.get(activityType);
    	 if(activityConfig!=null){
    		  return  activityConfig.getActTaskConfigsMap();
    	 }else{
    		 return null;
    	 } 
    }
 
     
    public static void main(String[] args) {
    	  new ActTemplateContextFactory();
    }
}
