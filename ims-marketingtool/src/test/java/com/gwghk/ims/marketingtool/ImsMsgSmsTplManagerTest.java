package com.gwghk.ims.marketingtool;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsTplVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsTpl;
import com.gwghk.ims.marketingtool.manager.ImsMsgSmsTplManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ImsMsgSmsTplManagerTest extends BaseTest{

	@Autowired
	private ImsMsgSmsTplManager imsMsgSmsTplManager ;
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		ImsMsgSmsTplVO vo = new ImsMsgSmsTplVO();
		vo.setPage(1);
		vo.setRows(2);
		vo.setCompanyId(1L);
		vo.setTplContent("测试短信");
		Map<String,Object> pageR = imsMsgSmsTplManager.findPageList(vo);
		if(pageR != null){
			List<ImsMsgSmsTplVO> list = (List<ImsMsgSmsTplVO>)pageR.get("list");
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ImsMsgSmsTplVO vo = new ImsMsgSmsTplVO();
		vo.setCompanyId(1L);
		List<ImsMsgSmsTpl> list = imsMsgSmsTplManager.findList(vo) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ImsMsgSmsTpl task = imsMsgSmsTplManager.findById(1L);
		System.out.println(task != null ? task.toString() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ImsMsgSmsTplVO vo = new ImsMsgSmsTplVO();
		vo.setCompanyId(1L);
		vo.setCreateDate(new Date());
		vo.setCode("A0001");
		vo.setTplContent("这是个测试短信消息模板");
		vo.setTplName("短信消息模板");
		imsMsgSmsTplManager.saveOrUpdate(vo);
	}
	
	@Test
	public void testDelete() {
		imsMsgSmsTplManager.deleteByIds("1");
	}
}
