package com.gwghk.ims.mis.gateway.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;

/**
 * 
 * 摘要：物品管理controller
 *
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/mis/act/items")
public class ActItemsSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActItemsSettingController.class);
	

	@Autowired
	private MisActItemsSettingDubboService misActItemsSettingDubboService;
	
	/**
	 * @api {post} /mis/act/items/page 分页查询
	 * @apiSampleRequest /mis/act/items/page
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
	@RequestMapping(value = "/page", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActItemsSettingVO>> pageList(@ModelAttribute ActItemsSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<PageR<ActItemsSettingVO>> result = misActItemsSettingDubboService.findPageList(vo,vo.getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/items/list 物品集合查询
	 * @apiSampleRequest /mis/act/items/list
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
	public MisRespResult<List<ActItemsSettingVO>> list(@ModelAttribute ActItemsSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<List<ActItemsSettingVO>> result = misActItemsSettingDubboService.findList(vo,vo.getCompanyId());
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/items/save  新增或修改物品
	 * @apiSampleRequest /mis/act/items/save
	 * @apiGroup ActItemsApi
	 * 
	 * @apiParam {Long} id 物品id (设置id值则根据id更新，否则为新增)
	 * @apiParam {String} itemNumber 物品编号(必填)
	 * @apiParam {String} itemName 物品名称(必填)
	 * @apiParam {String} itemType 物品类型编号(如：实物、虚拟物品...)(必填)
	 * @apiParam {Integer} itemStockAmount 物品数量
	 * @apiParam {String} itemCategory 物品种类编号(如：流量、话费、会员、串码)
	 * @apiParam {Integer} itemCategoryAmount 物品(虚拟物品)该种类的价值数量(如：话费20元)
	 * @apiParam {BigDecimal} itemPrice 物品价格或金额
	 * @apiParam {String} itemUnit 物品单位编号
	 * @apiParam {String} startDate 开始时间 (如:yyyy-mm-dd HH:mm:ss)
	 * @apiParam {String} endDate 结束时间(如:yyyy-mm-dd HH:mm:ss)
	 * @apiParam {String} enableFlag  物品状态(Y启用N禁用)
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{itemNumber:"A10001",itemName:"U盘",itemType:"real",itemStockAmount:100,itemPrice:10,enableFlag:"Y"}
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
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request,@ModelAttribute ActItemsSettingVO vo) {
		try {
			this.setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misActItemsSettingDubboService.saveOrUpdate(vo,vo.getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}


 
	 
	/**
	 * @api {post} /mis/act/items/delete/:ids  删除物品
	 * @apiSampleRequest /mis/act/items/delete/1001,1002
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
	@RequestMapping(value = "/delete/{ids}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> deleteByIds(@PathVariable String ids) {
		try {
			MisRespResult<Void> result = misActItemsSettingDubboService.deleteByIdArray(ids,getCompanyId()); 
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	 
	
	/**
	 * @api {post} /mis/act/items/:itemNumber 根据物品编号查询物品
	 * @apiSampleRequest /mis/act/items/fx_1001
	 * @apiGroup ActItemsApi
	 * 
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
	public MisRespResult<ActItemsSettingVO> findByItemNumber(@PathVariable String itemNumber) {
		try {
			MisRespResult<ActItemsSettingVO> result = misActItemsSettingDubboService.findByItemNumber(itemNumber,true,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/items/getNewItemNumber 系统自动生成的物品编号
	 * @apiSampleRequest /mis/act/items/getNewItemNumber
	 * @apiGroup ActItemsApi
	 * 
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
	@RequestMapping(value = "/getNewItemNumber", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<String> getNewItemNumber() {
		try {
			MisRespResult<String> result = misActItemsSettingDubboService.getNewItemNumber(getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}