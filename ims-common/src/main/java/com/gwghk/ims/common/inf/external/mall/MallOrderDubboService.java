package com.gwghk.ims.common.inf.external.mall;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.mall.MallOrderDTO;

/**
 * 查询订单信息
 * @author jackson.tang
 *
 */
public interface MallOrderDubboService {
	/**
	 * 分页形式 获取订单列表
	 * @param pageSearchDTO
	 * @return
	 */
	MisRespResult<PageR<MallOrderDTO>> findPageList(MallOrderDTO pageSearchDTO);
	/**
	 * 查询订单详情
	 * @param findDTO
	 * @return
	 */
	MisRespResult<MallOrderDTO> findById(MallOrderDTO findDTO);

}
