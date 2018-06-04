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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.Constants;
import com.gwghk.ims.common.inf.mis.sys.MisSystemDictDubboService;
import com.gwghk.ims.common.vo.system.SystemDictVO;
import com.gwghk.ims.mis.gateway.common.DictCache;

/**
 * @apiDefine SystemDictApi 数据字典
 */
@Controller
@RequestMapping("/mis/system/dict")
public class SystemDictController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemDictController.class);

	@Autowired
	private MisSystemDictDubboService misSystemDictDubboService;

	/**
	 * @api {post} /mis/system/dict/page 分页查询
	 * @apiSampleRequest /mis/system/dict/page
	 * @apiGroup SystemDictApi
	 * 
	 * @apiParam {String} dictCode 数据字典编号
	 * @apiParam {String} parentDictCode 父数据字典编号
	 * @apiParam {String} dictType 数据字典类型(1分组)(2节点)
	 * @apiParam {String} dictNameCn 数据字典名称
	 * @apiParam {String} dictNameEn 数据字典名称
	 * @apiParam {String} dictNameTw 数据字典名称
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{dictCode:"xxx"}
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
	public MisRespResult<List<SystemDictVO>> page(HttpServletRequest request, @ModelAttribute SystemDictVO vo) {
		try {
			if(Constants.superAdmin.equals(this.getUserNo())){
				vo.setCompanyId(null);//查询所有
			}else{
				vo.setCompanyId(this.getCompanyId());
			}
			MisRespResult<PageR<SystemDictVO>> result = misSystemDictDubboService.findPageList(vo);
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/dict/findByDictCode 查询数据字典
	 * @apiSampleRequest /mis/system/dict/findByDictCode
	 * @apiGroup SystemDictApi
	 * 
	 * @apiParam {String} dictCode (必填)数据字典编号
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{dictCode:"xxx"}
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
	@RequestMapping(value = "/findByDictCode", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemDictVO> findByDictCode(HttpServletRequest request, String dictCode){
		try {
			if(StringUtils.isBlank(dictCode)){
				return MisRespResult.error("参数错误");
			}
			MisRespResult<SystemDictVO> result = misSystemDictDubboService.findByDictCode(dictCode, this.getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/dict/findListByParentDictCode 查询子节点列表
	 * @apiSampleRequest /mis/system/dict/findListByParentDictCode
	 * @apiGroup SystemDictApi
	 * 
	 * @apiParam {String} parentDictCode (必填)父数据字典编号
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{parentDictCode:"xxx"}
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
	@RequestMapping(value = "/findListByParentDictCode", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<List<SystemDictVO>> findListByParentDictCode(HttpServletRequest request, String parentDictCode){
		try {
			if(StringUtils.isBlank(parentDictCode)){
				return MisRespResult.error("参数错误");
			}
			MisRespResult<List<SystemDictVO>> result = misSystemDictDubboService.findListByParentDictCode(parentDictCode, this.getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/dict/find/{dictId} 根据id查询
	 * @apiSampleRequest /mis/system/dict/find/{dictId}
	 * @apiGroup SystemDictApi
	 * 
	 * @apiParam {Long} dictId (必填)ID
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
	@RequestMapping(value = "/find/{dictId}", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<SystemDictVO> findById(@PathVariable Long dictId) {
		try {
			if(null == dictId){
				return MisRespResult.error("参数错误");
			}
			return misSystemDictDubboService.findById(dictId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/dict/save 新增或修改
	 * @apiSampleRequest /mis/system/dict/save
	 * @apiGroup SystemDictApi
	 * 
	 * @apiParam {Long} dictId 角色ID
	 * @apiParam {String} parentDictCode 父数据字典编码,(新增数据字典时不能为空，且存在)
	 * @apiParam {String} dictCode (必填)数据字典编码
	 * @apiParam {Long} dictType (必填)数据字典类型(1数据字典分组2数据字典)
	 * @apiParam {String} dictNameCn 数据字典名称-简体
	 * @apiParam {String} dictNameEn 数据字典名称-英文
	 * @apiParam {String} dictNameTw 数据字典名称-繁体
	 * @apiParam {Long} orderCode 排序号
	 * @apiParam {String} enableFlag 启用Y、禁用N
	 * @apiParam {Long} companyId 所属公司,为空表示全部公司共用
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
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute SystemDictVO vo) {
		try {
			if(StringUtils.isBlank(vo.getDictCode())
				|| StringUtils.isBlank(vo.getDictType())){
				return MisRespResult.error("参数错误");
			}
			if(null != vo.getCompanyId()){
				if(null == CompanyEnum.findById(vo.getCompanyId())){
					return MisRespResult.error("所属公司有误");
				}
			}
			if("2".equals(vo.getDictType())){
				if(StringUtils.isBlank(vo.getParentDictCode())){
					return MisRespResult.error("新增数据字典时，上级数据字典不能为空");
				}else{
					SystemDictVO record = misSystemDictDubboService.findByDictCode(vo.getParentDictCode(), this.getCompanyId()).getData();
					if(null == record){
						return MisRespResult.error("数据字典与上级数据字典的所属公司不一致，或者上级数据字典不存在");
					}else{
						if(record.getCompanyId() != vo.getCompanyId()){
							return MisRespResult.error("数据字典与上级数据字典的所属公司不一致");
						}
					}
				}
			}
			// 1、校验编号不能重复
			SystemDictVO entity = misSystemDictDubboService.findByDictCode(vo.getDictCode(), this.getCompanyId()).getData();
			if (null != entity && !entity.getDictId().equals(vo.getDictId())) {
				return MisRespResult.error("编号不能重复");
			}
			// 2、新增操作
			this.setPublicAttrWithoutCompanyId(vo, vo.getDictId());
			MisRespResult<Void> result = misSystemDictDubboService.saveOrUpdate(vo);
			if(result.isOk()){
				DictCache.refresh(vo.getDictCode());
				DictCache.refreshSubDictListMap(vo.getDictCode());
			}
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/system/dict/delete 根据id删除
	 * @apiSampleRequest /mis/system/dict/delete
	 * @apiGroup SystemDictApi
	 * 
	 * @apiParam {Long} dictId (必填)数据字典ID
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{dictId:"1"}
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
	public MisRespResult<Void> delete(HttpServletRequest request, @RequestParam Long dictId) {
		try {
			if(null == dictId){
				return MisRespResult.error("参数错误");
			}
			// 删除前校验
			SystemDictVO vo = misSystemDictDubboService.findById(dictId).getData();
			if(null != vo){
				List<SystemDictVO> list = misSystemDictDubboService.findListByParentDictCode(vo.getDictCode(), vo.getCompanyId()).getData();
				if(null != list && list.size() > 0){
					return MisRespResult.error("请先删除子节点");
				}
				MisRespResult<Void> result = misSystemDictDubboService.deleteByIdArray(dictId+"");
				if(result.isOk()){
					DictCache.refresh(vo.getDictCode());
					DictCache.refreshSubDictListMap(vo.getDictCode());
				}
				return result;
			}else{
				return MisRespResult.error("删除的数据不存在");
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}