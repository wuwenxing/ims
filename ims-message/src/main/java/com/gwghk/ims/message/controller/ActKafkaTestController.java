package com.gwghk.ims.message.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;

/**
 * 摘要：kafka测试类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
@RestController
@RequestMapping(value = "/act/kafka")
public class ActKafkaTestController {
	private static final Logger logger = LoggerFactory.getLogger(ActKafkaTestController.class);
	
	/**
	 * 功能：测试接口是否通
	 */
	@RequestMapping(value = "/test", method = {RequestMethod.POST,RequestMethod.GET})
	public ApiRespResult<Void> test(HttpServletRequest req){
		logger.info(">>>monitor kafka is survivaling.....");
		return new ApiRespResult<>(ApiResultCode.OK);
	}
}
