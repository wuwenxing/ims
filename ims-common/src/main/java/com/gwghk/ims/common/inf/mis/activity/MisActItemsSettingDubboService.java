package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;

public interface MisActItemsSettingDubboService {
	
	/**
	 * 根据物品编号获得物品
	 * @param itemNumber
	 * @return
	 */
	
	MisRespResult<ActItemsSettingVO> findByItemNumber(String itemNumber,Boolean isloadActUseFlag,@Company Long companyId);
	
	/**
	 * 根据物品名称获得物品
	 * @param itemName
	 * @return
	 */
	MisRespResult<List<ActItemsSettingVO>> findByItemName(String itemName,@Company Long companyId);
	
	/**
	 * 根据物品id获得物品
	 * @param itemNumber
	 * @return
	 */
	MisRespResult<ActItemsSettingVO> findById(Long id,@Company Long companyId);
	
	/**
	 * 分页查询物品
	 * @param dto
	 * @return
	 */
	MisRespResult<PageR<ActItemsSettingVO>> findPageList(ActItemsSettingVO dto,@Company Long companyId);
	
	/**
	 * 加载物品集合
	 * @param dto
	 * @return
	 */
	MisRespResult<List<ActItemsSettingVO>> findList(ActItemsSettingVO dto,@Company Long companyId);
	
	/**
	 * 保存或更新物品
	 * @param dto
	 * @return
	 */
	MisRespResult<Void> saveOrUpdate(ActItemsSettingVO dto,@Company Long companyId);
 
	/**
	 * 删除物品
	 * @param idArray
	 * @return
	 */
	MisRespResult<Void> deleteByIdArray(String idArray,@Company Long companyId);
	
	/**
	 * 获得系统默认的物品编号
	 * @param idArray
	 * @param companyId
	 * @return
	 */
	MisRespResult<String> getNewItemNumber(@Company Long companyId);
}
