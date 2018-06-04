package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.Constants;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.sys.MisSystemLogDubboService;
import com.gwghk.ims.common.vo.system.SystemLogVO;

/**
 * @apiDefine SystemLogApi 系统日志
 */
@Controller
@RequestMapping("/mis/system/log")
public class SystemLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogController.class);
	
	@Autowired
	private MisSystemLogDubboService misSystemLogDubboService;
	
	/**
	 * @api {post} /mis/system/log/page 分页查询
	 * @apiSampleRequest /mis/system/log/page
	 * @apiGroup SystemLogApi
	 * 
	 * @apiParam {String} userNo 用户No
	 * @apiParam {String} logType 操作
	 * @apiParam {String} startTime 查询开始时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} endTime 查询结束时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{userNo:"...",userName:"..."}
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
	@RequestMapping(value = "/page", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<SystemLogVO>> page(HttpServletRequest request,
			@ModelAttribute SystemLogVO vo) {
		try {
			if(Constants.superAdmin.equals(this.getUserNo())){
				vo.setCompanyId(null);//超级管理员，查询全部
			}else{
				vo.setCompanyId(this.getCompanyId());
			}
			MisRespResult<PageR<SystemLogVO>> result = misSystemLogDubboService.findPageList(vo);
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}