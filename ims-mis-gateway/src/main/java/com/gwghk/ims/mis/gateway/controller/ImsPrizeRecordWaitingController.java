package com.gwghk.ims.mis.gateway.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisImsPrizeRecordWaitingDubboService;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordWaitingVO;

/**
 * 
 * 摘要：物品发放记录
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月10日
 */
@Controller
@RequestMapping("/mis/act/prizerecord/waitsend")
public class ImsPrizeRecordWaitingController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(ImsPrizeRecordWaitingController.class) ;
	
	@Autowired
	private MisImsPrizeRecordWaitingDubboService misImsPrizeRecordWaitingDubboService;
	
    /**
	 * @api {post} /mis/act/prizerecord/waitsend/page 物品发放记录-应发列表分页查询
	 * @apiSampleRequest /mis/act/prizerecord/waitsend/page
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} recordNo 流水号
	 * @apiParam {String} thirdRecordNo 第三方流水号
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} itemType 物品-类型
	 * @apiParam {String} itemCategory 物品-种类
	 * @apiParam {String} itemName 物品-名称
	 * @apiParam {String} givedStatus 发放状态
	 * @apiParam {String} auditStatus 审核状态
	 * @apiParam {String} taskName 任务-名称
	 * @apiParam {Boolean} addFromBos 是否后台手动添加(0: 否 1:是)
	 * 
     * @apiParamExample {json} 请求样例
     * {actNo:xxxx}
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
    @RequestMapping(value = "/page", method = { RequestMethod.GET,RequestMethod.POST  })
    @ResponseBody
    public MisRespResult<List<ImsPrizeRecordWaitingVO>> waitSendList(@ModelAttribute ImsPrizeRecordWaitingVO vo, HttpServletRequest request) {
        try {
        	vo.setAlreadySend(false);
            MisRespResult<PageR<ImsPrizeRecordWaitingVO>> result = misImsPrizeRecordWaitingDubboService.findPageList(vo, getCompanyId()) ;
            return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
        } catch (Exception e) {
            logger.error("查询物品发放记录失败!", e);
            return null;
        }
    }
    
}
