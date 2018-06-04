package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActCouponInfoDTO;

public interface ActCouponInfoDubboService {

	MisRespResult<PageR<ActCouponInfoDTO>> findPageList(ActCouponInfoDTO dto);

	MisRespResult<List<ActCouponInfoDTO>> findList(ActCouponInfoDTO dto);

	MisRespResult<ActCouponInfoDTO> findById(Long id);

	MisRespResult<Void> saveOrUpdate(ActCouponInfoDTO dto);

	MisRespResult<Void> deleteByIdArray(String idArray);

}
