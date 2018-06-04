package com.gwghk.ims.activity;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.inf.mis.activity.MisActTaskItemsDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：后台-活动任务奖励物品测试用例
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月13日
 */
public class MisActTaskItemsManagerTest extends BaseTest{

	@Autowired
	private MisActTaskItemsDubboService misActTaskItemsDubboService;
	
	private Long getCompanyId(){
		return 1L;
	}
	
	  
	@Test
	public void testFindList(){
		System.out.println("------->testFindList");
		ActTaskItemsVO actTaskItemsVO = new ActTaskItemsVO();
		actTaskItemsVO.setCompanyId(getCompanyId());
		actTaskItemsVO.setActivityPeriods("fx_20180413134723");
		MisRespResult<List<ActTaskItemsVO>> misResult = misActTaskItemsDubboService.findList(actTaskItemsVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActTaskItemsVO> list = misResult.getData();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
}
