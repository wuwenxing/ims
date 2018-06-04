package com.gwghk.ims.mis.gateway.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.mis.gateway.common.Client;
import com.gwghk.ims.mis.gateway.enums.SessionKeyEnum;
import com.gwghk.ims.mis.gateway.util.AuthColumnUtil;
import com.gwghk.ims.mis.gateway.util.ContextHolderUtil;

@ControllerAdvice
public class AuthColumnInterceptor implements ResponseBodyAdvice<Object>{
	
	Logger logger = LoggerFactory.getLogger(AuthColumnInterceptor.class) ;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter,
            MediaType mediaType, Class clas, ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse) {
        //将返回值returnValue转成我需要的类型MisRespResult<?>  方便统一修改其中的某个属性  
        // MisRespResult是统一返回的一个类
    	Client client = (Client) ContextHolderUtil.getSession().getAttribute(SessionKeyEnum.client.getCode());
    	if(null == client || client.getUser() == null){
    		return returnValue ;
    	}
    	Long roleId = client.getUser().getRoleId() ;
    	MisRespResult<?> msg = null ;
    	if (returnValue instanceof MappingJacksonValue) {
			MappingJacksonValue m = (MappingJacksonValue) returnValue;
			if (m.getValue() instanceof MisRespResult<?>) {
				msg = (MisRespResult<?>) m.getValue();
			}
		}else if(returnValue instanceof  MisRespResult<?>){
			msg = (MisRespResult<?>) returnValue;
		}
    	if(null != msg){
    		Object obj = msg.getData() ;
			try {
				if (obj instanceof List) {
					List<Object> list = (List) obj;
					encryptColumn(list, roleId);
				}else{
					encryptColumn(obj, roleId);
				}
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
    	}
        return returnValue;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public boolean supports(MethodParameter methodParameter, Class clas) {
        //获取当前处理请求的controller的方法
        String methodName=methodParameter.getMethod().getName(); 
        // 不拦截/不需要处理返回值 的方法
        String method= "login"; //如登录
        //不拦截
        return !method.equals(methodName);
    }
    
    private void encryptColumn(List<Object> list,Long roleId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
    	AuthColumnUtil.encryptColumn(list, roleId);
    }
    
    private void encryptColumn(Object obj,Long roleId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
    	AuthColumnUtil.encryptColumn(obj, roleId);
    }
    
}