package com.gwghk.ims.base;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.base.dao.entity.ImsBlackList;
import com.gwghk.ims.base.manager.ImsBlackListManager;
import com.gwghk.ims.common.vo.base.ImsBlackListVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：全局黑名单处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月31日
 */
public class ImsBlackListManagerTest extends BaseTest{
	
	@Autowired
	private ImsBlackListManager imsBlackListManager;
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		ImsBlackListVO  imsBlackListVO = new ImsBlackListVO();
		imsBlackListVO.setPage(1);
		imsBlackListVO.setRows(2);
		imsBlackListVO.setAccountNo("1003");
		imsBlackListVO.setCompanyId(1L);
		imsBlackListVO.setSort("updateDate");
		Map<String,Object> pageR = imsBlackListManager.findPageList(imsBlackListVO,1L);
		if(pageR != null){
			List<ImsBlackListVO> logList = (List<ImsBlackListVO>)pageR.get("list");
			System.err.println(logList.size());
			System.out.println(JsonUtil.obj2Str(logList));
		}
		System.err.println(">>>>>>>>>>testFindPageList");
	}
	
	@Test
	public void testFindListByMap(){
		List<ImsBlackList> blackList = imsBlackListManager.findListByMap(new ImsBlackListVO("1003",1L));
		System.out.println(blackList);
		System.err.println(">>>>>>>>>>testFindListByMap");
	}
	
	@Test
	public void testSaveBatch(){
		Set<String> blackSet = new HashSet<>();
		blackSet.add("1011");
		blackSet.add("1012");
		int sum = imsBlackListManager.saveBatch(blackSet,"127.0.0.1",1L);
		System.err.println(">>>>>>>>>>testSaveBatch : "+sum);
	}
}
