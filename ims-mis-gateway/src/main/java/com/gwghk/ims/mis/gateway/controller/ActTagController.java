package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

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
import com.gwghk.ims.common.inf.mis.base.MisActTagDubboService;
import com.gwghk.ims.common.vo.base.ActTagVO;

/**
 * 摘要：后台-标签 controller
 * @apiDefine ActTagApi 用户标签
 * 
 * @author george.li
 * @version 1.0
 * @Date 2018年3月31日
 */
@Controller
@RequestMapping("/mis/act/tag")
public class ActTagController extends BaseController {
 
	private static final Logger logger = LoggerFactory.getLogger(ActTagController.class);

	@Autowired
	private MisActTagDubboService misActTagDubboService;
	
	/**
	 * @api {post} /mis/act/tag/page 分页查询
	 * @apiSampleRequest /mis/act/tag/page
	 * @apiGroup UserTagApi
	 * 
	 * @apiParam {String} tagCode 标签编码
	 * @apiParam {String} tagName 标签名称
	 * @apiParam {String} tagValue 标签值
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
	@RequestMapping(value = "/page", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<ActTagVO>> pageList(@ModelAttribute ActTagVO userTagVO) {
		try{
			MisRespResult<PageR<ActTagVO>> result = misActTagDubboService.findPageList(userTagVO,getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<ActTagVO> findById(@PathVariable("id") Long id) {
		try{
			return misActTagDubboService.findById(id,getCompanyId());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/tag/save 新增或修改
	 * @apiSampleRequest /mis/act/tag/save
	 * @apiGroup UserTagApi
	 * 
	 * @apiParam {String} tagCode 标签编码
	 * @apiParam {String} tagName 标签名称
	 * @apiParam {String} tagValue 标签值
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
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	@ResponseBody
	public MisRespResult<Void> save(@ModelAttribute ActTagVO userTagVO) {
		logger.info("save-->【{}】",userTagVO);
		try{
			// 校验标签编码不能为空
			if(null == userTagVO || StringUtils.isBlank(userTagVO.getTagCode())){
				return MisRespResult.error("标签编码不能为空");
			}
			// 校验标签编码不能重复
			ActTagVO dto = misActTagDubboService.findByCode(userTagVO.getTagCode(), this.getCompanyId()).getData();
			if (null != dto && !dto.getId().equals(userTagVO.getId())) {
				return MisRespResult.error("标签编码不能重复");
			}
			// 新增操作
			String temp = userTagVO.getEnableFlag();
			this.setPublicAttr(userTagVO,userTagVO.getId());
			userTagVO.setEnableFlag(temp);
			return misActTagDubboService.save(userTagVO,1l);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/tag/delete/{ids} 批量或单个删除
	 * @apiSampleRequest /mis/act/tag/delete/{ids}
	 * @apiGroup UserTagApi
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
	@RequestMapping(value = "/delete/{ids}", method = {RequestMethod.POST})
	@ResponseBody
	public MisRespResult<Void> delete(@PathVariable("ids") String ids) {
		logger.info("delete-->【{}】",ids);
		try{
			return misActTagDubboService.deleteByIds(ids, this.getCompanyId());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
