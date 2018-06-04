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
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgSmsTplDubboService;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsTplVO;

/**
 * 
 * 摘要：短信模板Controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月2日
 */

@Controller
@RequestMapping("/mis/ims/msg/sms/tpl")
public class ImsMsgSmsTplController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImsMsgSmsTplController.class);
	
	@Autowired
	private MisImsMsgSmsTplDubboService misImsMsgSmsTplDubboService ;

	/**
	 * @api {post} /mis/ims/msg/sms/tpl/save 短信消息模板-保存
	 * @apiSampleRequest /mis/ims/msg/sms/tpl/save 
	 * @apiGroup MarketToolApi
	 * 
	 * @apiParam {String} code 代码
	 * @apiParam {String} lang 语言
	 * @apiParam {String} tplName 模板名称
	 * @apiParam {String} tplContent 模板内容
	 * @apiParam {String} sendUrl 发送地址,多个地址之间用逗号分隔
	 * @apiParam {String} remark 备注
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {code:"xxxx",lang:"xxxx",tplName:"xxxx",tplContent:"xxxx",sendUrl:"xxxx",remark:"xxxx",enableFlag:"Y"}
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
	public MisRespResult<Void> save(HttpServletRequest request,@ModelAttribute ImsMsgSmsTplVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misImsMsgSmsTplDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	/**
	 * @api {post} /mis/ims/msg/sms/tpl/page 短信消息模板-分页查询
	 * @apiSampleRequest /mis/ims/msg/sms/tpl/page 
	 * @apiGroup MarketToolApi
	 * 
	 * @apiParam {String} code 代码
	 * @apiParam {String} lang 语言
	 * @apiParam {String} tplName 模板名称
	 * @apiParam {String} tplContent 模板内容
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {code:"xxxx",lang:"xxxx",tplName:"xxxx",tplContent:"xxxx",enableFlag:"Y"} * 
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
	public MisRespResult<List<ImsMsgSmsTplVO>> pageList(HttpServletRequest request,@ModelAttribute ImsMsgSmsTplVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			Map<String,Object> result = misImsMsgSmsTplDubboService.findPageList(vo,this.getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ImsMsgSmsTplVO>)list : null,result.get("total"));
			}else{
				logger.error("短信模板api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/ims/msg/sms/tpl/delete/{id} 短信消息模板-删除
	 * @apiSampleRequest /mis/ims/msg/sms/tpl/delete/1
	 * @apiGroup MarketingToolApi
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
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> delete(HttpServletRequest request,@PathVariable String id) {
		try {
			MisRespResult<Void> result = misImsMsgSmsTplDubboService.deleteByIds(id,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/ims/msg/app/tpl/list 短信消息模板-查询列表
	 * @apiSampleRequest /mis/ims/msg/app/tpl/list
	 * @apiGroup MarketingToolApi
	 * 
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
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<List<ImsMsgSmsTplVO>> listCode(@ModelAttribute ImsMsgSmsTplVO vo) {
		try {
			MisRespResult<List<ImsMsgSmsTplVO>> result = misImsMsgSmsTplDubboService.findList(vo, getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}