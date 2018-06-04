package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.Constants;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleDubboService;
import com.gwghk.ims.common.vo.system.SystemDictVO;
import com.gwghk.ims.common.vo.system.SystemMenuVO;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.ims.mis.gateway.common.DictCache;
import com.gwghk.ims.mis.gateway.common.RoleBean;
import com.gwghk.ims.mis.gateway.common.SystemCache;
import com.gwghk.ims.mis.gateway.common.TreeBean;
import com.gwghk.ims.mis.gateway.common.TreeBeanUtil;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.ims.mis.gateway.util.DownloadUtil;

import net.sf.json.JSONObject;

/**
 * 摘要：基础接口controller
 * 
 * @apiDefine SystemCommonApi 公共接口
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
@Controller
@RequestMapping("/mis/common")
public class CommonController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private MisSystemRoleDubboService misSystemRoleDubboService;

	/**
	 * @api {post} /mis/common/companyList
	 *      公司列表-(备注：所有要求登录的接口，增加参数，test参数不为空都代表测试，userNo参数为登录用户账号)
	 * @apiSampleRequest /mis/common/companyList
	 * @apiGroup SystemCommonApi
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
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/companyList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<Map<String, String>>> companyList() {
		try {
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			if (Constants.superAdmin.equals(this.getUserNo())) {
				for (CompanyEnum ae : CompanyEnum.values()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", ae.getId());
					map.put("code", ae.getCode());
					map.put("name", ae.getName());
					result.add(map);
				}
			} else {
				Map<String, String> map = new HashMap<String, String>();
				CompanyEnum ae = CompanyEnum.findById(this.getCompanyId());
				if (null != ae) {
					map.put("id", ae.getId());
					map.put("code", ae.getCode());
					map.put("name", ae.getName());
					result.add(map);
				}
			}
			return MisRespResult.success(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/common/findByDictCode 查询数据字典
	 * @apiSampleRequest /mis/common/findByDictCode
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {String} dictCode (必填)数据字典编号
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {dictCode:"xxx"}
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
	@RequestMapping(value = "/findByDictCode", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<SystemDictVO> findByDictCode(HttpServletRequest request, String dictCode) {
		try {
			if (StringUtils.isBlank(dictCode)) {
				return MisRespResult.error("参数错误");
			}
			SystemDictVO vo = DictCache.getDictEntity(dictCode);
			return MisRespResult.success(vo);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/common/listByParentDictCode 查询数据字典子节点列表
	 * @apiSampleRequest /mis/common/listByParentDictCode
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {String} parentDictCode (必填)父数据字典编号
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {parentDictCode:"xxx"}
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
	@RequestMapping(value = "/listByParentDictCode", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<SystemDictVO>> listByParentDictCode(HttpServletRequest request, String parentDictCode) {
		try {
			if (StringUtils.isBlank(parentDictCode)) {
				return MisRespResult.error("参数错误");
			}
			List<SystemDictVO> list = DictCache.getSubDictList(parentDictCode);
			return MisRespResult.success(list);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/common/menu/treeList 获取当前用户拥有的菜单权限列表,返回树形结构
	 * @apiSampleRequest /mis/common/menu/treeList
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {String} funFlag 是否显示功能列表(Y显示，其他值不显示，默认显示)
	 * @apiParam {String} type
	 *           查询标识,默认1,(1:查询roleId拥有的权限数据)(2:查询全部数据并且根据roleId设置属性checked,
	 *           拥有权限为true否则false)
	 * 
	 * @apiParamExample {json} 请求样例
	 *  {funFlag:"..."}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据-true代表有，false没有
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
	@RequestMapping(value = "/menu/treeList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<TreeBean>> menuTreeList(String funFlag, String type) {
		try {
			if (StringUtils.isBlank(funFlag)) {
				funFlag = "Y";
			}

			List<TreeBean> returnList = null;
			List<TreeBean> nodeListTmp = new ArrayList<>();
			List<SystemMenuVO> allList = SystemCache.getInstance()
					.getSystemMenuList()
					.stream().filter(r -> !"N".equals(r.getEnableFlag())).collect(Collectors.toList());
			List<SystemMenuVO> byRoleList = null;

			if (Constants.superAdmin.equals(this.getUserNo())) {
				if (null != allList) {
					for (SystemMenuVO vo : allList) {
						if (!"Y".equals(funFlag) && "2".equals(vo.getMenuType())) {
							continue;
						}
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
			} else if (null == type || "1".equals(type)) {
				byRoleList = SystemCache.getInstance().findMenuListByRoleId(this.getLoginUser().getRoleId())
						.stream().filter(r -> !"N".equals(r.getEnableFlag())).collect(Collectors.toList());
				if (null != byRoleList) {
					if (null != byRoleList) {
						for (SystemMenuVO vo : byRoleList) {
							if (!"Y".equals(funFlag) && "2".equals(vo.getMenuType())) {
								continue;
							}
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
				}
			} else {
				if (null != allList) {
					byRoleList = SystemCache.getInstance().findMenuListByRoleId(this.getLoginUser().getRoleId())
							.stream().filter(r -> !"N".equals(r.getEnableFlag())).collect(Collectors.toList());
					Map<Long, Long> map = new HashMap<Long, Long>();
					if (null != byRoleList) {
						for (SystemMenuVO vo : byRoleList) {
							map.put(vo.getMenuId(), vo.getMenuId());
						}
					}
					if (null != allList) {
						for (SystemMenuVO vo : allList) {
							if (!"Y".equals(funFlag) && "2".equals(vo.getMenuType())) {
								continue;
							}
							TreeBean e = new TreeBean();
							e.setId(vo.getMenuCode());
							e.setParentId(vo.getParentMenuCode());
							e.setName(vo.getMenuNameCn());
							if (null != map.get(vo.getMenuId())) {
								e.setChecked(true);
							} else {
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
			}
			returnList = TreeBeanUtil.formatTreeBean(nodeListTmp);
			return MisRespResult.success(returnList);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/common/rolelist/group 角色根据公司分组查询
	 * @apiSampleRequest /mis/common/rolelist/group
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {String} roleCode 角色编号
	 * @apiParam {String} roleName 角色名称
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * 
	 * @apiParamExample {json}
	 *  请求样例 {...}
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
	@RequestMapping(value = "/rolelist/group", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<RoleBean>> roleListGroup(HttpServletRequest request, @ModelAttribute SystemRoleVO vo) {
		try {
			List<RoleBean> roleBeanList = new ArrayList<RoleBean>();

			if (Constants.superAdmin.equals(this.getUserNo())) {
				vo.setCompanyId(null);// 超级管理员，查询全部
			} else {
				vo.setCompanyId(this.getCompanyId());
			}
			List<SystemRoleVO> list = misSystemRoleDubboService.findList(vo).getData();
			if (null != list) {
				Map<Long, List<SystemRoleVO>> groupMap = list.stream()
						.collect(Collectors.groupingBy(SystemRoleVO::getCompanyId));
				for (Map.Entry<Long, List<SystemRoleVO>> entry : groupMap.entrySet()) {
					Long companyId = entry.getKey();
					List<SystemRoleVO> roleList = entry.getValue();
					RoleBean bean = new RoleBean();
					bean.setCompanyId(companyId);
					bean.setRoleList(roleList);
					roleBeanList.add(bean);
				}
			}
			return MisRespResult.success(roleBeanList);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {get} /mis/common/down/tpl/{key} 下载文件模板
	 * @apiSampleRequest /mis/common/down/tpl/black_white_tpl
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {String} black_white_tpl 批量保存黑白名单模板
	 * @apiParam {String} prize_record_tpl 批量保存物品发放记录模板
	 * @apiParam {String} act_stringCode_tpl 批量上传串码模板
	 * @apiParam {String} user_tpl 批量上传用户模板
	 * @apiParam {String} recharge_add_tpl 批量上传流量话费模板
	 * 
	 */
	@RequestMapping(value = "/down/tpl/{key}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void downTemplate(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("key") String key) {
		try {
			new DownloadUtil().downloadExcelTemplate(DownTemplateEnum.getTpl(key), request, response);
		} catch (Exception e) {
			logger.error("downTemplate->下载物品发放记录批量添加模板失败!", e);
		}
	}
}