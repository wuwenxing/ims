package com.gwghk.ims.gateway.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.dto.activity.ActSettingDetailsDTO;
import com.gwghk.ims.common.inf.external.activity.ActSettingDubboService;

/**
 * 
 * 摘要：活动提案controller
 *
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月3日
 */
@Controller
@RequestMapping("/act")
public class ActSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActSettingController.class);
 
	@Autowired
	private ActSettingDubboService actSettingDubboService;

	/**
	 * @api {post} /act/{activityPeriods} 查看活动
	 * @apiSampleRequest /act/fx_001 
	 * @apiGroup ActSettingApi
	 * 
	 * @apiParam {String} activityPeriods 活动基本设置.活动编号
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {actBaseInfo.xxxx:"xxxx",actCondSetting.xxxx:"xxxx",actTaskSettings.xxxx:"xxxx",enableFlag:"Y"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/{activityPeriods}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<ActSettingDetailsDTO> findByActivityPeriods(@PathVariable String activityPeriods) {
		try {

			MisRespResult<ActSettingDetailsDTO> result = actSettingDubboService.findByActivityPeriods(activityPeriods,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}