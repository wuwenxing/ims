package com.gwghk.ims.message.dao.inf;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.Gts2RelatedCustomer;

/**
 * 
* @ClassName: Gts2RelatedCustomerMapper
* @Description: 真实模拟数据关系
* @author lawrence
* @date 2018年1月9日
*
 */
public interface Gts2RelatedCustomerDao extends BaseDao<Gts2RelatedCustomer>{
	int insert(Gts2RelatedCustomer gts2RelatedCustomer);
	
	int update(Gts2RelatedCustomer gts2RelatedCustomer);
	
	Gts2RelatedCustomer getById(Gts2RelatedCustomer gts2RelatedCustomer);
}