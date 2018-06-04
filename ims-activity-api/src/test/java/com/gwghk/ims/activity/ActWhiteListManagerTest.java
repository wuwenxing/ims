package com.gwghk.ims.activity;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.dao.entity.ActWhiteList;
import com.gwghk.ims.activity.dao.entity.ActWhiteListWrapper;
import com.gwghk.ims.activity.manager.ActWhiteListManager;
import com.gwghk.ims.activity.service.mis.MisActWhiteListDubboServiceImpl;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ActWhiteListManagerTest extends BaseTest{

	@Autowired
	private ActWhiteListManager actWhiteListManager;
	@Autowired
	private MisActWhiteListDubboServiceImpl misActWhiteListDubboService; 
	
	@Test
	public void testFindPageList(){
		ActWhiteListVO vo = new ActWhiteListVO();
		vo.setPage(1);
		vo.setEnableFlag(null);
		vo.setCompanyId(1L);
		PageR<ActWhiteListVO> pageR = actWhiteListManager.findPageList(vo);
		if(pageR != null){
			List<ActWhiteListVO> list = pageR.getList();
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ActWhiteListVO dto = new ActWhiteListVO();
		dto.setCompanyId(1L);
		List<ActWhiteListWrapper> list = actWhiteListManager.findList(dto) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ActWhiteList task = actWhiteListManager.findById(1);
		System.out.println(task != null ? task.getActivityPeriods() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ActWhiteListVO dto = new ActWhiteListVO();
		dto.setCompanyId(1L);
		dto.setCreateDate(new Date());
		dto.setActivityPeriods("A00000001");
		dto.setAccountNo("1000001");
		dto.setPlatform("GTS");
		dto.setEnv("demo");
		actWhiteListManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		actWhiteListManager.deleteByIdArray("1");
	}
	
}
