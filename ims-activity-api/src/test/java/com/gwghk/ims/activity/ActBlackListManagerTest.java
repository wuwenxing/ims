package com.gwghk.ims.activity;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActBlackListWrapper;
import com.gwghk.ims.activity.manager.ActBlackListManager;
import com.gwghk.ims.activity.service.mis.MisActBlackListDubboServiceImpl;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ActBlackListManagerTest extends BaseTest{

	@Autowired
	private ActBlackListManager actBlackListManager;
	@Autowired
	private MisActBlackListDubboServiceImpl misActBlackListDubboService; 
	
	@Test
	public void testFindPageList(){
		ActBlackListVO vo = new ActBlackListVO();
		vo.setPage(1);
		vo.setEnableFlag(null);
		vo.setCompanyId(1L);
		PageR<ActBlackListVO> pageR = actBlackListManager.findPageList(vo);
		if(pageR != null){
			List<ActBlackListVO> list = pageR.getList();
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ActBlackListVO dto = new ActBlackListVO();
		dto.setCompanyId(1L);
		List<ActBlackListWrapper> list = actBlackListManager.findList(dto) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ActBlackList task = actBlackListManager.findById(1);
		System.out.println(task != null ? task.getActivityPeriods() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ActBlackListVO dto = new ActBlackListVO();
		dto.setCompanyId(1L);
		dto.setCreateDate(new Date());
		dto.setActivityPeriods("A00000001");
		dto.setAccountNo("1000001");
		dto.setPlatform("GTS");
		dto.setEnv("demo");
		actBlackListManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		actBlackListManager.deleteByIdArray("1");
	}
	
}
