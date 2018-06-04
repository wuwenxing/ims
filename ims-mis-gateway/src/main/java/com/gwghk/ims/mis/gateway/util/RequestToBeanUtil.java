package com.gwghk.ims.mis.gateway.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 摘要：request与bean之间的转换工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月16日
 */
public class RequestToBeanUtil {
	private static Logger logger = LoggerFactory.getLogger(RequestToBeanUtil.class);
	
    /** 
     * 功能：将request对象转换为bean
     * @param request 请求对象 
     * @param obj     要设置Bean的类型,传入试为: Bean.class 
     */
    public static <T> T  request2bean(HttpServletRequest request,Class<T> clazz){    
        Enumeration<String> enume = request.getParameterNames();  //获取页面所有的请求参数名称
        T beanObj = getObjectByClass(clazz);
        try{     
            while(enume.hasMoreElements()){    
                String propertyName = enume.nextElement();   //参数名称              
                if(isCheckBeanExitsPropertyName(clazz,propertyName)){    //判断是否存在此属性
                    Object propertyValue = request.getParameter(propertyName);   //获取请求值 
                    setProperties(beanObj,propertyName,propertyValue);  
                }
            }
        }catch(Exception e){
        	logger.error(e.getMessage(),e);
        }    
        return beanObj;
    }
    
    /** 
     * 功能：对象之间值转换
     * @param <T> 
     * @param newSource  现将要设置新值的对象 
     * @param source     源数据对象 
     */  
    public static <T> void  beanConvert(T newSource,T source){  
        try {  
            BeanUtils.copyProperties(newSource,source);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
    }
      
    /** 
     * 功能：判断bean是否存在属性
     * @param clazz           Class对象 
     * @param propertyName    属性名称 
     * @return true || false  检查对象中是否存在该属性名称 
     */  
    private  static boolean isCheckBeanExitsPropertyName(Class<?> clazz,String propertyName){   
        boolean retValue = false;
        try {
            Field field =  clazz.getDeclaredField(propertyName);     
            if(null != field){
                retValue = true;
            }
        } catch (NoSuchFieldException e) {           
        	logger.error("类: " + clazz.getSimpleName()+",不存在属性名: "+propertyName+" ,详细错误信息: "+e.getMessage());        
        }
        return retValue;
    }

    private static <T> T getObjectByClass(Class<T> clazz){
        T t = null;
        try {
            t = clazz.newInstance();
        } catch(Exception e) {
        	logger.error(e.getMessage());
        }
        return t;
    }
    
    /**   
     * 设置字段值       
     * @param obj           实例对象
     * @param propertyName  属性名   
     * @param value         新的字段值   
     * @return            
     */     
    public static void setProperties(Object object, String propertyName,Object value) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName,object.getClass());
        Method methodSet = pd.getWriteMethod();
        methodSet.invoke(object,value);
    }
}
