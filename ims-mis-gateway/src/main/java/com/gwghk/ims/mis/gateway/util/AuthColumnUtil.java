package com.gwghk.ims.mis.gateway.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.vo.system.SystemRoleColumnAuthVO;
import com.gwghk.ims.mis.gateway.common.SystemCache;
import com.gwghk.unify.framework.common.util.DESEncryptUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * 摘要：字段的权限控制
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月19日
 */

public class AuthColumnUtil {
	static Logger logger = LoggerFactory.getLogger(AuthColumnUtil.class) ;
	
	static String encryptTxt = "******" ;
	
	/**
	 * 功能：对没有权限的字段转换为******处理
	 * @param obj 转换对象List
	 * @param roleId 角色ID
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void encryptColumn(List<?> obj,Long roleId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for(Object o : obj){
			encryptColumn(o, roleId);
		}
	}
	
	/**
	 * 功能：对没有权限的字段转换为******处理
	 * @param obj 转换对象
	 * @param roleId 角色ID
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void encryptColumn(Object obj,Long roleId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(null == obj) return ;
		Class<?> customerInfoClass = obj.getClass();
        Field[] fields = customerInfoClass.getDeclaredFields();
        List<String> list = new ArrayList<>() ;
       
        for (Field f : fields) {
            AuthColumn authColumn = f.getAnnotation(AuthColumn.class) ;
            if (authColumn != null) {
           		List<SystemRoleColumnAuthVO> roles = SystemCache.getInstance().getSystemRoleColumnAuthListByRoleId(roleId) ;
           		//获取该角色拥有的全部列权限
           		for(SystemRoleColumnAuthVO s : roles){
           			list.addAll(Arrays.asList(s.getViewColumns().split(","))) ;
           		}
                String authCode = authColumn.columnName().getCode();
                String refColumn = authColumn.refColumn() ;
                String fName = f.getName();
                Object columnObjVal = PropertyUtils.getProperty(obj, fName);
                if(null != columnObjVal) {
                	String content = PropertyUtils.getProperty(obj, fName).toString() ;
                	//判断是否拥有权限
               	 	if (null == list || !list.contains(authCode)) {
	               	 	if(StringUtil.isNotEmpty(refColumn)){
	               	 		//如果有关联加密内容，需要关联的字段内容进行加密
	    	 				String refContent = PropertyUtils.getProperty(obj, refColumn).toString() ;
	    	 				refContent = refContent.replace(content, encryptTxt) ;
	    	 				PropertyUtils.setProperty(obj, refColumn,refContent) ;
	    	 			}
	               	 	PropertyUtils.setProperty(obj, fName, encryptTxt);
                    }else{
                    	//如果需要解密字段内容
                   	 	if(authColumn.isDecrypt()){
                   	 		try {
                   	 			String decryContent = new DESEncryptUtil().decryptString(content) ;
	                   	 		if(StringUtil.isNotEmpty(refColumn)){
			    	 				String refContent = PropertyUtils.getProperty(obj, refColumn).toString() ;
			    	 				refContent = refContent.replace(content, decryContent) ;
			    	 				PropertyUtils.setProperty(obj, refColumn,refContent) ;
			    	 			}
	                   	 		PropertyUtils.setProperty(obj, fName, decryContent);
                   	 		} catch (Exception e) {
                   	 			logger.info("{},解密串码异常e:{}",new Object[]{content,e});
                   	 		}
	                   	 	
                        }
                    }
                }
            }
        }
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new DESEncryptUtil().decryptString("CRnxMGwyB6U="));
		System.out.println(new DESEncryptUtil().encryptString(""));
	}
}
