package com.gwghk.ims.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.base.dao.entity.KeyVal;
import com.gwghk.ims.base.manager.KeyValManager;
import com.gwghk.ims.common.vo.base.KeyValVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

import net.sf.ehcache.CacheManager;

/**
 * 摘要：键值对业务处理测试用例
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class KeyValManagerTest extends BaseTest{

	@Autowired
	private KeyValManager keyValManager;
	@Autowired
	private CacheManager cacheManager;
	
	@Test
	public void testFindById(){
		KeyVal  keyVal = keyValManager.findById(314L);
		System.out.println(keyVal != null ? keyVal.getDataKey() : null);
		System.err.println(">>>>>>>>>>testFindById");
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindPageList(){
		KeyValVO  keyValVO = new KeyValVO();
		keyValVO.setPage(1);
		keyValVO.setRows(1);
		keyValVO.setCompanyId(1L);
		Map<String,Object> pageR = keyValManager.findPageList(keyValVO,1L);
		if(pageR != null){
			List<KeyValVO> logList = (List<KeyValVO>)pageR.get("list");
			System.err.println(logList.size());
			System.out.println(JsonUtil.obj2Str(logList));
		}
		System.err.println(">>>>>>>>>>testFindPageList");
	}
	
	@Test
	public void testSave(){
		KeyValVO  keyValVO = new KeyValVO();
		keyValVO.setDataKey("9656556");
		keyValVO.setDataVal("yuyuyu2");
		keyValVO.setTag("rruru");
		keyValVO.setCreateDate(new Date());
		keyValVO.setUpdateDate(new Date());
		keyValVO.setEnableFlag("Y");
		keyValVO.setDeleteFlag("N");
		keyValVO.setCompanyId(1L);
		keyValManager.saveOrUpdate(keyValVO,1L);
		System.err.println(">>>>>>>>>>testSave");
	}
	
	@Test
	public void testUpdate(){
		KeyValVO  keyValVO = new KeyValVO();
		keyValVO.setId(420L);
		keyValVO.setDataKey("9656556");
		keyValVO.setTag("3333");
		keyValVO.setDeleteFlag("N");
		keyValManager.saveOrUpdate(keyValVO,1L);
		System.err.println(">>>>>>>>>>testUpdate");
	}
	
	@Test
	public void testDeleteByIdArray(){
		int num = keyValManager.deleteByIds("418,419");
		System.err.println(">>>>>>>>>>testDeleteByIdArray:"+num);
	}
	
	@Test
	public void testFindByKey(){
		String key="favorable_site_url";
		Long companyId = 1L;
		KeyVal keyVal = keyValManager.findByKey(key, companyId);
		System.out.println(keyVal);
		
		System.out.println("第二次查询，验证是否是查询数据库");
		KeyVal keyVal2 = keyValManager.findByKey(key, companyId);
		System.out.println(keyVal2);
		
		System.out.println("第三次查询，手动清除缓存");
		cacheManager.getCache("keyValCache").remove("keyAndCompanyId:" + key + "_" + companyId);
		KeyVal keyVal3 = keyValManager.findByKey(key, companyId);
		System.out.println(keyVal3);
		
	}
}
