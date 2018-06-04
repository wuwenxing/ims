package com.gwghk.ims.mis.gateway.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.ActStringCodeStatusEnum;
import com.gwghk.ims.common.excel.POIFormatConfig;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.mis.activity.MisActBlackListDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;
import com.gwghk.ims.mis.gateway.common.UserContext;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.ims.mis.gateway.util.AuthColumnUtil;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.gwghk.unify.framework.file.UploadFileInfo;
import com.gwghk.unify.framework.file.fastdfs.FastDFSManager;

/**
 * 
 * 摘要：活动黑名单controller
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月29日
 */

@Controller
@RequestMapping("/mis/act/blacklist")
public class ActBlackListController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActBlackListController.class);
	
	@Autowired
	private MisActBlackListDubboService misActBlackListDubboService ;

	/**
	 * @api {post} /mis/act/blacklist/save 活动黑名单-保存
	 * @apiSampleRequest /mis/act/blacklist/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 任务编号
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} mobile 手机账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} env 真实或模拟账号  real|demo
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"A10000001",accountNo:"1000021",mobile:"13800138000",platform:"GTS"}
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
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(@ModelAttribute ActBlackListVO vo) {
		try {
			setPublicAttr(vo, vo.getId());
			if(vo.getId()==null) {
				vo.setProposalStatus(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode());
			}else {
				vo.setApprover(UserContext.get().getLoginName());
				vo.setApproveDate(new Date());
			}
			if(CollectionUtils.isNotEmpty(misActBlackListDubboService.findList(vo,getCompanyId()).getData())){
				return MisRespResult.error(MisResultCode.Act21003);
			}
			MisRespResult<Void> result = misActBlackListDubboService.saveOrUpdate(vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/blacklist/save 活动黑名单-批量审批
	 * @apiSampleRequest /mis/act/blacklist/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 任务编号
	 * @apiParam {String} ids 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"A10000001",id:"1000021"}
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
	@RequestMapping(value = "/proposal/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> proposal(@PathVariable String id) {
		try {
			ActBlackListVO vo=new ActBlackListVO();
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misActBlackListDubboService.proposal(id, vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/blacklist/save 活动黑名单-批量取消
	 * @apiSampleRequest /mis/act/blacklist/cancel
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} ids 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"A10000001",id:"1000021"}
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
	@RequestMapping(value = "/cancel/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> cancel(@PathVariable String id) {
		try {
			ActBlackListVO vo=new ActBlackListVO();
			setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misActBlackListDubboService.cancel(id, vo,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	/**
	 * @api {post} /mis/act/blacklist/batch/save  活动黑名单-批量保存
	 * @apiSampleRequest /mis/act/blacklist/batch/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 任务编号，如果是新增则为空
	 * @apiParam {String} file 文件对象
	 * @apiParam {String} compareFilePath 对比的文件路径，eg:上传黑名单，则传白名单文件路径
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"A000001",compareFilePath:"http://xxxxx.com/111.xls",file:"Object"}
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
	@RequestMapping(value = "/batch/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<UploadFileInfo> batchSave(@ModelAttribute ActBlackListVO vo,@RequestParam("file") MultipartFile file,@RequestParam(name="compareFilePath",required=false)String compareFilePath,@RequestParam(name="activityPeriods",required=false)String activityPeriods) {
		try {
			MisRespResult<UploadFileInfo> res = new MisRespResult<>() ;
			setPublicAttr(vo, vo.getId());
			List<List<Object>> accountList = ExcelUtil.readExcelByInputStream(file.getInputStream(), file.getOriginalFilename());
			MisRespResult<Void> result = misActBlackListDubboService.checkUploadData(accountList, compareFilePath, activityPeriods, getCompanyId()) ;
			if(result.isOk()){
				res.setData(FastDFSManager.upload(file.getInputStream(), file.getOriginalFilename()));
				if(StringUtil.isNotEmpty(activityPeriods)){
					ActBlackListVO actBlackListVO = new ActBlackListVO() ;
					actBlackListVO.setActivityPeriods(activityPeriods);
					actBlackListVO.setFileName(res.getData().getFileName());
					actBlackListVO.setFilePath(res.getData().getFileAbsolutePath());
					result = misActBlackListDubboService.batchSave(actBlackListVO,getCompanyId()) ;
					res.setCode(result.getCode());
					res.setMsg(result.getMsg()) ;
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/blacklist/page 活动黑名单-分页查询
	 * @apiSampleRequest /mis/act/blacklist/page
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} activityPeriods 任务编号
	 * @apiParam {String} activityName 任务名称
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} env 真实或模拟账号  real|demo
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"A000001",accountNo:"1000001",platform:"GTS2",env:"demo",page:1,rows:20}
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
	@RequestMapping(value = "/page", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActBlackListVO>> pageList(@ModelAttribute ActBlackListVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<PageR<ActBlackListVO>> result = misActBlackListDubboService.findPageList(vo,getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/blacklist/delete/{id} 活动黑名单-删除
	 * @apiSampleRequest /mis/act/blacklist/delete/1
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} id ID(必填，多个,隔开)
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
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> delete(HttpServletRequest request,@PathVariable String id) {
		try {
			MisRespResult<Void> result = misActBlackListDubboService.deleteByIdArray(id,getCompanyId()) ;
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/whitelist/export 导出白名单
	 * @apiSampleRequest /mis/act/whitelist/export
	 * @apiGroup ActStringCodeApi
	 * 
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
			@ModelAttribute ActBlackListVO reqVo) {
		try {
			reqVo.setCompanyId(getCompanyId());
			// 1、导出工具类    
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(this.getClass().getResourceAsStream(DownTemplateEnum.BlackListUserInfo.getPath()));
			// 2、需要导出的数据
			MisRespResult<List<ActBlackListVO>> listResult =misActBlackListDubboService.findList(reqVo, this.getCompanyId());// misActStringCodeDubboService.findList(reqVo,reqVo.getCompanyId());
			if (listResult.isOk()) {
				List<ActBlackListVO> blackList = listResult.getData();
				if(CollectionUtils.isNotEmpty(blackList)){
					AuthColumnUtil.encryptColumn(blackList, getLoginUser().getRoleId()); 
				}
				// 3、格式化与导出
				builder.setList(blackList, new POIFormatConfig<ActBlackListVO>() {
					@Override
					public Object fromatValue(String fieldName, Object fieldValue, ActBlackListVO param) {
						if (null != fieldValue) {
							if (fieldName != null && "proposalStatus".equals(fieldName)) {
								fieldValue = ActProposalStatusEnum.formatByCode(fieldValue.toString());
							}
							if (fieldName != null && "env".equals(fieldName)) {
								fieldValue = ActEnvEnum.getLabelKeyByValue(fieldValue.toString());
							}
						}
						
						if (fieldValue == null) {
							return "";
						}
						return fieldValue;
					}
				});
				builder.put("totalRecordSize", blackList.size());
				builder.createSXSSFWb();
				// 对response处理使其支持Excel
				ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.BlackListUserInfo.getValue(), request, response);
				builder.write(response.getOutputStream());
			}
		} catch (Exception e) {
			logger.error("export->导出白名单列表失败!", e);
		}
	}
	
	
	
}