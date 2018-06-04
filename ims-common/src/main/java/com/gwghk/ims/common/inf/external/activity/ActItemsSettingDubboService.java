package com.gwghk.ims.common.inf.external.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActItemsSettingDTO;

/**
 * 查询物品信息
 * @author jackson.tang
 *
 */
public interface ActItemsSettingDubboService {
	/**
	 * 分页方式获取物品列表
	 * @param dto
	 * @return
	 */
	MisRespResult<PageR<ActItemsSettingDTO>> findPageList(ActItemsSettingDTO dto);
	/**
	 * 获取物品列表
	 * @param dto
	 * @return
	 */
	MisRespResult<List<ActItemsSettingDTO>> findList(ActItemsSettingDTO dto);
	/**
	 * 获取物品信息
	 * @param itemNumber 编号
	 * @param isloadActUseFlag 是否查询本物品是否在使用中
	 * @param companyId
	 * @return
	 */
	MisRespResult<ActItemsSettingDTO> findByItemNumber(String itemNumber, boolean isloadActUseFlag, @Company Long companyId);

}
