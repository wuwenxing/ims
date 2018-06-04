package com.gwghk.ims.gateway.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActItemsSettingDTO;
import com.gwghk.ims.common.inf.external.activity.ActItemsSettingDubboService;

/**
 * 
 * 摘要：物品管理controller
 *
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/act/item")
public class ActItemsSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActItemsSettingController.class);
	

	@Autowired
	private ActItemsSettingDubboService actItemsSettingDubboService;
	
	/**
	 * @api {post} /act/item/page 分页查询
	 * @apiSampleRequest /act/item/page
	 * 
	 * @apiGroup ActItemsApi

	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam {String} itemNumber 物品编号
	 * @apiParam {String} itemName 物品名称
	 * @apiParam {String} itemType 物品类型编号(eg：实物(real)、虚拟物品(virtual)...)
	 * @apiParam {String} itemCategory 物品种类编号(eg：流量:(mobile_data)、话费(mobile_charge)、会员(member_vip)、串码物品(string_code))
	 * @apiParam {String} startDate 开始时间 (eg:yyyy-mm-dd HH:mm:ss)
	 * @apiParam {String} endDate 结束时间(eg:yyyy-mm-dd HH:mm:ss)
	 * @apiParam {String} enableFlag 物品状态(Y启用N禁用)
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{itemNumber:"xxx",itemName:"xxx",itemType:"xxx",itemCategory:"xxx",enableFlag:"xxx",page:1,rows:20}
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
	@RequestMapping(value = "/page", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActItemsSettingDTO>> pageList(@ModelAttribute ActItemsSettingDTO dto) {
		try {
			dto.setCompanyId(getCompanyId());
			MisRespResult<PageR<ActItemsSettingDTO>> result = actItemsSettingDubboService.findPageList(dto);
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /act/item/list 物品集合查询
	 * @apiSampleRequest /act/item/list
	 * @apiGroup ActItemsApi

	 * @apiParam {String} itemNumber 物品编号
	 * @apiParam {String} itemName 物品名称
	 * @apiParam {String} itemType 物品类型编号(eg：实物(real)、虚拟物品(virtual)...)
	 * @apiParam {String} itemCategory 物品种类编号(eg：流量:(mobile_data)、话费(mobile_charge)、会员(member_vip)、串码物品(string_code))
	 * @apiParam {String} startDate 开始时间 (eg:yyyy-mm-dd HH:mm:ss)
	 * @apiParam {String} endDate 结束时间(eg:yyyy-mm-dd HH:mm:ss)
	 * @apiParam {String} enableFlag 物品状态(Y启用N禁用)
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{itemNumber:"xxx",itemName:"xxx",itemType:"xxx",itemCategory:"xxx",enableFlag:"xxx",page:1,rows:20}
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
	public MisRespResult<List<ActItemsSettingDTO>> list(@ModelAttribute ActItemsSettingDTO dto) {
		try {
			dto.setCompanyId(getCompanyId());
			MisRespResult<List<ActItemsSettingDTO>> result = actItemsSettingDubboService.findList(dto);
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /act/item/:itemNumber 根据物品编号查询物品
	 * @apiSampleRequest /act/item/fx_1001
	 * @apiGroup ActItemsApi
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
	@RequestMapping(value = "/{itemNumber}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<ActItemsSettingDTO> findByItemNumber(@PathVariable String itemNumber) {
		try {
			MisRespResult<ActItemsSettingDTO> result = actItemsSettingDubboService.findByItemNumber(itemNumber,true,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}