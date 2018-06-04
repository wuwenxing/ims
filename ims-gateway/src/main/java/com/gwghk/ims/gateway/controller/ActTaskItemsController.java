package com.gwghk.ims.gateway.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.dto.activity.ActTaskItemsDTO;
import com.gwghk.ims.common.inf.external.activity.ActTaskItemsDubboService;

/**
 * 
 * 摘要：活动任务
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月23日
 */
@RestController
@RequestMapping("/act/task")
public class ActTaskItemsController extends BaseController {

	@Autowired
	private  ActTaskItemsDubboService actTaskItemsDubboService ;

	@RequestMapping("/items")
	public ApiRespResult<List<Map<String,Object>>> items(@RequestParam("actNo")String actNo,@RequestParam("taskCode")String taskCode,@RequestParam("companyId")long companyId){
		ApiRespResult<List<ActTaskItemsDTO>> res = actTaskItemsDubboService.findListByTaskCode(actNo, taskCode, companyId) ;
		List<Map<String,Object>> resList = new ArrayList<>() ;
		if(res.isNotOk()){
			return ApiRespResult.error(res.getCode(),res.getMsg()) ;
		}
		List<ActTaskItemsDTO> list = res.getData() ;
		for(ActTaskItemsDTO dto : list) {
			Map<String,Object> map = new HashMap<>() ;
			map.put("itemId", dto.getId()) ;
			map.put("equalValue", dto.getEqualValue()) ;
			map.put("itemName", dto.getItemName()) ;
			map.put("itemNumber", dto.getItemNumber()) ;
			map.put("itemType", dto.getItemType()) ;
			map.put("itemCategory", dto.getItemCategory()) ;
			map.put("receiveMaxNum", dto.getReceiveMaxNum()) ;
			resList.add(map) ;
		}
		return ApiRespResult.success(resList) ;
	}
}
