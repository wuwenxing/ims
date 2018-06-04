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
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgBindDubboService;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;

/**
 * 摘要：消息模板绑定Controller
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月2日
 */
@Controller
@RequestMapping("/mis/ims/msg/bind")
public class ImsMsgBindController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImsMsgBindController.class);
	
	@Autowired
	private MisImsMsgBindDubboService misImsMsgBindDubboService ;

	/**
	 * @api {post} /mis/ims/msg/bind/save 消息模板绑定-保存
	 * @apiSampleRequest /mis/ims/msg/bind/save 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} bindCode 绑定编号
	 * @apiParam {String} smsCode 短信模板code
	 * @apiParam {String} appCode app模板code
	 * @apiParam {String} bindType 绑定类型  item(物品) | activity(活动)
	 * @apiParam {String} ext1 扩展字段1
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {bindCode:"xxxx",smsCode:"xxxx",appCode:"xxxx",bindType:"xxxx",ext1:"xxxx",enableFlag:"Y"}
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
	public MisRespResult<Void> save(HttpServletRequest request,@ModelAttribute ImsMsgBindVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misImsMsgBindDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	/**
	 * @api {post} /mis/ims/msg/bind/page 消息模板绑定-分页查询
	 * @apiSampleRequest /mis/ims/msg/bind/page 
	 * @apiGroup MarketingToolApi
	 * 
	 * @apiParam {String} bindCode 绑定编号
	 * @apiParam {String} smsCode 短信模板code
	 * @apiParam {String} appCode app模板code
	 * @apiParam {String} bindType 绑定类型  item(物品) | activity(活动)
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {bindCode:"xxxx",smsCode:"xxxx",appCode:"xxxx",bindType:"xxxx",enableFlag:"Y"}
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
	public MisRespResult<List<ImsMsgBindVO>> pageList(HttpServletRequest request,@ModelAttribute ImsMsgBindVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			Map<String,Object> result = misImsMsgBindDubboService.findPageList(vo,this.getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ImsMsgBindVO>)list : null,result.get("total"));
			}else{
				logger.error("消息绑定api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/ims/msg/bind/delete/{id} 消息模板绑定-删除
	 * @apiSampleRequest /mis/ims/msg/bind/delete/1
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
			MisRespResult<Void> result = misImsMsgBindDubboService.deleteByIds(id,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
}