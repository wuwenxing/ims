package com.gwghk.ims.bos.web.interceptors;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gwghk.ims.bos.web.common.cache.MenuCache;
import com.gwghk.ims.bos.web.common.context.Client;
import com.gwghk.ims.bos.web.common.enums.ErrorCodeEnum;
import com.gwghk.ims.bos.web.common.enums.SessionKeyEnum;
import com.gwghk.ims.bos.web.common.utils.SystemPathUtil;
import com.gwghk.ims.bos.web.controller.system.BaseController;
import com.gwghk.ims.common.dto.system.SystemMenuDTO;
import com.gwghk.ims.common.dto.system.SystemUserDTO;

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
		String requestPath = SystemPathUtil.getRequestPath(request);
		if(StringUtils.isBlank(requestPath)){
			response.sendRedirect(request.getContextPath() + "/LoginController/login");
			return false;
		}
		// 过滤urls
		if (null != BaseController.urls.get(requestPath)) {
			return true;
		} else if (null != BaseController.interfaceUrls.get(requestPath)) {
			return true;
		} else {
			Client client = (Client)request.getSession().getAttribute(SessionKeyEnum.client.getLabelKey());
			if (null != client && null != client.getUser()) {
				if(!actionAuth(request, response, client.getUser(), requestPath)){
					alertNoAuth(request, response);
					return false;
				}
				return true;
			}else{
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
	private boolean actionAuth(HttpServletRequest request, HttpServletResponse response, SystemUserDTO user, String requestPath)
			throws Exception {
		logger.info("userNo:" + user.getUserName() + "|comanyId:" + user.getCompanyId() + "|requestPath:" + requestPath);
		// 验证菜单权限,根据requestPath判断用户是否有权限
		boolean hasRecord = true;
		if(StringUtils.isNotBlank(requestPath)){
			String superAdminFlag = request.getSession().getAttribute(SessionKeyEnum.superAdminFlag.getLabelKey()) + "";
			if(StringUtils.isNotBlank(superAdminFlag) && "Y".equals(superAdminFlag)){
				hasRecord = true; // 有权限
			}else{
				// 如在需要权限控制的url中，则判断是否有权限
				String companyId = request.getSession().getAttribute(SessionKeyEnum.companyId.getLabelKey()) + "";
				Map<String, String> menuUrlMap = MenuCache.getMenuUrlList(companyId);
				if(null != menuUrlMap.get(requestPath)){
					@SuppressWarnings("unchecked")
					Map<String, SystemMenuDTO> map = (Map<String, SystemMenuDTO>)request.getSession().getAttribute(SessionKeyEnum.menuMap.getLabelKey());
					if(null != map){
						SystemMenuDTO menuEntity = map.get(requestPath);
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
	 * 功能：session超时-跳转到登录页
	 * 
	 * @param request
	 * @param response
	 */
	private void jumpLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		if (!"XMLHttpRequest".equalsIgnoreCase(requestType)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			response.sendRedirect(request.getContextPath() + "/page/common/sessionTimeOut.jsp");
		}else{
			// ajax请求
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.setHeader(ErrorCodeEnum.sessionTimeOut.getLabelKey(), ErrorCodeEnum.sessionTimeOut.getLabelKey());
			PrintWriter printWriter = response.getWriter();
			printWriter.print(ErrorCodeEnum.sessionTimeOut.getLabelKey());
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
			response.setContentType("text/html; charset=utf-8");
			response.sendRedirect(request.getContextPath() + "/page/common/noAuth.jsp");
		} else {
			// ajax请求
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.setHeader(ErrorCodeEnum.noPermission.getLabelKey(), ErrorCodeEnum.noPermission.getLabelKey());
			PrintWriter printWriter = response.getWriter();
			printWriter.print(ErrorCodeEnum.noPermission.getLabelKey());
			printWriter.flush();
			printWriter.close();
		}
	}

}
