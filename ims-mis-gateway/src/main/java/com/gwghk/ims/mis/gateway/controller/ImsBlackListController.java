package com.gwghk.ims.mis.gateway.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.base.MisImsBlackListDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.vo.base.ImsBlackListVO;
import com.gwghk.ims.mis.gateway.common.UserContext;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：全局黑名单controller
 * @apiDefine ImsBlackListApi 全局黑名单
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月4日
 */
@Controller
@RequestMapping("/mis/gw/blacklist")
public class ImsBlackListController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ImsBlackListController.class);

	@Autowired
	private MisImsBlackListDubboService  misImsBlackListDubboService;

	/**
	 * @api {post} /mis/gw/blacklist/page 分页查询
	 * @apiSampleRequest /mis/gw/blacklist/page
	 * @apiGroup ImsBlackListApi
	 * @apiParam {String} accountNo 客户账户
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {accountNo:"10001"}
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
	 *		"data": null,
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	@SuppressWarnings("unchecked")
	public MisRespResult<List<ImsBlackListVO>> pageList(@ModelAttribute ImsBlackListVO imsBlackListVO) {
		try {
			imsBlackListVO.setCompanyId(this.getCompanyId());
			Map<String,Object> result = misImsBlackListDubboService.findPageList(imsBlackListVO,this.getCompanyId());
			if(result != null){
				Object list = result.get("list");
				return MisRespResult.success(list != null ? (List<ImsBlackListVO>)list : null,result.get("total"));
			}else{
				logger.error("黑名单分页api 调用异常!");
				return MisRespResult.error(MisResultCode.EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/gw/blacklist/upload 批量上传账号
	 * @apiSampleRequest /mis/gw/blacklist/upload
	 * @apiGroup ImsBlackListApi
	 * 
	 * @apiParam {CommonsMultipartFile} file excel文件
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
	 *		"data": null,
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Integer> batchUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
		try {
			double megabytes = file.getSize() / (1024 * 1024);
			if (megabytes <= 5) {
				List<List<Object>> result = ExcelUtil.readExcelByInputStream(file.getInputStream(), file.getOriginalFilename());
				if (result == null || result.isEmpty()) {
					return MisRespResult.error(MisResultCode.Error40008);
				} else {
					Set<String> accounts = new HashSet<String>();	// 当前上传文件数据key,
					for (int i = 0; i < result.size(); i++) {
						List<Object> item = result.get(i);
						if (item.size() < 1) {
							String msg = String.format("上传失败:第%s行错误，账号为空!",i + 2);
							return MisRespResult.error(msg);
						}
						Object accountObj = item.get(0);
						if (accountObj == null|| StringUtil.isNullOrEmpty(accountObj.toString())) {
							String msg = String.format("上传失败:第%s行错误，账号为空!",i + 1);
							return MisRespResult.error(msg);
						}
						if (accountObj instanceof String == false) {
							String msg = String.format("上传失败:第%s行错误，账号格式错误，需为文本格式!",i + 1);
							return MisRespResult.error(msg);
						}
						String account = accountObj.toString().trim();
						if (accounts.contains(account)) {
							String msg = String.format("上传失败:第%s行错误，当前上传文件中存在相同的账号!",i + 1);
							return MisRespResult.error(msg);
						} else {
							accounts.add(account);
						}
					}
					return misImsBlackListDubboService.batchSave(accounts,UserContext.get().getLoginIp(),this.getCompanyId());
				}
			} else {
				return MisRespResult.error(MisResultCode.Error40009);
			}
		} catch (Exception e) {
			logger.error("batchUpload->black list file upload fail !", e);
		} 
		return MisRespResult.error(MisResultCode.FAIL);
	}
}