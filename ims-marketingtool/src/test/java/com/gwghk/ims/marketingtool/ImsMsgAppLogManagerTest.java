package com.gwghk.ims.marketingtool;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;
import com.gwghk.ims.marketingtool.manager.ImsMsgAppLogManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ImsMsgAppLogManagerTest extends BaseTest{

	@Autowired
	private ImsMsgAppLogManager imsMsgAppLogManager ;
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		ImsMsgAppLogVO vo = new ImsMsgAppLogVO();
		vo.setPage(1);
		vo.setRows(2);
		vo.setCompanyId(1L);
		Map<String,Object> pageR = imsMsgAppLogManager.findPageList(vo);
		if(pageR != null){
			List<ImsMsgAppLogVO> list = (List<ImsMsgAppLogVO>)pageR.get("list");
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ImsMsgAppLogVO vo = new ImsMsgAppLogVO();
		vo.setCompanyId(1L);
		List<ImsMsgAppLog> list = imsMsgAppLogManager.findList(vo) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ImsMsgAppLog task = imsMsgAppLogManager.findById(1);
		System.out.println(task != null ? task.toString() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ImsMsgAppLog vo = new ImsMsgAppLog();
		vo.setCompanyId(1L);
		vo.setCreateDate(new Date());
		vo.setCode("A0005");
		vo.setContent("测试推送一条app消息");
		vo.setTitle("测试app消息");
		imsMsgAppLogManager.saveOrUpdate(vo);
	}
	
	@Test
	public void testDelete() {
		imsMsgAppLogManager.deleteByIds("17");
	}
}
