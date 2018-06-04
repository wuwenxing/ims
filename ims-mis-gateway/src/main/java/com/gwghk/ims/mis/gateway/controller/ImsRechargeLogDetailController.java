package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gwghk.ims.common.common.Constants;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.enums.RechargeTypeEnum;
import com.gwghk.ims.common.enums.TableEnum;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsRechargeLogDetailDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.ims.mis.gateway.util.AuthColumnUtil;
import com.gwghk.ims.mis.gateway.util.IPUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * 摘要：流量、话费记录Controller
 * @apiDefine MarketToolApi 营销工具
 *
 * @author 流量、话费记录
 * @version 1.0
 * @Date 2018年4月2日
 */
@Controller
@RequestMapping("/mis/recharge")
public class ImsRechargeLogDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImsRechargeLogDetailController.class);

	@Autowired
	private MisImsRechargeLogDetailDubboService  misImsRechargeLogDetailDubboService;
	
	/**
	 * @api {post} /mis/recharge/page 分页查询流量、话费记录
	 * @apiSampleRequest /mis/recharge/page
	 * @apiGroup MarketToolApi
	 * 
	 * @apiParam {String} rechargeType 充值类型(flow/online)
	 * @apiParam {String} phone 充值手机号
	 * @apiParam {String} phoneArea 手机归属地
	 * @apiParam {String} rechargeSize 充值大小
	 * @apiParam {String} interfaceType 通道类型
	 * @apiParam {String} resBatchNo 返回批次号
	 * @apiParam {String} resCode 返回code
	 * @apiParam {String} commitStatus 提交状态
	 * @apiParam {String} sendStatus 充值状态
	 * @apiParam {String} ext1 扩展字段1
	 * @apiParam {String} ext2 扩展字段2
	 * @apiParam {String} ext3 扩展字段3
	 * @apiParam {String} startTimeStr 查询开始时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} endTimeStr 查询结束时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {...}
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
	public MisRespResult<List<ImsRechargeLogDetailVO>> pageList(HttpServletRequest request,@ModelAttribute ImsRechargeLogDetailVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			Map<String, Object> result = misImsRechargeLogDetailDubboService.findPageList(vo,this.getCompanyId());
			List<ImsRechargeLogDetailVO> list = (List<ImsRechargeLogDetailVO>)result.get("list");
			return MisRespResult.success(list, result.get("total"));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/recharge/send 流量、话费充值
	 * @apiSampleRequest /mis/recharge/send
	 * @apiGroup MarketToolApi
	 * 
	 * @apiParam {String} rechargeType （必填）充值类型(流量-flow/话费-online)
	 * @apiParam {String} phone （必填）充值手机号-11位
	 * @apiParam {String} rechargeSize （必填）充值大小-(流量单位都是M,只需传数值),(话费单位都是元,只需传数值)
	 * @apiParam {String} interfaceType （必填）通道类型-(欧飞通道-of)(...)
	 * @apiParam {String} ext1 扩展字段1
	 * @apiParam {String} ext2 扩展字段2 回调表名
	 * @apiParam {String} ext3 扩展字段3 回调表主键ID
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {...}
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
	@RequestMapping(value = "/send", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Map<String, Object>> send(HttpServletRequest request, @ModelAttribute ImsRechargeLogDetailVO vo) {
		try {
			if(null == vo
					||StringUtils.isBlank(vo.getRechargeType())
					|| StringUtils.isBlank(vo.getPhone()) 
					|| StringUtils.isBlank(vo.getRechargeSize()) 
					|| StringUtils.isBlank(vo.getInterfaceType()) 
					){
				return MisRespResult.error("参数错误");
			}
			this.setPublicAttr(vo, vo.getDetailId());
			return misImsRechargeLogDetailDubboService.send(vo, vo.getCompanyId());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：充值接口回调-欧飞话费及流量
	 */
	@RequestMapping(value = "/callbackFromOf", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> callbackFromOf(HttpServletRequest request, String rechargeType, String interfaceType) {
		String ret_code = request.getParameter("ret_code");
		String sporder_id = request.getParameter("sporder_id");
		String ordersuccesstime = request.getParameter("ordersuccesstime");
		String err_msg = request.getParameter("err_msg");
		logger.info("-->ret_code={},sporder_id={},ordersuccesstime={},err_msg={}", new Object[]{ret_code, sporder_id, ordersuccesstime, err_msg});
		try {
			return this.callbackFromOF(ret_code, sporder_id, ordersuccesstime, err_msg, IPUtil.getIp());
		} catch (Exception e) {
			logger.error("-->>欧飞话费回调失败！ret_code={}, sporder_id={}, err:{}"
					, new Object[]{ret_code, sporder_id, e});
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：欧飞话费及流量回调
	 */
	private MisRespResult<Void> callbackFromOF(String retCode, String sporderId, String orderSuccessTime, String errMsg, String ip) {
		try {
			if (StringUtils.isBlank(sporderId)) {
				logger.error("-->欧飞回调失败,sporderId为空");
				return MisRespResult.error("回调失败,sporderId为空");
			}
			// 返回的sporderId对应主键ID
			Long companyId = null;
			Long detailId = null;
			if(sporderId.startsWith(Constants.onlinePrefix)){
				String[] temp = sporderId.split("_");
				if(temp.length == 2){
					companyId = Long.parseLong(temp[1]);
					detailId = Long.parseLong(temp[2]);
				}else{
					logger.error("-->欧飞话费回调失败,sporderId有误");
					return MisRespResult.error("回调失败,sporderId有误");
				}
			}else if(sporderId.startsWith(Constants.flowPrefix)){
				String[] temp = sporderId.split("_");
				if(temp.length == 2){
					companyId = Long.parseLong(temp[1]);
					detailId = Long.parseLong(temp[2]);
				}else{
					logger.error("-->欧飞流量回调失败,sporderId有误");
					return MisRespResult.error("回调失败,sporderId有误");
				}
			}else{
				// 旧方式无前缀
				detailId = Long.parseLong(sporderId);
			}
			ImsRechargeLogDetailVO detailVO = misImsRechargeLogDetailDubboService.findById(detailId, companyId).getData();
			// 1代表成功，9代表撤消即失败
			// err_msg 失败原因(ret_code为1时，该值为空)
			if ("1".equals(retCode)) {
				logger.error("-->欧飞回调成功,充值成功,sporderId={}", sporderId);
				detailVO.setSendStatus("sendSuccess");
			} else {
				logger.error("-->欧飞回调成功,但充值失败,sporderId={}, errMsg={}", sporderId, errMsg);
				detailVO.setSendStatus("sendFail");
				detailVO.setRemark(errMsg);
			}
			detailVO.setUpdateDate(new Date());
			detailVO.setUpdateIp(ip);
			misImsRechargeLogDetailDubboService.saveOrUpdate(detailVO, companyId);
			// 更新发放记录状态-TODO
			String tableName = detailVO.getExt2();
			String tableId = detailVO.getExt3();
			if(StringUtils.isNotBlank(tableName) && 
					TableEnum.act_prize_record_.getCode(detailVO.getCompanyId()).equals(tableName)){
				if(StringUtils.isNotBlank(tableId)){
//					ActPrizeRecordVO actPrizeRecord = actPrizeRecordMapper.selectById(Long.parseLong(tableId), CompanyEnum.getCodeById(detailVO.getCompanyId()));
//					actPrizeRecord.setGiftPrice(detailVO.getPrice());
//					if(RechargeStatusEnum.sendSuccess.getLabelKey().equals(detailVO.getSendStatus())){
//						actPrizeRecord.setIssuingStatus(IssuingStatusEnum.issue_success.getValue());
//					}else{
//						actPrizeRecord.setIssuingStatus(IssuingStatusEnum.issue_fail.getValue());
//					}
//					actPrizeRecord.setUpdateDate(detailVO.getUpdateDate());
//					actPrizeRecord.setUpdateIp(detailVO.getUpdateIp());
//					actPrizeRecord.setCompanyCode(CompanyEnum.getCodeById(detailVO.getCompanyId()));
//					actPrizeRecordMapper.updateByPrimaryKey(actPrizeRecord);
					logger.error("-->欧飞回调成功-发放记录表");
				}
			}
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("-->欧飞Callback" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/recharge/export 导出充值记录
	 * @apiGroup MarketToolApi
	 * 
	 * @apiParam {String} rechargeType 充值类型(flow/online)
	 * @apiParam {String} phone 充值手机号
	 * @apiParam {String} phoneArea 手机归属地
	 * @apiParam {String} rechargeSize 充值大小
	 * @apiParam {String} interfaceType 通道类型
	 * @apiParam {String} resBatchNo 返回批次号
	 * @apiParam {String} resCode 返回code
	 * @apiParam {String} commitStatus 提交状态
	 * @apiParam {String} sendStatus 充值状态
	 * @apiParam {String} ext1 扩展字段1
	 * @apiParam {String} ext2 扩展字段2
	 * @apiParam {String} ext3 扩展字段3
	 * @apiParam {String} startTimeStr 查询开始时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} endTimeStr 查询结束时间,格式yyyy-MM-dd HH:mm:ss
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {...}
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
	@RequestMapping(value = "/export", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ImsRechargeLogDetailVO vo) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(this.getClass().getResourceAsStream(DownTemplateEnum.rechargeDetail.getPath()));
			// 2、需要导出的数据
			vo.setCompanyId(getCompanyId());
			MisRespResult<List<ImsRechargeLogDetailVO>> listResult = misImsRechargeLogDetailDubboService.findList(vo, this.getCompanyId());
			if (listResult.isOk()) {
				List<ImsRechargeLogDetailVO> recordList = listResult.getData();
				AuthColumnUtil.encryptColumn(recordList, this.getLoginUser().getRoleId());
				// 3、格式化与导出
				builder.setList(recordList, (n, v, o) -> {
					if ("enableFlag".equals(n)) {
						if ("Y".equals(v+"")) {
							return "启用";
						} else {
							return "禁用";
						}
					}if ("rechargeSize".equals(n)) {
						if (RechargeTypeEnum.online.getCode().equals(o.getRechargeType())) {
							return v + "元";
						}
					} else if ("companyId".equals(n+"")) {
						return CompanyEnum.findById(v).getName();
					}
					return v;
				});
				builder.put("totalRecordSize", recordList.size());
				builder.createSXSSFWb();
				// 对response处理使其支持Excel
				ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.rechargeDetail.getValue(), request, response);
				builder.write(response.getOutputStream());
			}
		} catch (Exception e) {
			logger.error("export->导出失败!", e);
		}
	}
	
	/**
	 * @api {post} /mis/recharge/upload/ 批量上传充值记录
	 * @apiSampleRequest /mis/recharge/upload/
	 * @apiGroup MarketToolApi
	 * 
	 * @apiParam {MultipartFile} file 充值记录文件
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Integer} data 返回新增条数
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 
	 * { "resultCode": "0", "resultMsg": "请求成功","data": {...}, "extendData": null }
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Integer> batchUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file
			) {
		try {
			double megabytes = file.getSize() / (1024 * 1024);
			if (megabytes <= 5) {
				List<List<Object>> result = ExcelUtil.readExcelByInputStream(file.getInputStream(), file.getOriginalFilename());
				if (result == null || result.isEmpty()) {
					return new MisRespResult<Integer>(MisResultCode.Error40008);
				} else {
				
					List<ImsRechargeLogDetailVO> imsRechargeLogList = new ArrayList<ImsRechargeLogDetailVO>();// 需要充值的对象
					for (int i = 0; i < result.size(); i++) {
						List<Object> item = result.get(i);
						if (item.size() < 1) {
							String msg = String.format("上传失败:第%s行错误，充值类型为空!",i + 2);
							return errorFailResult(msg);
						}
						
						//充值大小
					    String rechargeSize="";
					    //充值类型
						Object rechargeType = item.get(0);
						if (rechargeType == null|| StringUtil.isNullOrEmpty(rechargeType.toString())) {
							String msg = String.format("上传失败:第%s行错误，充值类型为空!",i + 2);
							return errorFailResult(msg);
						}
						if (rechargeType instanceof String == false) {
							String msg = String.format("上传失败:第%s行错误，充值类型格式错误，需为文本格式!", i + 2);
							return errorFailResult(msg);
						}
					 
						//话费充值
						if(rechargeType.equals(RechargeTypeEnum.online.getCode())) {
							Object online = item.get(3);
							if (online == null|| StringUtil.isNullOrEmpty(online.toString())) {
								String msg = String.format("上传失败:第%s行错误，充值金额为空!",i + 2);
								return errorFailResult(msg);
							}else if(StringUtil.isNumeric(online.toString())) {
								String msg = String.format("上传失败:第%s行错误，充值金额非数字!",i + 2);
								return errorFailResult(msg);
							}
							rechargeSize=online.toString();
							
						}else if(rechargeType.equals(RechargeTypeEnum.flow.getCode())) {
							Object flow = item.get(4);
							if (flow == null|| StringUtil.isNullOrEmpty(flow.toString())) {
								String msg = String.format("上传失败:第%s行错误，流量数额为空!",i + 2);
								return errorFailResult(msg);
							}else if(StringUtil.isNumeric(flow.toString())) {
								String msg = String.format("上传失败:第%s行错误，流量数额非数字!",i + 2);
								return errorFailResult(msg);
							}
							rechargeSize=flow.toString();
						}
						
						//通道类型
						Object interfaceType = item.get(1);
						if (interfaceType == null|| StringUtil.isNullOrEmpty(interfaceType.toString())) {
							String msg = String.format("上传失败:第%s行错误，通道类型为空!",i + 2);
							return errorFailResult(msg);
						}
						if (rechargeType instanceof String == false) {
							String msg = String.format("上传失败:第%s行错误，通道类型格式错误，需为文本格式!", i + 2);
							return errorFailResult(msg);
						}
						
						if (rechargeType.equals(RechargeChannelEnum.of.getLabelKey())) {
							if (!CompanyEnum.hx.getId().equals(this.getCompanyId().toString())) {
								String msg = String.format("上传失败:第%s行错误，欧飞通道目前只有HX使用!", i + 2);
								return errorFailResult(msg);
							}
						}
						
						
						//充值手机号码
						Object phone = item.get(2);
						if (phone == null|| StringUtil.isNullOrEmpty(phone.toString())) {
							String msg = String.format("上传失败:第%s行错误，手机号为空!",i + 2);
							return errorFailResult(msg);
						}					
						if (phone instanceof String == false) {
							String msg = String.format("上传失败:第%s行错误，充值类型格式错误，需为文本格式!", i + 2);
							return errorFailResult(msg);
						}
						
						ImsRechargeLogDetailVO imsRechargeLogDetailVO=new ImsRechargeLogDetailVO();
						imsRechargeLogDetailVO.setPhone(phone.toString());
						imsRechargeLogDetailVO.setInterfaceType(interfaceType.toString());
						imsRechargeLogDetailVO.setRechargeSize(rechargeSize);
						imsRechargeLogDetailVO.setRechargeType(rechargeType.toString());
						this.setPublicAttr(imsRechargeLogDetailVO, imsRechargeLogDetailVO.getDetailId());
						imsRechargeLogList.add(imsRechargeLogDetailVO);
					}
					if (imsRechargeLogList.size() == 0) {
						return errorFailResult("上传失败:上传的数据为空，无需上传!");
					}
					
					return misImsRechargeLogDetailDubboService.batchSend(imsRechargeLogList, getCompanyId());
				 
				}
			} else {
				return new MisRespResult<Integer>(MisResultCode.Error40009);
			}
		} catch (Exception e) {
			logger.error("<---batchUpload->file upload fail !", e);
		} 
		return new MisRespResult<Integer>(MisResultCode.FAIL);
	}
	
	private MisRespResult<Integer> errorFailResult(String msg) {
		MisRespResult<Integer> erroResult = new MisRespResult<Integer>(MisResultCode.FAIL.getMessage());
		erroResult.setMsg(msg);
		logger.error(msg);
		return erroResult;
	}
	
	
}