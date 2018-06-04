package com.gwghk.ims.activity.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.activity.dao.entity.Gts2symbolDemoRealWrapper;
import com.gwghk.ims.activity.dao.inf.VGts2symbolDemoRealDao;
import com.gwghk.ims.common.vo.activity.Gts2symbolDemoRealVO;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月28日  
* @version 1.0  
*/
@Component
public class VGts2symbolDemoRealManager {
	
	 
	
	@Autowired
	private VGts2symbolDemoRealDao vGts2symbolDemoRealDao;

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<Gts2symbolDemoRealWrapper> findList(Gts2symbolDemoRealVO vo) {
		 
		return vGts2symbolDemoRealDao.getGts2symbolDemoReals(vo);
	}

}
