package com.gwghk.ims.mis.gateway.controller;

import java.util.List;
import java.util.Map;

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
import com.gwghk.ims.common.inf.mis.activity.MisActTaskCustomSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskCustomSettingVO;

/**
 * 
 * 摘要：任务管理controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/mis/act/task/custom")
public class ActTaskCustomSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActTaskCustomSettingController.class);
	
	@Autowired
	private MisActTaskCustomSettingDubboService misActTaskCustomSettingDubboService;

	/**
	 * @api {post} /mis/act/task/custom/save  任务管理-保存自定义任务
	 * @apiSampleRequest /mis/act/task/custom/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} taskName 任务名称
	 * @apiParam {String} taskDesc 任务描述
	 * @apiParam {String} taskCode 任务识别码
	 * @apiParam {String} ruleCode 规则识别码
	 * @apiParam {String} enableFlag Y启用N停用
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{taskCode:"xxx",taskName:"xxx",ruleCode:"xxx",enableFlag:"xxx"}
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
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request,@ModelAttribute ActTaskCustomSettingVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misActTaskCustomSettingDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/task/custom/enable  任务管理-批量启用或停用
	 * @apiSampleRequest /mis/act/task/custom/enable
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} taskCodes 任务ID 多个用,隔开
	 * @apiParam {String} enableFlag Y启用N停用
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{ids:"1,2,3",enableFlag:"Y"}
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
	@RequestMapping(value = "/enable/{enable}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> update(HttpServletRequest request,@PathVariable("enable") String enable,@RequestParam("taskCodes") String taskCodes) {
		try {
			MisRespResult<Void> result = misActTaskCustomSettingDubboService.updateEnable(taskCodes, enable, getCompanyId()); 
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/task/custom/page 任务管理-分页查询
	 * @apiSampleRequest /mis/act/task/custom/page
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {Integer} taskType 任务类型 1:自定义任务 2:系统内置任务
	 * @apiParam {String} taskName 任务名称
	 * @apiParam {String} taskCode 任务识别码
	 * @apiParam {String} ruleCode 规则识别码
	 * @apiParam {String} startDate 创建开始时间
	 * @apiParam {String} endDate 创建结束时间
	 * @apiParam {String} enableFlag Y启用N停用
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{taskType:"xxx",taskCode:"xxx",taskName:"xxx",ruleCode:"xxx",enableFlag:"xxx",page:1,rows:20}
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
	@SuppressWarnings("unchecked")
	public MisRespResult<List<ActTaskCustomSettingVO>> pageList(HttpServletRequest request,@ModelAttribute ActTaskCustomSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			Map<String,Object> result = misActTaskCustomSettingDubboService.findPageList(vo,getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ActTaskCustomSettingVO>)list : null,result.get("total"));
			}else{
				logger.error("任务管理分页api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {get} /mis/act/task/custom/list 任务管理-查询任务列表
	 * @apiSampleRequest /mis/act/task/custom/list
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {Integer} taskType 任务类型 1:自定义任务 2:系统内置任务
	 * @apiParam {String} taskName 任务名称
	 * @apiParam {String} taskCode 任务识别码
	 * @apiParam {String} ruleCode 规则识别码
	 * @apiParam {String} enableFlag Y启用N停用
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{taskType:"xxx",taskCode:"xxx",taskName:"xxx",ruleCode:"xxx",enableFlag:"xxx",page:1,rows:20}
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
	public MisRespResult<List<ActTaskCustomSettingVO>> findList(HttpServletRequest request,@ModelAttribute ActTaskCustomSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			vo.setEnableFlag("Y");
			MisRespResult<List<ActTaskCustomSettingVO>> result = misActTaskCustomSettingDubboService.findList(vo, getCompanyId()); 
			System.out.println(result.getData());
			return result ;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/task/custom/delete/{id} 任务管理-批量删除
	 * @apiSampleRequest /mis/act/task/custom/delete/1
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {Integer} id 必填 多个,隔开
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{id:1}
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
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> delete(HttpServletRequest request,@PathVariable String id) {
		try {
			MisRespResult<Void> result = misActTaskCustomSettingDubboService.deleteByIds(id,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}