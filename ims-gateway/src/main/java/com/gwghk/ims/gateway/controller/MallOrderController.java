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
import com.gwghk.ims.common.dto.mall.MallOrderDTO;
import com.gwghk.ims.common.inf.external.mall.MallOrderDubboService;

/**
 * 摘要：商城-订单controller
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月28日
 */
@Controller
@RequestMapping("/mall/order")
public class MallOrderController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MallOrderController.class);
	
	@Autowired
	private  MallOrderDubboService  mallOrderDubboService;

	/**
	 * @api {post,get} /mall/order/page  分页查询
	 * @apiSampleRequest /mall/order/page
	 * @apiGroup MallOrderApi
	 * 
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
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
	public MisRespResult<List<MallOrderDTO>> page(@ModelAttribute MallOrderDTO pageSearchDTO) {

		try {
			pageSearchDTO.setCompanyId(getCompanyId());
			MisRespResult<PageR<MallOrderDTO>> result = mallOrderDubboService.findPageList(pageSearchDTO);
			
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
		
	/**
	 * @api {post,get} /mall/order/{id}  功能：根据id查询
	 * @apiSampleRequest /mall/order/2342
	 * @apiGroup MallOrderApi
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
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public MisRespResult<MallOrderDTO> findById(@PathVariable("id") Long id) {
		try {
			MallOrderDTO findDTO=new MallOrderDTO();
			findDTO.setId(id);
			findDTO.setCompanyId(getCompanyId());
			
			MisRespResult<MallOrderDTO> result = mallOrderDubboService.findById(findDTO);
			
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

}
