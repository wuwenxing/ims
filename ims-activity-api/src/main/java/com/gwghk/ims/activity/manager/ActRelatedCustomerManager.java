package com.gwghk.ims.activity.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActRelatedCustomer;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.inf.ActRelatedCustomerDao;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.vo.activity.ActSettingVO;

/**
 * 
 * 摘要：真实与模拟账号关系
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月11日
 */
@Component
@Transactional
public class ActRelatedCustomerManager {

	@Autowired
	private ActRelatedCustomerDao actRelatedCustomerDao ;

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public ActRelatedCustomer findByCustomerNumber(String customerNumber) {
		Map<String,Object> map = new HashMap<>() ;
		map.put("customerNumber", customerNumber) ;
		return actRelatedCustomerDao.findObjectByMap(map) ;
	}
	
	/**
	 * 查询绑定关系
	 * @param customerNumber
	 * @param env
	 * @return
	 */
	public ActRelatedCustomer findByCustomerNumber(String customerNumber,String env) {
		
		Map<String,Object> map = new HashMap<>() ;
		
		if(ActEnvEnum.REAL.getValue().equals(env)) {
			map.put("customerNumber", customerNumber) ;
		}else if(ActEnvEnum.DEMO.getValue().equals(env)) {
			map.put("demoCustomerNumber", customerNumber) ;
		}else {
			return null;
		}
		
		return actRelatedCustomerDao.findObjectByMap(map) ;
	}
	
}
