package com.gwghk.sys.api;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.system.SystemLogVO;
import com.gwghk.sys.api.manager.SystemLogManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：系统-用户处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemLogManagerTest extends BaseTest{

	@Autowired
	private SystemLogManager systemLogManager;
	
	@Test
	public void testFindPageList(){
		SystemLogVO log = new SystemLogVO();
		log.setPage(1);
		PageR<SystemLogVO> pageR = systemLogManager.findPageList(log);
		if(pageR != null){
			List<SystemLogVO> logList = pageR.getList();
			System.err.println(logList.size());
			System.out.println(JsonUtil.obj2Str(logList));
		}
	}
	
	@Test
	public void testInsert() throws Exception {
		SystemLogVO dto = new SystemLogVO();
		dto.setId(1L);
		dto.setUserNo("1");
		dto.setCompanyId(1L);
		systemLogManager.save(dto);
	}
}
