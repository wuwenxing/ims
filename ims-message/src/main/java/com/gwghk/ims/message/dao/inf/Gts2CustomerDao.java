package com.gwghk.ims.message.dao.inf;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.Gts2Customer;

/**
 * 
 * @ClassName: Gts2CustomerMapper
 * @Description: GTS2账户基础资料数据
 * @author lawrence
 * @date 2017年8月14日
 *
 */
public interface Gts2CustomerDao extends BaseDao<Gts2Customer>{
		
	Gts2Customer getByGts2CustomerId(Gts2Customer gts2Customer);
}