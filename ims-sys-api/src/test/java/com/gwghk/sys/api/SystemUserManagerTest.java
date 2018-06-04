package com.gwghk.sys.api;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.sys.api.dao.entity.SystemUser;
import com.gwghk.sys.api.manager.SystemUserManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：系统-用户处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemUserManagerTest extends BaseTest{

	@Autowired
	private SystemUserManager systemUserManager;
	
	@Test
	public void testFindPageList(){
		SystemUserVO user = new SystemUserVO();
		user.setPage(1);
		PageR<SystemUserVO> pageR = systemUserManager.findPageList(user);
		if(pageR != null){
			List<SystemUserVO> userList = pageR.getList();
			System.err.println(userList.size());
			System.out.println(JsonUtil.obj2Str(userList));
		}
	}
	
	@Test
	public void testFindById(){
		SystemUser user = systemUserManager.findById(21L);
		System.out.println(user != null ? user.getUserNo() : null);
	}
	
	@Test
	public void testFindList(){
		SystemUser systemUser = systemUserManager.findByUserNo("juliapm");
		System.out.println(systemUser.getUserId());
		System.out.println(systemUser != null ? systemUser.getUserName() : null);
	}
	
	@Test
	public void testInsert() throws Exception {
		SystemUserVO dto = new SystemUserVO();
		dto.setRoleId(1L);
		dto.setUserNo("1002");
		dto.setUserName("王五");
		dto.setPassword("1111");
		dto.setCompanyId(1L);
		systemUserManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testUpdate() throws Exception {
		SystemUserVO dto = new SystemUserVO();
		dto.setUserId(66L);
		dto.setUserName("李四");
		systemUserManager.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		systemUserManager.deleteByIdArray("64,65");
	}
	
}
