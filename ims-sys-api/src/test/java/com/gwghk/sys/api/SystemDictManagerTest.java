package com.gwghk.sys.api;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.sys.api.dao.entity.SystemDict;
import com.gwghk.sys.api.manager.SystemDictManager;

import net.sf.ehcache.CacheManager;

/**
 * 摘要：键值对业务处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemDictManagerTest extends BaseTest{

	@Autowired
	private SystemDictManager systemDictManager;
	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testFindByCode(){
		String code="DeviceType";
		Long companyId = 1L;
		SystemDict dict = systemDictManager.findByDictCode(code, companyId);
		System.out.println(dict);
		
		System.out.println("第二次查询，验证是否是查询数据库");
		SystemDict dict2 = systemDictManager.findByDictCode(code, companyId);
		System.out.println(dict2);
		
		System.out.println("第三次查询，手动清除缓存");
		cacheManager.getCache("dictCache").remove("dictCodeAndCompanyId:" + code + "_" + companyId);
		SystemDict dict3 = systemDictManager.findByDictCode(code, companyId);
		System.out.println(dict3);
		
	}
	
	@Test
	public void testFindByParentCode(){
		String code="DeviceType";
		Long companyId = 1L;
		List<SystemDict> dictList = systemDictManager.findListByParentDictCode(code, companyId);
		dictList.forEach(r -> System.out.println(r));
		
		System.out.println("第二次查询，验证是否是查询数据库");
		List<SystemDict> dictList2 = systemDictManager.findListByParentDictCode(code, companyId);
		dictList2.forEach(r -> System.out.println(r));
		
		System.out.println("第三次查询，手动清除缓存");
		cacheManager.getCache("dictCache").remove("parentDictCodeAndCompanyId:" + code + "_" + companyId);
		List<SystemDict> dictList3 = systemDictManager.findListByParentDictCode(code, companyId);
		dictList3.forEach(r -> System.out.println(r));
		
	}
}
