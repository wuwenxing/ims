package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActStringCodeStatusEnum;
import com.gwghk.ims.common.excel.POIFormatConfig;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.mis.activity.MisActStringCodeDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.vo.activity.ActStringCodeVO;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.ims.mis.gateway.util.AuthColumnUtil;
import com.gwghk.ims.mis.gateway.util.CollectionsUtil;
import com.gwghk.unify.framework.common.util.DESEncryptUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * 摘要：物品管理controller
 *
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月30日
 */
@Controller
@RequestMapping("/mis/act/stringcode")
public class ActStringCodeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActStringCodeController.class);

	@Autowired
	private MisActStringCodeDubboService misActStringCodeDubboService;

	/**
	 * @api {post} /mis/act/stringcode/page 分页查询
	 * @apiSampleRequest /mis/act/stringcode/page
	 * @apiGroup ActStringCodeApi
	 * @apiParam {String} itemNumber 物品编号(精确查询)
	 * @apiParam {String} itemName 物品名称(精确查询)
	 * @apiParam {Integer} status 串码状态(1未使用\2已使用)
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {itemNumber:"xxx",itemName:"xxx",status:1}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
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
	public MisRespResult<List<ActStringCodeVO>> pageList(@ModelAttribute ActStringCodeVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<PageR<ActStringCodeVO>> result = misActStringCodeDubboService.findPageList(vo,
					vo.getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/stringcode/save 新增或修改串码
	 * @apiSampleRequest /mis/act/stringcode/save
	 * @apiGroup ActStringCodeApi
	 * 
	 * @apiParam {Long} id 串码id (设置id值则根据id更新，否则为新增)
	 * @apiParam {String} itemNumber 所属的物品编号
	 * @apiParam {String} stringCode 串码
	 * @apiParam {Integer} status 串码状态(1未使用\2已使用)
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} taskTitle 任务标题
	 * @apiParam {String} accountNo 账号
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {itemNumber:"A10001",stringCode:"E001",status:1}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 * 
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute ActStringCodeVO vo) {
		try {
			this.setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misActStringCodeDubboService.saveOrUpdate(vo, vo.getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/stringcode/delete/:ids 删除串码
	 * @apiSampleRequest /mis/act/stringcode/delete/1001,1002
	 * @apiGroup ActStringCodeApi
	 *
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/delete/{ids}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> deleteByIdArray(@PathVariable String ids) {
		try {
			MisRespResult<Void> result = misActStringCodeDubboService.deleteByIds(ids, getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/stringcode/:stringCode 根据串码编号查询串码
	 * @apiSampleRequest /mis/act/stringcode/E1001
	 * @apiGroup ActStringCodeApi
	 * 
	 *
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/{stringCode}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<ActStringCodeVO> findByStringCode(@PathVariable String stringCode) {
		try {
			MisRespResult<ActStringCodeVO> result = misActStringCodeDubboService.findByStringCode(stringCode,
					getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/stringcode/count 获得串码个数
	 * @apiSampleRequest /mis/act/stringcode/count
	 * @apiGroup ActStringCodeApi
	 *
	 * @apiParam {String} itemNumber 所属的物品编号
	 * @apiParam {Integer} status 串码状态(1未使用\2已使用)
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/count", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Integer> getStringCodeCount(@ModelAttribute ActStringCodeVO reqVo) {
		try {
			reqVo.setCompanyId(getCompanyId());
			MisRespResult<Integer> result = misActStringCodeDubboService.getStringCodeCount(reqVo,
					reqVo.getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/stringcode/upload/:itemNumber 批量上传串码
	 * @apiSampleRequest /mis/act/stringcode/upload/E1001
	 * @apiGroup ActStringCodeApi
	 * 
	 * @apiParam {CommonsMultipartFile} file 串码文件
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Integer} data 返回新增条数
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/upload/{itemNumber}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Integer> batchUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@PathVariable String itemNumber) {
		try {
			double megabytes = file.getSize() / (1024 * 1024);
			if (megabytes <= 5) {
				List<List<Object>> result = ExcelUtil.readExcelByInputStream(file.getInputStream(),
						file.getOriginalFilename());
				if (result == null || result.isEmpty()) {
					return new MisRespResult<Integer>(MisResultCode.Error40008);
				} else {
					// 查找所有的串码编号，判断串码编号系统唯一
					List<String> stringCodes = null;
					MisRespResult<List<String>> reqResult = misActStringCodeDubboService.findAllStringCodes(getCompanyId());
					if (reqResult.isOk()) {
						stringCodes = reqResult.getData();
					} else {
						String msg = String.format("加载系统串码列表失败!", 0);
						return MisRespResult.error(msg);
					}
					Set<String> fileKeys = new HashSet<String>();// 当前上传文件数据key,
					List<String> stringCodeList = new ArrayList<String>();// 需要新增的串码
					for (int i = 0; i < result.size(); i++) {
						List<Object> item = result.get(i);
						if (item.size() < 1) {
							String msg = String.format("上传失败:第%s行错误，串码编号为空!", i + 2);
							return MisRespResult.error(msg);
						}
						Object stringCodeObj = item.get(0);
						if (stringCodeObj == null || StringUtil.isNullOrEmpty(stringCodeObj.toString())) {
							String msg = String.format("上传失败:第%s行错误，串码编号为空!", i + 2);
							return MisRespResult.error(msg);
						}
						if (stringCodeObj instanceof String == false) {
							String msg = String.format("上传失败:第%s行错误，串码编号格式错误，需为文本格式!", i + 2);
							return MisRespResult.error(msg);
						}
						String stringCode = stringCodeObj.toString().trim();
						if (fileKeys.contains(stringCode)) {
							String msg = String.format("上传失败:第%s行错误，当前上传文件中存在相同的串码编号!", i + 2);
							return MisRespResult.error(msg);
						} else {
							String encryptStringCode = new DESEncryptUtil().encryptString(stringCode);// 加密
							fileKeys.add(stringCode);
							if (CollectionsUtil.isEmpty(stringCodes) || !stringCodes.contains(encryptStringCode)) {// 当前系统中不存在
								stringCodeList.add(stringCode);
							}
						}
					}
					if (stringCodeList.size() == 0) {
						return MisRespResult.error("上传失败:上传的串码系统中都已存在，此次没有新增串码，无需上传!");
					}
					return misActStringCodeDubboService.batchAdd(stringCodeList, itemNumber, getCompanyId());
				}
			} else {
				return new MisRespResult<Integer>(MisResultCode.Error40009);
			}
		} catch (Exception e) {
			logger.error("<---batchUpload->file upload fail !", e);
		}
		return new MisRespResult<Integer>(MisResultCode.FAIL);
	}

	 

	/**
	 * @api {post} /mis/act/stringcode/export 导出串码
	 * @apiSampleRequest /mis/act/stringcode/export
	 * @apiGroup ActStringCodeApi
	 * 
	 * @apiParam {String} itemNumber 物品编号
	 * @apiParam {String} itemName 物品名称
	 * @apiParam {Integer} status 串码状态(1未使用\2已使用)
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Integer} data 返回新增条数
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/export", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ActStringCodeVO reqVo) {
		try {
			// 1、导出工具类    
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(this.getClass().getResourceAsStream(DownTemplateEnum.actStringCodeList.getPath()));
			// 2、需要导出的数据
			MisRespResult<List<ActStringCodeVO>> listResult = misActStringCodeDubboService.findList(reqVo,reqVo.getCompanyId());
			if (listResult.isOk()) {
				List<ActStringCodeVO> recordList = listResult.getData();
				if(CollectionUtils.isNotEmpty(recordList)){
					AuthColumnUtil.encryptColumn(recordList, getLoginUser().getRoleId()); 
				}
				// 3、格式化与导出
				builder.setList(recordList, new POIFormatConfig<ActStringCodeVO>() {
					@Override
					public Object fromatValue(String fieldName, Object fieldValue, ActStringCodeVO param) {
						if (null != fieldValue) {
							if (fieldName != null && "status".equals(fieldName)) {
								fieldValue = ActStringCodeStatusEnum.getNameByValue(Integer.parseInt(fieldValue.toString()));
							}
						}
						if (fieldValue == null) {
							return "";
						}
						return fieldValue;
					}
				});
				builder.put("totalRecordSize", recordList.size());
				builder.createSXSSFWb();
				// 对response处理使其支持Excel
				ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.actStringCodeList.getValue(), request, response);
				builder.write(response.getOutputStream());
			}
		} catch (Exception e) {
			logger.error("export->导出串码列表失败!", e);
		}
	}
}