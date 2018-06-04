package com.gwghk.ims.datasource.data;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.DataSourceTypeEnum;


@Aspect
@Component
public class DynamicDataSourceInject {
	//@Pointcut("execution(public * com.gwghk.ims.activity.service..*.*(..))")
	//public void pointcut() {}//使用全局拦截器
	
	//@Around(value="pointcut()")
	@Around(value="@annotation(com.gwghk.ims.common.annotation.Company)")
	public Object switchDataSource(ProceedingJoinPoint  jp) throws Throwable { 
		
		Object companyId=null;
		
		//从DTO里面找到getCompanyId()
        Object[] args=jp.getArgs();
        for(Object arg:args) {
        	Method m=null;
        	try {
        		m=arg.getClass().getMethod("getCompanyId");
        	}catch(Exception e) {
        		continue;
        	}
        	
        	if(m==null)
        		continue;
        	
        	//找到第一个参数包含getCompanyId方法的对象，取出companyId
        	companyId=m.invoke(arg);
        	break;
        }
        
        //切换数据源
        DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA,companyId);
        
        //放行
        return jp.proceed(args);
	}
	
}
