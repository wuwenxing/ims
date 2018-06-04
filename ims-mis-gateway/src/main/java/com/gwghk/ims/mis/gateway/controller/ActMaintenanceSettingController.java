package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

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
import com.gwghk.ims.common.inf.mis.activity.MisActMaintenanceSettingDubboService;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActMaintenanceSettingVO;

/**
 * 
 * 摘要：活动维护时间管理controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月29日
 */

@Controller
@RequestMapping("/mis/act/maintenance")
public class ActMaintenanceSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActMaintenanceSettingController.class);
	
	@Autowired
	private MisActMaintenanceSettingDubboService misActMaintenanceSettingDubboService;

	/**
	 * @api {post} /mis/act/maintenance/save 活动维护时间-保存
	 * @apiSampleRequest /mis/act/maintenance/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 任务编号
	 * @apiParam {String} startTime 维护开始时间(yyyy-MM-dd HH:mm:ss)
	 * @apiParam {String} endTime 维护结束时间(yyyy-MM-dd HH:mm:ss)
	 * @apiParam {String} enableFlag Y启用N停用
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"xxx",startTime:"xxx",endTime:"xxx"}
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
	public MisRespResult<Void> save(@ModelAttribute ActMaintenanceSettingVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misActMaintenanceSettingDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/maintenance/batch/effective 活动维护时间-批量生效
	 * @apiSampleRequest /mis/act/maintenance/batch/effective
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} ids ID多个,隔开
	
	 * 
	 * @apiParamExample {json} 请求样例 
	 * 	{ids:"xxx"}
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
	@RequestMapping(value = "/batch/effective", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> batchEffective(@RequestParam("id")String id) {
		try {
			BaseVO vo = new BaseVO() ;
			setPublicAttr(vo, 1);
			MisRespResult<Void> result = misActMaintenanceSettingDubboService.batchEffective(id,vo, getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/maintenance/page 活动维护时间-分页查询
	 * @apiSampleRequest /mis/act/maintenance/page
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 任务编号
	 * @apiParam {String} activityName 任务名称
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"A000001",activityName:"xxx",page:1,rows:20}
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
	public MisRespResult<List<ActMaintenanceSettingVO>> pageList(@ModelAttribute ActMaintenanceSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<PageR<ActMaintenanceSettingVO>> result = misActMaintenanceSettingDubboService.findPageList(vo,getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/maintenance/{id} 活动维护时间-根据ID查询
	 * @apiSampleRequest /mis/act/maintenance/1
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {Integer} id 
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
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST ,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<ActMaintenanceSettingVO> findById(@PathVariable Integer id) {
		try {
			MisRespResult<ActMaintenanceSettingVO> result = misActMaintenanceSettingDubboService.findById(id, getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/maintenance/delete/{id} 活动维护时间-删除
	 * @apiSampleRequest /mis/act/maintenance/delete/1
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} id 必填 多个用,隔开
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
	public MisRespResult<Void> delete(@PathVariable String id) {
		try {
			MisRespResult<Void> result = misActMaintenanceSettingDubboService.deleteByIdArray(id,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
}