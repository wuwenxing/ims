package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.Constants;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuRoleDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleColumnAuthDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleDubboService;
import com.gwghk.ims.common.vo.system.SystemMenuRoleVO;
import com.gwghk.ims.common.vo.system.SystemRoleColumnAuthVO;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.ims.mis.gateway.common.SystemCache;

/**
 * @apiDefine SystemRoleApi 角色管理
 */
@Controller
@RequestMapping("/mis/system/role")
public class SystemRoleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SystemRoleController.class);

	@Autowired
	private MisSystemRoleDubboService misSystemRoleDubboService;
	
	@Autowired
	private MisSystemMenuRoleDubboService misSystemMenuRoleDubboService;
	
	@Autowired
	private MisSystemRoleColumnAuthDubboService misSystemRoleColumnAuthDubboService;
	
	/**
	 * @api {post} /mis/system/role/page 分页查询
	 * @apiSampleRequest /mis/system/role/page
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {String} roleCode 角色编号
	 * @apiParam {String} roleName 角色名称
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{userNo:"...",userName:"...",email:"..."}
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
	public MisRespResult<List<SystemRoleVO>> page(HttpServletRequest request, @ModelAttribute SystemRoleVO vo) {
		try{
			if(Constants.superAdmin.equals(this.getUserNo())){
				vo.setCompanyId(null);//超级管理员，查询全部
			}else{
				vo.setCompanyId(this.getCompanyId());
			}
			MisRespResult<PageR<SystemRoleVO>> result = misSystemRoleDubboService.findPageList(vo);
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/role/list 列表查询
	 * @apiSampleRequest /mis/system/role/list
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {String} roleCode 角色编号
	 * @apiParam {String} roleName 角色名称
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{...}
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
	@RequestMapping(value = "/list", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<SystemRoleVO>> list(HttpServletRequest request, @ModelAttribute SystemRoleVO vo) {
		try{
			if(Constants.superAdmin.equals(this.getUserNo())){
				vo.setCompanyId(null);//超级管理员，查询全部
			}else{
				vo.setCompanyId(this.getCompanyId());
			}
			return misSystemRoleDubboService.findList(vo);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/role/find/{roleId} 根据id查询
	 * @apiSampleRequest /mis/system/role/find/{roleId}
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId (必填)角色ID
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
	@RequestMapping(value = "/find/{roleId}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemRoleVO> find(@PathVariable Long roleId) {
		try{
			return misSystemRoleDubboService.findById(roleId);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/role/save 新增或修改
	 * @apiSampleRequest /mis/system/role/save
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId 角色ID
	 * @apiParam {String} roleCode (必填)角色编号
	 * @apiParam {String} roleName 角色名称
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} companyId (必填)所属公司
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{...}
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
	@RequestMapping(value = "/save", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute SystemRoleVO vo) {
		try{
			// 校验编号不能为空
			if(null == vo || StringUtils.isBlank(vo.getRoleCode())){
				return MisRespResult.error("编号不能为空");
			}
			// 所属公司不能为空
			if(null == vo || null == vo.getCompanyId()){
				return MisRespResult.error("所属公司不能为空");
			}
			if(null == CompanyEnum.findById(vo.getCompanyId())){
				return MisRespResult.error("所属公司有误");
			}
			// 校验编号不能重复
			SystemRoleVO role = misSystemRoleDubboService.findByRoleCode(vo.getRoleCode(), vo.getCompanyId()).getData();
			if (null != role && !role.getRoleId().equals(vo.getRoleId())) {
				return MisRespResult.error("编号不能重复");
			}
			// 新增操作
			this.setPublicAttrWithoutCompanyId(vo, vo.getRoleId());
			MisRespResult<Long> result = misSystemRoleDubboService.saveOrUpdate(vo);
			if(result.isOk() && null != result.getData()){
				// 修改时，更新缓存
				SystemCache.getInstance().refreshRole(result.getData());
				SystemCache.getInstance().refreshMenuRole(result.getData());
			}
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/role/delete 根据id删除
	 * @apiSampleRequest /mis/system/role/delete
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {String} ids (必填)多个ID逗号隔开
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{ids:"1,2"}
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
	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> delete(String ids) {
		try{
			if(StringUtils.isBlank(ids)){
				return MisRespResult.error("参数错误");
			}
			MisRespResult<Void> result = misSystemRoleDubboService.deleteByIdArray(ids);
			if(result.isOk()){
				for(String id: ids.split(",")){
					// 删除时，更新缓存
					SystemCache.getInstance().refreshRole(Long.parseLong(id));
					SystemCache.getInstance().refreshMenuRole(Long.parseLong(id));
				}
			}
			return misSystemRoleDubboService.deleteByIdArray(ids);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/role/menu/save 新增或修改角色与菜单关联关系
	 * @apiSampleRequest /mis/system/role/menu/save
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId (必填)角色ID
	 * @apiParam {String} menuIds 菜单ID,多个逗号隔开(menuIds与menuCodes,选其一,优先menuIds生效)
	 * @apiParam {String} menuCodes 菜单编号,多个逗号隔开(menuIds与menuCodes,选其一,优先menuIds生效)
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{...}
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
	@RequestMapping(value = "/menu/save", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> saveOrUpdateRoleMenu(HttpServletRequest request, @ModelAttribute SystemMenuRoleVO vo) {
		try{
			if(null == vo.getRoleId()
					|| (StringUtils.isBlank(vo.getMenuIds()) && StringUtils.isBlank(vo.getMenuCodes()))){
				return MisRespResult.error("参数错误");
			}
			this.setPublicAttr(vo, vo.getMenuRoleId());
			MisRespResult<Void> result = misSystemMenuRoleDubboService.saveOrUpdateByRoleId(vo);
			if(result.isOk()){
				// 更新缓存
				SystemCache.getInstance().refreshRole(vo.getRoleId());
				SystemCache.getInstance().refreshMenuRole(vo.getRoleId());
			}
			return result;
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/role/columnauth/find 根据条件查询列权限
	 * @apiSampleRequest /mis/system/role/columnauth/find
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId (必填)角色ID
	 * @apiParam {String} viewType (必填)数据类型(customer_personal_info：客户信息)(common_columns：公共信息)
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{...}
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
	@RequestMapping(value = "/columnauth/find", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemRoleColumnAuthVO> findRoleColumnAuth(HttpServletRequest request, @ModelAttribute SystemRoleColumnAuthVO vo) {
		try{
			if(null == vo.getRoleId() || StringUtils.isBlank(vo.getViewType())){
				return MisRespResult.error("参数错误");
			}
			vo.setCompanyId(this.getCompanyId());
			List<SystemRoleColumnAuthVO> list = misSystemRoleColumnAuthDubboService.findList(vo).getData();
			if(null != list && list.size() > 0){
				return MisRespResult.success(list.get(0));
			}
			return MisRespResult.success(null);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/role/columnauth/save 新增或修改角色与列关联关系
	 * @apiSampleRequest /mis/system/role/columnauth/save
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId (必填)角色ID
	 * @apiParam {String} viewType (必填)数据类型(customer_personal_info：客户信息)(common_columns：公共信息)
	 * @apiParam {String} viewColumns 能查到的列(多个列逗号分隔，全无勾选传空值)(客户信息:email,mobilePhone,chineseName)(公共信息:stringCode)
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{...}
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
	@RequestMapping(value = "/columnauth/save", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> saveOrUpdateRoleColumnAuth(HttpServletRequest request, @ModelAttribute SystemRoleColumnAuthVO vo) {
		try{
			if(null == vo.getRoleId()
				|| StringUtils.isBlank(vo.getViewType())){
				return MisRespResult.error("参数错误");
			}
			this.setPublicAttr(vo, vo.getId());
			MisRespResult<Void> result = misSystemRoleColumnAuthDubboService.saveOrUpdateByRoleId(vo);
			if(result.isOk()){
				// 更新缓存
				SystemCache.getInstance().refreshRoleColumn(vo.getRoleId());
			}
			return result;
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
}