package com.gwghk.ims.bos.web.controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gwghk.ims.bos.web.common.cache.DictCache;
import com.gwghk.ims.bos.web.common.cache.MenuCache;
import com.gwghk.ims.bos.web.common.context.Client;
import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.easyui.PageGrid;
import com.gwghk.ims.bos.web.common.enums.DictCodeEnum;
import com.gwghk.ims.bos.web.common.enums.SessionKeyEnum;
import com.gwghk.ims.bos.web.common.enums.SystemLogEnum;
import com.gwghk.ims.bos.web.common.utils.JacksonUtil;
import com.gwghk.ims.bos.web.common.utils.JsonUtil;
import com.gwghk.ims.bos.web.common.utils.SignUtil;
import com.gwghk.ims.bos.web.common.utils.SystemPathUtil;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.BaseDTO;
import com.gwghk.ims.common.dto.system.SystemLogDTO;
import com.gwghk.ims.common.dto.system.SystemMenuDTO;
import com.gwghk.ims.common.dto.system.SystemUserDTO;
import com.gwghk.ims.common.inf.sys.SystemDictDubboService;
import com.gwghk.ims.common.inf.sys.SystemLogDubboService;
import com.gwghk.ims.common.inf.sys.SystemMenuDubboService;

/**
 * controller层公共方法
 * 
 * @author wayne
 */
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private SystemLogDubboService systemLogDubboService;
	@Autowired
	private SystemMenuDubboService systemMenuDubboService;
	@Autowired
	private SystemDictDubboService systemDictDubboService;

	// 排除不需权限验证的URL
	public static Map<String, String> urls = new HashMap<String, String>();
	// 系统接口的URL集合
	public static Map<String, String> interfaceUrls = new HashMap<String, String>();
	// 不记录日志的Url集合
	public static Map<String, String> notInUrls = new HashMap<String, String>();
	
	static{
		// 排除不需执行的URL
		urls.put("login", "");
		urls.put("LoginController", "");
		urls.put("LoginController/login", "");
		urls.put("LoginController/loginOut", "");
		urls.put("LoginController/captcha", "");
	}
	
	/**
	 * ModelAttribute的作用：表示请求该类的每个Controller前都会首先执行它
	 * 
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void initHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//处理ie跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", "3.2.1");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		// 设置缓存service
		DictCache.setSystemDictDubboService(systemDictDubboService);
		MenuCache.setSystemMenuDubboService(systemMenuDubboService);
		// 获得请求路径
		String requestPath = SystemPathUtil.getRequestPath(request);
		if(StringUtils.isBlank(requestPath)){
			response.sendRedirect(request.getContextPath() + "/LoginController/login");
			return;
		}
		
		// 过滤urls
		if (null != urls.get(requestPath)) {
		} else if (null != interfaceUrls.get(requestPath)) {
			this.interfaceHandle(request, response);
		} else {
			// 从session中获取登录的用户对象
			Client client = this.getLoginClient(request);
			if (null != client) {
				// 登录设置当前用户
				SystemUserDTO userEntity = client.getUser();
				String loginIp = client.getIp();
				String loginNo = userEntity.getUserNo();
				String loginName = userEntity.getUserName();
				Date loginDate = client.getLoginDate();
				String sessionId = client.getSessionId();
				String clientType = client.getClientType();
				Long companyId = this.getCompanyId(request);
				UserContext.setSystemUser(loginIp, loginNo, loginName, loginDate, sessionId, clientType, companyId);
				
				// 登录并验证权限以后-记录日志
				logHandle(request, response, requestPath, userEntity);
			} else {
				// 未登录默认跳转到登录页面
				response.sendRedirect(request.getContextPath() + "/LoginController/login");
				return;
			}
		}
		return;
	}

	/**
	 * 登录以后-记录日志
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void logHandle(HttpServletRequest request, HttpServletResponse response, String requestPath, SystemUserDTO userDTO)
			throws Exception {
		if(null == notInUrls.get(requestPath)){
			Map<String, String> paramMap = new HashMap<String, String>();
			@SuppressWarnings("rawtypes")
			Enumeration enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				String paramName = (String) enu.nextElement();
				String paramValue = request.getParameter(paramName);
				// 如果是修改密码，则改成***号代替；
				if("oldPassword".equals(paramName)
						||"newPassword".equals(paramName)
						||"newPasswordAgin".equals(paramName)){
					// logger.info("paramName=" + paramValue);
					paramValue = "***";
				}
				// 参数超过50长度，日志记录，数据库省略
				else if (paramValue.length() > 50) {
					logger.info("paramName=" + paramValue);
					paramValue = paramValue.substring(0, 50) + "...";
				}
				paramMap.put(paramName, paramValue);
			}
			String logType = SystemLogEnum.getLogType(requestPath);
			String params = JacksonUtil.toJSon(paramMap);
			SystemLogDTO logDto = new SystemLogDTO();
			logDto.setLogType(logType);
			logDto.setMethod(requestPath);
			logDto.setParams(params);
			logDto.setUserName(userDTO.getUserName());
			logDto.setUserNo(userDTO.getUserNo());
			this.setPublicAttr(logDto, logDto.getId());
			systemLogDubboService.addLog(logDto);
		}
	}
	
	/**
	 * 调用系统接口-统一处理
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void interfaceHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accountSid = request.getParameter("accountSid");
		if (StringUtils.isNotBlank(accountSid)) {
			String loginIp = this.getRemoteAddr(request);
			Long companyId = null;
			// 设置当前用户-系统接口
			if (SignUtil.GW_SID.equals(accountSid)) {
				companyId = 0L;
			} else if (SignUtil.FX_SID.equals(accountSid)) {
				companyId = 1L;
			} else if (SignUtil.PM_SID.equals(accountSid)) {
				companyId = 2L;
			} else if (SignUtil.HX_SID.equals(accountSid)) {
				companyId = 3L;
			} else if (SignUtil.CF_SID.equals(accountSid)) {
				companyId = 4L;
			} else {
				companyId = null;
			}
			UserContext.setSystemInterface(loginIp, companyId);
		}
	}

	/**
	 * 加载数据字典数据
	 */
	public void loadDict(HttpServletRequest request, DictCodeEnum... dictCodeEnum) {
		try {
			for (DictCodeEnum dict : dictCodeEnum) {
				request.setAttribute(dict.getLabelKey(), DictCache.getSubDictList(dict.getLabelKey()));
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 获取当前登录Client对象
	 * 
	 * @param request
	 */
	public Client getLoginClient(HttpServletRequest request) throws Exception {
		// 从session中获取登录的Client对象
		Object obj = request.getSession().getAttribute(SessionKeyEnum.client.getLabelKey());
		return (Client) obj;
	}

	/**
	 * 获取当前设置的业务类型
	 * 
	 * @param request
	 */
	public Long getCompanyId(HttpServletRequest request) throws Exception {
		// 从session中获取登录的Client对象
		String companyIdStr = request.getSession().getAttribute(SessionKeyEnum.companyId.getLabelKey()) + "";
		Long companyId = StringUtils.isNotBlank(companyIdStr) ? Long.parseLong(companyIdStr) : null;
		return companyId;
	}

	/**
	 * 获取当前登录用户SystemUserReqDTO对象
	 * 
	 * @param request
	 */
	public SystemUserDTO getLoginUser(HttpServletRequest request) throws Exception {
		Client client = getLoginClient(request);
		if(null != client){
			return client.getUser();
		}
		return null;
	}

	/**
	 * 获取当前登录用户是否超级管理员
	 * 
	 * @param request
	 */
	public boolean isSuperAdmin(HttpServletRequest request) throws Exception {
		String isSuperAdmin = request.getSession().getAttribute(SessionKeyEnum.superAdminFlag.getLabelKey()) + "";
		if ("Y".equals(isSuperAdmin)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前登录用户,拥有的菜单权限
	 * 
	 * @param request
	 */
	public Map<String, SystemMenuDTO> getMenuMapByLoginUser(HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, SystemMenuDTO> map = (Map<String, SystemMenuDTO>)request.getSession().getAttribute(SessionKeyEnum.menuMap.getLabelKey());
		return map;
	}

	/**
	 * 获取当前登录用户,是否拥有指定的菜单权限
	 * 
	 * @param request
	 * @param url
	 */
	public boolean checkMenuUrlByLoginUser(HttpServletRequest request, String url) throws Exception {
		Map<String, SystemMenuDTO> map = this.getMenuMapByLoginUser(request);
		if(null == map || null == map.get(url)){
			return false;
		}
		return true;
	}
	
	/**
	 * 功能：分页查询时构造公共的查询条件
	 */
	protected <T> PageGrid<T> createPageGrid(HttpServletRequest request, T t) throws Exception {
		PageGrid<T> pageGrid = new PageGrid<T>();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		pageGrid.setPageNumber(null == page ? 0 : Integer.parseInt(page));
		pageGrid.setPageSize(null == rows ? 10 : Integer.parseInt(rows));
		pageGrid.setSort(null == sort ? "" : sort);
		pageGrid.setOrder(null == order ? "" : order);
		return pageGrid;
	}
	
	/**
	 * 功能：分页查询时构造公共的查询条件
	 */
	protected <T> PageGrid<T> transPage(HttpServletRequest request, PageR<T> pageR, BaseDTO baseDTO) throws Exception {
		PageGrid<T> pageGrid = new PageGrid<T>();
		pageGrid.setPageNumber(baseDTO.getPage() < 0 ? 0:baseDTO.getPage());
		pageGrid.setPageSize(baseDTO.getRows() < 0 ? 0:baseDTO.getRows());
		pageGrid.setSort(StringUtils.isEmpty(baseDTO.getSort()) ? "" : baseDTO.getSort());
		pageGrid.setOrder(StringUtils.isEmpty(baseDTO.getOrder()) ? "" : baseDTO.getOrder());
		if(null != pageR && null != pageR.getRows() && pageR.getRows().size() > 0){
			pageGrid.setRows(pageR.getRows());
			if(StringUtils.isNotBlank(pageR.getTotal()+"")){
				pageGrid.setTotal(Integer.parseInt(pageR.getTotal()+""));
			}
		}else{
			pageGrid.setRows(new ArrayList<T>());
			pageGrid.setTotal(0);
		}
		return pageGrid;
	}
	
	/**
	 * form 提交返回json数据,采用此方法,可解决IE兼容性提示下载问题
	 * 
	 * @param response
	 * @param obj
	 */
	protected void outWrite(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JsonUtil.toJSONString(obj));
		out.flush();
	}

	/**
	 * 获取请求上下文中的真实IP地址
	 */
	protected String getRemoteAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP
			int index = ip.indexOf(',');
			if (index > -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}

		ip = request.getHeader("Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("WL-Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("HTTP_CLIENT_IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("X-Cluster-Client-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	/**
	 * 新增或更新时，设置公共的属性
	 * @param obj
	 * @param objId 对象为空的标示
	 * @return
	 */
	public void setPublicAttr(BaseDTO obj, Object objId) {
		if(null == objId){
			// 新增时，设置公共的属性
			Date date = new Date();
			obj.setCreateUser(UserContext.get().getLoginName());
			obj.setCreateDate(date);
			obj.setCreateIp(UserContext.get().getLoginIp());
			obj.setUpdateUser(UserContext.get().getLoginName());
			obj.setUpdateDate(date);
			obj.setUpdateIp(UserContext.get().getLoginIp());
			obj.setCompanyId(UserContext.get().getCompanyId());
			obj.setVersionNo(0);
			obj.setEnableFlag("Y");
			obj.setDeleteFlag("N");
		}else{
			// 更新时，设置公共的属性
			Date date = new Date();
			obj.setUpdateUser(UserContext.get().getLoginName());
			obj.setUpdateDate(date);
			obj.setUpdateIp(UserContext.get().getLoginIp());
			obj.setCompanyId(UserContext.get().getCompanyId());
		}
	}
	
}
