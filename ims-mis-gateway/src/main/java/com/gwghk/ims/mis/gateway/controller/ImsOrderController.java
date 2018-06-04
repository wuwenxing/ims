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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.order.MisOrderDubboService;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

/**
 * 摘要：商城-订单controller
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月28日
 */
@Controller
@RequestMapping("/mis/order")
public class ImsOrderController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ImsOrderController.class);
	
	@Autowired
	private  MisOrderDubboService  misOrderDubboService;

	/**
	 * @api {post,get} /mis/order/page  分页查询
	 * @apiSampleRequest /mis/order/page
	 * @apiGroup ImsOrderApi
	 * 
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {Integer} deliveryStatus  发货状态
	 * @apiParam {String} itemsName 商品名称
	 * @apiParam {String} itemsCode 商品编号
	 * @apiParam {String} activityName 活动名称
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} taskName 任务名称
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{accountNo:"A10000001",accountNo:"1000021",itemsName:"13800138000",itemsCode:"GTS"}
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
	@RequestMapping(value = "/page", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public MisRespResult<List<ImsOrderVO>> page(@ModelAttribute ImsOrderVO pageSearchVo) {

		try {
			pageSearchVo.setCompanyId(getCompanyId());
			//pageSearchVo.setCompanyCode(CompanyEnum.getCodeById(pageSearchVo.getCompanyId()));
			MisRespResult<PageR<ImsOrderVO>> result = misOrderDubboService.findPageList(pageSearchVo);
			
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
		
	/**
	 * @api {post,get} /mis/order/express/{id}  功能：根据id查询
	 * @apiSampleRequest /mis/order/express/{id}
	 * @apiGroup ImsOrderApi
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
	@RequestMapping(value = "/express/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public MisRespResult<ImsOrderVO> expressInfoById(@PathVariable("id") Long id) {
		try {
			ImsOrderVO findVo=new ImsOrderVO();
			findVo.setId(id);
			findVo.setCompanyId(getCompanyId());
			MisRespResult<ImsOrderVO> result = misOrderDubboService.findById(findVo);
			
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	 /**
	 * @api {post,get} /mis/order/express/save  功能：保存录入快递信息
	 * @apiSampleRequest /mis/order/express/save
	 * @apiGroup ImsOrderApi
	 * 
	 * @apiParam {Long} id 订单ID
	 * @apiParam {String} expressCompany 快递公司
	 * @apiParam {String} expressNo 快递单号
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
	@RequestMapping(value = "/express/save", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public MisRespResult<Void> saveExpress(@ModelAttribute ImsOrderVO imsOrderVo) {
		try {
			imsOrderVo.setCompanyId(getCompanyId());
			
			MisRespResult<Void> result=misOrderDubboService.updateExpress(imsOrderVo);
			if(result.isOk())
				return MisRespResult.success();
			else
				return MisRespResult.error(MisResultCode.FAIL.getCode(),result.getMsg());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
		
	}
	
	/**
	 * @api {post,get} /mis/order/express/batch/save  功能：批量保存录入快递信息
	 * @apiSampleRequest /mis/order/express/batch/save
	 * @apiGroup ImsOrderApi
	 * 
	 * @apiParam {Long} ids 订单ID集合
	 * @apiParam {String} expressCompany 快递公司
	 * @apiParam {String} expressNo 快递单号
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
	@RequestMapping(value = "/express/batch/save", method = {RequestMethod.POST})
	@ResponseBody
	public MisRespResult<Void> batchSaveExpress(HttpServletRequest request,@ModelAttribute ImsOrderVO imsOrderVo,@RequestParam("ids")String ids) {
		try {
			setPublicAttr(imsOrderVo, 0);
			return misOrderDubboService.batchUpdateExpress(ids,imsOrderVo);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	

	/**
	 * @api {post,get} /mis/order/deliveryStatus/batch/save  批量更新发货状态
	 * @apiSampleRequest /mis/order/deliveryStatus/batch/save
	 * @apiGroup ImsOrderApi
	 * 
	 * @apiParam {Long} ids 订单ID集合
	 * @apiParam {Integer} deliveryStatus 状态
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
	@RequestMapping(value = "/deliveryStatus/batch/save", method = { RequestMethod.POST})
	@ResponseBody
	public MisRespResult<Void> batchSave(@ModelAttribute ImsOrderVO imsOrderVo,@RequestParam("ids")String ids) {
		try {
			setPublicAttr(imsOrderVo, 0);
			MisRespResult<Void> result = misOrderDubboService.batchUpdateDeliveryStatus(ids,imsOrderVo);
			if(result.isOk())
				return MisRespResult.success();
			else
				return MisRespResult.error(MisResultCode.FAIL.getCode(),result.getMsg());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post,get} /mis/order/address/batch/save  批量更新收货地址
	 * @apiSampleRequest /mis/order/address/batch/save
	 * @apiGroup ImsOrderApi
	 * 
	 * @apiParam {Long} ids 订单ID集合
	 * @apiParam {String} address 收货地址
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
	@RequestMapping(value = "/address/batch/save", method = { RequestMethod.POST ,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> saveAddress(String ids,String address) {
		try {
			Long companyId=getCompanyId();
			String[] idArr=ids.split(",");
			MisRespResult<Void> result=misOrderDubboService.updateAddress(idArr,address,companyId);
			if(result.isOk())
				return MisRespResult.success();
			else
				return MisRespResult.error(MisResultCode.FAIL.getCode(),result.getMsg());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
