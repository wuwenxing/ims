package com.gwghk.ims.marketingtool;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgBind;
import com.gwghk.ims.marketingtool.manager.ImsMsgBindManager;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动黑名单Test
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class ImsMsgBindManagerTest extends BaseTest{

	@Autowired
	private ImsMsgBindManager imsMsgBindManager ;
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		ImsMsgBindVO vo = new ImsMsgBindVO();
		vo.setPage(1);
		vo.setRows(2);
		vo.setCompanyId(1L);
		Map<String,Object> pageR = imsMsgBindManager.findPageList(vo);
		if(pageR != null){
			List<ImsMsgBindVO> list = (List<ImsMsgBindVO>)pageR.get("list");
			System.err.println(list.size());
			System.out.println(JsonUtil.obj2Str(list));
		}
	}
	
	@Test
	public void testFindList(){
		ImsMsgBindVO vo = new ImsMsgBindVO();
		vo.setCompanyId(1L);
		List<ImsMsgBind> list = imsMsgBindManager.findList(vo) ;
		System.err.println(list.size());
		System.out.println(JsonUtil.obj2Str(list));
	}
	
	@Test
	public void testFindById(){
		ImsMsgBind task = imsMsgBindManager.findById(1L);
		System.out.println(task != null ? task.toString() : null);
	}
	
	@Test
	public void testFindByBindCode(){
		ImsMsgBind task = imsMsgBindManager.findByBindCode("A002") ;
		System.out.println(task != null ? task.toString() : null);
	}
	
	
	@Test
	public void testSave() throws Exception {
		ImsMsgBindVO vo = new ImsMsgBindVO();
		vo.setCompanyId(1L);
		vo.setCreateDate(new Date());
		vo.setAppCode("A001");
		vo.setSmsCode("B001");
		vo.setBindType("item");
		imsMsgBindManager.saveOrUpdate(vo);
	}
	
	@Test
	public void testDelete() {
		imsMsgBindManager.deleteByIds("1");
	}
}
