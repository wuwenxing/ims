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
import com.gwghk.ims.common.dto.mall.MallItemDTO;
import com.gwghk.ims.common.inf.external.mall.MallItemsDubboService;

/**
 * 商品查询
 * @author jackson.tang
 *
 */
@Controller
@RequestMapping("/mall/item")
public class MallItemController extends BaseController{
	
	public final String MALL_ITEM_PARENT_DICT_CODE="MallItemType";
	
	private static final Logger logger = LoggerFactory.getLogger(MallItemController.class);
	@Autowired
	private MallItemsDubboService mallItemsDubboService;
	
	/**
	 * @api {get,post} /mall/item/page 分页查询
	 * @apiSampleRequest /mall/item/page
	 * @apiGroup MallApi
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam activityName 活动名称
	 * @apiParam activityPeriods 活动编号
	 * @apiParam enableFlag 是否启用
	 * @apiParam mallItemsCode 物品编号
	 * @apiParam mallItemsName 物品名称
	 * @apiParam mallItemsType 物品类型
	 * @apiParam taskCode 任务编号
	 * @apiParam taskName 任务名称
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据 
	 * @apiSuccess {Object} extendData 扩展数据(总记录数)
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": 1000
	 *	}
	 */
    @RequestMapping(value = "/page", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<List<MallItemDTO>> page(@ModelAttribute MallItemDTO pageSearchDTO) {
		try {
			pageSearchDTO.setCompanyId(getCompanyId());
			MisRespResult<PageR<MallItemDTO>> result = mallItemsDubboService.findPageList(pageSearchDTO);
			
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
	 * @api {get,post} /mall/item/{id} 查询物品信息
	 * @apiSampleRequest /mall/item/{id}
	 * @apiGroup MallApi
	 * 
	 * @apiParam {String} id
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据(总记录数)
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": 1000
	 *	}
	 */
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<MallItemDTO> findById(@PathVariable("id") Long id) {
		try {
			return mallItemsDubboService.findById(id,getCompanyId());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
