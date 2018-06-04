package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.external.activity.ActSettlementDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActAccountActiviStatDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatViewVO;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;

/**
 * @apiDefine ActAccountActivityApi 活动参与用户
 */
@Controller
@RequestMapping("/mis/act/accountactivity")
public class ActAccountActivityController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActAccountActivityController.class);

	@Autowired
	private MisActAccountActiviStatDubboService misActAccountActiviStatDubboService;
	@Autowired
	private ActSettlementDubboService actSettlementDubboService; 

	/**
	 * @api {post} /mis/act/accountactivity/page 分页查询
	 * @apiSampleRequest /mis/act/accountactivity/page
	 * @apiGroup ActAccountActivityApi
	 * 
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} custMobile 手机号
	 * @apiParam {String} platform 平台，多个逗号隔开(GTS,GTS2,MT4,MT5)
	 * @apiParam {String} realSettlementFlag 清算状态,(false未清算,true清算)
	 * @apiParam {String} whitelistFlag 白名单活动,(false否,true是)
	 * @apiParam {String} startTimeStr 活动开始时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} endTimeStr 活动结束时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {...}
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
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActAccountActiviStatViewVO>> page(@ModelAttribute ActAccountActiviStatViewVO vo) {
		try {
			// 多平台条件查询处理
			if (StringUtils.isNotBlank(vo.getPlatform()) && vo.getPlatform().indexOf(",") != -1) {
				List<String> platforms = Arrays.asList(vo.getPlatform().split(",")).stream().filter(r -> r != null)
						.distinct().collect(Collectors.toList());
				vo.setPlatforms(platforms);
				vo.setPlatform(null);
			}
			vo.setCompanyId(this.getCompanyId());
			MisRespResult<PageR<ActAccountActiviStatViewVO>> result = misActAccountActiviStatDubboService.findPageList(vo,
					vo.getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/accountactivity/manualSettlement 手动清算
	 * @apiSampleRequest /mis/act/accountactivity/manualSettlement
	 * @apiGroup ActAccountActivityApi
	 * 
	 * @apiParam {String} ids 主键ID,多个逗号隔开
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {...}
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
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/manualSettlement", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> manualSettlement(String ids) {
		try {
			if (StringUtils.isBlank(ids)) {
				return MisRespResult.error("参数错误");
			}

			// 1、校验数据是否存在、及是否已经清算
			List<String> idList = Arrays.asList(ids.split(",")).stream().filter(r -> StringUtils.isNotBlank(r))
					.collect(Collectors.toList());
			List<ActAccountActiviStatViewVO> voList = new ArrayList<>();
			for (String id : idList) {
				ActAccountActiviStatViewVO vo = misActAccountActiviStatDubboService.findViewById(Long.parseLong(id), this.getCompanyId()).getData();
				if (null != vo) {
					if (vo.getSettleStatus() != null && vo.getSettleStatus() != 0) {
						return MisRespResult.error("对应的记录已经清算");// george.li 测试要求去除id={id} 
						//return MisRespResult.error("id=" + id + "对应的记录已经清算");
					}
					voList.add(vo);
				} else {
					return MisRespResult.error("id=" + id + "对应的记录不存在");
				}
			}
			
			// 2、调用清算接口 - TODO
			for (ActAccountActiviStatViewVO vo : voList) {
				BaseVO base = new BaseVO() ;
				setPublicAttr(base, vo.getId());
				actSettlementDubboService.beginUserSettlement(ActSettleTypeEnum.BLACKLIST,vo.getAccountNo(), vo.getActNo(), vo.getPlatform(),base, vo.getCompanyId()) ;
			}
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/accountactivity/export 导出活动参与用户
	 * @apiSampleRequest /mis/act/accountactivity/export
	 * @apiGroup ActAccountActivityApi
	 * 
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} custMobile 手机号
	 * @apiParam {String} platform 平台，多个逗号隔开(GTS,GTS2,MT4,MT5)
	 * @apiParam {String} realSettlementFlag 清算状态,(false未清算,true清算)
	 * @apiParam {String} whitelistFlag 白名单活动,(false否,true是)
	 * @apiParam {String} startTimeStr 活动开始时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} endTimeStr 活动结束时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
	 * @apiParamExample {json} 请求样例 
	 *  {...}
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
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping("/export")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ActAccountActiviStatViewVO vo) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(this.getClass().getResourceAsStream(DownTemplateEnum.ActAccountStatList.getPath()));
			// 2、需要导出的数据,账号查询，完成手数需补充统计
			// TODO 当前用户标签-tagCodes

			// 多平台条件查询处理
			if (StringUtils.isNotBlank(vo.getPlatform()) && vo.getPlatform().indexOf(",") != -1) {
				List<String> platforms = Arrays.asList(vo.getPlatform().split(",")).stream().filter(r -> r != null)
						.distinct().collect(Collectors.toList());
				vo.setPlatforms(platforms);
				vo.setPlatform(null);
			}
			vo.setCompanyId(this.getCompanyId());
			List<ActAccountActiviStatViewVO> recordList = misActAccountActiviStatDubboService
					.findList(vo, vo.getCompanyId()).getData();

			// 3、格式化与导出
			if (null == recordList) {
				recordList = new ArrayList<>();
			}
			builder.setList(recordList, (n, v, o) -> {
				// 清算状态(1: 未清算 2:清算中 3:清算成功 4:清算失败 )
				if (n.equals("settleStatus")) {
					if (null != v && !"1".equals(v+"")) {
						return "已完成";
					} else {
						return "未完成";
					}
				}
				// 清算方式(1:人工清算 2:自动清算)
				else if (n.equals("settleMode")) {
					if (null != v && "1".equals(v+"")) {
						return "人工清算";
					} else {
						return "自动清算";
					}
				}
				return v;
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.ActAccountStatList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("<----系统出现异常:", e);
		}
	}
}
