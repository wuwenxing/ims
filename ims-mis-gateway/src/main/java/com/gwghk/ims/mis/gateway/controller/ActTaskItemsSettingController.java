package com.gwghk.ims.mis.gateway.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisActTaskItemsDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;

/**
 * 
 * 摘要：活动奖励物品controller
 *
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月13日
 */
@Controller
@RequestMapping("/mis/act/taskItems")
public class ActTaskItemsSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActTaskItemsSettingController.class);
 
	@Autowired
	private MisActTaskItemsDubboService misActTaskItemsDubboService;
	
	 
	
	/**
	 * @api {post} /mis/act/taskItems/list 加载活动任务奖励列表
	 * @apiSampleRequest /mis/act/taskItems/list
	 * @apiGroup ActTaskItemsApi
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} taskSettingId 任务id
     * 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"xxx",taskSettingId:"xxx"}
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
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActTaskItemsVO>> getActList(@ModelAttribute ActTaskItemsVO vo) {
		try {
			MisRespResult<List<ActTaskItemsVO>> result = misActTaskItemsDubboService.findList(vo,vo.getCompanyId());
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}