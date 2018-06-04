package com.gwghk.ims.mis.gateway.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemUserDubboService;
import com.gwghk.ims.common.vo.system.LoginUserVO;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.ims.mis.gateway.common.Client;
import com.gwghk.ims.mis.gateway.enums.SessionKeyEnum;
import com.gwghk.ims.mis.gateway.util.BrowserUtil;
import com.gwghk.ims.mis.gateway.util.ContextHolderUtil;
import com.gwghk.ims.mis.gateway.util.IPUtil;
import com.gwghk.unify.framework.common.util.MD5;

/**
 * 摘要：登录controller
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
@Controller
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MisSystemUserDubboService misSystemUserDubboService;
	@Autowired
	private MisSystemRoleDubboService misSystemRoleDubboService;
	
	/**
	 * @api {post} /login 登陆验证
	 * @apiSampleRequest /login
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {String} userNo (必填)账号
	 * @apiParam {String} pwd (必填)密码
	 * @apiParam {String} captcha (必填)验证吗
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{userNo:"fxadmin", pwd:"111111", captcha:"1111"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": null,
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemUserVO> login(LoginUserVO LoginUserVO, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userNo = LoginUserVO.getUserNo(), password = LoginUserVO.getPwd(),
					captcha = LoginUserVO.getCaptcha();
			if (StringUtils.isBlank(userNo)) {
				return MisRespResult.error(MisResultCode.Err10003);
			}
			if (StringUtils.isBlank(password)) {
				return MisRespResult.error(MisResultCode.Err10004);
			}
			// 判断验证码是否正确
			if (null != isShowCapcha && "true".equalsIgnoreCase(isShowCapcha)) {
				if (StringUtils.isBlank(captcha)) {
					return MisRespResult.error(MisResultCode.Err10005);
				}
				String code = (String) ContextHolderUtil.getSession().getAttribute(SessionKeyEnum.captcha.getCode());
				if (!captcha.toUpperCase().equals(code)) {
					return MisRespResult.error(MisResultCode.Err10006);
				}
			}
			MisRespResult<SystemUserVO> userResult = misSystemUserDubboService.findByUserNo(userNo);
			SystemUserVO systemUserVO = userResult.getData();
			if (null == systemUserVO) {
				return MisRespResult.error(MisResultCode.Err10007);
			} else {
				// 验证角色
				if(null != systemUserVO.getRoleId()){
					SystemRoleVO systemRoleVO = misSystemRoleDubboService.findById(systemUserVO.getRoleId()).getData();
					if(null == systemRoleVO){
						return MisRespResult.error("用户未授角色");
					}else if("N".equals(systemRoleVO.getEnableFlag())){
						return MisRespResult.error("用户的角色已被禁用");
					}
				}else{
					return MisRespResult.error("用户未授角色");
				}
				if("N".equals(systemUserVO.getEnableFlag())){
					return MisRespResult.error("用户被禁用");
				}
				if (!MD5.getMD5(password.getBytes()).equals(systemUserVO.getPassword())) {
					return MisRespResult.error(MisResultCode.Err10008);
				}
			}
			// 验证ok,存入session
			Client client = new Client();
			client.setUser(systemUserVO);
			client.setLoginDate(new Date());
			client.setSessionId(request.getSession().getId());
			client.setIp(IPUtil.getIp());
			client.setBrowserType(BrowserUtil.checkBrowse(ContextHolderUtil.getRequest()));
			request.getSession().setAttribute(SessionKeyEnum.client.getCode(), client);
			initHandle(request, response); // 登录设置当前用户
			logger.info(">>>login success![userNo:{},userName:{},ip:{}]",
					new Object[] { userNo, systemUserVO.getUserName(), IPUtil.getIp() });
			return MisRespResult.success(systemUserVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 根据Token登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginByToken", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> loginByToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			String timestamp = request.getParameter("timestamp");
			String sign = request.getParameter("sign");
			String companyId = request.getParameter("companyId");
			String userNo = request.getParameter("userNo");
			logger.info("timestamp={},sign={},companyId={},userNo={}", new Object[]{timestamp, sign, companyId, userNo});
			if(StringUtils.isBlank(timestamp)
					|| StringUtils.isBlank(sign)
					|| StringUtils.isBlank(companyId)
					|| StringUtils.isBlank(userNo)){
				return MisRespResult.error("参数错误");
			}
			MisRespResult<SystemUserVO> userResult = misSystemUserDubboService.findByUserNo(userNo);
			SystemUserVO SystemUserVO = userResult.getData();
			if (null == SystemUserVO) {
				return MisRespResult.error(MisResultCode.Err10007);
			}
			// 验证ok,存入session
			Client client = new Client();
			client.setUser(SystemUserVO);
			client.setLoginDate(new Date());
			client.setSessionId(request.getSession().getId());
			client.setIp(IPUtil.getIp());
			client.setBrowserType(BrowserUtil.checkBrowse(ContextHolderUtil.getRequest()));
			request.getSession().setAttribute(SessionKeyEnum.client.getCode(), client);
			initHandle(request, response); // 登录设置当前用户
			logger.info(">>>login success![userNo:{}, ip:{}]", userNo, IPUtil.getIp());
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /login/out 退出登录
	 * @apiSampleRequest /login/out
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": null,
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/login/out", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> loginOut(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute(SessionKeyEnum.client.getCode());
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 重定向时，返回错误提示
	 */
	@RequestMapping(value = "/check/error", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> checkError(String code) {
		try {
			return MisRespResult.error(MisResultCode.getByCode(code));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}