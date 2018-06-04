package com.gwghk.ims.mis.gateway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
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
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisPrizeRecordRedoDubboService;
import com.gwghk.ims.common.vo.activity.PrizeRecordRedoVO;
import com.gwghk.ims.mis.gateway.common.DictCache;
import com.gwghk.ims.mis.gateway.common.UserContext;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：失败发放记录controller
 * @author cookie.lai
 * @Date 2018年3月29日
 */
@Controller
@RequestMapping("/mis/act/prizerecordredo")
public class ActPrizeRecordRedoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ActPrizeRecordRedoController.class);
	@Autowired
    private MisPrizeRecordRedoDubboService misPrizeRecordRedoDubboService;
    

    
	/**
	 * @api {get} /mis/act/prizerecordredo/page 分页查询
	 * @apiSampleRequest /mis/act/prizerecordredo/page
	 * @apiGroup PrizeRecordRedoApi
	 * 
	 * @apiParam {Long} rows (必填)每页多少条数据
	 * @apiParam {Long} page (必填)第几页
	 * @apiParam {String} sort 排序字段名
	 * @apiParam {String} order 排序字段顺序
	 * @apiParam {String} activityPeriods 活动编号
	 * @apiParam {String} activityName 活动名称
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {String} platform 平台
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{accountNo:"xxx",platform："xxx"}
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
    @RequestMapping(value = "/page", method = { RequestMethod.GET })
    @ResponseBody
    public MisRespResult<List<PrizeRecordRedoVO>> pageList(@ModelAttribute PrizeRecordRedoVO reqDto) {
    	try{
    	Long companyId = UserContext.get().getCompanyId();
		reqDto.setCompanyId(companyId);
    	MisRespResult<PageR<PrizeRecordRedoVO>> findPageList = misPrizeRecordRedoDubboService.findPageList(reqDto,companyId);
    	PageR<PrizeRecordRedoVO> page = findPageList.getData();
          if (page != null) {
              List<PrizeRecordRedoVO> recordList = page.getRows();
              if(CollectionUtils.isNotEmpty(recordList)){
                  for (PrizeRecordRedoVO r : recordList) {
                      r.setRedoStatus(DictCache.getDictNameByDictCode(r.getRedoStatus()));
                  }
              }
          }
          return MisRespResult.success(findPageList.getData().getList(),findPageList.getData().getTotal());
    	}catch(Exception e){
    		logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
    	}
    }
    /**
	 * @api {get} /mis/act/prizerecordredo/batch/resend/{ids} 批量重发
	 * @apiSampleRequest /mis/act/prizerecordredo/batch/resend/{ids}
	 * @apiGroup PrizeRecordRedoApi
	 * 
	 * @apiParam {String} idArray 重发id
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{accountNo:"xxx",platform："xxx"}
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
    @RequestMapping(value = "/batch/resend/{ids}", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public MisRespResult<Void> batchResend(@PathVariable("ids")String idArray) {
        try {
        	Long companyId = UserContext.get().getCompanyId();
            if(StringUtil.isNotEmpty(idArray)){
                List<Long> ids = new ArrayList<Long>();
                ids = Arrays.asList(idArray.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                misPrizeRecordRedoDubboService.updateResend(ids,companyId);
                return MisRespResult.success();
            }
            return MisRespResult.error(MisResultCode.EXCEPTION);
        } catch (Exception e) {
            logger.error("<----batchResend->批量重发!{}", idArray, e);
            return MisRespResult.error(MisResultCode.EXCEPTION);
        }
    }
    
    /**
   	 * @api {get} /mis/act/prizerecordredo/batch/delete/{ids} 批量删除
   	 * @apiSampleRequest /mis/act/prizerecordredo/batch/delete/{ids}
   	 * @apiGroup PrizeRecordRedoApi
   	 * 
   	 * @apiParam {String} idArray 删除id
   	 * 
        * @apiParamExample {json} 请求样例 
        * 	{accountNo:"xxx",platform："xxx"}
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
    @RequestMapping(value = "/batch/delete/{ids}", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public MisRespResult<Void> batchDelete(@PathVariable("ids")String idArray) {
        try {
        	Long companyId = UserContext.get().getCompanyId();
            if(StringUtil.isNotEmpty(idArray)){
                List<Long> ids = new ArrayList<Long>();
                ids = Arrays.asList(idArray.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                misPrizeRecordRedoDubboService.deleteByIds(ids,companyId);
                return MisRespResult.success();
            }
            return MisRespResult.error(MisResultCode.EXCEPTION);
        } catch (Exception e) {
            logger.error("<----batchDelete->批量删除!{}", idArray, e);
            return MisRespResult.error(MisResultCode.EXCEPTION);
        }
    }
}
