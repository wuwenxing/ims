package com.gwghk.ims.common.inf.external.mall;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.mall.MallItemDTO;

/**
 * 查询商品信息
 * @author jackson.tang
 *
 */
public interface MallItemsDubboService {
	/**
	 * 分页方式 获取商品列表
	 * @param pageSearchDTO
	 * @return
	 */
	MisRespResult<PageR<MallItemDTO>> findPageList(MallItemDTO pageSearchDTO);
	/**
	 * 查询商品详细信息
	 * @param id
	 * @param companyId
	 * @return
	 */
	MisRespResult<MallItemDTO> findById(Long id, @Company Long companyId);

}
