package com.gwghk.ims.gateway.service;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * 摘要：springmvc 跨域处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年3月31日
 */
@ControllerAdvice(basePackages = "com.gwghk.ims.gateway.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{
	public JsonpAdvice() {
		super("callback", "jsonp");
    }
}