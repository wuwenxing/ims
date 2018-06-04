package com.gwghk.ims.datasource.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;

public class DataSourceFilter implements Filter {

	private static final Logger logger=LoggerFactory.getLogger(DataSourceFilter.class);
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		
		//切换数据源
		try {
			switchDataSource(invoker, invocation);
		}catch(Exception e) {
			//切换数据源异常直接抛出异常
			logger.error(e.getMessage(),e);
			throw new RpcException(e.getMessage());
		}
		
		return invoker.invoke(invocation);
	}
	
	/**
	 * 切换数据源，优先通过company注解切换，注解不存在则通过VO里面的getCompanyId来切换， 如果都没有提供则使用默认数据源
	 * @param invoker
	 * @param invocation
	 * @throws Exception
	 */
	private void switchDataSource(Invoker<?> invoker, Invocation invocation) throws Exception{
		
		Object companyId=null;
		
		//1.通过company注解来获取companyId
		//获取目标调用方法
		Method method=invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
		Annotation[][] annotationArr= method.getParameterAnnotations();

		//从注解中找到所对应的位置
		int pos=-1;
		for(int i=0;i<annotationArr.length;i++) {
			for(Annotation annotation:annotationArr[i]) {
				if("com.gwghk.ims.common.annotation.Company".equals(annotation.annotationType().getName())) {
					pos=i;
					break;
				}
			}
		}
		
		//存在company注解的情况取出companyId
		if(pos>-1){
			companyId=invocation.getArguments()[pos];
		}
			
		//2.通过VO包含getCompanyId来获取companyId
		if(companyId==null) {
			Object[] args=invocation.getArguments();
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
		}
		
		//这个由用户手动去切换数据源
		if(companyId==null)
			return;
			
		//根据companyId切换数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
	}

}
