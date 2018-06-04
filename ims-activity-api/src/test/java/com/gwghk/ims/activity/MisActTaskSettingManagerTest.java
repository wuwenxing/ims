package com.gwghk.ims.activity;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.inf.mis.activity.MisActTaskSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：后台-活动任务测试用例
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月13日
 */
public class MisActTaskSettingManagerTest extends BaseTest{

	@Autowired
	private MisActTaskSettingDubboService misActTaskSettingDubboService;
	
	private Long getCompanyId(){
		return 1L;
	}
	
	  
	@Test
	public void testFindList(){
		System.out.println("------->testFindList");
		ActTaskSettingVO actTaskSettingVO = new ActTaskSettingVO();
		actTaskSettingVO.setCompanyId(getCompanyId());
		actTaskSettingVO.setActivityPeriods("fx_20180413134723");
		MisRespResult<List<ActTaskSettingVO>> misResult = misActTaskSettingDubboService.findList(actTaskSettingVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActTaskSettingVO> list = misResult.getData();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
}
