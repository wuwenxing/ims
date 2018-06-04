package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.Constants;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.mis.sys.MisSystemUserDubboService;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.util.MD5;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.ims.mis.gateway.common.SystemCache;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * @apiDefine SystemUserApi 用户管理
 */
@Controller
@RequestMapping("/mis/system/user")
public class SystemUserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

	@Autowired
	private MisSystemUserDubboService misSystemUserDubboService;

	/**
	 * @api {post} /mis/system/user/page 分页查询
	 * @apiSampleRequest /mis/system/user/page
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {Long} roleId 角色ID
	 * @apiParam {String} userNo 账号
	 * @apiParam {String} userName 用户名
	 * @apiParam {String} email 邮箱
	 * @apiParam {String} position 职位
	 * @apiParam {String} telephone 手机
	 * @apiParam {String} remark 备注
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {userNo:"...",userName:"...",email:"xxx"}
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
	public MisRespResult<List<SystemUserVO>> page(HttpServletRequest request, @ModelAttribute SystemUserVO vo) {
		try {
			if (Constants.superAdmin.equals(this.getUserNo())) {
				vo.setCompanyId(null);// 超级管理员，查询全部
				vo.setSuperAdmin(true);
			} else {
				vo.setCompanyId(this.getCompanyId());
			}
			MisRespResult<PageR<SystemUserVO>> result = misSystemUserDubboService.findPageList(vo);
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/find/{userId} 根据id查询
	 * @apiSampleRequest /mis/system/user/find/{userId}
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {Long} userId (必填)用户ID
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
	@RequestMapping(value = "/find/{userId}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<SystemUserVO> find(@PathVariable Long userId) {
		try {
			return misSystemUserDubboService.findById(userId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/save 新增或修改
	 * @apiSampleRequest /mis/system/user/save
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {Long} userId 用户ID
	 * @apiParam {Long} roleId 角色ID
	 * @apiParam {String} userNo (必填)账号
	 * @apiParam {String} userName 用户名
	 * @apiParam {String} password 密码(新增时必填)
	 * @apiParam {String} email 邮箱
	 * @apiParam {String} position 职位
	 * @apiParam {String} telephone 手机
	 * @apiParam {String} remark 备注
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} companyId (必填)所属公司
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {...}
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
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute SystemUserVO vo) {
		try {
			// 初始数据，不能修改
			if(Constants.superAdmin.equals(vo.getUserNo())){
				return MisRespResult.error("初始数据，不能修改");
			}
			// 校验账号不能为空
			if (StringUtils.isBlank(vo.getUserNo())) {
				return MisRespResult.error("账号不能为空");
			}
			if(null == vo.getUserId()){
				// 密码不能为空
				if (StringUtils.isBlank(vo.getPassword())) {
					return MisRespResult.error("密码不能为空");
				}
			}
			// 所属公司不能为空
			if (null == vo || null == vo.getCompanyId()) {
				return MisRespResult.error("所属公司不能为空");
			}
			if(null == CompanyEnum.findById(vo.getCompanyId())){
				return MisRespResult.error("所属公司有误");
			}
			// 角色不能为空
			if (null == vo || null == vo.getRoleId()) {
				return MisRespResult.error("角色不能为空");
			}
			// 校验账号不能重复
			SystemUserVO dto = misSystemUserDubboService.findByUserNo(vo.getUserNo()).getData();
			if (null != dto && !dto.getUserId().equals(vo.getUserId())) {
				return MisRespResult.error("账号不能重复");
			}
			// 新增或修改操作
			this.setPublicAttrWithoutCompanyId(vo, vo.getUserId());
			MisRespResult<Long> result = misSystemUserDubboService.saveOrUpdate(vo);
			if(result.isOk() && null != result.getData()){
				SystemCache.getInstance().refreshUser(result.getData());
			}
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/delete 根据id删除
	 * @apiSampleRequest /mis/system/user/delete
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {String} ids (必填)多个ID逗号隔开
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {ids:"1,2"}
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
	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> delete(String ids) {
		try {
			if (StringUtils.isBlank(ids)) {
				return MisRespResult.error("参数错误");
			}
			long count = Arrays.asList(ids.split(",")).stream().filter(r -> "1".equals(r)).count();
			if (count > 0) {
				return MisRespResult.error("超级用户不可删除");
			}
			return misSystemUserDubboService.deleteByIdArray(ids);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/password/reset 重置密码
	 * @apiSampleRequest /mis/system/user/password/reset
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {Long} userId (必填)用户ID
	 * @apiParam {String} password (必填)新密码
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {userId:"1", password:"..."}
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
	@RequestMapping(value = "/password/reset", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> passwordReset(HttpServletRequest request, Long userId, String password) {
		try {
			if (null == userId || StringUtils.isBlank(password)) {
				return MisRespResult.error("参数错误");
			}
			// 初始数据，不能修改
			if(userId == 1){
				return MisRespResult.error("初始数据，不能修改");
			}
			return misSystemUserDubboService.updatePassword(userId, password);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/password/update 修改密码
	 * @apiSampleRequest /mis/system/user/password/update
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {String} oldPassword (必填)原密码
	 * @apiParam {String} password (必填)新密码
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {userId:"1",oldPassword:"..",password:".."}
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
	@RequestMapping(value = "/password/update", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> passwordUpdate(HttpServletRequest request, String oldPassword,
			String password) {
		try {
			if (StringUtils.isBlank(password)) {
				return MisRespResult.error("参数错误");
			}
			if (oldPassword.equals(password)) {
				return MisRespResult.error("原密码与新密码相同");
			}
			SystemUserVO vo = misSystemUserDubboService.findById(this.getLoginUser().getUserId()).getData();
			if (null == vo) {
				return MisRespResult.error("用户不存在");
			}
			if (!vo.getPassword().equals(MD5.getMd5(oldPassword))) {
				return MisRespResult.error("原密码不正确");
			}
			return misSystemUserDubboService.updatePassword(this.getLoginUser().getUserId(), password);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/userrole/save 新增或修改用户与角色的关联关系
	 * @apiSampleRequest /mis/system/user/userrole/save
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId (必填)角色ID
	 * @apiParam {String} userIds 添加该角色的用户ID,多个逗号隔开
	 * @apiParam {String} unUserIds 移除该角色的用户ID,多个逗号隔开
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {userId:"1",roleId:"1"}
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
	@RequestMapping(value = "/userrole/save", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<Void> saveOrUpdateUserRole(HttpServletRequest request, @RequestParam String userIds,
			@RequestParam String unUserIds, @RequestParam Long roleId) {
		try {
			if (null == roleId) {
				return MisRespResult.error("参数错误");
			}
			if (StringUtils.isNotBlank(userIds)) {
				for (String userId : userIds.split(",")) {
					misSystemUserDubboService.updateUserRole(Long.parseLong(userId), roleId);
				}
			}
			if (StringUtils.isNotBlank(unUserIds)) {
				for (String userId : unUserIds.split(",")) {
					misSystemUserDubboService.updateUserRole(Long.parseLong(userId), null);
				}
			}
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/user/export 导出用户
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {Long} roleId 角色ID
	 * @apiParam {String} userNo 账号
	 * @apiParam {String} userName 用户名
	 * @apiParam {String} email 邮箱
	 * @apiParam {String} position 职位
	 * @apiParam {String} telephone 手机
	 * @apiParam {String} remark 备注
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
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
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute SystemUserVO vo) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(this.getClass().getResourceAsStream(DownTemplateEnum.SystemUserInfo.getPath()));
			// 2、需要导出的数据
			if (Constants.superAdmin.equals(this.getUserNo())) {
				vo.setCompanyId(null);// 超级管理员，查询全部
				vo.setSuperAdmin(true);
			} else {
				vo.setCompanyId(this.getCompanyId());
			}
			MisRespResult<List<SystemUserVO>> listResult = misSystemUserDubboService.findList(vo);
			if (listResult.isOk()) {
				List<SystemUserVO> recordList = listResult.getData();
				// 3、格式化与导出
				builder.setList(recordList, (n, v, o) -> {
					if ("enableFlag".equals(n)) {
						if ("Y".equals(v+"")) {
							return "启用";
						} else {
							return "禁用";
						}
					} else if ("companyId".equals(n+"")) {
						return CompanyEnum.findById(v).getName();
					}
					return v;
				});
				builder.put("totalRecordSize", recordList.size());
				builder.createSXSSFWb();
				// 对response处理使其支持Excel
				ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.SystemUserInfo.getValue(), request, response);
				builder.write(response.getOutputStream());
			}
		} catch (Exception e) {
			logger.error("export->导出失败!", e);
		}
	}
	
	/**
	 * @api {post} /mis/system/user/upload/ 批量上传用户
	 * @apiSampleRequest /mis/system/user/upload/
	 * @apiGroup SystemUserApi
	 * 
	 * @apiParam {MultipartFile} file excel导入文件
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
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> batchUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file
			) {
		try {
			double megabytes = file.getSize() / (1024 * 1024);
			if (megabytes <= 5) {
				List<List<Object>> result = ExcelUtil.readExcelByInputStream(file.getInputStream(), file.getOriginalFilename());
				if (result == null || result.isEmpty()) {
					return MisRespResult.error(MisResultCode.Error40008);
				} else {
					// 需要充值的对象
					List<SystemUserVO> userList = new ArrayList<SystemUserVO>();
					for (int i = 0; i < result.size(); i++) {
						
						List<Object> item = result.get(i);
						
						if (item.size() < 1) {
							String msg = String.format("上传失败:第%s行错误，账号不能为空!",i + 2);
							return MisRespResult.error(msg);
						}
						//账号*
						Object userNo = item.get(0);
						//姓名
						Object userName = item.get(1);
						//密码*
						Object pwd =null;
						if(item.size()>=3) {
							pwd= item.get(2);
						}
						//角色编号*
						SystemRoleVO roleVO = null;
						Object roleCode = item.get(3);
						//状态*(Y/N)
						Object enableFlag = null;
						if(item.size()>=5) {
							enableFlag = item.get(4);
						}
						//手提电话
						Object telephone =null;
						if(item.size()>=6) {
							telephone= item.get(5);
						}
						//电邮
						Object email=null;
						if(item.size()>=7) {
							email= item.get(6);
						}
						//职位
						Object position=null; 
						if(item.size()>=8) {
							position= item.get(7);
						}
						//描述
						Object remark =null;
						if(item.size()>=9) {
							remark= item.get(8);
						}

						if (userNo == null || StringUtil.isNullOrEmpty(userNo.toString())) {
							String msg = String.format("上传失败:第%s行错误，账号不能为空!",i + 2);
							return MisRespResult.error(msg);
						}else{
							SystemUserVO userVO= SystemCache.getInstance().getSystemUserByUserNo(userNo+"");
							if(null != userVO){
								String msg = String.format("上传失败:第%s行错误，用户账号已存在!",i + 2);
								return MisRespResult.error(msg);
							}
						}
						if (pwd == null || StringUtil.isNullOrEmpty(pwd.toString())) {
							String msg = String.format("上传失败:第%s行错误，密码不能为空!",i + 2);
							return MisRespResult.error(msg);
						}
						if (roleCode == null || StringUtil.isNullOrEmpty(roleCode.toString())) {
							String msg = String.format("上传失败:第%s行错误，角色编号不能为空!",i + 2);
							return MisRespResult.error(msg);
						}else{
							roleVO = SystemCache.getInstance().getSystemRoleByRoleCode(roleCode+"", this.getCompanyId());
							if(null == roleVO){
								String msg = String.format("上传失败:第%s行错误，角色编号不存在!",i + 2);
								return MisRespResult.error(msg);
							}
						}
						if (enableFlag == null || StringUtil.isNullOrEmpty(enableFlag.toString())) {
							String msg = String.format("上传失败:第%s行错误，状态不能为空!",i + 2);
							return MisRespResult.error(msg);
						}else{
							if(!("Y".equals(enableFlag) || "N".equals(enableFlag))){
								String msg = String.format("上传失败:第%s行错误，状态填写有误,正确格式(Y/N)!",i + 2);
								return MisRespResult.error(msg);
							}
						}
						long count = userList.stream().filter(r -> r.getUserNo().equals(userNo)).count();
						if(count > 0){
							String msg = String.format("上传失败:第%s行错误，excel中有重复用户账号!",i + 2);
							return MisRespResult.error(msg);
						}
						
						SystemUserVO systemUserVO=new SystemUserVO();
						systemUserVO.setUserNo(userNo+"");
						systemUserVO.setUserName(userName+"");
						systemUserVO.setPassword(pwd+"");
						systemUserVO.setRoleId(roleVO.getRoleId());
						systemUserVO.setTelephone(telephone+"");
						systemUserVO.setEmail(email+"");
						systemUserVO.setPosition(position+"");
						systemUserVO.setRemark(remark+"");
						systemUserVO.setEnableFlag(enableFlag+"");
						this.setPublicAttr(systemUserVO, systemUserVO.getUserId());
						userList.add(systemUserVO);
					}
					if (userList.size() == 0) {
						return MisRespResult.error("上传失败:上传的数据为空，无需上传!");
					}else{
						userList.stream().forEach(r -> {
							MisRespResult<Long> tempResult = misSystemUserDubboService.saveOrUpdate(r);
							if(tempResult.isOk() && null != tempResult.getData()){
								SystemCache.getInstance().refreshUser(tempResult.getData());
							}
						});
					}
				}
				return MisRespResult.success();
			} else {
				return MisRespResult.error(MisResultCode.Error40009);
			}
		} catch (Exception e) {
			logger.error("<---batchUpload->file upload fail !", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}