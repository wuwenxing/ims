package com.gwghk.ims.activity.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.manager.api.AbstractActivityType;
import com.gwghk.ims.activity.manager.api.AbstractBaseTask;
import com.gwghk.ims.activity.manager.issue.AbstractIssueItems;
import com.gwghk.ims.activity.manager.settle.AbstractSettlementType;
import com.gwghk.ims.common.annotation.IssueItemCategory;
import com.gwghk.ims.common.annotation.ActSettleType;
import com.gwghk.ims.common.annotation.ActTaskType;
import com.gwghk.ims.common.annotation.ActType;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.IssueItemCategoryEnum;

@Component
public class BeanFactoryUtil implements ApplicationContextAware{
	Logger logger = LoggerFactory.getLogger(BeanFactoryUtil.class) ;
	public static Map<String, AbstractActivityType> actBeanMap = new HashMap<>() ;
	public static Map<String, AbstractBaseTask> actTaskBeanMap = new HashMap<>() ;
	static Map<String, AbstractIssueItems> actIsuserBeanMap = new HashMap<>() ;
	static Map<Integer, AbstractSettlementType> actSettleBeanMap = new HashMap<>() ;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Object> actMap = applicationContext.getBeansWithAnnotation(ActType.class);
		for (Object serviceBean : actMap.values()) {
            try {
                //获取自定义注解上的value
                ActTypeEnum value = serviceBean.getClass().getAnnotation(ActType.class).value();
                actBeanMap.put(value.getCode(), (AbstractActivityType)serviceBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		Map<String, Object> actTaskMap = applicationContext.getBeansWithAnnotation(ActTaskType.class);
		for (Object serviceBean : actTaskMap.values()) {
            try {
                //获取自定义注解上的value
                ActTaskTypeEnum[] value = serviceBean.getClass().getAnnotation(ActTaskType.class).value();
                for(ActTaskTypeEnum act : value){
                	actTaskBeanMap.put(act.getCode(), (AbstractBaseTask)serviceBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		Map<String, Object> actSettleTypeMap = applicationContext.getBeansWithAnnotation(ActSettleType.class);
		for (Object serviceBean : actSettleTypeMap.values()) {
            try {
                //获取自定义注解上的value
                ActSettleTypeEnum[] value = serviceBean.getClass().getAnnotation(ActSettleType.class).value();
                for(ActSettleTypeEnum act : value){
                	actSettleBeanMap.put(Integer.valueOf(act.getCode()), (AbstractSettlementType)serviceBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		Map<String, Object> actItemsTypeMap = applicationContext.getBeansWithAnnotation(IssueItemCategory.class);
		for (Object serviceBean : actItemsTypeMap.values()) {
            try {
                //获取自定义注解上的value
            	IssueItemCategoryEnum[] value = serviceBean.getClass().getAnnotation(IssueItemCategory.class).value();
                for(IssueItemCategoryEnum act : value){
                	actIsuserBeanMap.put(act.getValue(), (AbstractIssueItems)serviceBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		logger.info("初始化actBeanMap:"+actBeanMap);
		logger.info("初始化actTaskBeanMap:"+actTaskBeanMap);
		logger.info("初始化actSettleBeanMap:"+actSettleBeanMap);
	}
	
	/**
	 * 获取活动类型的bean
	 * @param actType
	 * @return
	 */
	public static AbstractActivityType getActTypeBean(String actType){
		return actBeanMap.get(actType) ;
	}
	
	/**
	 * 获取任务类型的bean
	 * @param taskType
	 * @return
	 */
	public static AbstractBaseTask getActTaskTypeBean(String taskCode){
		return actTaskBeanMap.get(taskCode) ;
	}
	
	/**
	 * 获取清算类型的bean
	 * @param taskType
	 * @return
	 */
	public static AbstractSettlementType getActSettleTypeBean(Integer code){
		return actSettleBeanMap.get(code) ;
	}
	
	/**
	 * 获取物品类型的bean
	 * @param code
	 * @return
	 */
	public static AbstractIssueItems getIssueItemsBean(String code){
		return actIsuserBeanMap.get(code) ;
	}

}
