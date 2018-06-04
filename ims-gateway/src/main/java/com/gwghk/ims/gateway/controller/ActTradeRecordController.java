package com.gwghk.ims.gateway.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActTradeRecordDTO;
import com.gwghk.ims.common.inf.external.activity.ActTradeRecordDubboService;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：清洗后的交易数据controller
 * @author cookie.lai
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/act/traderecord")
public class ActTradeRecordController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ActTradeRecordController.class);
    
	@Autowired
    private ActTradeRecordDubboService actTradeRecordDubboService;

	
	/**
	 * @api {get} /act/traderecord/page 分页查询
	 * @apiSampleRequest /act/traderecord/page 
	 * @apiGroup ActTradeRecordApi
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam {String} accountNo 账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} tradeType (必填)开/平仓
	 * @apiParam {String} positionId 持仓id
	 * @apiParam {String} product 产品
	 * @apiParam {String} env (必填)账号类型
	 * @apiParam {String} tradeStartTime (必填)创建开始时间
	 * @apiParam {String} tradeEndTime (必填)创建结束时间
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
	public MisRespResult<List<ActTradeRecordDTO>> pageList(@ModelAttribute ActTradeRecordDTO dto) {
		try{
			if(StringUtil.isEmpty(dto.getTradeStartTime()) || StringUtil.isEmpty(dto.getTradeEndTime()) || StringUtil.isEmpty(dto.getEnv())){
				PageR<ActTradeRecordDTO> tradeRecordPage = new PageR<ActTradeRecordDTO>();
				tradeRecordPage.setList(new ArrayList<>());
	    		return MisRespResult.success(tradeRecordPage.getList(),tradeRecordPage.getTotal());
			}
			dto.setAccountNo(StringUtil.isBlank(dto.getAccountNo()) ? null : dto.getAccountNo());
			if(dto.getSort().equals("closeTime") || dto.getSort().equals("openTime")){
				dto.setSort("tradeTime");
			}
			Long companyId = getCompanyId();
			MisRespResult<PageR<ActTradeRecordDTO>> result = actTradeRecordDubboService.findPageList(dto,companyId);
			return MisRespResult.success(result.getData().getList(),result.getData().getTotal());
		}catch(Exception e){
			logger.error(">>>>>>ActTradeRecordController pageList error:{}",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
