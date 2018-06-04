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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisActProposalModifyDubboService;
import com.gwghk.ims.common.vo.activity.ActProposalModifyVO;

/**
 * 
 * 摘要：活动修改提案Controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月13日
 */
@Controller
@RequestMapping("/mis/act/proposal/modify")
public class ActProposalModifyController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActProposalModifyController.class);
	
	@Autowired
	private MisActProposalModifyDubboService misActProposalModifyDubboService;

	/**
	 * @api {get} /mis/act/proposal/modify/page 活动提案修改-分页查询
	 * @apiSampleRequest /mis/act/proposal/modify/page
	 * @apiGroup 活动修改提案
	 * 
	 * @apiParam {String} pno 提案号
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} activityName 活动编号
	 * @apiParam {String} activityType 活动类型
	 * @apiParam {String} activityStatus 活动状态
	 * @apiParam {String} proposalStatus 案狀態
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{pno:"xxx",activityPeriods:"xxx",activityName:"xxx",activityType:"xxx"}
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
	@RequestMapping(value = "/page", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public MisRespResult<List<ActProposalModifyVO>> page(HttpServletRequest request,@ModelAttribute ActProposalModifyVO vo) {
		try {
			//setPublicAttr(vo, vo.getId());
			MisRespResult<PageR<ActProposalModifyVO>> result = misActProposalModifyDubboService.findPageList(vo,getCompanyId()) ;
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/proposal/modify/:pno 根据活动修改提案编号查询
	 * @apiSampleRequest /mis/act/proposal/modify/E1001
	 * @apiGroup 活动修改提案
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
	@RequestMapping(value = "/{pno}", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Map<String,Object>> getActProposalModifyByPno(@PathVariable String pno) {
		try {
			return  misActProposalModifyDubboService.getActProposalModifyByPno(pno,getCompanyId()) ;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/proposal/modify/approve/:pno 审核通过指定修改提案
	 * @apiSampleRequest /mis/act/proposal/modify/approve/E1001
	 * @apiGroup 活动修改提案
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
	@RequestMapping(value = "/approve/{pno}", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> updateApproveModifyProposal(@PathVariable String pno) {
		try {
			return  misActProposalModifyDubboService.updateApproveModifyProposal(pno,false, getLoginUser(),getCompanyId()) ;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/proposal/modify/cancel/:pno 取消指定修改提案
	 * @apiSampleRequest /mis/act/proposal/modify/cancel/E1001
	 * @apiGroup 活动修改提案
	 * 
	 * @apiParam {String} cancelReason 取消原因
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
	@RequestMapping(value = "/cancel/{pno}", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> updateCancelModifyProposal(HttpServletRequest request,@PathVariable String pno) {
		try {
			String cancelReason = request.getParameter("cancelReason");
			return  misActProposalModifyDubboService.updateCancelModifyProposal(pno, cancelReason,getLoginUser(),getCompanyId()) ;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}