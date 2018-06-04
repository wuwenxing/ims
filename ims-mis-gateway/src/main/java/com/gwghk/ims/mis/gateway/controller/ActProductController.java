package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.gwghk.ims.common.inf.mis.activity.MisActProductDubboService;
import com.gwghk.ims.common.vo.activity.ActProductVO;

/**
 * @apiDefine ActProductApi 产品维护
 */
@Controller
@RequestMapping("/mis/act/product")
public class ActProductController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ActProductController.class);
	
	@Autowired
	private MisActProductDubboService misActProductDubboService;

	/**
	 * @api {post} /mis/act/product/page 分页查询
	 * @apiSampleRequest /mis/act/product/page
	 * @apiGroup ActProductApi
	 * 
	 * @apiParam {String} productName 产品名称
	 * @apiParam {String} productCode 产品编码
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{userNo:"...",userName:"...",email:"xxx"}
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
	@RequestMapping(value = "/page", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<ActProductVO>> page(HttpServletRequest request, @ModelAttribute ActProductVO vo) {
		try{
			vo.setCompanyId(this.getCompanyId());
			MisRespResult<PageR<ActProductVO>> result = misActProductDubboService.findPageList(vo);
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/product/find/{id} 根据id查询
	 * @apiSampleRequest /mis/act/product/find/{id}
	 * @apiGroup ActProductApi
	 * 
	 * @apiParam {Long} id (必填)ID
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
	@RequestMapping(value = "/find/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<ActProductVO> find(@PathVariable Long id) {
		try{
			return misActProductDubboService.findById(id);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/product/save 新增或修改
	 * @apiSampleRequest /mis/act/product/save
	 * @apiGroup ActProductApi
	 * 
	 * @apiParam {Long} id ID
	 * @apiParam {String} productName 产品名称
	 * @apiParam {String} productCode 产品编码
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{...}
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
	@RequestMapping(value = "/save", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute ActProductVO vo) {
		try{
			// 校验产品编码不能为空
			if(StringUtils.isBlank(vo.getProductCode())){
				return MisRespResult.error("产品编码不能为空");
			}
			// 校验产品编码不能重复
			ActProductVO dto = misActProductDubboService.findByProductCode(vo.getProductCode(), this.getCompanyId()).getData();
			if (null != dto && !dto.getId().equals(vo.getId())) {
				return MisRespResult.error("产品编码不能重复");
			}
			// 新增或修改操作
			String temp = vo.getEnableFlag();
			this.setPublicAttr(vo, vo.getId());
			vo.setEnableFlag(temp);
			return misActProductDubboService.saveOrUpdate(vo);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/product/delete/{ids} 根据id删除
	 * @apiSampleRequest /mis/act/product/delete/{ids}
	 * @apiGroup ActProductApi
	 * 
	 * @apiParam {String} ids (必填)多个ID逗号隔开
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{ids:"1,2"}
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
	@RequestMapping(value = "/delete/{ids}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> delete(@PathVariable String ids) {
		try{
			if(StringUtils.isBlank(ids)){
				return MisRespResult.error("参数错误");
			}
			return misActProductDubboService.deleteByIdArray(ids);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

}
