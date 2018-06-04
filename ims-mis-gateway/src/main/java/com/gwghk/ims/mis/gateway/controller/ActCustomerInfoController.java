package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.gwghk.ims.common.inf.mis.activity.MisActCustomerInfoDubboService;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;
import com.gwghk.ims.mis.gateway.common.UserContext;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要： 清洗后的客户信息
 * @author cookie.lai
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/mis/act/customerinfo")
public class ActCustomerInfoController extends BaseController {
	@Autowired
    private MisActCustomerInfoDubboService misActCustomerInfoDubboService;
	
	private static final Logger logger = LoggerFactory.getLogger(ActCustomerInfoController.class);
	
    
	/**
	 * @api {get} /mis/act/customerinfo/page 分页查询
	 * @apiSampleRequest /mis/act/customerinfo/page
	 * @apiGroup ActCustomerInfoApi
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam {String} accountEnv 账号类型
	 * @apiParam {String} accountNo 账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} email 邮箱
	 * @apiParam {String} accountStatus (必填)账号状态
	 * @apiParam {String} activatedStatus 激活状态
	 * @apiParam {String} registerStartTime (必填)注册查询开始时间
	 * @apiParam {String} registerEndTime (必填)注册查询结束时间
	 * @apiParam {String} activatedStartTime (必填)激活查询开始时间
	 * @apiParam {String} activatedEndTime (必填)激活查询结束时间
	 * @apiParam {String} cancelBefore 是否注销过
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{accountNo:"xxx",registerStartTime："xxx"}
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
	public MisRespResult<List<ActCustomerInfoVO>> pageList(@ModelAttribute ActCustomerInfoVO reqDto) {
		try{
			if(StringUtil.isEmpty(reqDto.getAccountType())){
				PageR<ActCustomerInfoVO>  actCustomerInfoPage = new PageR<ActCustomerInfoVO>();
				actCustomerInfoPage.setList(new ArrayList<>());
	    		return MisRespResult.success(actCustomerInfoPage.getList(),actCustomerInfoPage.getTotal());
			}
			Long companyId = UserContext.get().getCompanyId();
			MisRespResult<Map<String, Object>> result = misActCustomerInfoDubboService.findPageList(reqDto,companyId);
			if(result.isOk()){
				Map<String, Object> map = result.getData() ;
				Object list = map.get("list");
				return MisRespResult.success(list != null ? (List<ActCustomerInfoVO>)list : null,map.get("total"));
			}
			return MisRespResult.error(result.getCode(), result.getMsg());
		}catch(Exception e){
			logger.error(">>>>ActCustomerInfoController pageList error:{}",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {get} /mis/act/customerinfo/{platform}/{accountNo}获得该账号平台信息
	 * @apiSampleRequest /actCustomerInfoController/getAccountPlatformInfo
	 * @apiGroup ActCustomerInfoApi
	 * 
	 * @apiParam {String} accountNo (必填)账号
	 * @apiParam {String} platform 平台
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
    @RequestMapping(value = "/{platform}/{accountNo}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public MisRespResult<ActCustomerInfoVO> getAccountPlatformInfo(@PathVariable("accountNo")String accountNo,@PathVariable("platform")String platform) {
    	try{
        // 根据客户账号平台，加载客户姓名、手机号,邮箱，账户类型
	    	MisRespResult<ActCustomerInfoVO> custInfo = misActCustomerInfoDubboService.findActCustomerInfo(accountNo, platform, getCompanyId()) ;
	        return custInfo ;
    	}catch(Exception e){
    		logger.error(">>>>ActCustomerInfoController getAccountPlatformInfo error:{}",e);
    		return MisRespResult.error(MisResultCode.EXCEPTION);
    	}
    }
}
