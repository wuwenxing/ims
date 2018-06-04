package com.gwghk.ims.bos.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.bos.web.common.cache.MenuCache;
import com.gwghk.ims.bos.web.common.context.Client;
import com.gwghk.ims.bos.web.common.context.Constants;
import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.easyui.TreeBean;
import com.gwghk.ims.bos.web.common.enums.ErrorCodeEnum;
import com.gwghk.ims.bos.web.common.enums.LoginClientEnum;
import com.gwghk.ims.bos.web.common.enums.ResultCodeEnum;
import com.gwghk.ims.bos.web.common.enums.SessionKeyEnum;
import com.gwghk.ims.bos.web.common.enums.SystemConfigEnum;
import com.gwghk.ims.bos.web.common.response.ErrorCode;
import com.gwghk.ims.bos.web.common.response.ResultCode;
import com.gwghk.ims.bos.web.common.utils.ImageUtil;
import com.gwghk.ims.bos.web.common.utils.MD5;
import com.gwghk.ims.bos.web.common.utils.SystemConfigUtil;
import com.gwghk.ims.bos.web.common.utils.ValidateCodeUtil;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.dto.system.SystemMenuDTO;
import com.gwghk.ims.common.dto.system.SystemUserDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.sys.SystemUserDubboService;

@Controller
@RequestMapping({"/LoginController", ""})
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private SystemUserDubboService systemUserDubboService;
	
	/**
	 * 加载配置数据
	 */
	@ModelAttribute
	private void loadProperties(HttpServletRequest request) {
		try{
			request.setAttribute("systemDomainName", SystemConfigUtil.getProperty(SystemConfigEnum.systemDomainName));
			request.setAttribute("systemVersion", SystemConfigUtil.getProperty(SystemConfigEnum.systemVersion));
			request.setAttribute("systemPort", SystemConfigUtil.getProperty(SystemConfigEnum.systemPort));
			request.setAttribute("webLinkUrl", SystemConfigUtil.getProperty(SystemConfigEnum.webLinkUrl));
			request.setAttribute("cboardLinkUrl", SystemConfigUtil.getProperty(SystemConfigEnum.cboardLinkUrl));
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转登陆页面
	 */
	@RequestMapping(value = {"/login", ""}, method = { RequestMethod.GET })
	public String login(HttpServletRequest request) {
		try {
			this.loadProperties(request);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
		return "login/login";
	}

	/**
	 * 登陆验证
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode login(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam String loginName, @RequestParam String password, String captcha
			) {
		try{
			if (StringUtils.isBlank(loginName)) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userNameNotEmpty));
				return resultCode;
			}
			if (StringUtils.isBlank(password)) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordNotEmpty));
				return resultCode;
			}
//			if (StringUtils.isBlank(captcha)) {
//				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
//				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.validateCodeNotEmpty));
//				return resultCode;
//			}
//			String code = (String)request.getSession().getAttribute(SessionKeyEnum.captcha.getLabelKey());
//			if(!captcha.toUpperCase().equals(code)){
//				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
//				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.validateCodeError));
//				return resultCode;
//			}
			// 登录名及密码校验
			ApiRespResult<SystemUserDTO> result = systemUserDubboService.findByUserNo(loginName);
			SystemUserDTO userEntity = result.getData();
			if(null == userEntity){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userNameError));
				return resultCode;
			}else{
				if(!MD5.getMd5(password).equals(userEntity.getPassword())){
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordError));
					return resultCode;
				}
				if(!Constants.superAdmin.equals(userEntity.getUserNo())){
					// 不为超级管理员
					// 验证用户是否被禁用
					if(!"Y".equals(userEntity.getEnableFlag())){
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userDisable));
						return resultCode;
					}
					// 验证用户是否设置业务类型
					// 根据顺序[外汇\贵金属\恒信\创富]设置业务类型
					String companyIds = userEntity.getCompanyIds();
					if(StringUtils.isNotBlank(companyIds)){
						List<CompanyEnum> list = CompanyEnum.getList(companyIds);
						if(null != list && list.size()>0){
							Long companyId = list.get(0).getLabelKeyLong();
							request.getSession().setAttribute(SessionKeyEnum.companyId.getLabelKey(), companyId);
						}else{
							ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
							resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.companyIdsError));
							return resultCode;
						}
					}else{
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.notCompanyIds));
						return resultCode;
					}
				}else{
					// 为超级管理员-默认设置选择外汇业务类型
					Long companyId = CompanyEnum.fx.getLabelKeyLong();
					request.getSession().setAttribute(SessionKeyEnum.companyId.getLabelKey(), companyId);
				}
			}
			
			// 验证ok,存入session
			Client client = new Client();
			client.setClientType(LoginClientEnum.systemUserLogin.getLabelKey());
			client.setUser(userEntity);
			client.setLoginDate(new Date());
			client.setSessionId(session.getId());
			client.setIp(getRemoteAddr(request));
			client.setCompanyEnum(CompanyEnum.find(this.getCompanyId(request)));
			request.getSession().setAttribute(SessionKeyEnum.client.getLabelKey(), client);
			
			// 登录设置当前用户
			UserContext.setSystemUser(client.getIp(), userEntity.getUserNo(), 
					userEntity.getUserName(), client.getLoginDate(), client.getSessionId(), 
					client.getClientType(), this.getCompanyId(request));
			
			// 其他信息存入session
			if(Constants.superAdmin.equals(userEntity.getUserNo())){
				// 为超级管理员
				request.getSession().setAttribute(SessionKeyEnum.superAdminFlag.getLabelKey(), "Y");
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), null);
			}else{
				request.getSession().setAttribute(SessionKeyEnum.superAdminFlag.getLabelKey(), "N");
				Map<String, SystemMenuDTO> map = MenuCache.findMenuMapByUserId(userEntity.getUserId(), this.getCompanyId(request));
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), map);
			}
			
			// 返回成功
			return new ResultCode(ResultCodeEnum.success);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 首页
	 * @param page 跳转的具体页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index/{page}", method = { RequestMethod.GET })
	public String index(@PathVariable String page, HttpServletRequest request, HttpServletResponse response) {
		String sendPage = "login/"+page;
		try{
			SystemUserDTO loginUser = this.getLoginUser(request);
			if(null == loginUser){
				return sendPage;
			}
			// 顶部页签菜单列表
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = false;// 是否包含菜单
			boolean hasFun = false;// 是否包含功能
			boolean showFlag = false;// 没有权限，是否显示
			List<SystemMenuDTO> menuList = null;
			Long userId = loginUser.getUserId();
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			if(super.isSuperAdmin(request)){
				showFlag = true;// 没有权限，是否显示
				menuList = MenuCache.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList());
			}else{
				menuList = MenuCache.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList(this.getLoginUser(request).getCompanyIds()));
			}
			
			// 根据动态page,得出动态url
			String menuUrl = "LoginController/index/" + page;
			String topTagMenuCode_1 = MenuCache.getMenuUrlList().get(menuUrl);
			request.setAttribute("topTagMenuCode", topTagMenuCode_1);
			// 左侧菜单
			request.setAttribute("treeBeanList", this.getTreeBeanList(request, topTagMenuCode_1));
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
		return sendPage;
	}

	/**
	 * 左侧菜单公共方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List<TreeBean> getTreeBeanList(HttpServletRequest request, String topTagMenuCode) throws Exception{
		boolean hasTopTag = false;// 是否包含顶部页签
		boolean hasMenu = true;//是否包含菜单
		boolean hasFun = false;// 是否包含功能
		boolean showFlag = false;// 没有权限，是否显示
		Long userId = this.getLoginUser(request).getUserId();
		List<TreeBean> treeBeanList = new ArrayList<TreeBean>();
		if(super.isSuperAdmin(request)){
			treeBeanList = MenuCache.getMenuTreeJson(hasTopTag, hasMenu, hasFun, topTagMenuCode);
		}else{
			treeBeanList = MenuCache.getMenuTreeJsonByUserId(hasTopTag, hasMenu, hasFun, showFlag, userId, userId, topTagMenuCode);
		}
		return treeBeanList;
	}
	
	/**
	 * 登陆后加载menuAccordion
	 * @return
	 */
	@RequestMapping(value = "/loadMenuAccordion/{topTagMenuCode}", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeBean> loadMenuAccordion(@PathVariable String topTagMenuCode, HttpServletRequest request) {
		try{
			return this.getTreeBeanList(request, topTagMenuCode);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TreeBean>();
		}
	}

	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value = "/loginOut", method = { RequestMethod.GET })
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute(SessionKeyEnum.client.getLabelKey());
		this.loadProperties(request);
		return "login/login";
	}

	/**
	 * 生成验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");
		try {
			String code = ValidateCodeUtil.captcha();
			request.getSession().setAttribute(SessionKeyEnum.captcha.getLabelKey(), code);
			response.getOutputStream().write(ValidateCodeUtil.getBytes(ImageUtil.identifying(code)));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updatePassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode updatePassword(HttpServletRequest request, 
			@RequestParam String oldPassword,
			@RequestParam String newPassword) {
		try{
			if(oldPassword.equals(newPassword)){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordTheSame));
				return resultCode;
			}
			SystemUserDTO userEntity =  this.getLoginUser(request);
			if(MD5.getMd5(oldPassword).equals(userEntity.getPassword())){
				systemUserDubboService.updatePassword(userEntity.getUserId(), newPassword);
				userEntity.setPassword(MD5.getMd5(newPassword));
				return new ResultCode(ResultCodeEnum.success);
			}else{
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.oldPasswordError));
				return resultCode;
			}
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 业务权限change事件
	 */
	@RequestMapping(value = "/changeCompanyId", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode changeCompanyId(HttpServletRequest request, @RequestParam Long companyId) {
		try{
			// 查询拥有的业务权限
			List<CompanyEnum> list = null;
			SystemUserDTO loginUserEntity = this.getLoginUser(request);
			if(super.isSuperAdmin(request)){
				list = CompanyEnum.getList();
			}else{
				list = CompanyEnum.getList(loginUserEntity.getCompanyIds());
			}
			
			// 具有该业务权限标示
			boolean temp = false;
			if(null != list && list.size()>0){
				for(CompanyEnum companyEnum: list){
					if(companyEnum.getLabelKey().equals(companyId + "")){
						temp = true;
						break;
					}
				}
			}
			
			// 验证是否具有该业务权限
			if(!temp){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordTheSame));
				return resultCode;
			}
			
			// 改变当前业务类型
			request.getSession().setAttribute(SessionKeyEnum.companyId.getLabelKey(), companyId);
			((Client)request.getSession().getAttribute(SessionKeyEnum.client.getLabelKey())).setCompanyEnum(CompanyEnum.find(this.getCompanyId(request)));
			
			// 需重新设置菜单权限存入session
			if(super.isSuperAdmin(request)){
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), null);
			}else{
				Map<String, SystemMenuDTO> map = MenuCache.findMenuMapByUserId(loginUserEntity.getUserId(), this.getCompanyId(request));
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), map);
			}
			
			// 返回成功
			return new ResultCode(ResultCodeEnum.success);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
