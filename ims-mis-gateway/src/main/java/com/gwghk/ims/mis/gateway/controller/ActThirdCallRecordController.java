package com.gwghk.ims.mis.gateway.controller;

import java.util.Date;
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
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActThirdCallRecordDubboService;
import com.gwghk.ims.common.vo.activity.ActThirdCallRecordVO;
import com.gwghk.ims.mis.gateway.common.UserContext;

/**
 * 摘要：活动管理:外部调用查询Controller
 * @author cookie.lai
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/mis/act/thirdcallrecord")
public class ActThirdCallRecordController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ActThirdCallRecordController.class);
	
	@Autowired
	private MisActThirdCallRecordDubboService misActThirdCallRecordDubboService;

		
	/**
	 * @api {get} /mis/act/thirdcallrecord/page 分页查询
	 * @apiSampleRequest /mis/act/thirdcallrecord/page
	 * @apiGroup ActThirdCallRecordApi
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} accountNo 账号
	 * @apiParam {String} type 类别
	 * @apiParam {String} code 操作类型
	 * @apiParam {String} thirdDealResult 处理结果
	 * @apiParam {String} parentRecordNo 关联发放记录流水号
	 * @apiParam {String} thirdRecordNo 关联外部系统订单号
	 * @apiParam {String} platform 平台
	 * 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{accountNo:"xxx",platform："xxx"}
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
	@RequestMapping(value = "/page", method = {RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<ActThirdCallRecordVO>> pageList(@ModelAttribute ActThirdCallRecordVO reqDto) {
		try{
			Long companyId = UserContext.get().getCompanyId();
			MisRespResult<PageR<ActThirdCallRecordVO>> result = misActThirdCallRecordDubboService.findPageList(reqDto,companyId);
			return MisRespResult.success(result.getData().getList(),result.getData().getTotal()) ;
		}catch(Exception e){
			logger.error("<---pageList->查询分页失败", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
		
	/**
	 * 功能：根据id查询
	 */
	/**
	 * @api {get} /mis/act/thirdcallrecord/{id} 根据id查询调用信息
	 * @apiSampleRequest /mis/act/thirdcallrecord/{id}
	 * @apiGroup ActThirdCallRecordApi
	 * 
	 * @apiParam {String} id id
	 * 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{id:"xxx"}
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
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public MisRespResult<ActThirdCallRecordVO> findById(@PathVariable("id")Long id) {
		try{
			Long companyId = UserContext.get().getCompanyId();
			return misActThirdCallRecordDubboService.findById(id,companyId);
		}catch(Exception e){
			logger.error("<---系统出现异常!", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {get} /mis/act/thirdcallrecord/recall/{ids} 重新触发掉单记录
	 * @apiSampleRequest /mis/act/thirdcallrecordr/recall/{ids}
	 * @apiGroup ActThirdCallRecordApi
	 * 
	 * @apiParam {String} ids (必填)ids
	 * 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{accountNo:"xxx",platform："xxx"}
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
	@RequestMapping(value = "/recall/{ids}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public MisRespResult<Void> recall(@PathVariable("ids")String ids) {
		try{
			if (ids != null) {
	            String[] idArray = ids.split(",");
	            Long companyId = UserContext.get().getCompanyId();
	            String companyCode = CompanyEnum.getCodeById(companyId);
	            if(idArray!=null){
	            	for(String id:idArray){
	            		 MisRespResult<ActThirdCallRecordVO> findById = misActThirdCallRecordDubboService.findById(Long.parseLong(id),companyId);
	            		 ActThirdCallRecordVO actThirdCallRecord = findById.getData();
	            		if(actThirdCallRecord!=null && "failure".equals(
	            				actThirdCallRecord.getThirdDealResult())){
	            			//重置
	                		actThirdCallRecord.setTryCount(1);
	                		actThirdCallRecord.setUpdateDate(new Date());
	                		actThirdCallRecord.setCompanyCode(companyCode);
	                		misActThirdCallRecordDubboService.update(actThirdCallRecord,companyId);
	            		}
	            	}
	            }
			}
			return MisRespResult.success();
		}catch(Exception e){
			logger.error(">>>ActThirdCallRecordController recall error:{}",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}