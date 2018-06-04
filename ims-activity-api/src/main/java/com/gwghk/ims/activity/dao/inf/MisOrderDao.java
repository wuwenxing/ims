package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.ImsOrder;
import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

public interface MisOrderDao extends BaseDao<ImsOrder> {
	/**
	 * 分页查找
	 * @param reqDto
	 * @return
	 */
    List<ImsOrder> listByPage(ImsOrderVO reqDto);
}