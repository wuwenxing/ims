package com.gwghk.ims.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import com.gwghk.ims.activity.manager.ActTaskCustomSettingManager;
import com.gwghk.ims.common.vo.activity.ActTaskCustomSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：系统-用户处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ActTaskCustomSettingManagerTest extends BaseTest{

	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager;
	
	@Test
	public void testFindPageList(){
		ActTaskCustomSettingVO user = new ActTaskCustomSettingVO();
		user.setPage(1);
		user.setEnableFlag(null);
		user.setCompanyId(1L);
		Map<String,Object> pageR = actTaskCustomSettingManager.findPageList(user,1L);
		if(pageR != null){
			List<ActTaskCustomSettingVO> list = (List<ActTaskCustomSettingVO>)pageR.get("list");
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ActTaskCustomSettingVO dto = new ActTaskCustomSettingVO();
		dto.setCompanyId(3L);
		List<ActTaskCustomSetting> list = actTaskCustomSettingManager.findList(dto) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ActTaskCustomSetting task = actTaskCustomSettingManager.findById(1L);
		System.out.println(task != null ? task.getTaskName() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ActTaskCustomSettingVO dto = new ActTaskCustomSettingVO();
		dto.setCompanyId(1L);
		dto.setCreateDate(new Date());
		dto.setTaskCode("ABC");
		dto.setRuleCode("AABBCC");
		dto.setTaskName("自定义任务ABC");
		dto.setTaskDesc("我是自定义任务ABC");
		dto.setTaskType(1);
		dto.setCompanyId(1L);
		actTaskCustomSettingManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testUpdate() throws Exception {
		ActTaskCustomSettingVO dto = new ActTaskCustomSettingVO();
		dto.setId(1L);
		dto.setCompanyId(1L);
		dto.setCreateDate(new Date());
		dto.setTaskCode("ABC2");
		dto.setRuleCode("AABBCC2");
		dto.setTaskName("自定义任务ABC2");
		dto.setTaskDesc("我是自定义任务ABC2");
		dto.setTaskType(1);
		dto.setCompanyId(1L);
		actTaskCustomSettingManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		actTaskCustomSettingManager.deleteByIds("1");
	}
}
