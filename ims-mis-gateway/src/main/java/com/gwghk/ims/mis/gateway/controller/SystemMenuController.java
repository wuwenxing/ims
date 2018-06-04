package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuDubboService;
import com.gwghk.ims.common.vo.system.SystemMenuVO;
import com.gwghk.ims.mis.gateway.common.SystemCache;
import com.gwghk.ims.mis.gateway.common.TreeBean;
import com.gwghk.ims.mis.gateway.common.TreeBeanUtil;

import net.sf.json.JSONObject;

/**
 * @apiDefine SystemMenuApi 菜单管理
 */
@Controller
@RequestMapping("/mis/system/menu")
public class SystemMenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemMenuController.class);

	// 菜单共用，则统一使用fx
	private final static Long companyId = CompanyEnum.fx.getLongId();
	
	@Autowired
	private MisSystemMenuDubboService misSystemMenuDubboService;

	/**
	 * @api {post} /mis/system/menu/list 列表查询
	 * @apiSampleRequest /mis/system/menu/list
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {String} menuCode 菜单编号
	 * @apiParam {String} parentMenuCode 父菜单编号
	 * @apiParam {String} menuType 菜单类型
	 * @apiParam {String} menuNameCn 菜单名称
	 * @apiParam {String} menuNameEn 菜单名称
	 * @apiParam {String} menuNameTw 菜单名称
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
	public MisRespResult<List<SystemMenuVO>> list(HttpServletRequest request, @ModelAttribute SystemMenuVO vo) {
		try{
			vo.setCompanyId(companyId);
			return misSystemMenuDubboService.findList(vo);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/menu/listByRoleId 根据角色id查询拥有的菜单列表
	 * @apiSampleRequest /mis/system/menu/listByRoleId
	 * @apiGroup SystemRoleApi
	 * 
	 * @apiParam {Long} roleId (必填)角色ID
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
	@RequestMapping(value = "/listByRoleId", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<SystemMenuVO>> listByRoleId(HttpServletRequest request, Long roleId) {
		try{
			if(null == roleId){
				return MisRespResult.error("参数错误");
			}
			MisRespResult<List<SystemMenuVO>> result = misSystemMenuDubboService.findMenuListByRoleId(roleId);
			return result;
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/menu/listMenuTree 根据条件查询菜单列表-树形结构
	 * @apiSampleRequest /mis/system/menu/listMenuTree
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {Long} roleId 角色ID,不填则查全部
	 * @apiParam {String} type 查询标识,角色ID不为空时有效，(1:查询roleId拥有的权限数据)(2:查询全部数据并且根据roleId设置属性checked,拥有权限为true否则false)
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
	@RequestMapping(value = "/listMenuTree", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<TreeBean>> listMenuTree(Long roleId, String type) {
		try{
			List<TreeBean> returnList = null;
			List<TreeBean> nodeListTmp = new ArrayList<>();
			List<SystemMenuVO> allList = null;
			List<SystemMenuVO> byRoleList = null;
			
			if(null != roleId && "1".equals(type)){
				byRoleList = misSystemMenuDubboService.findMenuListByRoleId(roleId).getData();
				if(null != byRoleList){
					for(SystemMenuVO vo : byRoleList){
						TreeBean e = new TreeBean();
						e.setId(vo.getMenuCode());
						e.setParentId(vo.getParentMenuCode());
						e.setName(vo.getMenuNameCn());
						e.setChecked(true);
						JSONObject obj = new JSONObject();
						obj.put("menuId", vo.getMenuId());
						obj.put("menuUrl", vo.getMenuUrl());
						obj.put("menuType", vo.getMenuType());
						obj.put("menuItem", vo.getMenuItem());
						e.setAttributes(obj);
						nodeListTmp.add(e);
					}
				}
			}else if(null != roleId && "2".equals(type)){
				allList = misSystemMenuDubboService.findList(new SystemMenuVO()).getData();
				if(null != allList){
					byRoleList = misSystemMenuDubboService.findMenuListByRoleId(roleId).getData();
					Map<Long, Long> map = new HashMap<Long, Long>();
					if(null != byRoleList){
						for(SystemMenuVO vo : byRoleList){
							map.put(vo.getMenuId(), vo.getMenuId());
						}
					}
					if(null != allList){
						for(SystemMenuVO vo : allList){
							TreeBean e = new TreeBean();
							e.setId(vo.getMenuCode());
							e.setParentId(vo.getParentMenuCode());
							e.setName(vo.getMenuNameCn());
							if(null != map.get(vo.getMenuId())){
								e.setChecked(true);
							}else{
								e.setChecked(false);
							}
							JSONObject obj = new JSONObject();
							obj.put("menuId", vo.getMenuId());
							obj.put("menuUrl", vo.getMenuUrl());
							obj.put("menuType", vo.getMenuType());
							obj.put("menuItem", vo.getMenuItem());
							e.setAttributes(obj);
							nodeListTmp.add(e);
						}
					}
				}
			}else if(null == roleId){
				allList = misSystemMenuDubboService.findList(new SystemMenuVO()).getData();
				if(null != allList){
					for(SystemMenuVO vo : allList){
						TreeBean e = new TreeBean();
						e.setId(vo.getMenuCode());
						e.setParentId(vo.getParentMenuCode());
						e.setName(vo.getMenuNameCn());
						JSONObject obj = new JSONObject();
						obj.put("menuId", vo.getMenuId());
						obj.put("menuUrl", vo.getMenuUrl());
						obj.put("menuType", vo.getMenuType());
						obj.put("menuItem", vo.getMenuItem());
						e.setAttributes(obj);
						nodeListTmp.add(e);
					}
				}
			}else{
				return MisRespResult.error("参数错误");
			}
			returnList = TreeBeanUtil.formatTreeBean(nodeListTmp);
			return MisRespResult.success(returnList);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/menu/find/{menuId} 根据id查询
	 * @apiSampleRequest /mis/system/menu/find/{menuId}
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {Long} menuId (必填)ID
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
	@RequestMapping(value = "/find/{menuId}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemMenuVO> find(@PathVariable Long menuId) {
		try{
			return misSystemMenuDubboService.findById(menuId);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/menu/findByMenuCode 根据code查询
	 * @apiSampleRequest /mis/system/menu/findByMenuCode
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {String} menuCode (必填)菜单编号
	 * @apiParam {String} menuType 菜单类型，默认查询菜单(1菜单、2功能)
	 * @apiParam {String} parentMenuCode 父编号(菜单类型为2时，必填)
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
	@RequestMapping(value = "/findByMenuCode", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemMenuVO> findByMenuCode(HttpServletRequest request, String menuCode, String menuType, String parentMenuCode) {
		try{
			if(StringUtils.isBlank(menuCode)){
				return MisRespResult.error("参数错误");
			}
			if("2".equals(menuType)){
				if(StringUtils.isBlank(parentMenuCode)){
					return MisRespResult.error("参数错误");
				}
				return misSystemMenuDubboService.findByMenuCodeAndParentMenuCode(menuCode, parentMenuCode);
			}else{
				return misSystemMenuDubboService.findByMenuCode(menuCode, companyId);
			}
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/menu/listByParentMenuCode 根据父code查询子节点
	 * @apiSampleRequest /mis/system/menu/listByParentMenuCode
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {String} parentMenuCode (必填)父编号
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{parentMenuCode:"..."}
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
	@RequestMapping(value = "/listByParentMenuCode", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<SystemMenuVO>> listByParentMenuCode(HttpServletRequest request, String parentMenuCode) {
		try{
			if(StringUtils.isBlank(parentMenuCode)){
				MisRespResult.error("参数错误");
			}
			return misSystemMenuDubboService.findByParentMenuCode(parentMenuCode, companyId);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/menu/save 新增或修改
	 * @apiSampleRequest /mis/system/menu/save
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {Long} menuId 菜单ID
	 * @apiParam {String} parentMenuCode 父菜单编码,(新增功能时不能为空，且存在)
	 * @apiParam {String} menuCode (必填)菜单编码
	 * @apiParam {Long} menuType (必填)菜单类型(1菜单2功能)
	 * @apiParam {String} menuNameCn 菜单名称-简体
	 * @apiParam {String} menuNameEn 菜单名称-英文
	 * @apiParam {String} menuNameTw 菜单名称-繁体
	 * @apiParam {String} menuUrl 链接URL
	 * @apiParam {Long} orderCode 排序号
	 * @apiParam {String} enableFlag 启用Y、禁用N
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
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute SystemMenuVO vo) {
		try{
			if(StringUtils.isBlank(vo.getMenuCode()) || StringUtils.isBlank(vo.getMenuType())){
				return MisRespResult.error("参数错误");
			}
			if("2".equals(vo.getMenuType())){
				if(StringUtils.isBlank(vo.getParentMenuCode())){
					return MisRespResult.error("新增功能时，上级菜单编码不能为空");
				}else{
					SystemMenuVO record = misSystemMenuDubboService.findByMenuCode(vo.getParentMenuCode(), companyId).getData();
					if(null == record){
						return MisRespResult.error("上级菜单不存在");
					}
				}
				// 1、校验编号不能重复
				SystemMenuVO dto = misSystemMenuDubboService.findByMenuCodeAndParentMenuCode(vo.getMenuCode(), vo.getParentMenuCode()).getData();
				if (dto != null && !dto.getMenuId().equals(vo.getMenuId())) {
					return MisRespResult.error("编号不能重复");
				}
			}else{
				// 1、校验编号不能重复
				SystemMenuVO dto = misSystemMenuDubboService.findByMenuCode(vo.getMenuCode(), companyId).getData();
				if (dto != null && !dto.getMenuId().equals(vo.getMenuId())) {
					return MisRespResult.error("编号不能重复");
				}
			}
			// 2、新增操作
			this.setPublicAttrWithoutCompanyId(vo, vo.getMenuId());
			vo.setCompanyId(companyId);
			MisRespResult<Long> result = misSystemMenuDubboService.saveOrUpdate(vo);
			if(result.isOk() && null != result.getData()){
				// 更新缓存
				SystemCache.getInstance().refreshMenu(result.getData());
			}
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/system/menu/delete 根据id删除
	 * @apiSampleRequest /mis/system/menu/delete
	 * @apiGroup SystemMenuApi
	 * 
	 * @apiParam {String} menuId (必填)菜单ID
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{menuId:"..."}
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
	public MisRespResult<Void> delete(HttpServletRequest request, @RequestParam Long menuId) {
		try{
			if(null == menuId){
				return MisRespResult.error("参数错误");
			}
			SystemMenuVO menuVo = misSystemMenuDubboService.findById(menuId).getData();
			if(null != menuVo){
				// 删除前校验
				List<SystemMenuVO> menuList = misSystemMenuDubboService.findByParentMenuCode(menuVo.getMenuCode(), companyId).getData();
				if(null != menuList && menuList.size() > 0){
					return MisRespResult.error("请先删除子节点");
				}
				MisRespResult<Void> result = misSystemMenuDubboService.deleteById(menuId);
				if(result.isOk()){
					// 更新缓存
					SystemCache.getInstance().refreshMenu(menuId);
				}
				return result;
			}
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}