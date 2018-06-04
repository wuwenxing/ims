package com.gwghk.ims.common.inf.mis.order;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

public interface MisOrderDubboService {
	/**
	 * 查询
	 * @param pageSearchVo
	 * @return
	 */
	MisRespResult<PageR<ImsOrderVO>> findPageList(ImsOrderVO pageSearchVo);

	/**
	 * 获取订单详情
	 * @param findVo
	 * @return
	 */
	MisRespResult<ImsOrderVO> findById(ImsOrderVO findVo);
	/**
	 * 更新快递
	 * @param imsOrderVo
	 * @return
	 */
	MisRespResult<Void> updateExpress(ImsOrderVO imsOrderVo);
	/**
	 * 批量更新快递
	 * @param ids
	 * @param imsOrderVo
	 * @return
	 */
	MisRespResult<Void> batchUpdateExpress(String ids, ImsOrderVO imsOrderVo);
	/**
	 * 批量更新发货状态
	 * @param ids
	 * @param imsOrderVo
	 * @return
	 */
	MisRespResult<Void> batchUpdateDeliveryStatus(String ids, ImsOrderVO imsOrderVo);
	/**
	 * 更新收货地址
	 * @param id
	 * @param address
	 * @param companyId
	 * @return
	 */
	MisRespResult<Void> updateAddress(String[] ids, String address, @Company Long companyId);
}
