package com.gwghk.ims.activity.dao.inf;

import com.gwghk.ims.activity.dao.entity.ActProduct;
import com.gwghk.ims.common.common.BaseDao;

public interface ActProductDao extends BaseDao<ActProduct>{
	ActProduct findByProductCode(String productCode, Long companyId);
}