package com.gwghk.ims.gateway.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.dto.activity.ActCashoutRealDTO;
import com.gwghk.ims.common.inf.external.activity.ActCashoutRealDubboService;
import com.gwghk.ims.common.vo.activity.ActCashoutRealVO;

/**
 * 摘要：入金记录Controller
 * @author cookie.lai
 * @Date 2018年3月29日
 */

@Controller
@RequestMapping("/act/cashoutreal")
public class ActCashoutRealController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ActCashoutRealController.class);
	
	@Autowired
	private ActCashoutRealDubboService cashoutRealDubboService;

		
	/**
	 * @api {get} /act/cashoutreal/page 分页查询
	 * @apiSampleRequest /mis/act/cashoutreal/page 
	 * @apiGroup ActCashoutRealApi
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam {String} pno 订单号
	 * @apiParam {String} accountNo 账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} firstWithdraw 是否首次取款成功
	 * @apiParam {String} startApproveDate 订单开始时间
	 * @apiParam {String} endApproveDate 订单结束时间
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
	@SuppressWarnings("unchecked")
	public MisRespResult<List<ActCashoutRealDTO>> pageList(@ModelAttribute ActCashoutRealDTO dto) {
		try{
			dto.setCompanyId(getCompanyId());
			Map<String,Object> result = cashoutRealDubboService.findPageList(dto,getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ActCashoutRealDTO>)list : null,result.get("total"));
			}else{
				logger.error("出金信息分页api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		}catch(Exception e){
			logger.error("<---pageList->查询分页失败", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}