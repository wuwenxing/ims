package com.gwghk.ims.marketingtool;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppTplVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppTpl;
import com.gwghk.ims.marketingtool.manager.ImsMsgAppTplManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ImsMsgAppTplManagerTest extends BaseTest{

	@Autowired
	private ImsMsgAppTplManager imsMsgAppTplManager ;
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		ImsMsgAppTplVO vo = new ImsMsgAppTplVO();
		vo.setPage(1);
		vo.setRows(2);
		vo.setEnableFlag(null);
		vo.setCompanyId(1L);
		Map<String,Object> pageR = imsMsgAppTplManager.findPageList(vo);
		if(pageR != null){
			List<ImsMsgAppTplVO> list = (List<ImsMsgAppTplVO>)pageR.get("list");
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ImsMsgAppTplVO vo = new ImsMsgAppTplVO();
		vo.setCompanyId(1L);
		List<ImsMsgAppTpl> list = imsMsgAppTplManager.findList(vo) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ImsMsgAppTpl task = imsMsgAppTplManager.findById(1L);
		System.out.println(task != null ? task.toString() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ImsMsgAppTpl vo = new ImsMsgAppTpl();
		vo.setCompanyId(1L);
		vo.setCreateDate(new Date());
		vo.setCode("A0001");
		vo.setTplContent("这是个测试app消息模板");
		vo.setTplName("app消息模板");
		imsMsgAppTplManager.saveOrUpdate(vo);
	}
	
	@Test
	public void testDelete() {
		imsMsgAppTplManager.deleteByIds("1");
	}
	
}
