package com.gwghk.ims.common.inf.mis.mall;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.mall.ImsMallItemVO;

public interface MisMallItemsDubboService {
	/**
	 * 分页查询
	 * @param pageSearchVo
	 * @return
	 */
	MisRespResult<PageR<ImsMallItemVO>> findPageList(ImsMallItemVO pageSearchVo);
	/**
	 * 根据ID查询物品信息
	 * @param id
	 * @param companyId
	 * @return
	 */
	MisRespResult<ImsMallItemVO> findById(Long id, @Company Long companyId);
	/**
	 * 保存物品信息(包含更新与新增)
	 * @param imsMallItemsVo
	 * @return
	 */
	MisRespResult<Void> save(ImsMallItemVO imsMallItemsVo);
	/**
	 * 批量删除物品
	 * @param idArr
	 * @param companyId
	 * @return
	 */
	MisRespResult<Void> batchDel(String[] idArr,@Company Long companyId);
	/**
	 * 批量软删除
	 * @param idArr
	 * @param companyId
	 * @return
	 */
	MisRespResult<Void> batchSoftDel(String[] idArr,@Company Long companyId);

}
