package com.gwghk.ims.message.dao.inf;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.ActRelatedCustomer;

/**
 * 
 * @ClassName: ActRelatedCustomerMapper
 * @Description: 真实模拟数据关系
 * @author lawrence
 * @date 2018年1月9日
 *
 */
public interface ActRelatedCustomerDao extends BaseDao<ActRelatedCustomer> {

	ActRelatedCustomer getById(ActRelatedCustomer actRelatedCustomer);
}