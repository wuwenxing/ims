package com.gwghk.ims.activity;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.dao.entity.ActMaintenanceSetting;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSettingWrapper;
import com.gwghk.ims.activity.manager.ActMaintenanceSettingManager;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActMaintenanceSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：系统-用户处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ActMaintenanceManagerTest extends BaseTest{

	@Autowired
	private ActMaintenanceSettingManager actMaintenanceManager;
	
	@Test
	public void testFindPageList(){
		ActMaintenanceSettingVO vo = new ActMaintenanceSettingVO();
		vo.setPage(1);
		vo.setEnableFlag(null);
		vo.setCompanyId(1L);
		PageR<ActMaintenanceSettingVO> pageR = actMaintenanceManager.findPageList(vo);
		if(pageR != null){
			List<ActMaintenanceSettingVO> list = pageR.getList();
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ActMaintenanceSettingVO dto = new ActMaintenanceSettingVO();
		dto.setCompanyId(1L);
		List<ActMaintenanceSettingWrapper> list = actMaintenanceManager.findList(dto) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ActMaintenanceSetting task = actMaintenanceManager.findById(1);
		System.out.println(task != null ? task.getActivityPeriods() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ActMaintenanceSettingVO dto = new ActMaintenanceSettingVO();
		dto.setCompanyId(1L);
		dto.setCreateDate(new Date());
		dto.setActivityPeriods("A00000001");
		dto.setStartTime(new Date());
		dto.setEndTime(new Date(new Date().getTime()+10000000));
		actMaintenanceManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testUpdate() throws Exception {
		ActMaintenanceSettingVO dto = new ActMaintenanceSettingVO();
		dto.setId(1);
		dto.setCompanyId(1L);
		dto.setCreateDate(new Date());
		dto.setActivityPeriods("A00000001");
		dto.setStartTime(new Date());
		dto.setEndTime(new Date(new Date().getTime()+20000000));
		actMaintenanceManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		actMaintenanceManager.deleteByIdArray("1");
	}
	
}
