package com.gwghk.ims.mis.gateway.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgAppLogDubboService;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;

/**
 * 
 * 摘要：app消息记录Controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月2日
 */

@Controller
@RequestMapping("/mis/ims/msg/app/log")
public class ImsMsgAppLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImsMsgAppLogController.class);
	
	@Autowired
	private MisImsMsgAppLogDubboService misImsMsgAppLogDubboService ;

	/**
	 * @api {post} /mis/ims/msg/app/log/save app消息记录-保存
	 * @apiSampleRequest /mis/ims/msg/app/log/save 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} code 模板code
	 * @apiParam {String} msgId 消息ID
	 * @apiParam {String} recipents 接收人(多个人之间使用逗号分隔)
	 * @apiParam {String} title 标题
	 * @apiParam {String} content 内容
	 * @apiParam {Integer} contentType 模板内容类型(1:图片 2：图文 3; 其他)
	 * @apiParam {String} summary 内容摘要
	 * @apiParam {String} msgShowPosition 消息显示位置(1、小窗 2、 跑马灯 3、工具栏),多个用逗号分隔
	 * @apiParam {String} link 跳转的链接
	 * @apiParam {Date} sendTime 发送时间
	 * @apiParam {Integer} failTimes 失败的次数
	 * @apiParam {String} status 发送状态
	 * @apiParam {String} otherMsg 其他信息(json)
	 * @apiParam {String} ext1 扩展字段1
	 * @apiParam {String} pushApp 推送app(多个app之间用,分隔)
	 * @apiParam {String} pushType 推送方式(all/tag/alias/device)
	 * @apiParam {String} enableFlag Y启用N禁用
	 * @apiParam {Integer} msgType 1营销消息  2预警消息
	 * @apiParam {String} pushDevices 推送的设备  ios android pcui webui 
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {code:"xxxx",msgId:"xxxx",recipents:"xxxx",title:"xxxx",content:"xxxx",contentType:"xxxx",summary:"xxxx",msgShowPosition:"xxxx",link:"xxxx",sendTime:"xxxx",failTimes:"xxxx",status:"xxxx",otherMsg:"xxxx",ext1:"xxxx",pushApp:"xxxx",pushType:"xxxx",enableFlag:"Y"}
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
	public MisRespResult<Void> save(HttpServletRequest request,@ModelAttribute ImsMsgAppLogVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misImsMsgAppLogDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	/**
	 * @api {post} /mis/ims/msg/app/log/page app消息记录-分页查询
	 * @apiSampleRequest /mis/ims/msg/app/log/page 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} recipents 接收人(多个人之间使用逗号分隔)
	 * @apiParam {String} title 标题
	 * @apiParam {String} content 内容
	 * @apiParam {String} status 发送状态
	 * @apiParam {String} startTimeStr 发送时间开始
	 * @apiParam {String} endTimeStr 发送时间结束
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {recipents:"xxxx",title:"xxxx",content:"xxxx",status:"xxxx",startTimeStr:"2018-04-02 17:28:13",endTimeStr:"2018-04-03 17:28:13"}
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
	public MisRespResult<List<ImsMsgAppLogVO>> pageList(HttpServletRequest request,@ModelAttribute ImsMsgAppLogVO vo) {
		try {
			vo.setCompanyId(this.getCompanyId());
			Map<String,Object> result = misImsMsgAppLogDubboService.findPageList(vo,this.getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ImsMsgAppLogVO>)list : null,result.get("total"));
			}else{
				logger.error("app消息记录api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/ims/msg/app/log/resend/{id} app消息记录-重发
	 * @apiSampleRequest /mis/ims/msg/app/log/resend/1
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} ids 必填 多个,隔开
	 * 
	 * @apiParamExample {json} 请求样例 
	 * 	{ids:1,2,3}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/resend", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> resend(HttpServletRequest request,@ModelAttribute ImsMsgAppLogVO vo) {
		try {
			setPublicAttr(vo, 0);
			MisRespResult<Void> result = misImsMsgAppLogDubboService.resend(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}