package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.SmsChannelEnum;
import com.gwghk.ims.common.enums.SmsSourceEnum;
import com.gwghk.ims.common.enums.SmsStatusEnum;
import com.gwghk.ims.common.excel.POIFormatConfig;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgSmsLogDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsLogVO;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.ims.mis.gateway.util.AuthColumnUtil;

/**
 * 
 * 摘要：短信日志记录Controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月2日
 */

@Controller
@RequestMapping("/mis/ims/msg/sms/log")
public class ImsMsgSmsLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImsMsgSmsLogController.class);
	
	@Autowired
	private MisImsMsgSmsLogDubboService misImsMsgSmsLogDubboService ;

	/**
	 * @api {post} /mis/ims/msg/sms/log/save 短信消息记录-保存
	 * @apiSampleRequest /mis/ims/msg/sms/log/save 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} content 短信内容
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {mobile:"xxxx",content:"xxxx"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request,@ModelAttribute ImsMsgSmsLogVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misImsMsgSmsLogDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	/**
	 * @api {post} /mis/ims/msg/sms/log/page 短信消息记录-分页查询
	 * @apiSampleRequest /mis/ims/msg/sms/log/page 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} content 短信内容
	 * @apiParam {String} channel 发送渠道
	 * @apiParam {String} status 发送状态
	 * @apiParam {String} source 来源
	 * @apiParam {String} businessNo 业务编号
	 * @apiParam {String} startTimeStr 发送时间开始
	 * @apiParam {String} endTimeStr 发送时间结束
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {mobile:"xxxx",content:"xxxx",channel:"xxxx",status:"xxxx",source:"xxxx",businessNo:"xxxx",startTimeStr:"xxxx",endTimeStr:"xxxx"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	@SuppressWarnings("unchecked")
	public MisRespResult<List<ImsMsgSmsLogVO>> pageList(HttpServletRequest request,@ModelAttribute ImsMsgSmsLogVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			Map<String,Object> result = misImsMsgSmsLogDubboService.findPageList(vo,this.getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ImsMsgSmsLogVO>)list : null,result.get("total"));
			}else{
				logger.error("短信日志api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/ims/msg/sms/log/export 短信消息记录-导出Excel
	 * @apiSampleRequest /mis/ims/msg/sms/log/export 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} content 短信内容
	 * @apiParam {String} channel 发送渠道
	 * @apiParam {String} status 发送状态
	 * @apiParam {String} source 来源
	 * @apiParam {String} businessNo 业务编号
	 * @apiParam {String} startTimeStr 发送时间开始
	 * @apiParam {String} endTimeStr 发送时间结束
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {mobile:"xxxx",content:"xxxx",channel:"xxxx",status:"xxxx",source:"xxxx",businessNo:"xxxx",startTimeStr:"xxxx",endTimeStr:"xxxx"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/export", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> exportExcel(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ImsMsgSmsLogVO vo) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(this.getClass().getResourceAsStream(DownTemplateEnum.smsLogDetail.getPath()));
			// 2、需要导出的数据
			MisRespResult<List<ImsMsgSmsLogVO>> res = misImsMsgSmsLogDubboService.findList(vo,getCompanyId());
			if(res.isNotOk()){
				return MisRespResult.error(res.getCode(), res.getMsg());
			}
			List<ImsMsgSmsLogVO> recordList = res.getData() ;
			if(null == recordList){
				recordList = new ArrayList<>() ;
			}
			warpDataList(recordList);
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<ImsMsgSmsLogVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, ImsMsgSmsLogVO param) {
					if (null != fieldValue && !"".equals(fieldValue)) {
						if (fieldName != null && "status".equals(fieldName + "")) {
							return SmsStatusEnum.getLabelByValue(fieldValue+"") ;
						}
						if (fieldName != null && "channel".equals(fieldName + "")) {
							return SmsChannelEnum.getLabelByCode(fieldValue.toString());
						}
						if (fieldName != null && "source".equals(fieldName + "")) {
							return SmsSourceEnum.getLabelByValue(fieldValue.toString());
						}
					} else if (fieldValue instanceof String) {
						fieldValue = "";
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.smsLogDetail.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null ;
	}
	
	/**
	 * 功能：列表字段特殊处理
	 */
	private void warpDataList(List<ImsMsgSmsLogVO> recordList) throws Exception {
		if (!CollectionUtils.isEmpty(recordList)) {
			AuthColumnUtil.encryptColumn(recordList, getLoginUser().getRoleId());
		}
	}
}