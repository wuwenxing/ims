package com.gwghk.ims.mis.gateway.interceptor;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.Constants;
import com.gwghk.ims.common.enums.ErrorCodeEnum;
import com.gwghk.ims.common.vo.system.SystemMenuVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.ims.mis.gateway.common.Client;
import com.gwghk.ims.mis.gateway.common.SystemCache;
import com.gwghk.ims.mis.gateway.common.UserContext;
import com.gwghk.ims.mis.gateway.controller.BaseController;
import com.gwghk.ims.mis.gateway.enums.SessionKeyEnum;
import com.gwghk.ims.mis.gateway.util.BrowserUtil;
import com.gwghk.ims.mis.gateway.util.GsonUtil;
import com.gwghk.ims.mis.gateway.util.IPUtil;
import com.gwghk.ims.mis.gateway.util.SignUtil;
import com.gwghk.ims.mis.gateway.util.SystemConfigUtil;
import com.gwghk.ims.mis.gateway.util.SystemPathUtil;

/**
 * 权限拦截器
 * @author wayne
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String isAllowAllUrl = SystemConfigUtil.getProperty("isAllowAllUrl");
		if("true".equals(isAllowAllUrl)){
			return true;
		}
    	//处理ie跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", "3.2.1");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		
		String requestPath = SystemPathUtil.getRequestPath(request);
		if(StringUtils.isBlank(requestPath)){
			jumpLoginPage(request, response);
			return false;
		}
		
		// 过滤urls
		if (null != BaseController.urls.get(requestPath)) {
			return true;
		}else {
			Client client = (Client)request.getSession().getAttribute(SessionKeyEnum.client.getCode());
			if (null != client && null != client.getUser()) {
				if(!actionAuth(request, response, client.getUser(), requestPath)){
					alertNoAuth(request, response);
					return false;
				}
				return true;
			}else{
				// 验证token
				if(actionToken(request, response, requestPath)){
					return true;
				}
				jumpLoginPage(request, response);
				return false;
			}
		}
	}

	/**
	 * 动作拦截
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean actionAuth(HttpServletRequest request, HttpServletResponse response, SystemUserVO user, String requestPath)
			throws Exception {
		logger.info("userNo:" + user.getUserNo() + "|comanyId:" + user.getCompanyId() + "|requestPath:" + requestPath);
		// 验证菜单权限,根据requestPath判断用户是否有权限
		boolean hasRecord = true;
		if(StringUtils.isNotBlank(requestPath)){
			if(Constants.superAdmin.equals(user.getUserNo())){
				hasRecord = true; // 有权限,超级管理员
			}else{
				// 如在需要权限控制的url中，则判断是否有权限
				Map<String, String> menuUrlMap = SystemCache.getInstance().getMenuUrlList();
				if(null != menuUrlMap.get(requestPath)){
					Map<String, SystemMenuVO> map = SystemCache.getInstance().findMenuMapByRoleId(user.getRoleId());
					if(null != map){
						SystemMenuVO menuEntity = map.get(requestPath);
						if(null == menuEntity){
							hasRecord = false; // 无权限
						}
					}else{
						hasRecord = false; // 无权限
					}
				}
			}
		}
		return hasRecord;
	}
	
	/**
	 * 未登录时，第二种方式通过token验证，进行访问
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean actionToken(HttpServletRequest request, HttpServletResponse response, String requestPath)
			throws Exception {
		String test = request.getParameter("test");
		String active = SystemConfigUtil.getProperty("system.active");
		logger.info("test={},active={}", new Object[]{test,active});
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		String companyId = request.getParameter("companyId");
		String userNo = request.getParameter("userNo");
		logger.info("timestamp={},sign={},companyId={},userNo={}", new Object[]{timestamp, sign, companyId, userNo});
		
		// 当active=uat时，test参数不为空都为测试，userNo默认superAdmin，companyId为登录用户的companyId
		if(StringUtils.isNotBlank(test) && "uat".equals(active)){
	        UserContext userContext = new UserContext();
	        userContext.setLoginName(userNo);
	        if(StringUtils.isBlank(userNo)){
	        	userNo = Constants.superAdmin;
	        }
	        userContext.setLoginNo(userNo);
	        userContext.setLoginIp(IPUtil.getIp());
	        userContext.setSessionId(request.getSession().getId());
	        userContext.setClientType(BrowserUtil.checkBrowse(request));
	        userContext.setLoginDate(new Date());
	        UserContext.set(userContext);
			return true;
		}
		
		// 校验逻辑
		if(StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(sign)
				|| StringUtils.isBlank(companyId)
				|| StringUtils.isBlank(userNo)){
			return false;
		}
		// 签权验证
		if(!SignUtil.validate(timestamp, sign, companyId)){
			return false;
		}
        UserContext userContext = new UserContext();
        userContext.setLoginName(userNo);
        userContext.setLoginNo(userNo);
        userContext.setLoginIp(IPUtil.getIp());
        userContext.setCompanyId(Long.parseLong(companyId));
        userContext.setCompanyCode(CompanyEnum.getCodeById(companyId));
        userContext.setSessionId(request.getSession().getId());
        userContext.setClientType(BrowserUtil.checkBrowse(request));
        userContext.setLoginDate(new Date());
        UserContext.set(userContext);
		return true;
	}

	/**
	 * 功能：session超时-跳转到登录页
	 * 
	 * @param request
	 * @param response
	 */
	private void jumpLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		if (!"XMLHttpRequest".equalsIgnoreCase(requestType)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.sendRedirect(request.getContextPath() + "/check/error?code=" + MisResultCode.Err10009.getCode());
		}else{
			// ajax请求
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.setHeader(ErrorCodeEnum.sessionTimeOut.getLabelKey(), ErrorCodeEnum.sessionTimeOut.getLabelKey());
			PrintWriter printWriter = response.getWriter();
			printWriter.print(GsonUtil.GsonString(MisRespResult.error(MisResultCode.Err10009)));
			printWriter.flush();
			printWriter.close();
		}
	}

	/**
	 * 功能：提示无权限
	 * 
	 * @param request
	 * @param response
	 */
	private void alertNoAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		if (!"XMLHttpRequest".equalsIgnoreCase(requestType)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.sendRedirect(request.getContextPath() + "/check/error?code=" + MisResultCode.Err10010.getCode());
		} else {
			// ajax请求
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.setHeader(ErrorCodeEnum.noPermission.getLabelKey(), ErrorCodeEnum.noPermission.getLabelKey());
			PrintWriter printWriter = response.getWriter();
			printWriter.print(GsonUtil.GsonString(MisRespResult.error(MisResultCode.Err10010)));
			printWriter.flush();
			printWriter.close();
		}
	}

}
