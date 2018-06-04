package com.gwghk.ims.marketingtool;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsLogVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsLog;
import com.gwghk.ims.marketingtool.manager.ImsMsgSmsLogManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ImsMsgSmsLogManagerTest extends BaseTest{

	@Autowired
	private ImsMsgSmsLogManager imsMsgSmsLogManager ;
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		ImsMsgSmsLogVO vo = new ImsMsgSmsLogVO();
		vo.setPage(1);
		vo.setRows(2);
		vo.setCompanyId(1L);
		Map<String,Object> pageR = imsMsgSmsLogManager.findPageList(vo);
		if(pageR != null){
			List<ImsMsgSmsLogVO> list = (List<ImsMsgSmsLogVO>)pageR.get("list");
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ImsMsgSmsLogVO vo = new ImsMsgSmsLogVO();
		vo.setCompanyId(1L);
		List<ImsMsgSmsLog> list = imsMsgSmsLogManager.findList(vo) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ImsMsgSmsLog task = imsMsgSmsLogManager.findById(1L);
		System.out.println(task != null ? task.toString() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ImsMsgSmsLog vo = new ImsMsgSmsLog();
		vo.setId(1L);
		vo.setCompanyId(1L);
		vo.setCreateDate(new Date());
		vo.setCode("A0001");
		vo.setContent("测试推送一条app消息2");
		vo.setMobile("13800138000");
		imsMsgSmsLogManager.saveOrUpdate(vo);
	}
	
	@Test
	public void testDelete() {
		imsMsgSmsLogManager.deleteByIds("1");
	}
}
