package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

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

import com.gwghk.ims.common.annotation.AuditInfoBeforeSave;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.mall.MisMallItemsDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemDictDubboService;
import com.gwghk.ims.common.vo.mall.ImsMallItemVO;
import com.gwghk.ims.common.vo.system.SystemDictVO;

/**
 * 商城管理
 * @author jackson.tang
 *
 */
@Controller
@RequestMapping("/mall/item")
public class ImsMallItemController extends BaseController{
	
	public final String MALL_ITEM_PARENT_DICT_CODE="MallItemType";
	
	private static final Logger logger = LoggerFactory.getLogger(ImsMallItemController.class);
	@Autowired
	private MisMallItemsDubboService misMallItemsDubboService;
	@Autowired
	private MisSystemDictDubboService misSystemDictDubboService;
	
	/**
	 * @api {get,post} /mall/item/page 分页查询
	 * @apiSampleRequest /mall/item/page
	 * @apiGroup misMallApi
	 * 
	 * @apiParam activityName 活动名称
	 * @apiParam activityPeriods 活动编号
	 * @apiParam enableFlag 是否启用
	 * @apiParam mallItemsCode 物品编号
	 * @apiParam mallItemsName 物品名称
	 * @apiParam mallItemsType 物品类型
	 * @apiParam taskCode 任务编号
	 * @apiParam taskName 任务名称
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
    @RequestMapping(value = "/page", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<List<ImsMallItemVO>> page(@ModelAttribute ImsMallItemVO pageSearchVo) {
		try {
			pageSearchVo.setCompanyId(getCompanyId());
			
			//保存 tagCodes 
			//List<String> tagCodes = Arrays.asList(userContext.getTagCode().split(","));
			//pageSearchVo.setTagCodes(tagCodes);
			
			//@todo这里可以走缓存
			List<SystemDictVO> list=getMallItemTypeList();
			
			MisRespResult<PageR<ImsMallItemVO>> result = misMallItemsDubboService.findPageList(pageSearchVo);
			if(result.getData()!=null && result.getData().getList()!=null) {
				for(ImsMallItemVO vo: result.getData().getList()){
					vo.setMallItemsTypeName(getMallItemTypeName(list, vo.getItemsType()));
					vo.setEnableFlagName("Y".equals(vo.getEnableFlag()) ? "启用":"禁用");
				}
			}
			
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
	 * @api {get,post} /mall/item/type/list 所有的商品类型
	 * @apiSampleRequest /mall/item/type/list
	 * @apiGroup misMallApi
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
    @RequestMapping(value = "/type/list", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<List<SystemDictVO>> itemTypeList(@ModelAttribute ImsMallItemVO pageSearchVo) {
		try {
			return MisRespResult.success(getMallItemTypeList());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
     * 默认输出是中文
     * @param list 数据源头
     * @param type
     * @return
     */
    private String getMallItemTypeName(List<SystemDictVO> list,String type) {
    	if(list==null || type==null)
    		return null;

    	for(SystemDictVO vo:list) {
    		if(type.equals(vo.getDictCode()))
    			return vo.getDictNameCn();
    	}
    	
    	return null;
    }
    
    /**
     * 从数据字典里面获取商品所有类型
     * @return
     */
    private List<SystemDictVO> getMallItemTypeList(){
    	SystemDictVO dictVo=new SystemDictVO();
		dictVo.setParentDictCode(MALL_ITEM_PARENT_DICT_CODE);
		MisRespResult<List<SystemDictVO>> result= misSystemDictDubboService.findList(dictVo);
		
		if(result.isNotOk() || result.getData()==null)
			return null;
		
		return result.getData();
    }
    
    /**
	 * @api {get,post} /mall/item/{id} 查询物品信息
	 * @apiSampleRequest /mall/item/{id}
	 * @apiGroup misMallApi
	 * 
	 * @apiParam {String} id
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
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<ImsMallItemVO> findById(@PathVariable("id") Long id) {
		try {
			return misMallItemsDubboService.findById(id,getCompanyId());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
	 * @api {post} /mall/item/save 保存物品信息
	 * @apiSampleRequest /mall/item/save
	 * @apiGroup misMallApi
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
    @RequestMapping(value = "/save", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
	public MisRespResult<Void> save(@ModelAttribute ImsMallItemVO imsMallItemsVo) {
		try {
			setPublicAttr(imsMallItemsVo,imsMallItemsVo.getId());
			return misMallItemsDubboService.save(imsMallItemsVo);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
	 * @api {get,post} /mall/item/batch/del 批量删除
	 * @apiSampleRequest /mall/item/batch/del
	 * @apiGroup misMallApi
	 * 
	 * @apiParam {String} ids
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
    @RequestMapping(value = "/batch/del", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<Void> batchDel(@RequestParam("ids") String ids) {
		try {
			String[] idArr=ids.split(",");
			return misMallItemsDubboService.batchSoftDel(idArr,getCompanyId());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
    
    /**
	 * @api {get,post} /mall/item/{id}/status/update 改变启用禁用状态
	 * @apiSampleRequest /mall/item/{id}/status/update
	 * @apiGroup misMallApi
	 * 
	 * @apiParam {Long} id
	 * @apiParam {Sting} flag
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
    @RequestMapping(value = "/{id}/status/update", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public MisRespResult<Void> statusUpdate(@PathVariable(value="id") @RequestParam(value="id",required=true) Long id,@RequestParam(value="flag",required=true) String flag) {
		try {
			
			ImsMallItemVO vo=new ImsMallItemVO();
			vo.setId(id);
			vo.setEnableFlag("1".equals(flag) ? "Y" : "N");
			return misMallItemsDubboService.save(vo);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
