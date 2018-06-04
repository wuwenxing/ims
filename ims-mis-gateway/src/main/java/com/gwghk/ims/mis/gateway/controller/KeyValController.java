package com.gwghk.ims.mis.gateway.controller;

import java.util.List;
import java.util.Map;

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
import com.gwghk.ims.common.inf.mis.base.MisKeyValDubboService;
import com.gwghk.ims.common.vo.base.KeyValVO;

/**
 * 摘要：后台-键值对controller
 * @apiDefine KeyValApi 键值维护
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月28日
 */
@Controller
@RequestMapping("/mis/base/keyval")
public class KeyValController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(KeyValController.class);

	@Autowired
	private MisKeyValDubboService misKeyValDubboService;
	
	/**
	 * @api {post} /mis/base/keyval/page 分页查询
	 * @apiSampleRequest /mis/base/keyval/page
	 * @apiGroup KeyValApi
	 * 
	 * @apiParam {String} dataKey 键
	 * @apiParam {String} dataVal 值
	 * @apiParam {String} tag 标签
	 * @apiParam {String} remark 备注
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{dataKey:"...",dataVal:"...",tag:"xxx",enableFlag:"xxx"}
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
	@RequestMapping(value = "/page", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@SuppressWarnings("unchecked")
	public MisRespResult<List<KeyValVO>> pageList(@ModelAttribute KeyValVO keyValVO) {
		try{
			keyValVO.setCompanyId(this.getCompanyId());
			Map<String,Object> result = misKeyValDubboService.findPageList(keyValVO,this.getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<KeyValVO>)list : null,result.get("total"));
			}else{
				logger.error("黑名单分页api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/base/keyval/{id} 根据id查询
	 * @apiSampleRequest /mis/base/keyval/{id}
	 * @apiGroup KeyValApi
	 * 
	 * @apiParam {Long} id
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{id:"1"}
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
	@RequestMapping(value = "/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<KeyValVO> findById(@PathVariable("id") Long id) {
		try{
			return misKeyValDubboService.findById(id,this.getCompanyId());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/base/keyval/save 新增或修改
	 * @apiSampleRequest /mis/base/keyval/save
	 * @apiGroup KeyValApi
	 * 
	 * @apiParam {String} dataKey (必填)键
	 * @apiParam {String} dataVal (必填)值
	 * @apiParam {String} tag 标签
	 * @apiParam {String} remark 备注
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{dataKey:"...",dataVal:"...",tag:"xxx",enableFlag:"xxx"}
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
	public MisRespResult<Void> save(@ModelAttribute KeyValVO keyValVO) {
		try{
			// 校验参数
			if(null == keyValVO 
					|| StringUtils.isBlank(keyValVO.getDataKey())
					|| StringUtils.isBlank(keyValVO.getDataVal())){
				return MisRespResult.error("参数错误");
			}
			// 新增操作
			this.setPublicAttr(keyValVO, keyValVO.getId());
			logger.info("save-->【{}】",keyValVO);
			return misKeyValDubboService.save(keyValVO,this.getCompanyId());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/base/keyval/delete/{ids} 批量或单个删除
	 * @apiSampleRequest /mis/base/keyval/delete/{ids}
	 * @apiGroup KeyValApi
	 * 
	 * @apiParam {String} ids 多个ID逗号隔开
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
	public MisRespResult<Void> delete(@PathVariable("ids") String ids) {
		try{
			logger.info("delete-->【{}】",ids);
			return misKeyValDubboService.deleteByIds(ids,this.getCompanyId());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}