package com.gwghk.ims.mis.gateway.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gwghk.ims.mis.gateway.enums.SessionKeyEnum;
import com.gwghk.ims.mis.gateway.util.ImageUtil;
import com.gwghk.ims.mis.gateway.util.ValidateCodeUtil;

/**
 * 摘要：验证码controller
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
@Controller
public class ValidateCodeController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);

	/**
	 * @api {post} /captcha 生成验证码
	 * @apiSampleRequest /captcha
	 * @apiGroup SystemCommonApi
	 */
    @RequestMapping(value = "/captcha", method = {RequestMethod.POST,RequestMethod.GET})
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/png");
        try {
            String code = ValidateCodeUtil.captcha();
            request.getSession().setAttribute(SessionKeyEnum.captcha.getCode(),code);
            response.getOutputStream().write(ValidateCodeUtil.getBytes(ImageUtil.identifying(code)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
