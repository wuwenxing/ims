package com.gwghk.ims.mis.gateway.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.mis.sys.MisSystemDictDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemLogDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemUserDubboService;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.system.SystemLogVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.ims.mis.gateway.common.Client;
import com.gwghk.ims.mis.gateway.common.DictCache;
import com.gwghk.ims.mis.gateway.common.UserContext;
import com.gwghk.ims.mis.gateway.enums.SessionKeyEnum;
import com.gwghk.ims.mis.gateway.enums.SystemLogEnum;
import com.gwghk.ims.mis.gateway.util.BrowserUtil;
import com.gwghk.ims.mis.gateway.util.ContextHolderUtil;
import com.gwghk.ims.mis.gateway.util.GsonUtil;
import com.gwghk.ims.mis.gateway.util.IPUtil;
import com.gwghk.ims.mis.gateway.util.SystemPathUtil;

/**
 * 摘要：基础controller
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
@Controller
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	// 改为public，给外部直接引用
    
    // 是否校验验证码
	@Value("${isShowCapcha}")
	public String isShowCapcha;
	
	@Autowired
	private MisSystemDictDubboService systemDictDubboService;

	@Autowired
	private MisSystemLogDubboService systemLogDubboService;

	@Autowired
	private MisSystemUserDubboService systemUserDubboService;

	// 排除不需权限验证的URL
	public static Map<String, String> urls = new HashMap<String, String>();
	// 不记录日志的Url集合
	public static Map<String, String> notInUrls = new HashMap<String, String>();
	
	static{
		// 排除不需执行的URL
		urls.put("captcha", "");
		urls.put("login", "");
		urls.put("login/out", "");
		urls.put("check/error", "");
	}
	
    /**
     * ModelAttribute的作用：表示请求该类的每个Controller前都会首先执行它
     */
    @ModelAttribute
    public void initHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//处理ie跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", "3.2.1");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		// 设置service
		DictCache.setSystemDictDubboService(systemDictDubboService);
		// requestPath
		String requestPath = SystemPathUtil.getRequestPath(request);
		if(StringUtils.isBlank(requestPath)){
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.sendRedirect(request.getContextPath() + "/check/error?code=" + MisResultCode.Err10009.getCode());
			return;
		}
        // 从session中获取登录的用户对象
        Client client = (Client)request.getSession().getAttribute(SessionKeyEnum.client.getCode());
        if (null != client) {
        	SystemUserVO userVO = client.getUser();
            UserContext userContext = new UserContext();
            userContext.setLoginName(userVO.getUserName());
            userContext.setLoginNo(userVO.getUserNo());
            userContext.setRoleId(userVO.getRoleId());
            userContext.setLoginIp(client.getIp());
            userContext.setCompanyId(userVO.getCompanyId());
            userContext.setCompanyCode(CompanyEnum.getCodeById(userVO.getCompanyId()));
            userContext.setSessionId(client.getSessionId());
            userContext.setBrowserType(client.getBrowserType());
            userContext.setClientType(client.getClientType());
            userContext.setLoginDate(client.getLoginDate());
            UserContext.set(userContext);
            this.logHandle(request, response, requestPath, userVO);
        } else {
        	if(null == UserContext.get()){
                // 未登录默认system
                UserContext userContext = new UserContext();
                userContext.setLoginName("system");
                userContext.setLoginNo("system");
                userContext.setLoginIp("127.0.0.1");
                userContext.setCompanyId(CompanyEnum.fx.getLongId());
                userContext.setCompanyCode(CompanyEnum.fx.getCode());
                userContext.setSessionId("system");
                userContext.setClientType("system");
                userContext.setLoginDate(new Date());
                UserContext.set(userContext);
        	}else{
            	// 过滤urls
        		if (null != BaseController.urls.get(requestPath)) {
        			
        		}else{
        			// 已通过token验证
        			SystemUserVO userVO = systemUserDubboService.findByUserNo(UserContext.get().getLoginNo()).getData();
        			if(null != userVO){
        				// companyId为登录用户的companyId
        				UserContext.get().setCompanyId(userVO.getCompanyId());
        				UserContext.get().setCompanyCode(CompanyEnum.getCodeById(userVO.getCompanyId()));
        				// 验证ok,存入session
        				Client clientObj = new Client();
        				clientObj.setUser(userVO);
        				clientObj.setLoginDate(new Date());
        				clientObj.setSessionId(request.getSession().getId());
        				clientObj.setIp(IPUtil.getIp());
        				clientObj.setBrowserType(BrowserUtil.checkBrowse(request));
        				request.getSession().setAttribute(SessionKeyEnum.client.getCode(), clientObj);
        				logger.info(">>>login success![userNo:{}, ip:{}]", userVO.getUserNo(), IPUtil.getIp());
        			}else{
        				logger.info(">>>login fail![userNo={}不存在]", UserContext.get().getLoginNo());
        				response.setCharacterEncoding("utf-8");
        				response.setContentType("application/json; charset=utf-8");
        				response.sendRedirect(request.getContextPath() + "/check/error?code=" + MisResultCode.Err10007.getCode());
        				return;
        			}
        		}
        	}
        }
        logger.debug("initHandle->{}", UserContext.get().toString());
    }

	/**
	 * 登录以后-记录日志
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void logHandle(HttpServletRequest request, HttpServletResponse response, String requestPath, SystemUserVO userVO)
			throws Exception {
		if(null == notInUrls.get(requestPath)){
			Map<String, String> paramMap = new HashMap<String, String>();
			@SuppressWarnings("rawtypes")
			Enumeration enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				String paramName = (String) enu.nextElement();
				String paramValue = request.getParameter(paramName);
				// 如果是修改密码，则改成***号代替；
				if("pwd".equals(paramName)
						||"newPwd".equals(paramName)
						||"newPwdAgin".equals(paramName)){
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
			String params = GsonUtil.GsonString(paramMap);
			SystemLogVO logVO = new SystemLogVO();
			logVO.setLogType(logType);
			logVO.setMethod(requestPath);
			logVO.setParams(params);
			logVO.setUserName(userVO.getUserName());
			logVO.setUserNo(userVO.getUserNo());
			logVO.setBroswer(BrowserUtil.checkBrowse(ContextHolderUtil.getRequest()));
			this.setPublicAttr(logVO, logVO.getId());
			systemLogDubboService.addLog(logVO);
		}
	}
    
    /**
     * 获取当前登录Client对象
     */
    public Client getLoginClient() {
        Object obj = ContextHolderUtil.getSession().getAttribute(SessionKeyEnum.client.getCode());
        return (Client) obj;
    }

    /**
     * 获取当前登录用户
     */
    public SystemUserVO getLoginUser() {
        Client client = getLoginClient();
        return client == null ? null : client.getUser();
    }
    
    /**
     * 获取当前登录用户
     */
    public String getLoginUserName() {
        Client client = getLoginClient();
        return client == null || client.getUser()==null ? null : client.getUser().getUserName();
    }

	/**
	 * 获取当前用户的业务类型
	 */
	public Long getCompanyId() {
		SystemUserVO userDTO = getLoginUser();
        return userDTO == null ? null : userDTO.getCompanyId();
	}

	/**
	 * 获取当前用户No
	 */
	public String getUserNo() throws Exception {
		SystemUserVO userDTO = getLoginUser();
        return userDTO == null ? null : userDTO.getUserNo();
	}
	
	/**
	 * 新增或更新时，设置公共的属性，剔除属性值
	 * @param obj
	 * @param objId 对象为空的标示
	 * @param attrs enableFlag,companyId等等
	 * @return
	 */
	protected void setPublicAttrWithoutAttrs(BaseVO obj, Object objId, String... attrs) {
		Date date = new Date();
		if(null == objId){
			// 新增时，设置公共的属性
			obj.setCreateUser(UserContext.get().getLoginName());
			obj.setCreateDate(date);
			obj.setCreateIp(UserContext.get().getLoginIp());
			obj.setVersionNo(0);
			if(null != attrs && attrs.length > 0){
				if(!Arrays.asList(attrs).contains("enableFlag")){
					obj.setEnableFlag("Y");
				}
			}else{
				obj.setEnableFlag("Y");
			}
			obj.setDeleteFlag("N");
		}
		// 更新时，设置公共的属性
		obj.setUpdateUser(UserContext.get().getLoginName());
		obj.setUpdateDate(date);
		obj.setUpdateIp(UserContext.get().getLoginIp());
		if(null != attrs && attrs.length > 0){
			if(!Arrays.asList(attrs).contains("companyId")){
				obj.setCompanyId(UserContext.get().getCompanyId());
			}
		}else{
			obj.setCompanyId(UserContext.get().getCompanyId());
		}
	}
	
	/**
	 * 新增或更新时，设置公共的属性，剔除companyId
	 * @param obj
	 * @param objId 对象为空的标示
	 * @return
	 */
	protected void setPublicAttrWithoutCompanyId(BaseVO obj, Object objId) {
		Date date = new Date();
		if(null == objId){
			// 新增时，设置公共的属性
			obj.setCreateUser(UserContext.get().getLoginName());
			obj.setCreateDate(date);
			obj.setCreateIp(UserContext.get().getLoginIp());
			obj.setVersionNo(0);
			if(StringUtils.isBlank(obj.getEnableFlag())){
				obj.setEnableFlag("Y");
			}
			if(StringUtils.isBlank(obj.getDeleteFlag())){
				obj.setDeleteFlag("N");
			}
		}
		// 更新时，设置公共的属性
		obj.setUpdateUser(UserContext.get().getLoginName());
		obj.setUpdateDate(date);
		obj.setUpdateIp(UserContext.get().getLoginIp());
	}
	
	/**
	 * 新增或更新时，设置公共的属性，包含companyId
	 * @param obj
	 * @param objId 对象为空的标示
	 * @return
	 */
	protected void setPublicAttr(BaseVO obj, Object objId) {
		Date date = new Date();
		if(null == objId){
			// 新增时，设置公共的属性
			obj.setCreateUser(UserContext.get().getLoginName());
			obj.setCreateDate(date);
			obj.setCreateIp(UserContext.get().getLoginIp());
			obj.setVersionNo(0);
			if(StringUtils.isBlank(obj.getEnableFlag())){
				obj.setEnableFlag("Y");
			}
			if(StringUtils.isBlank(obj.getDeleteFlag())){
				obj.setDeleteFlag("N");
			}
		}
		// 更新时，设置公共的属性
		obj.setUpdateUser(UserContext.get().getLoginName());
		obj.setUpdateDate(date);
		obj.setUpdateIp(UserContext.get().getLoginIp());
		obj.setCompanyId(UserContext.get().getCompanyId());
	}
	
}
